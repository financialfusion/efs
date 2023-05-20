package com.ffusion.services.approvals;

import com.ffusion.approvals.ApprovalsException;
import com.ffusion.approvals.IApprovable;
import com.ffusion.approvals.IApprovalsPlugin;
import com.ffusion.approvals.adapter.ApprovalsAdapter;
import com.ffusion.approvals.adapter.IApprovalsAdapter;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.approvals.ApprovalsChainItem;
import com.ffusion.beans.approvals.ApprovalsChainItems;
import com.ffusion.beans.approvals.ApprovalsDecision;
import com.ffusion.beans.approvals.ApprovalsDecisions;
import com.ffusion.beans.approvals.ApprovalsGroup;
import com.ffusion.beans.approvals.ApprovalsGroupMember;
import com.ffusion.beans.approvals.ApprovalsGroupMembers;
import com.ffusion.beans.approvals.ApprovalsGroups;
import com.ffusion.beans.approvals.ApprovalsHistory;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsItemCount;
import com.ffusion.beans.approvals.ApprovalsItemCounts;
import com.ffusion.beans.approvals.ApprovalsItemError;
import com.ffusion.beans.approvals.ApprovalsItemErrors;
import com.ffusion.beans.approvals.ApprovalsItems;
import com.ffusion.beans.approvals.ApprovalsLevel;
import com.ffusion.beans.approvals.ApprovalsLevels;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.business.ServicesPackage;
import com.ffusion.beans.business.ServicesPackages;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.csil.handlers.BankEmployeeAdmin;
import com.ffusion.csil.handlers.UserAdminHandler;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.services.IApprovalsService2;
import com.ffusion.util.IntMap;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;

