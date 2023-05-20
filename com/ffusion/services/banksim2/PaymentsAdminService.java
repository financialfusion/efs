package com.ffusion.services.banksim2;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffActivities;
import com.ffusion.beans.affiliatebank.CutOffActivity;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.beans.affiliatebank.FulfillmentSystem;
import com.ffusion.beans.affiliatebank.FulfillmentSystems;
import com.ffusion.beans.affiliatebank.ScheduleActivities;
import com.ffusion.beans.affiliatebank.ScheduleActivity;
import com.ffusion.beans.affiliatebank.ScheduleCategory;
import com.ffusion.beans.affiliatebank.ScheduleType;
import com.ffusion.beans.affiliatebank.ScheduleTypes;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.paymentsadmin.ProcessingWindow;
import com.ffusion.beans.paymentsadmin.ProcessingWindows;
import com.ffusion.beans.systemadmin.DRKey;
import com.ffusion.beans.systemadmin.DRSetting;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.BeansConverter;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.efs.adapters.profile.BusinessAdapter;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.ffs.bpw.BPWServices.BPWServices;
import com.ffusion.ffs.bpw.BPWServices.BPWServicesHome;
import com.ffusion.ffs.bpw.adminEJB.BPWAdminHome;
import com.ffusion.ffs.bpw.adminEJB.IBPWAdmin;
import com.ffusion.ffs.bpw.interfaces.CutOffActivityInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffActivityInfoList;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfoList;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowInfo;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowList;
import com.ffusion.ffs.bpw.interfaces.ScheduleActivityList;
import com.ffusion.ffs.bpw.interfaces.ScheduleCategoryInfo;
import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
import com.ffusion.services.PAException;
import com.ffusion.services.PaymentsAdminService3;
import com.ffusion.systemadmin.SystemAdminUtil;
import com.ffusion.systemadmin.adapter.SystemAdminAdapter;
import com.ffusion.util.ContextPool;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.Vector;
import java.util.logging.Level;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

