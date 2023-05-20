package com.ffusion.csil.core;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.ApplicationHistories;
import com.ffusion.beans.applications.ApplicationHistory;
import com.ffusion.beans.applications.Banks;
import com.ffusion.beans.applications.Categories;
import com.ffusion.beans.applications.Category;
import com.ffusion.beans.applications.Form;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.util.StringList;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Applications
  extends Initialize
{
  private static Entitlement arb = new Entitlement("ApplicationView", null, null);
  private static Entitlement ark = new Entitlement("ApplicationCrud", null, null);
  private static Entitlement aq8 = new Entitlement("BC Manage Multiple Banks Simultaneously", null, null);
  private static Entitlement arm = new Entitlement("Manage Consumer Banking", null, null);
  private static Entitlement aqS = new Entitlement("Manage Corporate Banking", null, null);
  private static final String arA = "com.ffusion.util.logging.audit.applications";
  private static final String aqX = "AuditMessage_1";
  private static final String aqT = "AuditMessage_2";
  private static final String arp = "AuditMessage_3";
  private static final String aq5 = "AuditMessage_4";
  private static final String aqW = "AuditMessage_5";
  private static final String aq9 = "AuditMessage_6";
  private static final String aq0 = "AuditMessage_7";
  private static final String aq7 = "AuditMessage_8";
  private static final String arz = "AuditMessage_9";
  private static final String aqR = "AuditMessage_10";
  private static final String aqU = "AuditMessage_11";
  private static final String arl = "AuditMessage_12";
  private static final String arv = "AuditMessage_13";
  private static final String aq1 = "AuditMessage_14";
  private static final String aq6 = "AuditMessage_15";
  private static final String arq = "AuditMessage_16";
  private static final String ari = "AuditMessage_17";
  private static final String aro = "AuditMessage_18";
  private static final String aqV = "AuditMessage_19";
  private static final String arr = "AuditMessage_20";
  private static final String arh = "AuditMessage_21";
  private static final String arj = "AuditMessage_22";
  private static final String arn = "AuditMessage_23";
  private static final String ars = "AuditMessage_24";
  private static final String aqY = "AuditMessage_25";
  private static final String arx = "AuditMessage_26";
  private static final String arw = "AuditMessage_27";
  private static final String ara = "AuditMessage_28";
  private static final String aru = "AuditMessage_29";
  private static final String arg = "AuditMessage_30";
  private static final String art = "AuditMessage_31";
  private static final String ary = "AuditMessage_32";
  private static final String aq4 = "AuditMessage_33";
  private static final String arf = "AuditMessage_34";
  private static final String aq3 = "AuditMessage_35";
  private static final String aq2 = "AuditMessage_36";
  private static final String are = "AuditMessage_37";
  private static final String ard = "AuditMessage_38";
  private static final String aqZ = "AuditMessage_39";
  private static final String arc = "AuditMessage_40";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Applications.initialize");
    com.ffusion.csil.handlers.Applications.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.Applications.getService();
  }
  
  public static Banks getBanks(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.GetBanks";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arb))
    {
      long l = System.currentTimeMillis();
      Banks localBanks = com.ffusion.csil.handlers.Applications.getBanks(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBanks;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_1", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Product getProduct(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.GetProduct";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arb)))
    {
      Object[] arrayOfObject = new Object[0];
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_2", arrayOfObject);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    Product localProduct = com.ffusion.csil.handlers.Applications.getProduct(paramSecureUser, paramProduct, paramHashMap);
    PerfLog.log(str, l, true);
    if (paramSecureUser == null) {
      debug(str);
    } else {
      debug(paramSecureUser, str);
    }
    return localProduct;
  }
  
  public static Products getProducts(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.GetProducts";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arb)))
    {
      Object[] arrayOfObject = new Object[0];
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_3", arrayOfObject);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    Products localProducts = com.ffusion.csil.handlers.Applications.getProducts(paramSecureUser, paramProduct, paramHashMap);
    PerfLog.log(str, l, true);
    if (paramSecureUser == null) {
      debug(str);
    } else {
      debug(paramSecureUser, str);
    }
    return localProducts;
  }
  
  public static Product modifyProduct(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Applications.ModifyProduct";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ark)))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Product localProduct = com.ffusion.csil.handlers.Applications.modifyProduct(paramSecureUser, paramProduct, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[1];
      LocalizableString localLocalizableString2 = null;
      if (paramProduct.getTitle() != null)
      {
        arrayOfObject2[0] = paramProduct.getTitle();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_5", arrayOfObject2);
      }
      else
      {
        arrayOfObject2[0] = "";
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_19", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str2, 1601);
      debug(paramSecureUser, str1);
      return localProduct;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_4", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Product deleteProduct(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Applications.DeleteProduct";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ark)))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Product localProduct = com.ffusion.csil.handlers.Applications.deleteProduct(paramSecureUser, paramProduct, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[1];
      LocalizableString localLocalizableString2 = null;
      if (paramProduct.getTitle() != null)
      {
        arrayOfObject2[0] = paramProduct.getTitle();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_7", arrayOfObject2);
      }
      else
      {
        arrayOfObject2[0] = "";
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_39", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str2, 1602);
      debug(paramSecureUser, str1);
      return localProduct;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_6", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Application addApplication(SecureUser paramSecureUser, Application paramApplication, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Applications.AddApplication";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ark)))
    {
      Object[] arrayOfObject1 = new Object[0];
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_8", arrayOfObject1);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    Application localApplication = com.ffusion.csil.handlers.Applications.addApplication(paramSecureUser, paramApplication, paramString, paramHashMap);
    PerfLog.log(str1, l, true);
    Object[] arrayOfObject2 = new Object[3];
    arrayOfObject2[1] = (paramApplication.getFirstName() == null ? "" : paramApplication.getFirstName());
    arrayOfObject2[2] = (paramApplication.getLastName() == null ? "" : paramApplication.getLastName());
    LocalizableString localLocalizableString2 = null;
    try
    {
      Product localProduct1 = new Product();
      localProduct1.setBankID(paramApplication.getBankID());
      localProduct1.setProductID(paramApplication.getProductID());
      Product localProduct2 = com.ffusion.csil.handlers.Applications.getProduct(paramSecureUser, localProduct1, paramHashMap);
      if (localProduct2.getTitle() != null)
      {
        arrayOfObject2[0] = localProduct2.getTitle();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_9", arrayOfObject2);
      }
      else
      {
        arrayOfObject2[0] = "";
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_32", arrayOfObject2);
      }
    }
    catch (Exception localException)
    {
      arrayOfObject2[0] = "";
      localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_32", arrayOfObject2);
    }
    if (paramSecureUser == null)
    {
      debug(str1);
    }
    else
    {
      audit(paramSecureUser, localLocalizableString2, str2, 1603);
      debug(paramSecureUser, str1);
    }
    return localApplication;
  }
  
  public static Application getApplication(SecureUser paramSecureUser, Application paramApplication, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.GetApplication";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arb))
    {
      long l = System.currentTimeMillis();
      Application localApplication = com.ffusion.csil.handlers.Applications.getApplication(paramSecureUser, paramApplication, paramBoolean, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localApplication;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_10", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static com.ffusion.beans.applications.Applications getApplications(SecureUser paramSecureUser, Application paramApplication, String paramString1, String paramString2, String paramString3, String paramString4, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.GetApplications";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arb)))
    {
      jdMethod_for(paramSecureUser, paramApplication.getOwner(), false, str);
      long l = System.currentTimeMillis();
      com.ffusion.beans.applications.Applications localApplications = com.ffusion.csil.handlers.Applications.getApplications(paramSecureUser, paramApplication, paramString1, paramString2, paramString3, paramString4, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localApplications;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_11", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static String getDisplayCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "Applications.getDisplayCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arb))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.Applications.getDisplayCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_12", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static String getMaxResultCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "Applications.getMaxResultCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arb))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.Applications.getMaxResultCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_13", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Application deleteApplication(SecureUser paramSecureUser, Application paramApplication, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Applications.DeleteApplication";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ark))
    {
      if (paramSecureUser.getUserType() == 2) {
        jdMethod_for(paramSecureUser, paramApplication.getOwner(), true, str1);
      }
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Application localApplication = com.ffusion.csil.handlers.Applications.deleteApplication(paramSecureUser, paramApplication, paramHashMap);
      PerfLog.log(str1, l, true);
      int i = 1;
      Object[] arrayOfObject2 = new Object[4];
      arrayOfObject2[1] = (paramApplication.getFirstName() == null ? "" : paramApplication.getFirstName());
      arrayOfObject2[2] = (paramApplication.getLastName() == null ? "" : paramApplication.getLastName());
      arrayOfObject2[3] = "";
      try
      {
        DateTime localDateTime = paramApplication.getCreateDate();
        if (localDateTime != null) {
          arrayOfObject2[3] = new LocalizableDate(localDateTime, false);
        } else {
          i = 0;
        }
      }
      catch (Exception localException1)
      {
        if (paramApplication.getCreateDate() != null) {
          arrayOfObject2[3] = paramApplication.getCreateDateString();
        } else {
          i = 0;
        }
      }
      LocalizableString localLocalizableString2 = null;
      try
      {
        Product localProduct1 = new Product();
        localProduct1.setBankID(paramApplication.getBankID());
        localProduct1.setProductID(paramApplication.getProductID());
        Product localProduct2 = com.ffusion.csil.handlers.Applications.getProduct(paramSecureUser, localProduct1, paramHashMap);
        int j = (localProduct2 != null) && (localProduct2.getTitle() != null) ? 1 : 0;
        arrayOfObject2[0] = (j != 0 ? localProduct2.getTitle() : "");
        if ((i != 0) && (j != 0)) {
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_15", arrayOfObject2);
        } else if ((i != 0) && (j == 0)) {
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_33", arrayOfObject2);
        } else if ((i == 0) && (j != 0)) {
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_35", arrayOfObject2);
        } else {
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_36", arrayOfObject2);
        }
      }
      catch (Exception localException2)
      {
        arrayOfObject2[0] = "";
        if (i != 0) {
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_33", arrayOfObject2);
        } else {
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_36", arrayOfObject2);
        }
      }
      audit(paramSecureUser, localLocalizableString2, str2, 1605);
      debug(paramSecureUser, str1);
      return localApplication;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_14", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Application modifyApplication(SecureUser paramSecureUser, Application paramApplication, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Applications.ModifyApplication";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ark))
    {
      if (paramSecureUser.getUserType() == 2)
      {
        Application localApplication1 = com.ffusion.csil.handlers.Applications.getApplication(paramSecureUser, paramApplication, false, paramHashMap);
        if (localApplication1 == null) {
          throw new CSILException(7094);
        }
        jdMethod_for(paramSecureUser, localApplication1.getOwner(), true, str1);
        if (!localApplication1.getOwner().equals(paramApplication.getOwner())) {
          jdMethod_for(paramSecureUser, paramApplication, str1);
        }
      }
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Application localApplication2 = com.ffusion.csil.handlers.Applications.modifyApplication(paramSecureUser, paramApplication, paramString, paramHashMap);
      PerfLog.log(str1, l, true);
      jdMethod_for(paramSecureUser, paramApplication, str2, 1604, paramHashMap);
      debug(paramSecureUser, str1);
      return localApplication2;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_16", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, Application paramApplication, String paramString, int paramInt, HashMap paramHashMap)
  {
    int i = 1;
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[1] = (paramApplication.getFirstName() == null ? "" : paramApplication.getFirstName());
    arrayOfObject[2] = (paramApplication.getLastName() == null ? "" : paramApplication.getLastName());
    arrayOfObject[3] = "";
    try
    {
      DateTime localDateTime = paramApplication.getCreateDate();
      if (localDateTime != null) {
        arrayOfObject[3] = new LocalizableDate(localDateTime, false);
      } else {
        i = 0;
      }
    }
    catch (Exception localException1)
    {
      if (paramApplication.getCreateDate() != null) {
        arrayOfObject[3] = paramApplication.getCreateDateString();
      } else {
        i = 0;
      }
    }
    LocalizableString localLocalizableString = null;
    try
    {
      Product localProduct1 = new Product();
      localProduct1.setBankID(paramApplication.getBankID());
      localProduct1.setProductID(paramApplication.getProductID());
      Product localProduct2 = com.ffusion.csil.handlers.Applications.getProduct(paramSecureUser, localProduct1, paramHashMap);
      int j = (localProduct2 != null) && (localProduct2.getTitle() != null) ? 1 : 0;
      arrayOfObject[0] = (j != 0 ? localProduct2.getTitle() : "");
      if ((i != 0) && (j != 0)) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_17", arrayOfObject);
      } else if ((i != 0) && (j == 0)) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_34", arrayOfObject);
      } else if ((i == 0) && (j != 0)) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_37", arrayOfObject);
      } else {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_38", arrayOfObject);
      }
    }
    catch (Exception localException2)
    {
      arrayOfObject[0] = "";
      if (i != 0) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_34", arrayOfObject);
      } else {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_38", arrayOfObject);
      }
    }
    audit(paramSecureUser, localLocalizableString, paramString, paramInt);
  }
  
  public static com.ffusion.beans.applications.Applications modifyApplications(SecureUser paramSecureUser, StringList paramStringList, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Applications.ModifyApplications";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ark))
    {
      if (paramSecureUser.getUserType() == 2)
      {
        Iterator localIterator1 = paramStringList.iterator();
        localObject = null;
        while (localIterator1.hasNext())
        {
          localObject = new Application(paramSecureUser.getLocale());
          ((Application)localObject).setAppID((String)localIterator1.next());
          localObject = com.ffusion.csil.handlers.Applications.getApplication(paramSecureUser, (Application)localObject, false, paramHashMap);
          if (localObject == null) {
            throw new CSILException(7094);
          }
          jdMethod_for(paramSecureUser, ((Application)localObject).getOwner(), true, str1);
          if (!((Application)localObject).getOwner().equals(paramString1))
          {
            ((Application)localObject).setOwner(paramString1);
            jdMethod_for(paramSecureUser, (Application)localObject, str1);
          }
        }
      }
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.beans.applications.Applications localApplications = com.ffusion.csil.handlers.Applications.modifyApplications(paramSecureUser, paramStringList, paramString1, paramString2, paramString3, paramHashMap);
      PerfLog.log(str1, l, true);
      Iterator localIterator2 = localApplications.iterator();
      while (localIterator2.hasNext()) {
        try
        {
          Application localApplication = (Application)localIterator2.next();
          localApplication = com.ffusion.csil.handlers.Applications.getApplication(paramSecureUser, localApplication, false, paramHashMap);
          jdMethod_for(paramSecureUser, localApplication, str2, 1609, paramHashMap);
        }
        catch (Exception localException)
        {
          Object[] arrayOfObject2 = new Object[0];
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_40", arrayOfObject2);
          audit(paramSecureUser, localLocalizableString, str2, 1609);
        }
      }
      debug(paramSecureUser, str1);
      return localApplications;
    }
    Object[] arrayOfObject1 = new Object[0];
    Object localObject = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_18", arrayOfObject1);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Status deleteStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Applications.DeleteStatus";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ark)))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Status localStatus = com.ffusion.csil.handlers.Applications.deleteStatus(paramSecureUser, paramStatus, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = (paramStatus.getName() == null ? "" : paramStatus.getName());
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_21", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 1608);
      debug(paramSecureUser, str1);
      return localStatus;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_20", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Status addStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Applications.AddStatus";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ark)))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Status localStatus = com.ffusion.csil.handlers.Applications.addStatus(paramSecureUser, paramStatus, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = (paramStatus.getName() == null ? "" : paramStatus.getName());
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_23", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 1606);
      debug(paramSecureUser, str1);
      return localStatus;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_22", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Status modifyStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Applications.ModifyStatus";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ark)))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Status localStatus = com.ffusion.csil.handlers.Applications.modifyStatus(paramSecureUser, paramStatus, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = (paramStatus.getName() == null ? "" : paramStatus.getName());
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_25", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 1607);
      debug(paramSecureUser, str1);
      return localStatus;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_24", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Status getStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.GetStatus";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arb))
    {
      long l = System.currentTimeMillis();
      Status localStatus = com.ffusion.csil.handlers.Applications.getStatus(paramSecureUser, paramStatus, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStatus;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_26", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Statuses getStatuses(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.GetStatuses";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arb))
    {
      long l = System.currentTimeMillis();
      Statuses localStatuses = com.ffusion.csil.handlers.Applications.getStatuses(paramSecureUser, paramStatus, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStatuses;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_27", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Categories getCategories(SecureUser paramSecureUser, Category paramCategory, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.GetCategories";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arb))
    {
      long l = System.currentTimeMillis();
      Categories localCategories = com.ffusion.csil.handlers.Applications.getCategories(paramSecureUser, paramCategory, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCategories;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_28", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Form getForm(SecureUser paramSecureUser, Form paramForm, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.GetForm";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arb)))
    {
      Object[] arrayOfObject = new Object[0];
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_29", arrayOfObject);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    Form localForm = com.ffusion.csil.handlers.Applications.getForm(paramSecureUser, paramForm, paramHashMap);
    PerfLog.log(str, l, true);
    if (paramSecureUser == null) {
      debug(str);
    } else {
      debug(paramSecureUser, str);
    }
    return localForm;
  }
  
  public static Integer getApplicationCount(SecureUser paramSecureUser, Application paramApplication, String paramString1, String paramString2, String paramString3, String paramString4, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.GetApplicationCount";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arb)))
    {
      jdMethod_for(paramSecureUser, paramApplication.getOwner(), false, str);
      long l = System.currentTimeMillis();
      Integer localInteger = com.ffusion.csil.handlers.Applications.getApplicationCount(paramSecureUser, paramApplication, paramString1, paramString2, paramString3, paramString4, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localInteger;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_30", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ApplicationHistories getHistories(SecureUser paramSecureUser, ApplicationHistory paramApplicationHistory, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.GetHistories";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arb)))
    {
      int i = 0;
      localObject = paramApplicationHistory.getAppID();
      if (localObject != null) {
        try
        {
          Integer.parseInt((String)localObject);
          i = 1;
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
      if (i == 0) {
        throw new CSILException(str, 7094);
      }
      long l = System.currentTimeMillis();
      ApplicationHistories localApplicationHistories = com.ffusion.csil.handlers.Applications.getHistories(paramSecureUser, paramApplicationHistory, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localApplicationHistories;
    }
    Object[] arrayOfObject = new Object[0];
    Object localObject = new LocalizableString("com.ffusion.util.logging.audit.applications", "AuditMessage_31", arrayOfObject);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, String paramString1, boolean paramBoolean, String paramString2)
    throws CSILException
  {
    int i = 0;
    int j = -1;
    if (paramString1 != null) {
      try
      {
        j = Integer.parseInt(paramString1);
        i = 1;
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    if (i == 0) {
      throw new CSILException(paramString2, 7093);
    }
    int k = 0;
    if (j != paramSecureUser.getProfileID())
    {
      if (paramBoolean)
      {
        HashMap localHashMap = new HashMap();
        localHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(localHashMap);
        BankEmployee localBankEmployee1 = new BankEmployee(paramSecureUser.getLocale());
        localBankEmployee1.setId(String.valueOf(paramSecureUser.getProfileID()));
        localBankEmployee1 = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, localBankEmployee1, localHashMap);
        BankEmployee localBankEmployee2 = new BankEmployee(paramSecureUser.getLocale());
        localBankEmployee2.setSupervisor(localBankEmployee1.getId());
        localBankEmployee2.setAffiliateBankIds(localBankEmployee1.getAffiliateBankIds());
        BankEmployees localBankEmployees = BankEmployeeAdmin.getBankEmployeeList(paramSecureUser, localBankEmployee2, localHashMap);
        Iterator localIterator = localBankEmployees.iterator();
        BankEmployee localBankEmployee3 = null;
        while ((localIterator.hasNext()) && (k == 0))
        {
          localBankEmployee3 = (BankEmployee)localIterator.next();
          if (localBankEmployee3.getId().trim().equals(paramString1)) {
            k = 1;
          }
        }
      }
    }
    else {
      k = 1;
    }
    if (k == 0) {
      throw new CSILException(paramString2, 7098);
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, Application paramApplication, String paramString)
    throws CSILException
  {
    HashMap localHashMap = new HashMap();
    localHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(localHashMap);
    BankEmployee localBankEmployee = new BankEmployee(paramSecureUser.getLocale());
    localBankEmployee.setId(paramApplication.getOwner());
    localBankEmployee = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, localBankEmployee, localHashMap);
    EntitlementGroupMember localEntitlementGroupMember = localBankEmployee.getEntitlementGroupMember();
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, ark)) {
      throw new CSILException(paramString, 7096);
    }
    int i = 0;
    ArrayList localArrayList = localBankEmployee.getAffiliateBankIds();
    String str = String.valueOf(paramApplication.getAffiliateBankID());
    if ((localArrayList != null) && (localArrayList.contains(str))) {
      i = 1;
    } else if (Entitlements.checkEntitlement(localEntitlementGroupMember, aq8)) {
      i = 1;
    } else if ((localBankEmployee.getDefaultAffiliateBankId() != null) && (localBankEmployee.getDefaultAffiliateBankId().trim().equals(str))) {
      i = 1;
    }
    if (i == 0) {
      throw new CSILException(paramString, 7097);
    }
    MessageQueue localMessageQueue = new MessageQueue();
    localMessageQueue.setQueueType(String.valueOf(1));
    MessageQueues localMessageQueues = MessageAdmin.getMessageQueues(paramSecureUser, localMessageQueue, localHashMap);
    localMessageQueue = localMessageQueues.getByStatProdID(paramApplication.getStatusID(), paramApplication.getProductID());
    if ((localMessageQueue.getIsConsumer()) && (!localMessageQueue.getIsCorporate()) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, arm))) {
      throw new CSILException(7099);
    }
    if ((localMessageQueue.getIsCorporate()) && (!localMessageQueue.getIsConsumer()) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aqS))) {
      throw new CSILException(7100);
    }
    if ((localMessageQueue.getIsCorporate()) && (localMessageQueue.getIsConsumer()) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aqS)) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, arm))) {
      throw new CSILException(7101);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Applications
 * JD-Core Version:    0.7.0.1
 */