public class ApprovalsService
  implements IApprovalsService2
{
  public static final String SERVICE_INIT_XML = "approvals.xml";
  private static final String jdField_new = "APPROVALS_PLUGIN_LIST";
  private static final String jdField_do = "APPROVALS_PLUGIN";
  private static final String jdField_case = "ITEM_TYPE";
  private static final String jdField_int = "ITEM_SUBTYPE_LIST";
  private static final String jdField_for = "ITEM_SUBTYPE";
  private static final String jdField_byte = "PLUGIN_CLASS";
  private static final String jdField_try = "PLUGIN_CONFIG";
  private IApprovalsAdapter jdField_if;
  private IntMap a;
  
  public synchronized void initialize(HashMap paramHashMap)
    throws ApprovalsException
  {
    try
    {
      InputStream localInputStream = null;
      localObject1 = null;
      try
      {
        localInputStream = ResourceUtil.getResourceAsStream(this, "approvals.xml");
        localObject1 = Strings.streamToString(localInputStream);
      }
      catch (Exception localException2)
      {
        throw new ApprovalsException(30003, " Unable to load Approvals Service configuration file approvals.xml");
      }
      localObject2 = null;
      try
      {
        localObject2 = new XMLTag(true);
        ((XMLTag)localObject2).build((String)localObject1);
        localObject1 = null;
      }
      catch (Exception localException3)
      {
        throw new ApprovalsException(30003, " An error occurred while parsing the Approvals Service configuration file approvals.xml", localException3);
      }
      this.a = new IntMap();
      DebugLog.log(Level.INFO, "Processing Approvals Service config xml approvals.xml");
      localObject3 = ((XMLTag)localObject2).getContainedTag("APPROVALS_PLUGIN_LIST");
      ArrayList localArrayList1 = (ArrayList)XMLUtil.tagToHashMap((XMLTag)localObject3).get("APPROVALS_PLUGIN");
      if (localArrayList1 != null)
      {
        Iterator localIterator1 = localArrayList1.iterator();
        while (localIterator1.hasNext())
        {
          HashMap localHashMap1 = (HashMap)localIterator1.next();
          int i = Integer.parseInt((String)localHashMap1.get("ITEM_TYPE"));
          if (i != 1)
          {
            DebugLog.log(Level.SEVERE, " An Approvals Service plugin could not be loaded because the specified item type is not supported: " + i);
          }
          else
          {
            String str1 = (String)localHashMap1.get("PLUGIN_CLASS");
            Class localClass = null;
            try
            {
              localClass = Class.forName(str1);
            }
            catch (ClassNotFoundException localClassNotFoundException)
            {
              DebugLog.log(Level.SEVERE, " Unable to load Approvals Service plugin class " + str1);
            }
            continue;
            IApprovalsPlugin localIApprovalsPlugin = null;
            try
            {
              localIApprovalsPlugin = (IApprovalsPlugin)localClass.newInstance();
            }
            catch (ClassCastException localClassCastException)
            {
              DebugLog.log(Level.SEVERE, " The following Approvals Service plugin class could not be loaded as it does not implement IApprovalsPlugin: " + str1);
              continue;
            }
            catch (Exception localException4)
            {
              DebugLog.log(Level.SEVERE, " An error occurred while instantiating Approvals Service plugin class " + str1 + ": " + localException4.getMessage());
            }
            continue;
            HashMap localHashMap2 = null;
            Object localObject4 = localHashMap1.get("PLUGIN_CONFIG");
            if ((localObject4 instanceof HashMap)) {
              localHashMap2 = (HashMap)localObject4;
            }
            DebugLog.log(Level.INFO, "Initializing Approvals Service plugin " + str1);
            try
            {
              localIApprovalsPlugin.initialize(paramHashMap, localHashMap2);
            }
            catch (Throwable localThrowable2)
            {
              DebugLog.log(Level.SEVERE, " An error occurred while initializing Approvals Service plugin " + str1 + ": " + localThrowable2.getMessage());
            }
            continue;
            ArrayList localArrayList2 = (ArrayList)((HashMap)localHashMap1.get("ITEM_SUBTYPE_LIST")).get("ITEM_SUBTYPE");
            Iterator localIterator2 = localArrayList2.iterator();
            while (localIterator2.hasNext())
            {
              String str2 = (String)localIterator2.next();
              int j = Integer.parseInt(str2);
              if (this.a.containsKey(j)) {
                DebugLog.log(Level.SEVERE, " An Approvals Service plugin mapping for the following item subtype already exists: " + str2);
              } else {
                this.a.put(j, localIApprovalsPlugin);
              }
            }
          }
        }
      }
      this.jdField_if = new ApprovalsAdapter();
      this.jdField_if.initialize(paramHashMap);
      DebugLog.log(Level.INFO, "Approvals service initialized");
    }
    catch (ApprovalsException localApprovalsException)
    {
      this.jdField_if = null;
      throw localApprovalsException;
    }
    catch (Exception localException1)
    {
      this.jdField_if = null;
      localObject1 = new StringWriter();
      localObject2 = new PrintWriter((Writer)localObject1);
      localException1.printStackTrace((PrintWriter)localObject2);
      localObject3 = "An error occurred while initializing the Approvals Service: " + ((StringWriter)localObject1).toString();
      DebugLog.log(Level.SEVERE, (String)localObject3);
      throw new ApprovalsException(0, (String)localObject3, localException1);
    }
    catch (Throwable localThrowable1)
    {
      this.jdField_if = null;
      Object localObject1 = new StringWriter();
      Object localObject2 = new PrintWriter((Writer)localObject1);
      localThrowable1.printStackTrace((PrintWriter)localObject2);
      Object localObject3 = "An error occurred while initializing the Approvals Service: " + ((StringWriter)localObject1).toString();
      DebugLog.log(Level.SEVERE, (String)localObject3);
      throw new ApprovalsException(0, (String)localObject3, localThrowable1);
    }
  }
  
  public ApprovalsLevels getWorkflowLevels(SecureUser paramSecureUser, int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws ApprovalsException
  {
    return getWorkflowLevels(paramSecureUser, paramInt, 1, paramString1, paramString2, paramHashMap);
  }
  
  public ApprovalsLevels getWorkflowLevels(SecureUser paramSecureUser, int paramInt1, int paramInt2, String paramString1, String paramString2, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "com.ffusion.services.approvals.ApprovalsService.getWorkflowLevels";
    a(str1);
    if (paramInt2 == 2) {
      try
      {
        String str2 = jdField_if(paramSecureUser, paramInt1);
        paramHashMap.put("ApprovalServicesPackageAffiliateBankCurrencyCode", str2);
      }
      catch (CSILException localCSILException)
      {
        throw new ApprovalsException(30151, "Could not obtain the approval flow levels.", localCSILException);
      }
    }
    ApprovalsLevels localApprovalsLevels = this.jdField_if.getWorkflowLevels(paramInt1, paramInt2, paramString1, paramString2, paramHashMap);
    if (paramInt2 == 2) {
      paramHashMap.remove("ApprovalServicesPackageAffiliateBankCurrencyCode");
    }
    return localApprovalsLevels;
  }
  
  public void addWorkflowLevel(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.addWorkflowLevel";
    a(str);
    this.jdField_if.addWorkflowLevel(paramApprovalsLevel, paramHashMap);
  }
  
  public void updateWorkflowLevels(SecureUser paramSecureUser, ApprovalsLevels paramApprovalsLevels, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.updateWorkflowLevels";
    a(str);
    this.jdField_if.updateWorkflowLevels(paramApprovalsLevels, paramHashMap);
  }
  
  public void removeWorkflowLevels(SecureUser paramSecureUser, ApprovalsLevels paramApprovalsLevels, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.removeWorkflowLevels";
    a(str);
    this.jdField_if.removeWorkflowLevels(paramApprovalsLevels, paramHashMap);
  }
  
  public ApprovalsChainItems getWorkflowChainItems(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.getWorkflowChainItems";
    a(str);
    return this.jdField_if.getWorkflowChainItems(paramApprovalsLevel, paramHashMap);
  }
  
  public void setWorkflowChainItems(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, ApprovalsChainItems paramApprovalsChainItems, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "com.ffusion.services.approvals.ApprovalsService.setWorkflowChainItems";
    a(str1);
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      if (!a(paramSecureUser, paramApprovalsChainItems, paramApprovalsLevel.getBusinessID(), paramApprovalsLevel.getObjectType(), paramSecureUser.getUserType() == 1, paramHashMap))
      {
        this.jdField_if.setWorkflowChainItems(paramApprovalsLevel, paramApprovalsChainItems, paramHashMap);
      }
      else
      {
        String str2 = str1 + ": The approval chain could not be set because it contains one or more stall conditions";
        throw new ApprovalsException(30135, str2);
      }
    }
    catch (CSILException localCSILException)
    {
      String str3 = str1 + ": " + 30134;
      DebugLog.throwing(str3, localCSILException);
      throw new ApprovalsException(30134, "Could not modify the approval flow chain for the specified approval level");
    }
  }
  
  public void clearWorkflowChainItems(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.clearWorkflowChainItems";
    a(str);
    this.jdField_if.clearWorkflowChainItems(paramApprovalsLevel, paramHashMap);
  }
  
  public ApprovalsStatuses getPendingItems(int paramInt, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.getPendingItems";
    a(str);
    return this.jdField_if.getPendingItems(paramInt, paramHashMap1, paramHashMap2);
  }
  
  public ApprovalsStatuses getSubmittedItems(int paramInt, String[] paramArrayOfString, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.getSubmittedItems";
    a(str);
    return this.jdField_if.getSubmittedItems(paramInt, paramArrayOfString, paramHashMap1, paramHashMap2);
  }
  
  public int getNumberOfPendingApprovals(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.getNumberOfPendingApprovals";
    a(str);
    int i = 0;
    int j = paramSecureUser.getProfileID();
    int k = paramSecureUser.getUserType();
    int m = paramSecureUser.getEntitlementID();
    ApprovalsItems localApprovalsItems = this.jdField_if.getPendingApprovals(j, k, m, paramHashMap1, paramHashMap2);
    localApprovalsItems = jdField_if(paramSecureUser, localApprovalsItems, str, paramHashMap2);
    if (localApprovalsItems != null) {
      i = localApprovalsItems.size();
    }
    return i;
  }
  
  public ApprovalsItemCounts getNumberOfPendingApprovalsDetail(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.getNumberOfPendingApprovalsDetail";
    a(str);
    int i = paramSecureUser.getProfileID();
    int j = paramSecureUser.getUserType();
    int k = paramSecureUser.getEntitlementID();
    ApprovalsItems localApprovalsItems = this.jdField_if.getPendingApprovals(i, j, k, null, paramHashMap);
    return a(paramSecureUser, localApprovalsItems, str, paramHashMap);
  }
  
  public ApprovalsItems getPendingApprovals(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.getPendingApprovals";
    a(str);
    int i = paramSecureUser.getProfileID();
    int j = paramSecureUser.getUserType();
    int k = paramSecureUser.getEntitlementID();
    ApprovalsItems localApprovalsItems = this.jdField_if.getPendingApprovals(i, j, k, paramHashMap1, paramHashMap2);
    return jdField_if(paramSecureUser, localApprovalsItems, str, paramHashMap2);
  }
  
  private ApprovalsItemCounts a(SecureUser paramSecureUser, ApprovalsItems paramApprovalsItems, String paramString, HashMap paramHashMap)
    throws ApprovalsException
  {
    if (paramApprovalsItems == null) {
      return null;
    }
    HashMap localHashMap = new HashMap();
    ApprovalsItemCounts localApprovalsItemCounts = new ApprovalsItemCounts(Locale.getDefault());
    IApprovable localIApprovable = null;
    IApprovalsPlugin localIApprovalsPlugin = null;
    Iterator localIterator = paramApprovalsItems.iterator();
    Object localObject1;
    Object localObject2;
    while (localIterator.hasNext())
    {
      localObject1 = (ApprovalsItem)localIterator.next();
      try
      {
        localIApprovalsPlugin = a(((ApprovalsItem)localObject1).getItemType(), ((ApprovalsItem)localObject1).getItemSubType());
      }
      catch (ApprovalsException localApprovalsException)
      {
        DebugLog.throwing(paramString + ": Unable to load approval plugin for item type " + ((ApprovalsItem)localObject1).getItemType() + ", subtype " + ((ApprovalsItem)localObject1).getItemSubType(), localApprovalsException);
      }
      continue;
      localIApprovable = ((TWTransaction)((ApprovalsItem)localObject1).getItem()).getTransaction();
      boolean bool;
      try
      {
        bool = localIApprovalsPlugin.isUserEntitledToApprove(paramSecureUser, localIApprovable, paramHashMap);
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing(paramString + ": An error occurred while checking approval entitlements", localThrowable);
      }
      continue;
      if ((bool) && (((ApprovalsItem)localObject1).getItemType() == 1))
      {
        localObject2 = new Integer(((ApprovalsItem)localObject1).getItemSubType());
        Integer localInteger2 = (Integer)localHashMap.get(localObject2);
        if (localInteger2 == null) {
          localInteger2 = new Integer(1);
        } else {
          localInteger2 = new Integer(localInteger2.intValue() + 1);
        }
        localHashMap.put(localObject2, localInteger2);
      }
    }
    localIterator = localHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (Integer)localIterator.next();
      Integer localInteger1 = (Integer)localHashMap.get(localObject1);
      localObject2 = localApprovalsItemCounts.add();
      ((ApprovalsItemCount)localObject2).setItemType(1);
      ((ApprovalsItemCount)localObject2).setItemSubType(((Integer)localObject1).intValue());
      ((ApprovalsItemCount)localObject2).setNumItems(localInteger1.intValue());
    }
    return localApprovalsItemCounts;
  }
  
  private ApprovalsItems jdField_if(SecureUser paramSecureUser, ApprovalsItems paramApprovalsItems, String paramString, HashMap paramHashMap)
    throws ApprovalsException
  {
    if (paramApprovalsItems == null) {
      return null;
    }
    IApprovable localIApprovable = null;
    IApprovalsPlugin localIApprovalsPlugin = null;
    Iterator localIterator = paramApprovalsItems.iterator();
    while (localIterator.hasNext())
    {
      ApprovalsItem localApprovalsItem = (ApprovalsItem)localIterator.next();
      try
      {
        localIApprovalsPlugin = a(localApprovalsItem.getItemType(), localApprovalsItem.getItemSubType());
      }
      catch (ApprovalsException localApprovalsException)
      {
        DebugLog.throwing(paramString + ": Unable to load approval plugin for item type " + localApprovalsItem.getItemType() + ", subtype " + localApprovalsItem.getItemSubType(), localApprovalsException);
      }
      continue;
      localIApprovable = ((TWTransaction)localApprovalsItem.getItem()).getTransaction();
      boolean bool;
      try
      {
        bool = localIApprovalsPlugin.isUserEntitledToApprove(paramSecureUser, localIApprovable, paramHashMap);
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing(paramString + ": An error occurred while checking approval entitlements", localThrowable);
      }
      continue;
      if (!bool) {
        localIterator.remove();
      }
    }
    return paramApprovalsItems;
  }
  
  public ApprovalsItems getPendingApprovalsForBank(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.getPendingApprovalsForBank";
    a(str);
    int i = paramSecureUser.getProfileID();
    int j = paramSecureUser.getUserType();
    int k = paramSecureUser.getEntitlementID();
    int m = paramSecureUser.getAffiliateIDValue();
    ApprovalsItems localApprovalsItems = this.jdField_if.getPendingApprovalsForBank(i, j, k, m, paramHashMap);
    return jdField_if(paramSecureUser, localApprovalsItems, str, paramHashMap);
  }
  
  public ApprovalsItems getConsumerPendingApprovalsForBank(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.getConsumerPendingApprovalsForBank";
    a(str);
    ApprovalsItems localApprovalsItems = this.jdField_if.getConsumerPendingApprovalsForBank(paramSecureUser, paramHashMap);
    return jdField_if(paramSecureUser, localApprovalsItems, str, paramHashMap);
  }
  
  public ApprovalsItem createApprovalItem(SecureUser paramSecureUser, int paramInt, IApprovable paramIApprovable, String[] paramArrayOfString1, String[] paramArrayOfString2, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "com.ffusion.services.approvals.ApprovalsService.createApprovalItem";
    a(str1);
    int i = paramSecureUser.getBusinessID();
    int j = paramSecureUser.getProfileID();
    String str2 = null;
    String str3 = null;
    if (i != 0)
    {
      str2 = paramSecureUser.getBusinessName();
      str3 = paramSecureUser.getBusinessCIF();
    }
    Object localObject = new User();
    try
    {
      if (i == 0) {
        localObject = CustomerAdapter.getUserById(j);
      } else {
        localObject = Util.getBusinessEmployeeFromProfileID(paramSecureUser, j, paramHashMap);
      }
    }
    catch (Exception localException1)
    {
      throw new ApprovalsException(30112, "Could not submit the item for approval");
    }
    int k = -1;
    try
    {
      EntitlementGroupMember localEntitlementGroupMember = com.ffusion.csil.core.EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
      localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
      EntitlementGroup localEntitlementGroup;
      for (int m = localEntitlementGroupMember.getEntitlementGroupId(); m != 0; m = localEntitlementGroup.getParentId())
      {
        localEntitlementGroup = Entitlements.getEntitlementGroup(m);
        if (localEntitlementGroup == null) {
          break;
        }
        if (localEntitlementGroup.getEntGroupType().equals("ServicesPackage"))
        {
          k = m;
          break;
        }
      }
    }
    catch (Exception localException2)
    {
      throw new ApprovalsException(30153, "Could not submit the item for approval");
    }
    if (k == -1) {
      throw new ApprovalsException(30153, "Could not submit the item for approval");
    }
    try
    {
      String str4 = jdField_if(paramSecureUser, k);
      paramHashMap.put("ApprovalServicesPackageAffiliateBankCurrencyCode", str4);
    }
    catch (CSILException localCSILException)
    {
      throw new ApprovalsException(30153, "Could not submit the item for approval", localCSILException);
    }
    paramHashMap.put("ApprovalServicesPackageId", new Integer(k));
    String str5 = ((User)localObject).getFullNameWithLoginId();
    if (str5.length() == 0) {
      throw new ApprovalsException(30112, "Could not submit the item for approval");
    }
    ApprovalsItem localApprovalsItem = this.jdField_if.createApprovalItem(i, j, str2, str3, str5, paramInt, paramIApprovable, paramArrayOfString1, paramArrayOfString2, paramHashMap);
    paramHashMap.remove("ApprovalServicesPackageId");
    paramHashMap.remove("ApprovalServicesPackageAffiliateBankCurrencyCode");
    return localApprovalsItem;
  }
  
  public ApprovalsItem modifyApprovalItem(ApprovalsItem paramApprovalsItem, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.modifyApprovalItem";
    a(str);
    return this.jdField_if.modifyApprovalItem(paramApprovalsItem, paramHashMap);
  }
  
  public ApprovalsItems submitDecisions(SecureUser paramSecureUser, ApprovalsDecisions paramApprovalsDecisions, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.submitDecisions";
    a(str);
    int i = paramSecureUser.getProfileID();
    int j = paramSecureUser.getUserType();
    int k = paramSecureUser.getEntitlementID();
    if (paramApprovalsDecisions == null) {
      throw new ApprovalsException(30073, "Submitting the approval decisions failed");
    }
    paramApprovalsDecisions = (ApprovalsDecisions)paramApprovalsDecisions.clone();
    ApprovalsItemErrors localApprovalsItemErrors = null;
    int m = 0;
    if (paramHashMap == null)
    {
      paramHashMap = new HashMap();
      m = 1;
      localApprovalsItemErrors = new ApprovalsItemErrors(Locale.getDefault());
    }
    else
    {
      localApprovalsItemErrors = (ApprovalsItemErrors)paramHashMap.remove("ApprovalsItemErrors");
      if (localApprovalsItemErrors == null) {
        localApprovalsItemErrors = new ApprovalsItemErrors(Locale.getDefault());
      }
    }
    try
    {
      ApprovalsItems localApprovalsItems = new ApprovalsItems(Locale.getDefault());
      Iterator localIterator = paramApprovalsDecisions.iterator();
      Object localObject3;
      Object localObject4;
      Object localObject5;
      Object localObject6;
      while (localIterator.hasNext())
      {
        localObject1 = (ApprovalsDecision)localIterator.next();
        ApprovalsItem localApprovalsItem = ((ApprovalsDecision)localObject1).getApprovalItem();
        localObject3 = null;
        localObject4 = null;
        try
        {
          localObject4 = a(localApprovalsItem.getItemType(), localApprovalsItem.getItemSubType());
        }
        catch (ApprovalsException localApprovalsException2)
        {
          ApprovalsItemError localApprovalsItemError1 = localApprovalsItemErrors.add();
          localApprovalsItemError1.setApprovalItem(localApprovalsItem);
          localApprovalsItemError1.setErrorCode(30140);
          DebugLog.throwing(str + ": Unable to load approval plugin for item type " + localApprovalsItem.getItemType() + ", subtype " + localApprovalsItem.getItemSubType(), localApprovalsException2);
        }
        continue;
        localObject3 = ((TWTransaction)localApprovalsItem.getItem()).getTransaction();
        boolean bool;
        try
        {
          bool = ((IApprovalsPlugin)localObject4).isUserEntitledToApprove(paramSecureUser, (IApprovable)localObject3, paramHashMap);
        }
        catch (Throwable localThrowable1)
        {
          localIterator.remove();
          ApprovalsItemError localApprovalsItemError2 = localApprovalsItemErrors.add();
          localApprovalsItemError2.setApprovalItem(localApprovalsItem);
          localApprovalsItemError2.setErrorCode(30141);
          DebugLog.throwing(str + ": An error occurred while checking approval entitlements", localThrowable1);
        }
        continue;
        if (!bool)
        {
          localIterator.remove();
          localObject5 = localApprovalsItemErrors.add();
          ((ApprovalsItemError)localObject5).setApprovalItem(localApprovalsItem);
          ((ApprovalsItemError)localObject5).setErrorCode(30142);
        }
        else if (((ApprovalsDecision)localObject1).getDecision().equals("Approved"))
        {
          localObject5 = null;
          Object localObject7;
          try
          {
            localObject5 = ((IApprovalsPlugin)localObject4).checkApprovalLimits(paramSecureUser, (IApprovable)localObject3, paramHashMap);
          }
          catch (Throwable localThrowable2)
          {
            localIterator.remove();
            localObject7 = localApprovalsItemErrors.add();
            ((ApprovalsItemError)localObject7).setApprovalItem(localApprovalsItem);
            ((ApprovalsItemError)localObject7).setErrorCode(30143);
            DebugLog.throwing(str + ": An error occurred while checking approval limits", localThrowable2);
          }
          continue;
          if ((localObject5 != null) && (((Limits)localObject5).size() > 0))
          {
            localIterator.remove();
            localObject6 = localApprovalsItemErrors.add();
            ((ApprovalsItemError)localObject6).setApprovalItem(localApprovalsItem);
            ((ApprovalsItemError)localObject6).setErrorCode(30144);
            localObject7 = (HashMap)paramHashMap.get("ExceededApprovalLimitsMap");
            if (localObject7 == null)
            {
              localObject7 = new HashMap();
              paramHashMap.put("ExceededApprovalLimitsMap", localObject7);
            }
            ((HashMap)localObject7).put(localObject1, localObject5);
          }
          else {}
        }
        else if (((ApprovalsDecision)localObject1).getDecision().equals("Rejected"))
        {
          localApprovalsItems.add(localApprovalsItem);
        }
      }
      Object localObject1 = null;
      try
      {
        localObject1 = this.jdField_if.submitDecisions(i, j, k, paramApprovalsDecisions, paramHashMap);
      }
      catch (ApprovalsException localApprovalsException1)
      {
        localIterator = paramApprovalsDecisions.iterator();
        while (localIterator.hasNext())
        {
          localObject3 = (ApprovalsDecision)localIterator.next();
          localObject4 = ((ApprovalsDecision)localObject3).getApprovalItem();
          Integer localInteger = (Integer)paramHashMap.get(localObject4);
          if (localInteger != null)
          {
            localObject5 = localApprovalsItemErrors.add();
            ((ApprovalsItemError)localObject5).setApprovalItem((ApprovalsItem)localObject4);
            ((ApprovalsItemError)localObject5).setErrorCode(localInteger.intValue());
            paramHashMap.remove(localObject4);
            if (((ApprovalsDecision)localObject3).getDecision().equals("Approved"))
            {
              localObject6 = null;
              try
              {
                localObject6 = a(((ApprovalsItem)localObject4).getItemType(), ((ApprovalsItem)localObject4).getItemSubType());
              }
              catch (ApprovalsException localApprovalsException3)
              {
                DebugLog.throwing(str + ": Unable to rollback approval limits for failed decision", localApprovalsException3);
              }
              continue;
              try
              {
                ((IApprovalsPlugin)localObject6).rollbackApprovalLimits(paramSecureUser, ((TWTransaction)((ApprovalsItem)localObject4).getItem()).getTransaction(), paramHashMap);
              }
              catch (Throwable localThrowable3)
              {
                DebugLog.throwing(str + ": Unable to rollback approval limits for failed decision", localThrowable3);
              }
            }
          }
        }
        if ((m != 0) && (localApprovalsItemErrors.size() > 0)) {
          throw new ApprovalsException(30145, "");
        }
        localObject3 = null;
        return localObject3;
      }
      localObject1 = jdField_if(paramSecureUser, (ApprovalsItems)localObject1, localApprovalsItemErrors, paramHashMap);
      a(paramSecureUser, localApprovalsItems, localApprovalsItemErrors, paramHashMap);
      if ((m != 0) && (localApprovalsItemErrors.size() > 0)) {
        throw new ApprovalsException(30145, "");
      }
      Object localObject2 = localObject1;
      return localObject2;
    }
    finally
    {
      paramHashMap.put("ApprovalsItemErrors", localApprovalsItemErrors);
    }
  }
  
  public void removeApprovalItem(ApprovalsItem paramApprovalsItem, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.removeApprovalItem";
    a(str);
    this.jdField_if.removeApprovalItem(paramApprovalsItem, paramHashMap);
  }
  
  public void removeBusiness(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.removeBusiness";
    a(str);
    this.jdField_if.removeBusiness(paramInt, paramHashMap);
  }
  
  public void removeObject(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.removeObject";
    a(str);
    this.jdField_if.removeObject(paramInt1, paramInt2, paramHashMap);
  }
  
  public boolean isUserInUse(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.isUserInUse";
    a(str);
    int i = paramSecureUser.getProfileID();
    int j = paramSecureUser.getUserType();
    return this.jdField_if.isUserInUse(i, j, paramHashMap);
  }
  
  public ApprovalsItems removeUser(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.removeUser";
    a(str);
    int i = paramSecureUser.getProfileID();
    int j = paramSecureUser.getUserType();
    ApprovalsItems localApprovalsItems = this.jdField_if.removeUser(i, j, paramHashMap);
    ApprovalsItemErrors localApprovalsItemErrors = new ApprovalsItemErrors(Locale.getDefault());
    localApprovalsItems = a(localApprovalsItems, localApprovalsItemErrors, paramHashMap);
    if (paramHashMap != null) {
      paramHashMap.put("ApprovalsItemErrors", localApprovalsItemErrors);
    }
    return localApprovalsItems;
  }
  
  public boolean isGroupInUse(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.isGroupInUse";
    a(str);
    return this.jdField_if.isGroupInUse(paramInt, paramHashMap);
  }
  
  public boolean isBusinessInUse(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.isBusinessInUse";
    a(str);
    return this.jdField_if.isBusinessInUse(paramInt, paramHashMap);
  }
  
  public boolean isObjectInUse(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.isObjectInUse";
    a(str);
    return this.jdField_if.isObjectInUse(paramInt1, paramInt2, paramHashMap);
  }
  
  public ApprovalsItems removeGroup(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.removeGroup";
    a(str);
    ApprovalsItems localApprovalsItems = this.jdField_if.removeGroup(paramInt, paramHashMap);
    ApprovalsItemErrors localApprovalsItemErrors = new ApprovalsItemErrors(Locale.getDefault());
    localApprovalsItems = a(localApprovalsItems, localApprovalsItemErrors, paramHashMap);
    if (paramHashMap != null) {
      paramHashMap.put("ApprovalsItemErrors", localApprovalsItemErrors);
    }
    return localApprovalsItems;
  }
  
  public ApprovalsHistory getItemHistory(int paramInt, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.getItemHistory";
    a(str);
    return this.jdField_if.getItemHistory(paramInt, paramHashMap1, paramHashMap2);
  }
  
  public boolean isUserApprover(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.isUserApprover";
    a(str);
    int i = paramSecureUser.getProfileID();
    int j = paramSecureUser.getUserType();
    int k = paramSecureUser.getEntitlementID();
    return this.jdField_if.isUserApprover(i, j, k, paramHashMap);
  }
  
  public void addApprovalGroup(SecureUser paramSecureUser, ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.addApprovalGroup";
    a(str);
    this.jdField_if.addApprovalGroup(paramApprovalsGroup, paramHashMap);
  }
  
  public ApprovalsItems removeApprovalGroup(SecureUser paramSecureUser, ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.removeApprovalGroup";
    a(str);
    ApprovalsItems localApprovalsItems = this.jdField_if.removeApprovalGroup(paramApprovalsGroup.getApprovalsGroupID(), paramHashMap);
    ApprovalsItemErrors localApprovalsItemErrors = new ApprovalsItemErrors(Locale.getDefault());
    localApprovalsItems = a(localApprovalsItems, localApprovalsItemErrors, paramHashMap);
    if (paramHashMap != null) {
      paramHashMap.put("ApprovalsItemErrors", localApprovalsItemErrors);
    }
    return localApprovalsItems;
  }
  
  public void updateApprovalGroup(SecureUser paramSecureUser, ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.updateApprovalGroup";
    a(str);
    this.jdField_if.updateApprovalGroup(paramApprovalsGroup, paramHashMap);
  }
  
  public boolean isApprovalGroupInUse(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.isApprovalGroupInUse";
    a(str);
    return this.jdField_if.isApprovalGroupInUse(paramInt, paramHashMap);
  }
  
  public ApprovalsGroupMembers getApprovalGroupMembers(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.getApprovalGroupMembers";
    a(str);
    return this.jdField_if.getApprovalGroupMembers(paramInt, paramHashMap);
  }
  
  public void assignApprovalGroups(SecureUser paramSecureUser, int paramInt, ApprovalsGroups paramApprovalsGroups, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.assignApprovalGroups";
    a(str);
    this.jdField_if.assignApprovalGroups(paramInt, paramApprovalsGroups, paramHashMap);
  }
  
  public ApprovalsGroups getApprovalGroups(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.getApprovalGroups";
    a(str);
    return this.jdField_if.getApprovalGroups(paramSecureUser.getBusinessID(), paramHashMap);
  }
  
  public ApprovalsGroups getApprovalGroupsForUser(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.getApprovalGroupsForUser";
    a(str);
    return this.jdField_if.getApprovalGroupsForUser(paramInt, paramHashMap);
  }
  
  public BusinessEmployees getEntitledBusinessEmployees(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.getEntitledBusinessEmployees";
    a(str);
    ArrayList localArrayList = null;
    Object localObject1;
    try
    {
      localArrayList = Entitlements.getEntitlementTypes("approval activity", paramString);
    }
    catch (CSILException localCSILException1)
    {
      localObject1 = str + ": An error occurred while getting the list of approval entitlements for the activity type " + paramString;
      DebugLog.throwing((String)localObject1, localCSILException1);
      throw new ApprovalsException(30146, (String)localObject1);
    }
    if ((localArrayList == null) || (localArrayList.size() == 0)) {
      try
      {
        BusinessEmployee localBusinessEmployee = new BusinessEmployee();
        localBusinessEmployee.set("BANK_ID", paramSecureUser.getBankID());
        localBusinessEmployee.setBusinessId(paramSecureUser.getBusinessID());
        return UserAdmin.getBusinessEmployees(paramSecureUser, localBusinessEmployee, paramHashMap);
      }
      catch (CSILException localCSILException2)
      {
        localObject1 = str + ": An error occurred while looking up the entitled business employees";
        DebugLog.throwing((String)localObject1, localCSILException2);
        throw new ApprovalsException(30147, (String)localObject1);
      }
    }
    BusinessEmployees localBusinessEmployees = null;
    try
    {
      localObject1 = a(paramSecureUser);
      localObject2 = new ArrayList();
      a(localArrayList, (EntitlementGroup)localObject1, (ArrayList)localObject2, new Entitlement());
      localBusinessEmployees = UserAdminHandler.getBusinessEmployeesByIds(paramSecureUser, (ArrayList)localObject2, paramHashMap);
    }
    catch (CSILException localCSILException3)
    {
      localObject2 = str + ": An error occurred while looking up the entitled business employees";
      DebugLog.throwing((String)localObject2, localCSILException3);
      throw new ApprovalsException(30147, (String)localObject2);
    }
    catch (ApprovalsException localApprovalsException)
    {
      Object localObject2 = str + ": An error occurred while looking up the entitled business employees";
      DebugLog.throwing((String)localObject2, localApprovalsException);
      throw new ApprovalsException(30147, (String)localObject2);
    }
    return localBusinessEmployees;
  }
  
  public EntitlementGroups getEntitledGroups(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "com.ffusion.services.approvals.ApprovalsService.getEntitledGroups";
    a(str1);
    ArrayList localArrayList = null;
    try
    {
      localArrayList = Entitlements.getEntitlementTypes("approval activity", paramString);
    }
    catch (CSILException localCSILException1)
    {
      String str2 = str1 + ": An error occurred while getting the list of approval entitlements for the activity type " + paramString;
      DebugLog.throwing(str2, localCSILException1);
      throw new ApprovalsException(30148, str2);
    }
    EntitlementGroup localEntitlementGroup = null;
    String str3;
    try
    {
      localEntitlementGroup = a(paramSecureUser);
    }
    catch (CSILException localCSILException2)
    {
      str3 = str1 + ": An error occurred while looking up the entitled groups";
      DebugLog.throwing(str3, localCSILException2);
      throw new ApprovalsException(30149, str3);
    }
    catch (ApprovalsException localApprovalsException)
    {
      str3 = str1 + ": An error occurred while looking up the entitled groups";
      DebugLog.throwing(str3, localApprovalsException);
      throw new ApprovalsException(30149, str3);
    }
    if ((localArrayList == null) || (localArrayList.size() == 0)) {
      try
      {
        EntitlementGroups localEntitlementGroups1 = new EntitlementGroups();
        a(localEntitlementGroup, localEntitlementGroups1);
        return localEntitlementGroups1;
      }
      catch (CSILException localCSILException3)
      {
        str3 = str1 + ": An error occurred while looking up the entitled groups";
        DebugLog.throwing(str3, localCSILException3);
        throw new ApprovalsException(30149, str3);
      }
    }
    EntitlementGroups localEntitlementGroups2 = new EntitlementGroups();
    try
    {
      a(localArrayList, localEntitlementGroup, localEntitlementGroups2, new Entitlement());
    }
    catch (CSILException localCSILException4)
    {
      String str4 = str1 + ": An error occurred while looking up the entitled groups";
      DebugLog.throwing(str4, localCSILException4);
      throw new ApprovalsException(30149, str4);
    }
    return localEntitlementGroups2;
  }
  
  public void cleanup(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "com.ffusion.services.approvals.ApprovalsService.cleanup";
    a(str);
    this.jdField_if.cleanup(paramInt, paramHashMap);
  }
  
  private void a(String paramString)
    throws ApprovalsException
  {
    if (this.jdField_if == null)
    {
      String str = "The Approvals Service must be initialized before any calls are made to the service method " + paramString + ".";
      DebugLog.log(Level.SEVERE, str);
      throw new ApprovalsException(30002, str);
    }
  }
  
  private ApprovalsItems jdField_if(SecureUser paramSecureUser, ApprovalsItems paramApprovalsItems, ApprovalsItemErrors paramApprovalsItemErrors, HashMap paramHashMap)
  {
    if ((paramApprovalsItems == null) || (paramApprovalsItems.size() == 0)) {
      return paramApprovalsItems;
    }
    String str = "com.ffusion.services.approvals.ApprovalsService.processApprovedItems";
    Iterator localIterator = paramApprovalsItems.iterator();
    ApprovalsItems localApprovalsItems = new ApprovalsItems(Locale.getDefault());
    while (localIterator.hasNext())
    {
      ApprovalsItem localApprovalsItem = (ApprovalsItem)localIterator.next();
      IApprovalsPlugin localIApprovalsPlugin = null;
      try
      {
        localIApprovalsPlugin = (IApprovalsPlugin)this.a.get(localApprovalsItem.getItemSubType());
        SecureUser localSecureUser = Util.getSecureUserFromProfile(paramSecureUser, localApprovalsItem.getSubmittingUserID());
        IApprovable localIApprovable = ((TWTransaction)localApprovalsItem.getItem()).getTransaction();
        localIApprovalsPlugin.processApprovedItem(localSecureUser, localIApprovable, paramHashMap);
      }
      catch (Throwable localThrowable1)
      {
        localApprovalsItems.add(localApprovalsItem);
        localIterator.remove();
        int i;
        if ((localThrowable1 instanceof CSILException))
        {
          i = ((CSILException)localThrowable1).getServiceError();
          StringWriter localStringWriter = new StringWriter();
          PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
          localThrowable1.printStackTrace(localPrintWriter);
          DebugLog.log(Level.FINE, "NIGHTLY TEST DEBUG:\n" + localStringWriter.toString());
          DebugLog.log(Level.FINE, "NIGHTLY TEST_DEBUG: errorcode = " + i);
        }
        else if ((localThrowable1 instanceof ApprovalsException))
        {
          i = ((ApprovalsException)localThrowable1).getErrorCode();
        }
        else
        {
          i = 30005;
        }
        if (localIApprovalsPlugin != null) {
          try
          {
            localIApprovalsPlugin.rollbackApprovalLimits(paramSecureUser, ((TWTransaction)localApprovalsItem.getItem()).getTransaction(), paramHashMap);
          }
          catch (Throwable localThrowable2)
          {
            DebugLog.throwing(str + ": An error occurred while rolling back approval limits on failed approval for item with id " + localApprovalsItem.getItemID(), localThrowable2);
          }
        }
        ApprovalsItemError localApprovalsItemError = paramApprovalsItemErrors.add();
        localApprovalsItemError.setApprovalItem(localApprovalsItem);
        localApprovalsItemError.setErrorCode(i);
        DebugLog.throwing(str + ": An error occurred while processing approved item with id " + localApprovalsItem.getItemID(), localThrowable1);
      }
    }
    if (!localApprovalsItems.isEmpty()) {
      try
      {
        this.jdField_if.setItemsFailedExecution(localApprovalsItems, paramHashMap);
      }
      catch (ApprovalsException localApprovalsException)
      {
        DebugLog.throwing(str, localApprovalsException);
      }
    }
    return paramApprovalsItems;
  }
  
  private ApprovalsItems a(ApprovalsItems paramApprovalsItems, ApprovalsItemErrors paramApprovalsItemErrors, HashMap paramHashMap)
  {
    if ((paramApprovalsItems == null) || (paramApprovalsItems.size() == 0)) {
      return paramApprovalsItems;
    }
    String str = "com.ffusion.services.approvals.ApprovalsService.processCompletedItems";
    Iterator localIterator = paramApprovalsItems.iterator();
    while (localIterator.hasNext())
    {
      ApprovalsItem localApprovalsItem = (ApprovalsItem)localIterator.next();
      IApprovalsPlugin localIApprovalsPlugin = null;
      try
      {
        localIApprovalsPlugin = (IApprovalsPlugin)this.a.get(localApprovalsItem.getItemSubType());
        SecureUser localSecureUser = Util.getSecureUserFromProfile(new SecureUser(), localApprovalsItem.getSubmittingUserID());
        IApprovable localIApprovable = ((TWTransaction)localApprovalsItem.getItem()).getTransaction();
        localIApprovalsPlugin.processApprovedItem(localSecureUser, localIApprovable, paramHashMap);
      }
      catch (Throwable localThrowable)
      {
        localIterator.remove();
        int i;
        if ((localThrowable instanceof CSILException)) {
          i = ((CSILException)localThrowable).getServiceError();
        } else if ((localThrowable instanceof ApprovalsException)) {
          i = ((ApprovalsException)localThrowable).getErrorCode();
        } else {
          i = 30007;
        }
        ApprovalsItemError localApprovalsItemError = paramApprovalsItemErrors.add();
        localApprovalsItemError.setApprovalItem(localApprovalsItem);
        localApprovalsItemError.setErrorCode(i);
        DebugLog.throwing(str, localThrowable);
      }
    }
    return paramApprovalsItems;
  }
  
  private void a(SecureUser paramSecureUser, ApprovalsItems paramApprovalsItems, ApprovalsItemErrors paramApprovalsItemErrors, HashMap paramHashMap)
  {
    if ((paramApprovalsItems == null) || (paramApprovalsItems.size() == 0)) {
      return;
    }
    String str = "com.ffusion.services.approvals.ApprovalsService.processRejectedItems";
    Iterator localIterator = paramApprovalsItems.iterator();
    ApprovalsItems localApprovalsItems = new ApprovalsItems(Locale.getDefault());
    while (localIterator.hasNext())
    {
      ApprovalsItem localApprovalsItem = (ApprovalsItem)localIterator.next();
      try
      {
        IApprovalsPlugin localIApprovalsPlugin = (IApprovalsPlugin)this.a.get(localApprovalsItem.getItemSubType());
        SecureUser localSecureUser = Util.getSecureUserFromProfile(paramSecureUser, localApprovalsItem.getSubmittingUserID());
        localObject = ((TWTransaction)localApprovalsItem.getItem()).getTransaction();
        localIApprovalsPlugin.processRejectedItem(localSecureUser, (IApprovable)localObject, paramHashMap);
      }
      catch (Throwable localThrowable)
      {
        localApprovalsItems.add(localApprovalsItem);
        localIterator.remove();
        int i;
        if ((localThrowable instanceof CSILException))
        {
          i = ((CSILException)localThrowable).getServiceError();
          localObject = new StringWriter();
          PrintWriter localPrintWriter = new PrintWriter((Writer)localObject);
          localThrowable.printStackTrace(localPrintWriter);
          DebugLog.log(Level.FINE, "NIGHTLY TEST DEBUG:\n" + ((StringWriter)localObject).toString());
          DebugLog.log(Level.FINE, "NIGHTLY TEST_DEBUG: errorcode = " + i);
        }
        else if ((localThrowable instanceof ApprovalsException))
        {
          i = ((ApprovalsException)localThrowable).getErrorCode();
        }
        else
        {
          i = 30006;
        }
        Object localObject = paramApprovalsItemErrors.add();
        ((ApprovalsItemError)localObject).setApprovalItem(localApprovalsItem);
        ((ApprovalsItemError)localObject).setErrorCode(i);
        DebugLog.throwing(str, localThrowable);
      }
    }
    if (!localApprovalsItems.isEmpty()) {
      try
      {
        this.jdField_if.setItemsFailedRejection(localApprovalsItems, paramHashMap);
      }
      catch (ApprovalsException localApprovalsException)
      {
        DebugLog.throwing(str, localApprovalsException);
      }
    }
  }
  
  private EntitlementGroup a(SecureUser paramSecureUser)
    throws ApprovalsException, CSILException
  {
    for (EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(paramSecureUser.getEntitlementID()); !localEntitlementGroup.getEntGroupType().equals("Business"); localEntitlementGroup = Entitlements.getEntitlementGroup(localEntitlementGroup.getParentId())) {
      if (localEntitlementGroup.getParentId() == 0) {
        throw new ApprovalsException(0, "Unable to find the entitlement group for the business");
      }
    }
    return localEntitlementGroup;
  }
  
  private void a(EntitlementGroup paramEntitlementGroup, EntitlementGroups paramEntitlementGroups)
    throws CSILException
  {
    paramEntitlementGroups.add(paramEntitlementGroup);
    Iterator localIterator = null;
    EntitlementGroups localEntitlementGroups = null;
    EntitlementGroup localEntitlementGroup = null;
    localEntitlementGroups = Entitlements.getChildrenEntitlementGroups(paramEntitlementGroup.getGroupId());
    if (localEntitlementGroups != null)
    {
      localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        localEntitlementGroup = (EntitlementGroup)localIterator.next();
        a(localEntitlementGroup, paramEntitlementGroups);
      }
    }
  }
  
  private void a(ArrayList paramArrayList, EntitlementGroup paramEntitlementGroup, EntitlementGroups paramEntitlementGroups, Entitlement paramEntitlement)
    throws CSILException
  {
    int i = paramEntitlementGroup.getGroupId();
    int j = paramArrayList.size();
    Object localObject;
    for (int k = 0; k < j; k++)
    {
      localObject = (String)paramArrayList.get(k);
      paramEntitlement.setOperationName((String)localObject);
      if (Entitlements.checkEntitlement(i, paramEntitlement)) {
        break;
      }
      paramArrayList.remove(k);
      j--;
      k--;
    }
    if (paramArrayList.size() == 0) {
      return;
    }
    paramEntitlementGroups.add(paramEntitlementGroup);
    EntitlementGroups localEntitlementGroups = Entitlements.getChildrenEntitlementGroups(i);
    if (localEntitlementGroups != null)
    {
      localObject = localEntitlementGroups.iterator();
      while (((Iterator)localObject).hasNext())
      {
        EntitlementGroup localEntitlementGroup = (EntitlementGroup)((Iterator)localObject).next();
        ArrayList localArrayList = (ArrayList)paramArrayList.clone();
        a(localArrayList, localEntitlementGroup, paramEntitlementGroups, paramEntitlement);
      }
    }
  }
  
  private void a(ArrayList paramArrayList1, EntitlementGroup paramEntitlementGroup, ArrayList paramArrayList2, Entitlement paramEntitlement)
    throws CSILException
  {
    int i = paramEntitlementGroup.getGroupId();
    int j = paramArrayList1.size();
    for (int k = 0; k < j; k++)
    {
      localObject1 = (String)paramArrayList1.get(k);
      paramEntitlement.setOperationName((String)localObject1);
      if (Entitlements.checkEntitlement(i, paramEntitlement)) {
        break;
      }
      paramArrayList1.remove(k);
      j--;
      k--;
    }
    if (paramArrayList1.size() == 0) {
      return;
    }
    EntitlementGroupMembers localEntitlementGroupMembers = Entitlements.getMembers(i);
    Object localObject2;
    Object localObject3;
    if (localEntitlementGroupMembers != null)
    {
      localObject1 = localEntitlementGroupMembers.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (EntitlementGroupMember)((Iterator)localObject1).next();
        for (int m = 0; m < j; m++)
        {
          localObject3 = (String)paramArrayList1.get(m);
          paramEntitlement.setOperationName((String)localObject3);
          if (Entitlements.checkEntitlement((EntitlementGroupMember)localObject2, paramEntitlement))
          {
            paramArrayList2.add(((EntitlementGroupMember)localObject2).getId());
            break;
          }
        }
      }
    }
    Object localObject1 = Entitlements.getChildrenEntitlementGroups(i);
    if (localObject1 != null)
    {
      localObject2 = ((EntitlementGroups)localObject1).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        EntitlementGroup localEntitlementGroup = (EntitlementGroup)((Iterator)localObject2).next();
        localObject3 = (ArrayList)paramArrayList1.clone();
        a((ArrayList)localObject3, localEntitlementGroup, paramArrayList2, paramEntitlement);
      }
    }
  }
  
  private IApprovalsPlugin a(int paramInt1, int paramInt2)
    throws ApprovalsException
  {
    IApprovalsPlugin localIApprovalsPlugin = null;
    if (paramInt1 == 1) {
      localIApprovalsPlugin = (IApprovalsPlugin)this.a.get(paramInt2);
    } else {
      throw new ApprovalsException(30075, "");
    }
    if (localIApprovalsPlugin == null) {
      throw new ApprovalsException(30004, "");
    }
    return localIApprovalsPlugin;
  }
  
  private boolean a(SecureUser paramSecureUser, ApprovalsChainItems paramApprovalsChainItems, int paramInt1, int paramInt2, boolean paramBoolean, HashMap paramHashMap)
    throws ApprovalsException, CSILException
  {
    int i = paramApprovalsChainItems.size();
    int[] arrayOfInt1 = new int[i];
    int[] arrayOfInt2 = new int[i];
    for (int j = 0; j < i; j++)
    {
      arrayOfInt1[j] = 0;
      arrayOfInt2[j] = 0;
    }
    ArrayList localArrayList1 = new ArrayList();
    HashMap localHashMap = a(paramSecureUser, paramApprovalsChainItems, arrayOfInt1, arrayOfInt2, paramInt1, paramInt2, paramBoolean, paramHashMap);
    int k = localHashMap.size();
    if (i > k)
    {
      localObject = new boolean[i];
      for (int m = 0; m < i; m++) {
        localObject[m] = 1;
      }
      a(localArrayList1, (boolean[])localObject, arrayOfInt1, k);
      a(localArrayList1, arrayOfInt2, paramApprovalsChainItems, paramHashMap);
      return true;
    }
    Object localObject = new int[k];
    boolean[] arrayOfBoolean1 = new boolean[k];
    boolean[] arrayOfBoolean2 = new boolean[i];
    boolean[][] arrayOfBoolean = new boolean[k][i];
    for (int n = 0; n < k; n++) {
      arrayOfBoolean1[n] = true;
    }
    for (n = 0; n < i; n++) {
      arrayOfBoolean2[n] = true;
    }
    for (n = 0; n < k; n++) {
      for (int i1 = 0; i1 < i; i1++) {
        arrayOfBoolean[n][i1] = 0;
      }
    }
    Iterator localIterator = localHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      ArrayList localArrayList2 = (ArrayList)localHashMap.get(localInteger);
      int i2 = ((Integer)localArrayList2.get(0)).intValue();
      int i3 = localArrayList2.size();
      localObject[i2] = (i3 - 1);
      for (int i4 = 1; i4 < i3; i4++) {
        arrayOfBoolean[i2][((Integer)localArrayList2.get(i4)).intValue()] = 1;
      }
    }
    boolean bool = a(arrayOfBoolean, arrayOfBoolean2, arrayOfBoolean1, (int[])localObject, arrayOfInt1, localArrayList1);
    if (bool) {
      a(localArrayList1, arrayOfInt2, paramApprovalsChainItems, paramHashMap);
    }
    return bool;
  }
  
  private boolean a(boolean[][] paramArrayOfBoolean, boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, ArrayList paramArrayList)
    throws ApprovalsException
  {
    int i = paramArrayOfInt2.length;
    for (int j = 0; j < i; j++) {
      if ((paramArrayOfBoolean1[j] != 0) && (paramArrayOfInt2[j] == 0))
      {
        a(paramArrayList, paramArrayOfBoolean1, paramArrayOfInt2, a(paramArrayOfBoolean2));
        return true;
      }
    }
    j = a(paramArrayOfBoolean1);
    if (j == 1) {
      return false;
    }
    int k = a(paramArrayOfBoolean2);
    if (j > k)
    {
      a(paramArrayList, paramArrayOfBoolean1, paramArrayOfInt2, k);
      return true;
    }
    int m = paramArrayOfInt1.length;
    int n = -1;
    for (int i1 = 0; i1 < m; i1++) {
      if (paramArrayOfInt1[i1] == 1)
      {
        n = i1;
        break;
      }
    }
    if (n != -1)
    {
      i1 = -1;
      for (int i2 = 0; i2 < i; i2++) {
        if (paramArrayOfBoolean[n][i2] != 0)
        {
          i1 = i2;
          break;
        }
      }
      if (i1 == -1) {
        throw new ApprovalsException(30134, "Could not modify the approval flow chain for the specified approval level");
      }
      a(paramArrayOfBoolean, paramArrayOfBoolean1, paramArrayOfBoolean2, paramArrayOfInt1, paramArrayOfInt2, n, i1, null, null, null, true);
      return a(paramArrayOfBoolean, paramArrayOfBoolean1, paramArrayOfBoolean2, paramArrayOfInt1, paramArrayOfInt2, paramArrayList);
    }
    int[] arrayOfInt = new int[2];
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    int i3 = a(paramArrayOfInt2, paramArrayOfBoolean1);
    for (int i4 = 0; i4 < m; i4++) {
      if (paramArrayOfBoolean[i4][i3] != 0)
      {
        a(paramArrayOfBoolean, paramArrayOfBoolean1, paramArrayOfBoolean2, paramArrayOfInt1, paramArrayOfInt2, i4, i3, arrayOfInt, localArrayList1, localArrayList2, false);
        if (!a(paramArrayOfBoolean, paramArrayOfBoolean1, paramArrayOfBoolean2, paramArrayOfInt1, paramArrayOfInt2, paramArrayList)) {
          return false;
        }
        a(paramArrayOfBoolean, paramArrayOfBoolean1, paramArrayOfBoolean2, paramArrayOfInt1, paramArrayOfInt2, i4, i3, arrayOfInt, localArrayList1, localArrayList2);
      }
    }
    return true;
  }
  
  private void a(boolean[][] paramArrayOfBoolean, boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int[] paramArrayOfInt3, ArrayList paramArrayList1, ArrayList paramArrayList2, boolean paramBoolean)
  {
    paramArrayOfBoolean1[paramInt2] = false;
    paramArrayOfBoolean2[paramInt1] = false;
    if (!paramBoolean)
    {
      paramArrayOfInt3[0] = paramArrayOfInt1[paramInt1];
      paramArrayOfInt3[1] = paramArrayOfInt2[paramInt2];
    }
    paramArrayOfInt1[paramInt1] = 0;
    paramArrayOfInt2[paramInt2] = 0;
    int i = paramArrayOfInt1.length;
    for (int j = 0; j < i; j++) {
      if (paramArrayOfBoolean[j][paramInt2] != 0)
      {
        if (j != paramInt1) {
          paramArrayOfInt1[j] -= 1;
        }
        if (!paramBoolean) {
          paramArrayList2.add(new Integer(j));
        }
        paramArrayOfBoolean[j][paramInt2] = 0;
      }
    }
    if (!paramBoolean)
    {
      i = paramArrayOfInt2.length;
      for (j = 0; j < i; j++) {
        if (paramArrayOfBoolean[paramInt1][j] != 0)
        {
          if (j != paramInt2) {
            paramArrayOfInt2[j] -= 1;
          }
          paramArrayList1.add(new Integer(j));
          paramArrayOfBoolean[paramInt1][j] = 0;
        }
      }
    }
  }
  
  private void a(boolean[][] paramArrayOfBoolean, boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int[] paramArrayOfInt3, ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    paramArrayOfBoolean1[paramInt2] = true;
    paramArrayOfBoolean2[paramInt1] = true;
    paramArrayOfInt1[paramInt1] = paramArrayOfInt3[0];
    paramArrayOfInt2[paramInt2] = paramArrayOfInt3[1];
    Iterator localIterator = paramArrayList2.iterator();
    int i;
    while (localIterator.hasNext())
    {
      i = ((Integer)localIterator.next()).intValue();
      paramArrayOfBoolean[i][paramInt2] = 1;
      if (i != paramInt1) {
        paramArrayOfInt1[i] += 1;
      }
    }
    localIterator = paramArrayList1.iterator();
    while (localIterator.hasNext())
    {
      i = ((Integer)localIterator.next()).intValue();
      paramArrayOfBoolean[paramInt1][i] = 1;
      if (i != paramInt2) {
        paramArrayOfInt2[i] += 1;
      }
    }
  }
  
  private void a(ArrayList paramArrayList, boolean[] paramArrayOfBoolean, int[] paramArrayOfInt, int paramInt)
  {
    paramArrayList.removeAll(paramArrayList);
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++)
    {
      if ((paramArrayOfBoolean[j] != 0) && (paramArrayOfInt[j] == 0)) {
        localArrayList1.add(new Integer(j));
      }
      if (paramArrayOfBoolean[j] != 0) {
        localArrayList2.add(new Integer(j));
      }
    }
    paramArrayList.add(localArrayList1);
    paramArrayList.add(localArrayList2);
    paramArrayList.add(new Integer(paramInt));
  }
  
  private boolean a(HashMap paramHashMap1, Integer paramInteger, int paramInt, int[] paramArrayOfInt, HashMap paramHashMap2)
  {
    Object localObject1 = paramHashMap2.get("Active Users List");
    if (localObject1 != null) {
      if ((localObject1 instanceof Users))
      {
        localObject2 = ((Users)localObject1).getByID(paramInteger.toString());
        if (localObject2 == null) {
          return false;
        }
      }
      else if ((localObject1 instanceof BankEmployees))
      {
        localObject2 = ((BankEmployees)localObject1).getByID(paramInteger.toString());
        if (localObject2 == null) {
          return false;
        }
      }
    }
    Object localObject2 = null;
    if (paramHashMap1.containsKey(paramInteger))
    {
      localObject2 = (ArrayList)paramHashMap1.get(paramInteger);
    }
    else
    {
      localObject2 = new ArrayList();
      ((ArrayList)localObject2).add(new Integer(paramHashMap1.size()));
      paramArrayOfInt[paramInt] += 1;
    }
    ((ArrayList)localObject2).add(new Integer(paramInt));
    paramHashMap1.put(paramInteger, localObject2);
    return true;
  }
  
  private ServicesPackage a(SecureUser paramSecureUser, int paramInt)
    throws ApprovalsException, CSILException
  {
    HashMap localHashMap = new HashMap();
    localHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(localHashMap);
    ServicesPackages localServicesPackages = com.ffusion.csil.core.Business.getServicesPackages(paramSecureUser, null, localHashMap);
    ServicesPackage localServicesPackage = localServicesPackages.getById(paramInt);
    if (localServicesPackage == null) {
      throw new ApprovalsException(30151, "an invalid services pacakge ID has been specified ");
    }
    return localServicesPackage;
  }
  
  private String jdField_if(SecureUser paramSecureUser, int paramInt)
    throws ApprovalsException, CSILException
  {
    ServicesPackage localServicesPackage = a(paramSecureUser, paramInt);
    String str = localServicesPackage.getAffiliateBankId();
    AffiliateBank localAffiliateBank = null;
    if (str != null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(localHashMap);
      int i = Integer.parseInt(str);
      localAffiliateBank = AffiliateBankAdmin.getAffiliateBankByID(paramSecureUser, i, localHashMap);
      if (localAffiliateBank != null) {
        return localAffiliateBank.getCurrencyCode();
      }
    }
    return Util.getLimitBaseCurrency();
  }
  
  private HashMap a(SecureUser paramSecureUser, ApprovalsChainItems paramApprovalsChainItems, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, boolean paramBoolean, HashMap paramHashMap)
    throws ApprovalsException, CSILException
  {
    HashMap localHashMap = new HashMap();
    int i = 0;
    String str = null;
    Iterator localIterator = paramApprovalsChainItems.iterator();
    Object localObject1;
    if (!paramBoolean) {
      if (paramInt2 == 1)
      {
        localObject1 = new com.ffusion.beans.business.Business();
        ((com.ffusion.beans.business.Business)localObject1).setId(paramInt1);
        localObject1 = com.ffusion.csil.handlers.Business.getBusiness(null, (com.ffusion.beans.business.Business)localObject1, paramHashMap);
        str = String.valueOf(((com.ffusion.beans.business.Business)localObject1).getAffiliateBankID());
      }
      else if (paramInt2 == 2)
      {
        localObject1 = a(paramSecureUser, paramInt1);
        if (localObject1 != null) {
          str = ((ServicesPackage)localObject1).getAffiliateBankId();
        }
      }
    }
    while (localIterator.hasNext())
    {
      localObject1 = (ApprovalsChainItem)localIterator.next();
      if (((ApprovalsChainItem)localObject1).isUsingUser())
      {
        Integer localInteger1 = new Integer(((ApprovalsChainItem)localObject1).getGroupOrUserID());
        if (a(localHashMap, localInteger1, i, paramArrayOfInt2, paramHashMap)) {
          paramArrayOfInt1[i] = 1;
        }
      }
      else
      {
        int j;
        Object localObject2;
        int k;
        int m;
        Object localObject3;
        Object localObject4;
        if (((ApprovalsChainItem)localObject1).getIsApprovalsGroup())
        {
          j = ((ApprovalsChainItem)localObject1).getGroupOrUserID();
          localObject2 = getApprovalGroupMembers(null, j, paramHashMap);
          if (localObject2 != null)
          {
            k = ((ApprovalsGroupMembers)localObject2).size();
            for (m = 0; m < k; m++)
            {
              localObject3 = (ApprovalsGroupMember)((ApprovalsGroupMembers)localObject2).get(m);
              localObject4 = new Integer(((ApprovalsGroupMember)localObject3).getUserID());
              if (a(localHashMap, (Integer)localObject4, i, paramArrayOfInt2, paramHashMap)) {
                paramArrayOfInt1[i] += 1;
              }
            }
          }
          else
          {
            paramArrayOfInt1[i] = 0;
          }
        }
        else
        {
          j = ((ApprovalsChainItem)localObject1).getGroupOrUserID();
          paramArrayOfInt1[i] = 0;
          localObject2 = Entitlements.getMembers(j);
          if (localObject2 != null)
          {
            k = ((EntitlementGroupMembers)localObject2).size();
            for (m = 0; m < k; m++)
            {
              localObject3 = (EntitlementGroupMember)((EntitlementGroupMembers)localObject2).get(m);
              if (paramBoolean)
              {
                localObject4 = new Integer(((EntitlementGroupMember)localObject3).getId());
                if (a(localHashMap, (Integer)localObject4, i, paramArrayOfInt2, paramHashMap)) {
                  paramArrayOfInt1[i] += 1;
                }
              }
              else if (Entitlements.checkEntitlement((EntitlementGroupMember)localObject3, new Entitlement("Bank Approve Limits", null, null)))
              {
                localObject4 = new BankEmployee(Locale.getDefault());
                ((BankEmployee)localObject4).setId(((EntitlementGroupMember)localObject3).getId());
                localObject4 = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, (BankEmployee)localObject4, paramHashMap);
                ArrayList localArrayList = ((BankEmployee)localObject4).getAffiliateBankIds();
                boolean bool = false;
                if (paramInt2 == 1) {
                  bool = localArrayList.contains(str);
                } else if (paramInt2 == 2) {
                  if (str == null) {
                    bool = true;
                  } else {
                    bool = localArrayList.contains(str);
                  }
                }
                if ((Entitlements.checkEntitlement((EntitlementGroupMember)localObject3, new Entitlement("BC Manage Multiple Banks Simultaneously", null, null))) || (bool))
                {
                  Integer localInteger2 = new Integer(((EntitlementGroupMember)localObject3).getId());
                  if (a(localHashMap, localInteger2, i, paramArrayOfInt2, paramHashMap)) {
                    paramArrayOfInt1[i] += 1;
                  }
                }
              }
            }
          }
          else
          {
            paramArrayOfInt1[i] = 0;
          }
        }
      }
      i++;
    }
    return localHashMap;
  }
  
  private void a(ArrayList paramArrayList, int[] paramArrayOfInt, ApprovalsChainItems paramApprovalsChainItems, HashMap paramHashMap)
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    ArrayList localArrayList4 = (ArrayList)paramArrayList.get(0);
    ArrayList localArrayList5 = (ArrayList)paramArrayList.get(1);
    int i = ((Integer)paramArrayList.get(2)).intValue();
    int j = 0;
    int m;
    if (localArrayList4.size() > 0)
    {
      k = localArrayList4.size();
      j += k;
      localApprovalsChainItem = null;
      localInteger1 = null;
      for (m = 0; m < k; m++)
      {
        Integer localInteger2 = (Integer)localArrayList4.get(m);
        localArrayList5.remove(localInteger2);
        localApprovalsChainItem = (ApprovalsChainItem)paramApprovalsChainItems.get(localInteger2.intValue());
        localInteger1 = new Integer(localApprovalsChainItem.getGroupOrUserID());
        if (localApprovalsChainItem.isUsingUser())
        {
          if (!localArrayList3.contains(localInteger1)) {
            localArrayList3.add(localInteger1);
          }
        }
        else if (localApprovalsChainItem.getIsApprovalsGroup())
        {
          if (!localArrayList2.contains(localInteger1)) {
            localArrayList2.add(localInteger1);
          }
        }
        else if (!localArrayList1.contains(localInteger1)) {
          localArrayList1.add(localInteger1);
        }
      }
    }
    int k = localArrayList5.size();
    if (k > i) {
      j += k - i;
    }
    ApprovalsChainItem localApprovalsChainItem = null;
    Integer localInteger1 = null;
    while (k > i)
    {
      m = -1;
      int n = -1;
      int i1 = -1;
      for (int i2 = 0; i2 < k; i2++)
      {
        n = ((Integer)localArrayList5.get(i2)).intValue();
        if (i1 == -1)
        {
          m = n;
          i1 = paramArrayOfInt[m];
        }
        else if (paramArrayOfInt[n] < i1)
        {
          m = n;
          i1 = paramArrayOfInt[m];
        }
      }
      localArrayList5.remove(new Integer(m));
      localApprovalsChainItem = (ApprovalsChainItem)paramApprovalsChainItems.get(m);
      localInteger1 = new Integer(localApprovalsChainItem.getGroupOrUserID());
      if (localApprovalsChainItem.isUsingUser())
      {
        if (!localArrayList3.contains(localInteger1)) {
          localArrayList3.add(localInteger1);
        }
      }
      else if (localApprovalsChainItem.getIsApprovalsGroup())
      {
        if (!localArrayList2.contains(localInteger1)) {
          localArrayList2.add(localInteger1);
        }
      }
      else if (!localArrayList1.contains(localInteger1)) {
        localArrayList1.add(localInteger1);
      }
      k = localArrayList5.size();
    }
    if (localArrayList3.size() > 0) {
      paramHashMap.put("ApprovalsStallConditionsUserIDs", localArrayList3);
    }
    if (localArrayList2.size() > 0) {
      paramHashMap.put("ApprovalsStallConditionsApprovalGroupIDs", localArrayList2);
    }
    if (localArrayList1.size() > 0) {
      paramHashMap.put("ApprovalsStallConditionsEntitlementGroupIDs", localArrayList1);
    }
    if (j != 0) {
      paramHashMap.put("ApprovalsStallConditionsStallCount", new Integer(j));
    }
  }
  
  private int a(boolean[] paramArrayOfBoolean)
  {
    int i = 0;
    int j = paramArrayOfBoolean.length;
    for (int k = 0; k < j; k++) {
      if (paramArrayOfBoolean[k] != 0) {
        i++;
      }
    }
    return i;
  }
  
  private int a(int[] paramArrayOfInt, boolean[] paramArrayOfBoolean)
  {
    int i = -1;
    int j = paramArrayOfInt.length;
    int k = -1;
    for (int m = 0; m < j; m++) {
      if (paramArrayOfBoolean[m] != 0) {
        if (k == -1)
        {
          k = paramArrayOfInt[m];
          i = m;
        }
        else if (paramArrayOfInt[m] < k)
        {
          k = paramArrayOfInt[m];
          i = m;
        }
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.approvals.ApprovalsService
 * JD-Core Version:    0.7.0.1
 */