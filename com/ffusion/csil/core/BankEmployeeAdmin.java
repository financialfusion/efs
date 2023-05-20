package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

public class BankEmployeeAdmin
  extends Initialize
{
  private static Entitlement avZ = new Entitlement("BankEmployeeCreate", null, null);
  private static Entitlement avV = new Entitlement("BankEmployeeEdit", null, null);
  private static Entitlement avE = new Entitlement("BankEmployeeDelete", null, null);
  private static Entitlement avI = new Entitlement("BankEmployeeView", null, null);
  private static Entitlement avL = new Entitlement("BankEmployeeAdmin", null, null);
  private static Entitlement av2 = new Entitlement("HistoryCreate", null, null);
  private static Entitlement avA = new Entitlement("HistoryView", null, null);
  private static final String av4 = "com.ffusion.util.logging.audit.bankemployeeadmin";
  private static final String avR = "AuditMessage_1";
  private static final String avy = "AuditMessage_2";
  private static final String avB = "AuditMessage_3";
  private static final String avt = "AuditMessage_4";
  private static final String avP = "AuditMessage_5";
  private static final String avr = "AuditMessage_6";
  private static final String avO = "AuditMessage_7";
  private static final String av0 = "AuditMessage_8";
  private static final String avz = "AuditMessage_9";
  private static final String avx = "AuditMessage_10";
  private static final String avQ = "AuditMessage_11";
  private static final String avT = "AuditMessage_12";
  private static final String avW = "AuditMessage_13";
  private static final String avG = "AuditMessage_14";
  private static final String av3 = "AuditMessage_15";
  private static final String avK = "AuditMessage_16";
  private static final String av1 = "AuditMessage_17";
  private static final String avC = "AuditMessage_18";
  private static final String avD = "AuditMessage_19";
  private static final String avS = "AuditMessage_20";
  private static final String avM = "AuditMessage_21";
  private static final String avN = "AuditMessage_22";
  private static final String avu = "AuditMessage_23";
  private static final String avF = "AuditMessage_24";
  private static final String avs = "AuditMessage_25";
  private static final String avw = "AuditMessage_26";
  private static final String avH = "AuditMessage_27";
  private static final String avU = "AuditMessage_28";
  private static final String avv = "AuditMessage_29";
  private static final String avJ = "AuditMessage_30";
  private static final String avX = "AuditMessage_31";
  private static final String avY = "AuditMessage_32";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.BankEmployeeAdmin.initialize");
    com.ffusion.csil.handlers.BankEmployeeAdmin.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.BankEmployeeAdmin.getService();
  }
  
  public static BankEmployee addBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BankEmployeeAdmin.AddBankEmployee";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, avZ))
    {
      EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(localEntitlementGroupMember, paramBankEmployee.getJobTypeId());
      if (Entitlements.canAdminister(localEntitlementAdmin))
      {
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        BankEmployee localBankEmployee = com.ffusion.csil.handlers.BankEmployeeAdmin.addBankEmployee(paramSecureUser, paramBankEmployee, paramHashMap);
        Entitlements.addMember(localEntitlementGroupMember, localBankEmployee.getEntitlementGroupMember());
        PerfLog.log(str1, l, true);
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = paramBankEmployee.getUserName();
        LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_2", arrayOfObject2);
        audit(paramSecureUser, localLocalizableString2, str2, 1800);
        debug(paramSecureUser, str1);
        return localBankEmployee;
      }
      Object[] arrayOfObject1 = new Object[0];
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_1", arrayOfObject1);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    throw new CSILException(str1, 20001);
  }
  
  public static BankEmployee getBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.GetBankEmployee";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avI))
    {
      long l = System.currentTimeMillis();
      BankEmployee localBankEmployee = com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployee(paramSecureUser, paramBankEmployee, paramHashMap);
      jdMethod_for(localBankEmployee);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBankEmployee;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_3", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static BankEmployee getBankEmployeeById(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.GetBankEmployeeById";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avI)))
    {
      long l = System.currentTimeMillis();
      BankEmployee localBankEmployee = com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, paramBankEmployee, paramHashMap);
      jdMethod_for(localBankEmployee);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBankEmployee;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_4", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static BankEmployees getBankEmployeesByJobTypeId(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    return getBankEmployeesByJobTypeId(paramSecureUser, 0, paramInt, paramHashMap);
  }
  
  public static BankEmployees getBankEmployeesByJobTypeId(SecureUser paramSecureUser, int paramInt1, HashMap paramHashMap, int paramInt2)
    throws CSILException
  {
    return getBankEmployeesByJobTypeId(paramSecureUser, paramInt2, paramInt1, paramHashMap);
  }
  
  public static BankEmployees getBankEmployeesByJobTypeId(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BankEmployeeAdmin.GetBankEmployeesByJobTypeId";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avI)))
    {
      long l = System.currentTimeMillis();
      ArrayList localArrayList1 = new ArrayList();
      EntitlementGroupMembers localEntitlementGroupMembers = Entitlements.getMembers(paramInt2);
      Iterator localIterator = localEntitlementGroupMembers.iterator();
      while (localIterator.hasNext())
      {
        localObject = (EntitlementGroupMember)localIterator.next();
        if (((EntitlementGroupMember)localObject).getMemberType().equalsIgnoreCase("User") == true) {
          localArrayList1.add(((EntitlementGroupMember)localObject).getId());
        }
      }
      Object localObject = com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployeesByIds(paramSecureUser, localArrayList1, paramHashMap);
      if ((localObject != null) && (paramInt1 != 0))
      {
        int i = 0;
        BankEmployee localBankEmployee = null;
        ArrayList localArrayList2 = null;
        String str2 = String.valueOf(paramInt1);
        ListIterator localListIterator = ((BankEmployees)localObject).listIterator();
        while (localListIterator.hasNext())
        {
          i = 0;
          localBankEmployee = (BankEmployee)localListIterator.next();
          if ((str2.equals(localBankEmployee.getDefaultAffiliateBankId())) || (localBankEmployee.getDefaultAffiliateBankId().equals("0")))
          {
            i = 1;
          }
          else
          {
            localArrayList2 = localBankEmployee.getAffiliateBankIds();
            for (int j = 0; (j < localArrayList2.size()) && (i == 0); j++) {
              if (str2.equals(localArrayList2.get(j))) {
                i = 1;
              }
            }
          }
          if (i == 0) {
            localListIterator.remove();
          }
        }
      }
      jdMethod_for((BankEmployees)localObject);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localObject;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_5", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static BankEmployees getBankEmployees(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.GetBankEmployees";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avI)))
    {
      long l = System.currentTimeMillis();
      BankEmployees localBankEmployees = com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployees(paramSecureUser, paramBankEmployee, paramHashMap);
      jdMethod_for(localBankEmployees);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBankEmployees;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_6", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static String getDisplayCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "BankEmployeeAdmin.getDisplayCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avI))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.BankEmployeeAdmin.getDisplayCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_7", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static String getMaxResultCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "BankEmployeeAdmin.getMaxResultCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avI))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.BankEmployeeAdmin.getMaxResultCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_8", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static boolean hasDirectReports(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.hasDirectReports";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avI)))
    {
      long l = System.currentTimeMillis();
      boolean bool = com.ffusion.csil.handlers.BankEmployeeAdmin.hasDirectReports(paramSecureUser, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return bool;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_9", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static boolean hasDirectReports(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.hasDirectReports";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avI)))
    {
      long l = System.currentTimeMillis();
      boolean bool = com.ffusion.csil.handlers.BankEmployeeAdmin.hasDirectReports(paramSecureUser, paramInt1, paramInt2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return bool;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_9", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static BankEmployees getBankEmployeeList(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.GetBankEmployeeList";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avI)))
    {
      long l = System.currentTimeMillis();
      int i = 0;
      BankEmployees localBankEmployees1 = new BankEmployees(paramSecureUser.getLocale());
      BankEmployees localBankEmployees2 = com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployeeList(paramSecureUser, paramBankEmployee, paramHashMap);
      jdMethod_for(localBankEmployees2);
      if (paramBankEmployee != null) {
        i = paramBankEmployee.getJobTypeId();
      }
      if (i > 0)
      {
        Iterator localIterator = localBankEmployees2.iterator();
        while (localIterator.hasNext())
        {
          BankEmployee localBankEmployee1 = (BankEmployee)localIterator.next();
          if (localBankEmployee1.getJobTypeId() == i)
          {
            BankEmployee localBankEmployee2 = localBankEmployees1.add();
            localBankEmployee2.set(localBankEmployee1);
          }
        }
      }
      else
      {
        localBankEmployees1.set(localBankEmployees2);
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBankEmployees1;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_10", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static String getBankEmployeeCount(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BankEmployeeAdmin.getBankEmployeeCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avI))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployeeCount(paramSecureUser, paramBankEmployee, paramHashMap);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_11", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static BankEmployee modifyBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.ModifyBankEmployee";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Object localObject2;
    Object localObject3;
    Object localObject4;
    if ((!String.valueOf(paramSecureUser.getProfileID()).equals(paramBankEmployee.getId())) && (Entitlements.checkEntitlement(localEntitlementGroupMember, avV)))
    {
      EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(localEntitlementGroupMember, paramBankEmployee.getJobTypeId());
      if (Entitlements.canAdminister(localEntitlementAdmin))
      {
        localObject1 = Entitlements.getMember(paramBankEmployee.getEntitlementGroupMember());
        localObject2 = new EntitlementAdmin(localEntitlementGroupMember, ((EntitlementGroupMember)localObject1).getEntitlementGroupId());
        if (Entitlements.canAdminister((EntitlementAdmin)localObject2))
        {
          long l2 = System.currentTimeMillis();
          localObject3 = TrackingIDGenerator.GetNextID();
          localObject4 = com.ffusion.csil.handlers.BankEmployeeAdmin.modifyBankEmployee(paramSecureUser, paramBankEmployee, paramHashMap);
          if (((EntitlementGroupMember)localObject1).getEntitlementGroupId() != ((BankEmployee)localObject4).getEntitlementGroupMember().getEntitlementGroupId()) {
            Entitlements.modifyMember(localEntitlementGroupMember, ((BankEmployee)localObject4).getEntitlementGroupMember());
          }
          PerfLog.log(str, l2, true);
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = paramBankEmployee.getUserName();
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_13", arrayOfObject2);
          audit(paramSecureUser, localLocalizableString, (String)localObject3, 1801);
          debug(paramSecureUser, str);
          return localObject4;
        }
        throw new CSILException(str, 20001);
      }
      throw new CSILException(str, 20001);
    }
    if (String.valueOf(paramSecureUser.getProfileID()).equals(paramBankEmployee.getId()))
    {
      long l1 = System.currentTimeMillis();
      localObject2 = TrackingIDGenerator.GetNextID();
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      int i = Password.validatePassword(paramBankEmployee.getPassword(), 2, paramHashMap);
      if (i != 0) {
        throw new CSILException(str, 19002);
      }
      paramHashMap.put("SELF_MODIFY", "1");
      BankEmployee localBankEmployee = com.ffusion.csil.handlers.BankEmployeeAdmin.modifyBankEmployee(paramSecureUser, paramBankEmployee, paramHashMap);
      PerfLog.log(str, l1, true);
      localObject3 = new Object[1];
      localObject3[0] = paramBankEmployee.getUserName();
      localObject4 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_14", (Object[])localObject3);
      audit(paramSecureUser, (ILocalizable)localObject4, (String)localObject2, 1801);
      debug(paramSecureUser, str);
      return localBankEmployee;
    }
    Object[] arrayOfObject1 = new Object[0];
    Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_12", arrayOfObject1);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static BankEmployee deleteBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BankEmployeeAdmin.DeleteBankEmployee";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, avE))
    {
      localObject = new EntitlementAdmin(localEntitlementGroupMember, paramBankEmployee.getJobTypeId());
      if (Entitlements.canAdminister((EntitlementAdmin)localObject))
      {
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        BankEmployee localBankEmployee = com.ffusion.csil.handlers.BankEmployeeAdmin.deleteBankEmployee(paramSecureUser, paramBankEmployee, paramHashMap);
        PerfLog.log(str1, l, true);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramBankEmployee.getUserName();
        LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_16", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, str2, 1802);
        debug(paramSecureUser, str1);
        return localBankEmployee;
      }
      throw new CSILException(str1, 20001);
    }
    Object localObject = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_15", (Object[])localObject);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Histories addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.AddHistory";
    long l = System.currentTimeMillis();
    Histories localHistories = com.ffusion.csil.handlers.BankEmployeeAdmin.addHistory(paramSecureUser, paramHistories, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localHistories;
  }
  
  public static Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.GetHistory";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avA))
    {
      long l = System.currentTimeMillis();
      Histories localHistories = com.ffusion.csil.handlers.BankEmployeeAdmin.getHistory(paramSecureUser, paramHistory, paramCalendar1, paramCalendar2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localHistories;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_17", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.GetHistory";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avA))
    {
      long l = System.currentTimeMillis();
      Histories localHistories = com.ffusion.csil.handlers.BankEmployeeAdmin.getHistory(paramSecureUser, paramHistory, paramCalendar1, paramCalendar2, paramInt1, paramInt2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localHistories;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_17", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static BankEmployee redistributeWorkloads(SecureUser paramSecureUser, BankEmployee paramBankEmployee1, BankEmployee paramBankEmployee2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BankEmployeeAdmin.redistributeWorkloads";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avL))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      BankEmployee localBankEmployee = com.ffusion.csil.handlers.BankEmployeeAdmin.redistributeWorkloads(paramSecureUser, paramBankEmployee1, paramBankEmployee2, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramBankEmployee2.getName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_19", arrayOfObject2);
      logEntitlementFault(paramSecureUser, localLocalizableString2, TrackingIDGenerator.GetNextID());
      audit(paramSecureUser, localLocalizableString2, str2, 1804);
      debug(paramSecureUser, str1);
      return localBankEmployee;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_18", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static BankEmployee signonBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BankEmployeeAdmin.signonBankEmployee";
    long l = System.currentTimeMillis();
    BankEmployee localBankEmployee = null;
    Object localObject4;
    try
    {
      if (getInfoForAuditing(paramBankEmployee, paramHashMap))
      {
        paramSecureUser.setProfileID(paramBankEmployee.getId());
        EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
        localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
        paramBankEmployee.setJobTypeId(localEntitlementGroupMember.getEntitlementGroupId());
        localBankEmployee = com.ffusion.csil.handlers.BankEmployeeAdmin.signonBankEmployee(paramSecureUser, paramBankEmployee, paramHashMap);
      }
      else
      {
        throw new CSILException(str1, 3005);
      }
    }
    catch (CSILException localCSILException)
    {
      int i = localCSILException.getCode();
      localObject2 = new Object[1];
      String str2 = TrackingIDGenerator.GetNextID();
      localObject2[0] = paramBankEmployee.getUserName();
      switch (i)
      {
      case 3005: 
      case 20013: 
        localObject4 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_25", (Object[])localObject2);
        audit(paramSecureUser, (ILocalizable)localObject4, str2, 1810);
        break;
      case 19002: 
        localObject4 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_26", (Object[])localObject2);
        paramSecureUser.setProfileID(paramBankEmployee.getId());
        audit(paramSecureUser, (ILocalizable)localObject4, str2, 1809);
        break;
      case 3008: 
        localObject4 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_31", (Object[])localObject2);
        paramSecureUser.setProfileID(paramBankEmployee.getId());
        audit(paramSecureUser, (ILocalizable)localObject4, str2, 1813);
        break;
      default: 
        localObject4 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_24", null);
        paramSecureUser.setProfileID(paramBankEmployee.getId());
        audit(paramSecureUser, (ILocalizable)localObject4, str2, 1808);
      }
      throw localCSILException;
    }
    paramSecureUser.setProfileID(localBankEmployee.getId());
    paramSecureUser.setId(localBankEmployee.getEmployeeId());
    paramSecureUser.setBankID(localBankEmployee.getBankId());
    paramSecureUser.setEntitlementID(localBankEmployee.getJobTypeId());
    paramSecureUser.setUserType(2);
    paramSecureUser.setAffiliateID(localBankEmployee.getSecureUser().getAffiliateID());
    Iterator localIterator = localBankEmployee.keySet().iterator();
    while (localIterator.hasNext())
    {
      localObject1 = localIterator.next();
      paramSecureUser.put(localObject1, paramBankEmployee.get(localObject1));
    }
    Object localObject1 = TrackingIDGenerator.GetNextID();
    Object localObject2 = (Integer)paramSecureUser.get("AUTHENTICATE");
    if ((localObject2 == null) || (((Integer)localObject2).intValue() == 3018))
    {
      int j = Password.validatePassword(localBankEmployee.getPassword(), 2, paramHashMap);
      if (j != 0)
      {
        paramSecureUser.put("AUTHENTICATE", new Integer(3024));
        localObject4 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_30", null);
        audit(paramSecureUser, (ILocalizable)localObject4, (String)localObject1, 3204);
      }
      else
      {
        localObject4 = new Object[1];
        localObject4[0] = localBankEmployee.getUserName();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_20", (Object[])localObject4);
        audit(paramSecureUser, localLocalizableString, (String)localObject1, 1805);
      }
    }
    else
    {
      Object localObject3;
      if (((Integer)localObject2).intValue() == 3007)
      {
        localObject3 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_29", null);
        audit(paramSecureUser, (ILocalizable)localObject3, (String)localObject1, 3204);
      }
      else
      {
        localObject3 = new Object[1];
        localObject3[0] = localBankEmployee.getUserName();
        localObject4 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_20", (Object[])localObject3);
        audit(paramSecureUser, (ILocalizable)localObject4, (String)localObject1, 1805);
      }
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return localBankEmployee;
  }
  
  public static EntitlementGroups getJobTypes(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.getJobTypes";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avI)))
    {
      long l = System.currentTimeMillis();
      EntitlementGroups localEntitlementGroups = Entitlements.getEntitlementGroupsByTypeAndSvcBureau("EmployeeType", paramSecureUser.getBankIDValue());
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      Iterator localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator.next();
        localEntitlementGroup.setLocale(paramSecureUser.getLocale());
      }
      return localEntitlementGroups;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_21", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static EntitlementGroups getGroupsAdministratorOf(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.getGroupsAdministratorOf";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avI)))
    {
      long l = System.currentTimeMillis();
      EntitlementGroups localEntitlementGroups = Entitlements.getGroupsAdministeredBy(paramSecureUser.getEntitlementID(), "EmployeeType");
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localEntitlementGroups;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_22", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static BankEmployees getSubordinateBankEmployees(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.getSubordinateBankEmployees";
    BankEmployees localBankEmployees1 = null;
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avI)))
    {
      long l = System.currentTimeMillis();
      Object localObject = new BankEmployees(paramSecureUser.getLocale());
      BankEmployee localBankEmployee1 = null;
      BankEmployee localBankEmployee2 = null;
      localBankEmployees1 = new BankEmployees(paramSecureUser.getLocale());
      localBankEmployee2 = new BankEmployee(paramSecureUser.getLocale());
      localBankEmployee2.setSupervisor(paramBankEmployee.getId());
      localBankEmployee2.setAffiliateBankIds(paramBankEmployee.getAffiliateBankIds());
      ((BankEmployees)localObject).set(com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployeeList(paramSecureUser, localBankEmployee2, paramHashMap));
      while (((BankEmployees)localObject).size() > 0)
      {
        BankEmployees localBankEmployees2 = new BankEmployees(paramSecureUser.getLocale());
        Iterator localIterator = ((BankEmployees)localObject).iterator();
        while (localIterator.hasNext())
        {
          localBankEmployee1 = (BankEmployee)localIterator.next();
          if (localBankEmployees1.getByID(localBankEmployee1.getId()) == null)
          {
            localBankEmployee2 = new BankEmployee(paramSecureUser.getLocale());
            localBankEmployee2.setSupervisor(localBankEmployee1.getId());
            localBankEmployee2.setAffiliateBankIds(paramBankEmployee.getAffiliateBankIds());
            localBankEmployees2.set(com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployeeList(paramSecureUser, localBankEmployee2, paramHashMap));
            localBankEmployees1.add(localBankEmployee1);
          }
        }
        localObject = localBankEmployees2;
      }
      jdMethod_for(localBankEmployees1);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      Object[] arrayOfObject = new Object[0];
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_23", arrayOfObject);
      logEntitlementFault(paramSecureUser, localLocalizableString, null);
      throw new CSILException(str, 20001);
    }
    return localBankEmployees1;
  }
  
  private static void jdMethod_for(BankEmployee paramBankEmployee)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = Entitlements.getMember(paramBankEmployee.getEntitlementGroupMember());
    paramBankEmployee.setJobTypeId(localEntitlementGroupMember.getEntitlementGroupId());
  }
  
  private static void jdMethod_for(BankEmployees paramBankEmployees)
    throws CSILException
  {
    Iterator localIterator = paramBankEmployees.iterator();
    while (localIterator.hasNext())
    {
      BankEmployee localBankEmployee = (BankEmployee)localIterator.next();
      try
      {
        jdMethod_for(localBankEmployee);
      }
      catch (CSILException localCSILException)
      {
        DebugLog.log("WARNING: Unable to retrieve EntitlementGroup for BankEmployeewith ID = " + localBankEmployee.getId());
      }
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, BankEmployees paramBankEmployees1, BankEmployees paramBankEmployees2, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    BankEmployees localBankEmployees1 = getBankEmployeesByJobTypeId(paramSecureUser, paramInt, paramHashMap);
    BankEmployees localBankEmployees2;
    BankEmployees localBankEmployees3;
    if (paramBankEmployees1.size() <= localBankEmployees1.size())
    {
      localBankEmployees2 = paramBankEmployees1;
      localBankEmployees3 = localBankEmployees1;
    }
    else
    {
      localBankEmployees2 = localBankEmployees1;
      localBankEmployees3 = paramBankEmployees1;
    }
    Iterator localIterator = localBankEmployees2.iterator();
    while (localIterator.hasNext())
    {
      BankEmployee localBankEmployee1 = (BankEmployee)localIterator.next();
      BankEmployee localBankEmployee2 = localBankEmployees3.getByID(localBankEmployee1.getId());
      if (localBankEmployee2 != null)
      {
        BankEmployee localBankEmployee3 = paramBankEmployees2.add();
        localBankEmployee3.set(localBankEmployee2);
      }
    }
  }
  
  public static boolean getInfoForAuditing(BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    return com.ffusion.csil.handlers.BankEmployeeAdmin.getInfoForAuditing(paramBankEmployee, paramHashMap);
  }
  
  public static void resetBankEmployeePassword(SecureUser paramSecureUser, BankEmployee paramBankEmployee, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.resetBankEmployeePassword";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Object localObject1;
    Object localObject2;
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, avV))
    {
      com.ffusion.csil.handlers.BankEmployeeAdmin.resetBankEmployeePassword(paramSecureUser, paramBankEmployee, paramString, paramHashMap);
      localObject1 = TrackingIDGenerator.GetNextID();
      localObject2 = new Object[1];
      localObject2[0] = paramBankEmployee.getUserName();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_28", (Object[])localObject2);
      audit(paramSecureUser, localLocalizableString, (String)localObject1, 1812);
    }
    else
    {
      localObject1 = new Object[0];
      localObject2 = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_27", (Object[])localObject1);
      logEntitlementFault(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
  }
  
  public static void modifyBankEmployeePasswordStatus(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankEmployeeAdmin.modifyBankEmployeePasswordStatus";
    int i = 0;
    try
    {
      i = Integer.parseInt(paramBankEmployee.getId());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new CSILException(str, 3003, localNumberFormatException);
    }
    if ((paramSecureUser.getProfileID() == i) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avV)))
    {
      com.ffusion.csil.handlers.BankEmployeeAdmin.modifyBankEmployeePasswordStatus(paramSecureUser, paramBankEmployee, paramHashMap);
    }
    else
    {
      Object[] arrayOfObject = new Object[0];
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_12", arrayOfObject);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
  }
  
  public static void modifyBankEmployeePassword(SecureUser paramSecureUser, BankEmployee paramBankEmployee, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BankEmployeeAdmin.modifyBankEmployeePassword";
    int i = 0;
    try
    {
      i = Integer.parseInt(paramBankEmployee.getId());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new CSILException(str1, 3003, localNumberFormatException);
    }
    if (paramSecureUser.getProfileID() != i) {
      throw new CSILException(str1, 3003);
    }
    com.ffusion.csil.handlers.BankEmployeeAdmin.modifyBankEmployeePassword(paramSecureUser, paramBankEmployee, paramString1, paramString2, paramHashMap);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramBankEmployee.getUserName();
    String str2 = TrackingIDGenerator.GetNextID();
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankemployeeadmin", "AuditMessage_32", arrayOfObject);
    audit(paramSecureUser, localLocalizableString, str2, 1814);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.BankEmployeeAdmin
 * JD-Core Version:    0.7.0.1
 */