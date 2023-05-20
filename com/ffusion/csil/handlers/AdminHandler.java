package com.ffusion.csil.handlers;

import com.ffusion.beans.Module;
import com.ffusion.beans.Modules;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.TransactionType;
import com.ffusion.beans.TransactionTypes;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.Admin;
import com.ffusion.services.admin.AdminException;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.logging.DebugLog;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

public class AdminHandler
  extends Initialize
{
  private static final String a6h = "Admin";
  private static Admin a6l = null;
  private static final String a6o = "TransactionTypeText";
  private static final String a6p = "TransactionTypeModule";
  private static final String a6e = "TransactionTypeApplication";
  private static final String a6g = "TransactionModuleName";
  private static final String a6j = ",";
  private static final String a6k = "TransactionTypes_Bank";
  private static final String a6n = "TransactionTypes_Business";
  private static final String a6f = "TransactionTypes_Consumer";
  private static final String a6i = "TransactionModules_Bank";
  private static final String a6m = "TransactionModules_Business";
  private static final String a6c = "TransactionModules_Consumer";
  private static final String a6r = "B";
  private static final String a6q = "C";
  private static final String a6d = "E";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "AdminHandler.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Admin", str, 20107);
    a6l = (Admin)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      a6l.initialize();
    }
    catch (AdminException localAdminException)
    {
      CSILException localCSILException = new CSILException(20107, localAdminException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return a6l;
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("ACCOUNT_SERVICE", AccountHandler.getAccountService());
      paramHashMap.put("USER_ADMIN_SERVICE", UserAdminHandler.getUserAdminService());
      return a6l.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (AdminException localAdminException)
    {
      CSILException localCSILException = new CSILException(-1009, t(localAdminException.code), localAdminException);
      DebugLog.throwing("AdminHandler.getReportData", localCSILException);
      throw localCSILException;
    }
  }
  
  private static int t(int paramInt)
  {
    switch (paramInt)
    {
    case 1: 
      return 19003;
    }
    return paramInt;
  }
  
  public static TransactionTypes getTransactionTypes(Locale paramLocale)
  {
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.beans.banking.resources", paramLocale);
    if (localResourceBundle == null) {
      return null;
    }
    Enumeration localEnumeration = localResourceBundle.getKeys();
    TransactionTypes localTransactionTypes = new TransactionTypes();
    int i = "TransactionTypeText".length();
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      if (str1.startsWith("TransactionTypeText")) {
        try
        {
          int j = Integer.parseInt(str1.substring(i));
          String str2 = localResourceBundle.getString(str1);
          localTransactionTypes.add(new TransactionType(j, str2));
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
    }
    return localTransactionTypes;
  }
  
  public static TransactionTypes getTransactionTypesForModule(Locale paramLocale, String paramString)
  {
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.beans.banking.resources", paramLocale);
    if (localResourceBundle == null) {
      return null;
    }
    Enumeration localEnumeration = localResourceBundle.getKeys();
    TransactionTypes localTransactionTypes = new TransactionTypes();
    int i = "TransactionTypeModule".length();
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      if (str1.startsWith("TransactionTypeModule")) {
        try
        {
          String str2 = localResourceBundle.getString(str1);
          if (str2.equals(paramString))
          {
            int j = Integer.parseInt(str1.substring(i));
            StringBuffer localStringBuffer = new StringBuffer();
            localStringBuffer.append("TransactionTypeText");
            localStringBuffer.append(j);
            String str3 = null;
            try
            {
              str3 = localResourceBundle.getString(localStringBuffer.toString());
            }
            catch (MissingResourceException localMissingResourceException) {}
            continue;
            localTransactionTypes.add(new TransactionType(j, str3));
          }
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
    }
    return localTransactionTypes;
  }
  
  public static TransactionTypes getApplicationTransactionTypes(Locale paramLocale, String paramString)
  {
    TransactionTypes localTransactionTypes = getTransactionTypes(paramLocale);
    return jdMethod_for(paramLocale, localTransactionTypes, paramString);
  }
  
  public static TransactionTypes getApplicationTransactionTypesForModule(Locale paramLocale, String paramString1, String paramString2)
  {
    TransactionTypes localTransactionTypes = getTransactionTypesForModule(paramLocale, paramString2);
    return jdMethod_for(paramLocale, localTransactionTypes, paramString1);
  }
  
  public static TransactionTypes getConsumerTransactionTypes(Locale paramLocale)
  {
    return getApplicationTransactionTypes(paramLocale, "TransactionTypes_Consumer");
  }
  
  public static TransactionTypes getBusinessTransactionTypes(Locale paramLocale)
  {
    return getApplicationTransactionTypes(paramLocale, "TransactionTypes_Business");
  }
  
  public static TransactionTypes getBankTransactionTypes(Locale paramLocale)
  {
    return getApplicationTransactionTypes(paramLocale, "TransactionTypes_Bank");
  }
  
  public static TransactionTypes getConsumerTransactionTypesForModule(Locale paramLocale, String paramString)
  {
    return getApplicationTransactionTypesForModule(paramLocale, "TransactionTypes_Consumer", paramString);
  }
  
  public static TransactionTypes getBusinessTransactionTypesForModule(Locale paramLocale, String paramString)
  {
    return getApplicationTransactionTypesForModule(paramLocale, "TransactionTypes_Business", paramString);
  }
  
  public static TransactionTypes getBankTransactionTypesForModule(Locale paramLocale, String paramString)
  {
    return getApplicationTransactionTypesForModule(paramLocale, "TransactionTypes_Bank", paramString);
  }
  
  public static Modules getApplicationModules(Locale paramLocale, String paramString)
  {
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.beans.banking.resources", paramLocale);
    if (localResourceBundle == null) {
      return null;
    }
    String str1 = localResourceBundle.getString(paramString);
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",", false);
    HashSet localHashSet = new HashSet();
    while (localStringTokenizer.hasMoreTokens()) {
      try
      {
        Integer localInteger1 = new Integer(localStringTokenizer.nextToken());
        localHashSet.add(localInteger1);
      }
      catch (NumberFormatException localNumberFormatException1) {}
    }
    Enumeration localEnumeration = localResourceBundle.getKeys();
    Modules localModules = new Modules();
    int i = "TransactionModuleName".length();
    while (localEnumeration.hasMoreElements())
    {
      String str2 = (String)localEnumeration.nextElement();
      if (str2.startsWith("TransactionModuleName")) {
        try
        {
          Integer localInteger2 = new Integer(str2.substring(i));
          if (localHashSet.contains(localInteger2))
          {
            String str3 = localResourceBundle.getString(str2);
            localModules.add(new Module(localInteger2.intValue(), str3));
          }
        }
        catch (NumberFormatException localNumberFormatException2) {}
      }
    }
    return localModules;
  }
  
  public static Modules getBankModules(Locale paramLocale)
  {
    return getApplicationModules(paramLocale, "TransactionModules_Bank");
  }
  
  public static Modules getBusinessModules(Locale paramLocale)
  {
    return getApplicationModules(paramLocale, "TransactionModules_Business");
  }
  
  public static Modules getConsumerModules(Locale paramLocale)
  {
    return getApplicationModules(paramLocale, "TransactionModules_Consumer");
  }
  
  public static Modules getModules(Locale paramLocale)
  {
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.beans.banking.resources", paramLocale);
    if (localResourceBundle == null) {
      return null;
    }
    Enumeration localEnumeration = localResourceBundle.getKeys();
    Modules localModules = new Modules();
    int i = "TransactionModuleName".length();
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      if (str1.startsWith("TransactionModuleName")) {
        try
        {
          int j = Integer.parseInt(str1.substring(i));
          String str2 = localResourceBundle.getString(str1);
          localModules.add(new Module(j, str2));
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
    }
    return localModules;
  }
  
  private static TransactionTypes jdMethod_for(Locale paramLocale, TransactionTypes paramTransactionTypes, String paramString)
  {
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.beans.banking.resources", paramLocale);
    if (localResourceBundle == null) {
      return null;
    }
    String str1 = "";
    if (paramString.equals("TransactionTypes_Bank")) {
      str1 = "B";
    } else if (paramString.equals("TransactionTypes_Business")) {
      str1 = "C";
    } else {
      str1 = "E";
    }
    Iterator localIterator = paramTransactionTypes.iterator();
    while (localIterator.hasNext())
    {
      TransactionType localTransactionType = (TransactionType)localIterator.next();
      int i = localTransactionType.getId();
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("TransactionTypeApplication");
      localStringBuffer.append(Integer.toString(i));
      String str2 = null;
      try
      {
        str2 = localResourceBundle.getString(localStringBuffer.toString());
      }
      catch (Exception localException) {}
      if ((str2 != null) && (str2.length() > 0) && (str2.indexOf(str1) == -1)) {
        localIterator.remove();
      }
    }
    return paramTransactionTypes;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.AdminHandler
 * JD-Core Version:    0.7.0.1
 */