public class PaymentsAdminService
  extends Base
  implements PaymentsAdminService3
{
  private static final String cz = "BPWAdminCallBackJNDIName";
  private String cA = "bpw.BPWAdminHome";
  private String cB = "BPWServices";
  
  public void processOFXRequest() {}
  
  public int initialize(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "banksim.xml";
    }
    return initialize(paramString, new a());
  }
  
  protected IBPWAdmin getBPWAdminHandler()
  {
    IBPWAdmin localIBPWAdmin = null;
    ContextPool localContextPool = null;
    Context localContext = null;
    for (int i = 0; i < this.provider_url_list.size(); i++)
    {
      String str = (String)this.provider_url_list.get(i);
      try
      {
        if (debugService)
        {
          DebugLog.log(Level.INFO, "##################### Getting BPW Handler ################");
          DebugLog.log(Level.INFO, "provider_url = " + str);
        }
        localContextPool = getContextPool(str);
        localContext = localContextPool.getContext();
        Object localObject1 = localContext.lookup(this.cA);
        localObject2 = (BPWAdminHome)PortableRemoteObject.narrow(localObject1, BPWAdminHome.class);
        localIBPWAdmin = ((BPWAdminHome)localObject2).create();
        localContextPool.returnContext(localContext);
        if (localIBPWAdmin != null)
        {
          try
          {
            if (localIBPWAdmin.ping()) {
              return localIBPWAdmin;
            }
          }
          catch (Exception localException2)
          {
            DebugLog.throwing("Couldn't ping the BPW server for " + str, localException2);
          }
          removeBPWAdminHandler(localIBPWAdmin);
        }
      }
      catch (Throwable localThrowable)
      {
        Object localObject2;
        DebugLog.throwing("Couldn't get handler in BankSim service for " + str, localThrowable);
        if ((localContext != null) && (localContextPool != null)) {
          try
          {
            localContext = localContextPool.refreshContext(localContext);
            localObject2 = localContext.lookup(this.cA);
            BPWAdminHome localBPWAdminHome = (BPWAdminHome)PortableRemoteObject.narrow(localObject2, BPWAdminHome.class);
            localIBPWAdmin = localBPWAdminHome.create();
            localContextPool.returnContext(localContext);
            if (localIBPWAdmin != null)
            {
              try
              {
                if (localIBPWAdmin.ping()) {
                  return localIBPWAdmin;
                }
              }
              catch (Exception localException3)
              {
                DebugLog.throwing("Couldn't ping the BPW server for " + str, localException3);
              }
              removeBPWAdminHandler(localIBPWAdmin);
            }
          }
          catch (Exception localException1)
          {
            DebugLog.throwing("Couldn't refresh the contexts for " + str, localException1);
            if ((localContextPool != null) && (localContext != null)) {
              localContextPool.returnContext(localContext);
            }
          }
        }
      }
    }
    return null;
  }
  
  protected void removeBPWAdminHandler(IBPWAdmin paramIBPWAdmin)
  {
    if (paramIBPWAdmin != null) {
      try
      {
        if (debugService) {
          DebugLog.log(Level.INFO, "##################### Removing BPW Handler ################");
        }
        paramIBPWAdmin.remove();
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("Error removing bpw handler", localThrowable);
      }
    }
  }
  
  protected BPWServices getBPWHandler()
  {
    BPWServices localBPWServices = null;
    ContextPool localContextPool = null;
    Context localContext = null;
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
        DebugLog.log(Level.INFO, "##################### Getting BPW Handler ################");
        DebugLog.log(Level.INFO, "provider_url = " + str);
        localContextPool = getContextPool(str);
        localContext = localContextPool.getContext();
        Object localObject1 = localContext.lookup(this.cB);
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
            localObject2 = localContext.lookup(this.cB);
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
    return null;
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
  
  public ProcessingWindow addProcessingWindow(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    ProcessingWindowInfo localProcessingWindowInfo = null;
    try
    {
      localProcessingWindowInfo = paramProcessingWindow.getProcessingWindowInfo();
      DebugLog.log(Level.INFO, "*** BPW Adding ProcessingWindowInfo = [" + localProcessingWindowInfo.toString() + "]");
      localProcessingWindowInfo = localIBPWAdmin.addProcessingWindow(localProcessingWindowInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(2);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localProcessingWindowInfo.getStatusCode();
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Add Wire ProcessingWindow failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + localProcessingWindowInfo.getStatusCode());
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localProcessingWindowInfo.getStatusMsg());
      throw new PAException(jdMethod_byte(i));
    }
    paramProcessingWindow.setId(localProcessingWindowInfo.getWindowId());
    return paramProcessingWindow;
  }
  
  public ProcessingWindow modifyProcessingWindow(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    ProcessingWindowInfo localProcessingWindowInfo = null;
    try
    {
      localProcessingWindowInfo = paramProcessingWindow.getProcessingWindowInfo();
      DebugLog.log(Level.INFO, "*** BPW Modifying ProcessingWindowInfo = [" + localProcessingWindowInfo.toString() + "]");
      localProcessingWindowInfo = localIBPWAdmin.modProcessingWindow(localProcessingWindowInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(3);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localProcessingWindowInfo.getStatusCode();
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Modify Wire ProcessingWindow failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localProcessingWindowInfo.getStatusMsg());
      throw new PAException(jdMethod_byte(i));
    }
    return paramProcessingWindow;
  }
  
  public void deleteProcessingWindow(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    ProcessingWindowInfo localProcessingWindowInfo = null;
    try
    {
      localProcessingWindowInfo = paramProcessingWindow.getProcessingWindowInfo();
      localProcessingWindowInfo = localIBPWAdmin.delProcessingWindow(localProcessingWindowInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(4);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localProcessingWindowInfo.getStatusCode();
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Delete Wire ProcessingWindow failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localProcessingWindowInfo.getStatusMsg());
      throw new PAException(jdMethod_byte(i));
    }
  }
  
  public ProcessingWindows getProcessingWindows(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    ProcessingWindowList localProcessingWindowList = new ProcessingWindowList();
    try
    {
      String[] arrayOfString;
      if (!jdMethod_new(paramProcessingWindow.getBankId()))
      {
        arrayOfString = new String[] { paramProcessingWindow.getBankId() };
        localProcessingWindowList.setFIId(arrayOfString);
      }
      if (!jdMethod_new(paramProcessingWindow.getCustomerId()))
      {
        arrayOfString = new String[] { paramProcessingWindow.getCustomerId() };
        localProcessingWindowList.setCustomerId(arrayOfString);
      }
      if (!jdMethod_new(paramProcessingWindow.getPaymentType()))
      {
        arrayOfString = new String[] { paramProcessingWindow.getPaymentType() };
        localProcessingWindowList.setPmtType(arrayOfString);
      }
      if (!jdMethod_new(paramProcessingWindow.getPaymentSubType()))
      {
        arrayOfString = new String[] { paramProcessingWindow.getPaymentSubType() };
        localProcessingWindowList.setPmtSubType(arrayOfString);
      }
      localProcessingWindowList = localIBPWAdmin.getProcessingWindows(localProcessingWindowList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(5);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localProcessingWindowList.getStatusCode();
    if (i == 16020) {
      return new ProcessingWindows();
    }
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Get Wire ProcessingWindows failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localProcessingWindowList.getStatusMsg());
      throw new PAException(jdMethod_byte(i));
    }
    ProcessingWindowInfo[] arrayOfProcessingWindowInfo = localProcessingWindowList.getProcessingWindows();
    ProcessingWindows localProcessingWindows = new ProcessingWindows();
    if (arrayOfProcessingWindowInfo == null) {
      return localProcessingWindows;
    }
    for (int j = 0; j < arrayOfProcessingWindowInfo.length; j++)
    {
      ProcessingWindow localProcessingWindow = new ProcessingWindow();
      localProcessingWindow.setProcessingWindowInfo(arrayOfProcessingWindowInfo[j]);
      localProcessingWindows.add(localProcessingWindow);
    }
    return localProcessingWindows;
  }
  
  public FulfillmentSystems getFulfillmentSystems(SecureUser paramSecureUser, HashMap paramHashMap)
    throws PAException
  {
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    FulfillmentSystems localFulfillmentSystems = new FulfillmentSystems();
    try
    {
      FulfillmentInfo[] arrayOfFulfillmentInfo = localIBPWAdmin.getFulfillmentSystems();
      if ((arrayOfFulfillmentInfo != null) && (arrayOfFulfillmentInfo.length != 0)) {
        for (int i = 0; i < arrayOfFulfillmentInfo.length; i++)
        {
          FulfillmentSystem localFulfillmentSystem = new FulfillmentSystem();
          localFulfillmentSystem.setFulfillmentInfo(arrayOfFulfillmentInfo[i]);
          localFulfillmentSystems.add(localFulfillmentSystem);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(15);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    return localFulfillmentSystems;
  }
  
  public ArrayList getGlobalPayeeGroups(SecureUser paramSecureUser, HashMap paramHashMap)
    throws PAException
  {
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    ArrayList localArrayList = new ArrayList();
    try
    {
      String[] arrayOfString = localIBPWAdmin.getGlobalPayeeGroups();
      for (int i = 0; i < arrayOfString.length; i++) {
        localArrayList.add(arrayOfString[i]);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(54);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    return localArrayList;
  }
  
  public Payees searchGlobalPayees(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws PAException
  {
    Payees localPayees = new Payees();
    Payee localPayee = new Payee();
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    try
    {
      PayeeInfo localPayeeInfo = BeansConverter.payeeToPayeeInfo(paramPayee);
      PayeeInfo[] arrayOfPayeeInfo = localIBPWAdmin.searchGlobalPayees(localPayeeInfo, 7);
      for (int i = 0; i < arrayOfPayeeInfo.length; i++)
      {
        localPayee = BeansConverter.PayeeInfoToPayee(arrayOfPayeeInfo[i]);
        localPayees.add(localPayee);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(55);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    return localPayees;
  }
  
  public Payee getGlobalPayee(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    PayeeInfo localPayeeInfo = null;
    Payee localPayee = new Payee();
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    try
    {
      localPayeeInfo = localIBPWAdmin.getGlobalPayee(Integer.toString(paramInt));
      localPayee = BeansConverter.PayeeInfoToPayee(localPayeeInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(56);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localPayeeInfo.getStatusCode();
    if (i != 0) {
      throw new PAException(jdMethod_byte(i));
    }
    return localPayee;
  }
  
  public Payee modifyGlobalPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    PayeeInfo localPayeeInfo = null;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    Payee localPayee = new Payee();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    try
    {
      localPayeeInfo = BeansConverter.payeeToPayeeInfo(paramPayee);
      localPayeeInfo.setAgentId(String.valueOf(paramSecureUser.getProfileID()));
      localPayeeInfo.setAgentType(String.valueOf(paramSecureUser.getUserType()));
      localPayeeInfo = localIBPWAdmin.updateGlobalPayee(localPayeeInfo);
      localPayee = BeansConverter.PayeeInfoToPayee(localPayeeInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(59);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localPayeeInfo.getStatusCode();
    if (i != 0) {
      throw new PAException(jdMethod_byte(i));
    }
    return localPayee;
  }
  
  public ScheduleCategory getScheduleCategory(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    ScheduleCategoryInfo localScheduleCategoryInfo = null;
    try
    {
      localScheduleCategoryInfo = localIBPWAdmin.getScheduleCategoryInfo(paramString1, paramString2);
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      throw new PAException(6);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    ScheduleCategory localScheduleCategory = new ScheduleCategory();
    localScheduleCategory.setLocale(paramSecureUser.getLocale());
    i = localScheduleCategoryInfo.getStatusCode();
    if (i == 16020)
    {
      localScheduleCategory.setFIId(paramString1);
      localScheduleCategory.setCategory(paramString2);
      return localScheduleCategory;
    }
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Get Schedule Category Info failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localScheduleCategoryInfo.getStatusMsg());
      throw new PAException(6);
    }
    localScheduleCategory.setScheduleCategoryInfo(localScheduleCategoryInfo);
    ScheduleTypes localScheduleTypes = localScheduleCategory.getScheduleTypes();
    if (localScheduleTypes != null)
    {
      Iterator localIterator = localScheduleTypes.iterator();
      while (localIterator.hasNext())
      {
        ScheduleType localScheduleType = (ScheduleType)localIterator.next();
        localIBPWAdmin = getBPWAdminHandler();
        try
        {
          SchedulerInfo localSchedulerInfo = localIBPWAdmin.getSchedulerInfo(localScheduleType.getInstructionType(), localScheduleType.getFIId());
          if ((localSchedulerInfo != null) && (localSchedulerInfo.getStatusCode() == 0))
          {
            localScheduleType.setNextRunTime(localSchedulerInfo.NextRunTime);
            localScheduleType.setFinalRunTime(localSchedulerInfo.FinalRuntimeForNextProcessDate);
            Calendar localCalendar = Calendar.getInstance();
            TimeZone localTimeZone = localCalendar.getTimeZone();
            localScheduleType.setNextRunTimeZone(localTimeZone.getID());
          }
          if (localIBPWAdmin != null) {
            removeBPWAdminHandler(localIBPWAdmin);
          }
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
          throw new PAException(6);
        }
        finally
        {
          if (localIBPWAdmin != null) {
            removeBPWAdminHandler(localIBPWAdmin);
          }
        }
      }
    }
    return localScheduleCategory;
  }
  
  public ScheduleType addScheduleType(SecureUser paramSecureUser, ScheduleType paramScheduleType, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    InstructionType localInstructionType = null;
    try
    {
      localInstructionType = paramScheduleType.getInstructionTypeInfo();
      DebugLog.log(Level.INFO, "*** BPW Adding ScheduleType = [" + localInstructionType.toString() + "]");
      localIBPWAdmin.addScheduleConfig(localInstructionType);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(7);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localInstructionType.getStatusCode();
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Add ScheduleType failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localInstructionType.getStatusMsg());
      throw new PAException(jdMethod_byte(i));
    }
    return paramScheduleType;
  }
  
  public ScheduleType modifyScheduleType(SecureUser paramSecureUser, ScheduleType paramScheduleType, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    InstructionType localInstructionType = null;
    try
    {
      localInstructionType = paramScheduleType.getInstructionTypeInfo();
      DebugLog.log(Level.INFO, "*** BPW Modifying ScheduleType = [" + localInstructionType.toString() + "]");
      localIBPWAdmin.updateScheduleConfig(localInstructionType);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(8);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localInstructionType.getStatusCode();
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Modify ScheduleType failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localInstructionType.getStatusMsg());
      throw new PAException(jdMethod_byte(i));
    }
    return paramScheduleType;
  }
  
  public void deleteScheduleType(SecureUser paramSecureUser, ScheduleType paramScheduleType, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    InstructionType localInstructionType = null;
    try
    {
      localInstructionType = paramScheduleType.getInstructionTypeInfo();
      localIBPWAdmin.deleteScheduleConfig(localInstructionType);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(9);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localInstructionType.getStatusCode();
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Delete ScheduleType failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localInstructionType.getStatusMsg());
      throw new PAException(jdMethod_byte(i));
    }
  }
  
  public ScheduleType getScheduleType(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    InstructionType localInstructionType = null;
    try
    {
      localInstructionType = localIBPWAdmin.getScheduleConfig(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(10);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localInstructionType.getStatusCode();
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Get ScheduleType failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localInstructionType.getStatusMsg());
      throw new PAException(jdMethod_byte(i));
    }
    ScheduleType localScheduleType = new ScheduleType();
    localScheduleType.setLocale(paramSecureUser.getLocale());
    localScheduleType.setInstructionTypeInfo(localInstructionType);
    return localScheduleType;
  }
  
  public CutOffTime addCutOffTime(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    CutOffInfo localCutOffInfo = null;
    try
    {
      localCutOffInfo = paramCutOffTime.getCutOffInfo();
      DebugLog.log(Level.INFO, "*** BPW Adding CutOffInfo = [" + localCutOffInfo.toString() + "]");
      localCutOffInfo = localIBPWAdmin.addCutOff(localCutOffInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(11);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localCutOffInfo.getStatusCode();
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Add CutOff failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localCutOffInfo.getStatusMsg());
      throw new PAException(jdMethod_byte(i));
    }
    paramCutOffTime.setCutOffId(localCutOffInfo.getCutOffId());
    return paramCutOffTime;
  }
  
  public CutOffTime modifyCutOffTime(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    CutOffInfo localCutOffInfo = null;
    try
    {
      localCutOffInfo = paramCutOffTime.getCutOffInfo();
      DebugLog.log(Level.INFO, "*** BPW Modifying CutOffInfo = [" + localCutOffInfo.toString() + "]");
      localCutOffInfo = localIBPWAdmin.modCutOff(localCutOffInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(12);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localCutOffInfo.getStatusCode();
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Modify CutOff failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localCutOffInfo.getStatusMsg());
      throw new PAException(jdMethod_byte(i));
    }
    return paramCutOffTime;
  }
  
  public void deleteCutOffTime(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    CutOffInfo localCutOffInfo = null;
    try
    {
      localCutOffInfo = paramCutOffTime.getCutOffInfo();
      localCutOffInfo = localIBPWAdmin.deleteCutOff(localCutOffInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(13);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localCutOffInfo.getStatusCode();
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Delete CutOff failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localCutOffInfo.getStatusMsg());
      throw new PAException(jdMethod_byte(i));
    }
  }
  
  public CutOffTimes getCutOffTimes(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    CutOffInfoList localCutOffInfoList = new CutOffInfoList();
    try
    {
      if (!jdMethod_new(paramCutOffTime.getFIId())) {
        localCutOffInfoList.setFIId(paramCutOffTime.getFIId());
      }
      if (!jdMethod_new(paramCutOffTime.getInstructionType())) {
        localCutOffInfoList.setInstructionType(paramCutOffTime.getInstructionType());
      }
      String[] arrayOfString;
      if (!jdMethod_new(paramCutOffTime.getStatus()))
      {
        arrayOfString = new String[] { paramCutOffTime.getStatus() };
        localCutOffInfoList.setStatusList(arrayOfString);
      }
      if ((paramHashMap != null) && (paramHashMap.get("CutOffIDList") != null))
      {
        arrayOfString = (String[])paramHashMap.get("CutOffIDList");
        localCutOffInfoList.setCutOffIdList(arrayOfString);
      }
      localCutOffInfoList = localIBPWAdmin.getCutOffList(localCutOffInfoList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(14);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localCutOffInfoList.getStatusCode();
    if (i == 16020) {
      return new CutOffTimes();
    }
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Get CutOffTimes failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localCutOffInfoList.getStatusMsg());
      throw new PAException(jdMethod_byte(i));
    }
    CutOffInfo[] arrayOfCutOffInfo = localCutOffInfoList.getCutOffInfoList();
    CutOffTimes localCutOffTimes = new CutOffTimes();
    if (arrayOfCutOffInfo == null) {
      return localCutOffTimes;
    }
    for (int j = 0; j < arrayOfCutOffInfo.length; j++)
    {
      CutOffTime localCutOffTime = new CutOffTime();
      localCutOffTime.setCutOffInfo(arrayOfCutOffInfo[j]);
      localCutOffTimes.add(localCutOffTime);
    }
    return localCutOffTimes;
  }
  
  public void resubmitSchedule(String paramString1, String paramString2, String paramString3, String paramString4)
    throws PAException
  {
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    try
    {
      if (jdMethod_new(paramString4) == true) {
        localIBPWAdmin.resubmitEvent(paramString2, paramString1, paramString3);
      } else {
        localIBPWAdmin.resubmitEvent(paramString2, paramString1, paramString3, paramString4);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(16);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
  }
  
  public void runSchedule(String paramString1, String paramString2)
    throws PAException
  {
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    try
    {
      localIBPWAdmin.runBatchProcess(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(17);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
  }
  
  public CutOffActivities getCutOffActivities(SecureUser paramSecureUser, String paramString, PagingContext paramPagingContext, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    CutOffActivityInfoList localCutOffActivityInfoList = new CutOffActivityInfoList();
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss");
    try
    {
      if (!jdMethod_new(paramString))
      {
        localObject1 = new String[] { paramString };
        localCutOffActivityInfoList.setCutOffIdList((String[])localObject1);
      }
      Object localObject1 = null;
      if (paramPagingContext.getStartDate() != null)
      {
        localObject1 = localDateFormat.format(paramPagingContext.getStartDate().getTime());
      }
      else
      {
        localObject2 = GregorianCalendar.getInstance();
        localObject1 = localDateFormat.format(((Calendar)localObject2).getTime());
      }
      localCutOffActivityInfoList.setStartDate((String)localObject1);
      localObject2 = null;
      if (paramPagingContext.getEndDate() != null)
      {
        localObject2 = localDateFormat.format(paramPagingContext.getEndDate().getTime());
      }
      else
      {
        Calendar localCalendar = GregorianCalendar.getInstance();
        localObject2 = localDateFormat.format(localCalendar.getTime());
      }
      localCutOffActivityInfoList.setEndDate((String)localObject2);
      localCutOffActivityInfoList = localIBPWAdmin.getCutOffActivityList(localCutOffActivityInfoList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(18);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localCutOffActivityInfoList.getStatusCode();
    if (i == 16020) {
      return new CutOffActivities();
    }
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Get CutOffActivities failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localCutOffActivityInfoList.getStatusMsg());
      throw new PAException(18);
    }
    CutOffActivityInfo[] arrayOfCutOffActivityInfo = localCutOffActivityInfoList.getCutOffActivity();
    Object localObject2 = new CutOffActivities();
    if (arrayOfCutOffActivityInfo == null) {
      return localObject2;
    }
    for (int j = 0; j < arrayOfCutOffActivityInfo.length; j++)
    {
      CutOffActivity localCutOffActivity = ((CutOffActivities)localObject2).add();
      localCutOffActivity.setCutOffActivityInfo(arrayOfCutOffActivityInfo[j]);
    }
    return localObject2;
  }
  
  public void rerunCutOffProcess(SecureUser paramSecureUser, CutOffTime paramCutOffTime, String paramString, HashMap paramHashMap)
    throws PAException
  {
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    try
    {
      localIBPWAdmin.rerunCutOff(paramCutOffTime.getFIId(), paramCutOffTime.getInstructionType(), paramCutOffTime.getCutOffId(), paramString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(19);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
  }
  
  public String getGeneratedFileName(SecureUser paramSecureUser, CutOffTime paramCutOffTime, String paramString, HashMap paramHashMap)
    throws PAException
  {
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    try
    {
      String str = localIBPWAdmin.getGeneratedFileName(paramCutOffTime.getFIId(), paramCutOffTime.getInstructionType(), paramString);
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(20);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
  }
  
  public void cleanup(HashMap paramHashMap)
    throws PAException
  {
    HashMap localHashMap1 = null;
    HashMap localHashMap2 = null;
    HashMap localHashMap3 = null;
    HashMap localHashMap4 = null;
    HashMap localHashMap5 = null;
    Properties localProperties = null;
    EntitlementGroups localEntitlementGroups1 = null;
    DRKey localDRKey = null;
    DRSetting localDRSetting = null;
    String str1 = " ";
    localProperties = new Properties();
    localProperties.setProperty("AchPayments", "ACHBATCHTRN");
    localProperties.setProperty("BillPayments", "PMTTRN");
    localProperties.setProperty("CashConcentration", "CASHCONTRN");
    localProperties.setProperty("TaxPayments", "TAXTRN");
    localProperties.setProperty("ChildSupport", "CHILDSUPPORTTRN");
    localProperties.setProperty("Transfers", "INTRATRN");
    localProperties.setProperty("ExternalTransfers", "ETFTRN");
    localProperties.setProperty("Wires", "WIRETRN");
    try
    {
      localHashMap1 = SystemAdminAdapter.getDataRetentionSettings(0, 0, paramHashMap);
      localEntitlementGroups1 = Entitlements.getEntitlementGroupsByType("MarketSegment");
      Iterator localIterator1 = localEntitlementGroups1.iterator();
      while (localIterator1.hasNext())
      {
        EntitlementGroup localEntitlementGroup1 = (EntitlementGroup)localIterator1.next();
        EntitlementGroups localEntitlementGroups2 = Entitlements.getChildrenByGroupType(localEntitlementGroup1.getGroupId(), "ServicesPackage");
        localHashMap3 = SystemAdminAdapter.getDataRetentionSettings(2, localEntitlementGroup1.getGroupId(), paramHashMap);
        Iterator localIterator2 = localEntitlementGroups2.iterator();
        while (localIterator2.hasNext())
        {
          EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localIterator2.next();
          EntitlementGroups localEntitlementGroups3 = Entitlements.getChildrenByGroupType(localEntitlementGroup2.getGroupId(), "BusinessAdmin");
          EntitlementGroups localEntitlementGroups4 = Entitlements.getChildrenByGroupType(localEntitlementGroup2.getGroupId(), "ConsumerAdmin");
          localHashMap4 = SystemAdminAdapter.getDataRetentionSettings(3, localEntitlementGroup2.getGroupId(), paramHashMap);
          Iterator localIterator3 = localEntitlementGroups3.iterator();
          EntitlementGroup localEntitlementGroup3;
          Object localObject1;
          Iterator localIterator4;
          Object localObject2;
          Object localObject3;
          Object localObject4;
          Object localObject5;
          ArrayList localArrayList1;
          Object localObject6;
          Object localObject7;
          while (localIterator3.hasNext())
          {
            localEntitlementGroup3 = (EntitlementGroup)localIterator3.next();
            localObject1 = Entitlements.getMembers(localEntitlementGroup3.getGroupId());
            localIterator4 = ((EntitlementGroupMembers)localObject1).iterator();
            while (localIterator4.hasNext())
            {
              localObject2 = (EntitlementGroupMember)localIterator4.next();
              int i = -1;
              localObject3 = null;
              localObject4 = null;
              HashMap localHashMap6 = null;
              try
              {
                i = Integer.parseInt(((EntitlementGroupMember)localObject2).getId());
              }
              catch (Exception localException2)
              {
                i = -1;
              }
              localObject3 = BusinessAdapter.getBusiness(i);
              if (localObject3 != null)
              {
                localObject5 = null;
                localArrayList1 = null;
                localObject6 = null;
                localHashMap5 = SystemAdminAdapter.getDataRetentionSettings(4, ((Business)localObject3).getIdValue(), paramHashMap);
                localHashMap2 = SystemAdminAdapter.getDataRetentionSettings(1, ((Business)localObject3).getAffiliateBankID(), paramHashMap);
                localObject4 = new ArrayList(5);
                ((ArrayList)localObject4).add(0, localHashMap5);
                ((ArrayList)localObject4).add(0, localHashMap4);
                ((ArrayList)localObject4).add(0, localHashMap3);
                ((ArrayList)localObject4).add(0, localHashMap2);
                ((ArrayList)localObject4).add(0, localHashMap1);
                localHashMap6 = SystemAdminUtil.getOverriddenDataRetentionSettings((ArrayList)localObject4, paramHashMap);
                localObject5 = new ArrayList();
                localArrayList1 = new ArrayList();
                Iterator localIterator5 = localProperties.keySet().iterator();
                while (localIterator5.hasNext())
                {
                  localObject7 = (String)localIterator5.next();
                  localDRKey = new DRKey();
                  localDRKey.setDataType((String)localObject7);
                  localDRKey.setDataClass(str1);
                  localDRSetting = (DRSetting)localHashMap6.get(localDRKey);
                  if (localDRSetting != null)
                  {
                    ((ArrayList)localObject5).add(localProperties.getProperty((String)localObject7));
                    localArrayList1.add(Integer.toString(localDRSetting.getRetentionDays()));
                  }
                }
                if ((((ArrayList)localObject5).size() > 0) && (localArrayList1.size() > 0))
                {
                  localObject6 = getBPWAdminHandler();
                  if (localObject6 == null) {
                    throw new PAException(1);
                  }
                  try
                  {
                    ((IBPWAdmin)localObject6).cleanup(Integer.toString(((Business)localObject3).getIdValue()), (ArrayList)localObject5, localArrayList1, paramHashMap);
                  }
                  catch (Exception localException3)
                  {
                    localException3.printStackTrace();
                    throw new PAException(21);
                  }
                  finally
                  {
                    if (localObject6 != null) {
                      removeBPWAdminHandler((IBPWAdmin)localObject6);
                    }
                  }
                }
              }
            }
          }
          localIterator3 = localEntitlementGroups4.iterator();
          while (localIterator3.hasNext())
          {
            localEntitlementGroup3 = (EntitlementGroup)localIterator3.next();
            localObject1 = Entitlements.getChildrenByGroupType(localEntitlementGroup3.getGroupId(), "USER");
            localIterator4 = ((EntitlementGroups)localObject1).iterator();
            while (localIterator4.hasNext())
            {
              localObject2 = (EntitlementGroup)localIterator4.next();
              EntitlementGroupMembers localEntitlementGroupMembers = Entitlements.getMembers(((EntitlementGroup)localObject2).getGroupId());
              localObject3 = localEntitlementGroupMembers.iterator();
              while (((Iterator)localObject3).hasNext())
              {
                localObject4 = (EntitlementGroupMember)((Iterator)localObject3).next();
                int j = -1;
                localObject5 = null;
                localArrayList1 = null;
                localObject6 = null;
                try
                {
                  j = Integer.parseInt(((EntitlementGroupMember)localObject4).getId());
                }
                catch (Exception localException4)
                {
                  j = -1;
                }
                localObject5 = CustomerAdapter.getUserById(j);
                if (localObject5 != null)
                {
                  ArrayList localArrayList2 = null;
                  localObject7 = null;
                  IBPWAdmin localIBPWAdmin = null;
                  localHashMap2 = SystemAdminAdapter.getDataRetentionSettings(1, ((User)localObject5).getAffiliateBankID(), paramHashMap);
                  localArrayList1 = new ArrayList(5);
                  localArrayList1.add(0, localHashMap4);
                  localArrayList1.add(0, localHashMap3);
                  localArrayList1.add(0, localHashMap2);
                  localArrayList1.add(0, localHashMap1);
                  localObject6 = SystemAdminUtil.getOverriddenDataRetentionSettings(localArrayList1, paramHashMap);
                  localArrayList2 = new ArrayList();
                  localObject7 = new ArrayList();
                  Iterator localIterator6 = localProperties.keySet().iterator();
                  while (localIterator6.hasNext())
                  {
                    String str2 = (String)localIterator6.next();
                    localDRKey = new DRKey();
                    localDRKey.setDataType(str2);
                    localDRKey.setDataClass(str1);
                    localDRSetting = (DRSetting)((HashMap)localObject6).get(localDRKey);
                    if (localDRSetting != null)
                    {
                      localArrayList2.add(localProperties.getProperty(str2));
                      ((ArrayList)localObject7).add(Integer.toString(localDRSetting.getRetentionDays()));
                    }
                  }
                  if ((localArrayList2.size() > 0) && (((ArrayList)localObject7).size() > 0))
                  {
                    localIBPWAdmin = getBPWAdminHandler();
                    if (localIBPWAdmin == null) {
                      throw new PAException(1);
                    }
                    try
                    {
                      localIBPWAdmin.cleanup(((User)localObject5).getId(), localArrayList2, (ArrayList)localObject7, paramHashMap);
                    }
                    catch (Exception localException5)
                    {
                      localException5.printStackTrace();
                      throw new PAException(21);
                    }
                    finally
                    {
                      if (localIBPWAdmin != null) {
                        removeBPWAdminHandler(localIBPWAdmin);
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      throw new PAException(21);
    }
  }
  
  public ScheduleActivities getScheduleActivities(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new PAException(1);
    }
    ScheduleActivityList localScheduleActivityList = new ScheduleActivityList();
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss");
    try
    {
      if (!jdMethod_new(paramString2))
      {
        localObject1 = new String[] { paramString2 };
        localScheduleActivityList.setInstructionTypeList((String[])localObject1);
      }
      if (!jdMethod_new(paramString1)) {
        localScheduleActivityList.setFIID(paramString1);
      }
      Object localObject1 = null;
      if (paramPagingContext.getStartDate() != null)
      {
        localObject1 = localDateFormat.format(paramPagingContext.getStartDate().getTime());
      }
      else
      {
        localObject2 = GregorianCalendar.getInstance();
        localObject1 = localDateFormat.format(((Calendar)localObject2).getTime());
      }
      localScheduleActivityList.setFromDate((String)localObject1);
      localObject2 = null;
      if (paramPagingContext.getEndDate() != null)
      {
        localObject2 = localDateFormat.format(paramPagingContext.getEndDate().getTime());
      }
      else
      {
        Calendar localCalendar = GregorianCalendar.getInstance();
        localObject2 = localDateFormat.format(localCalendar.getTime());
      }
      localScheduleActivityList.setToDate((String)localObject2);
      localScheduleActivityList = localIBPWAdmin.getScheduleActivityList(localScheduleActivityList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(51);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localScheduleActivityList.getStatusCode();
    if (i == 16020) {
      return new ScheduleActivities();
    }
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Get ScheduleActivities failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localScheduleActivityList.getStatusMsg());
      throw new PAException(51);
    }
    ScheduleActivities localScheduleActivities = new ScheduleActivities();
    Object localObject2 = localScheduleActivityList.getScheduleActivity();
    if (localObject2 == null) {
      return localScheduleActivities;
    }
    for (int j = 0; j < localObject2.length; j++)
    {
      ScheduleActivity localScheduleActivity = localScheduleActivities.add();
      localScheduleActivity.setScheduleActivityInfo(localObject2[j]);
    }
    return localScheduleActivities;
  }
  
  private static int jdMethod_byte(int paramInt)
  {
    int i = 0;
    switch (paramInt)
    {
    case 26001: 
      i = 23;
      break;
    case 27001: 
      i = 23;
      break;
    case 26002: 
      i = 24;
      break;
    case 26003: 
      i = 25;
      break;
    case 26004: 
      i = 26;
      break;
    case 26005: 
      i = 27;
      break;
    case 26006: 
      i = 28;
      break;
    case 26007: 
      i = 29;
      break;
    case 26008: 
      i = 30;
      break;
    case 26009: 
      i = 31;
      break;
    case 26010: 
      i = 32;
      break;
    case 26011: 
      i = 33;
      break;
    case 26012: 
      i = 34;
      break;
    case 26013: 
      i = 35;
      break;
    case 26014: 
      i = 36;
      break;
    case 26015: 
      i = 37;
      break;
    case 26016: 
      i = 38;
      break;
    case 26017: 
      i = 39;
      break;
    case 26018: 
      i = 40;
      break;
    case 26019: 
      i = 42;
      break;
    case 26020: 
      i = 43;
      break;
    case 26021: 
      i = 44;
      break;
    case 26022: 
      i = 45;
      break;
    case 19700: 
      i = 52;
      break;
    case 19710: 
      i = 46;
      break;
    case 19720: 
      i = 47;
      break;
    case 19730: 
      i = 48;
      break;
    case 19740: 
      i = 49;
      break;
    case 19750: 
      i = 50;
      break;
    case 26043: 
      i = 59;
      break;
    case 26044: 
      i = 56;
      break;
    case 26042: 
      i = 58;
      break;
    case 26045: 
      i = 61;
      break;
    case 26046: 
      i = 62;
    }
    return i;
  }
  
  private static boolean jdMethod_new(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }
  
  public com.ffusion.beans.banking.BankingDays getBankingDaysInRange(com.ffusion.beans.banking.BankingDays paramBankingDays, HashMap paramHashMap)
    throws PAException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null)
    {
      DebugLog.log(Level.SEVERE, "*** Cannot get banking days, unable to get BPW handler.");
      throw new PAException(53);
    }
    com.ffusion.ffs.bpw.interfaces.BankingDays localBankingDays = null;
    try
    {
      localBankingDays = (com.ffusion.ffs.bpw.interfaces.BankingDays)BeansConverter.efsToBpwBean(paramBankingDays, paramHashMap);
      localBankingDays = localBPWServices.getBankingDaysInRange(localBankingDays, paramHashMap);
      paramBankingDays = (com.ffusion.beans.banking.BankingDays)BeansConverter.bpwToEfsBean(localBankingDays, paramHashMap);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(53);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    return paramBankingDays;
  }
  
  public void addGlobalPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null)
    {
      DebugLog.log(Level.SEVERE, "*** Cannot add global payee, unable to get BPW handler.");
      throw new PAException(1);
    }
    PayeeInfo localPayeeInfo;
    try
    {
      localPayeeInfo = BeansConverter.payeeToPayeeInfo(paramPayee);
      localPayeeInfo.setAgentId(String.valueOf(paramSecureUser.getProfileID()));
      localPayeeInfo.setAgentType(String.valueOf(paramSecureUser.getUserType()));
      localPayeeInfo = localIBPWAdmin.addGlobalPayee(localPayeeInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(57);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localPayeeInfo.getStatusCode();
    if (i != 0) {
      throw new PAException(jdMethod_byte(i));
    }
  }
  
  public void deleteGlobalPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws PAException
  {
    int i = -1;
    PayeeInfo localPayeeInfo = new PayeeInfo();
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null)
    {
      DebugLog.log(Level.SEVERE, "*** Cannot delete global payee, unable to get BPW handler.");
      throw new PAException(1);
    }
    try
    {
      localPayeeInfo = BeansConverter.payeeToPayeeInfo(paramPayee);
      localPayeeInfo.setAgentId(String.valueOf(paramSecureUser.getProfileID()));
      localPayeeInfo.setAgentType(String.valueOf(paramSecureUser.getUserType()));
      localPayeeInfo = localIBPWAdmin.deleteGlobalPayee(localPayeeInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new PAException(60);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localPayeeInfo.getStatusCode();
    if (i != 0) {
      throw new PAException(jdMethod_byte(i));
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
      if (paramString1.equals("BPWAdminCallBackJNDIName")) {
        PaymentsAdminService.this.cA = paramString2;
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.banksim2.PaymentsAdminService
 * JD-Core Version:    0.7.0.1
 */