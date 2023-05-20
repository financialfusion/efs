package com.ffusion.util;

import com.ffusion.beans.banking.TransactionTypeInfo;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

public class TransactionTypeCache
{
  public static final Locale DEFAULT_LOCALE = new Locale(Locale.ENGLISH.getLanguage(), Locale.US.getCountry());
  private static HashMap jdField_new = null;
  private static HashMap jdField_int = null;
  private static HashMap jdField_do = null;
  private static String jdField_for = null;
  private static final String jdField_if = "default";
  private static final String a = ".xml";
  
  public static void initTransactionTypesCache(String paramString)
    throws Exception
  {
    if (jdField_new != null)
    {
      if (!jdField_for.equals(paramString)) {
        throw new Exception("The transaction type cache has already been initialized with filename '" + jdField_for + "', the initiatlization call for the filename '" + paramString + "' has failed.");
      }
      return;
    }
    jdField_for = paramString;
    String str1 = paramString.substring(0, paramString.length() - ".xml".length());
    ArrayList localArrayList1 = LocaleUtil.getXMLFilesWithBase(str1);
    localArrayList1.add(0, paramString);
    String str2 = null;
    jdField_new = new HashMap();
    jdField_int = new HashMap();
    jdField_do = new HashMap();
    for (int i = 0; i < localArrayList1.size(); i++)
    {
      String str3 = (String)localArrayList1.get(i);
      if (paramString.equals(str3)) {
        str2 = "default";
      } else {
        str2 = LocaleUtil.getLocaleIDFromXMLFileName(str3, str1);
      }
      HashMap localHashMap1 = XMLUtil.readXmlToHashMap(new Object(), str3);
      HashMap localHashMap2 = (HashMap)localHashMap1.get("TRANSACTION_TYPE_LIST");
      if (localHashMap2 == null) {
        throw new Exception("The TRANSACTION_TYPE_LIST tag did not exist in the document");
      }
      ArrayList localArrayList2 = (ArrayList)localHashMap2.get("TRANSACTION_TYPE");
      if (localArrayList2 == null) {
        throw new Exception("TRANSACTION_TYPE tags were not found in the XML document");
      }
      a(localArrayList2, str2, str3);
    }
  }
  
  private static void a(ArrayList paramArrayList, String paramString1, String paramString2)
  {
    Locale localLocale = paramString1 == "default" ? DEFAULT_LOCALE : LocaleUtil.getLocaleFromLocaleID(paramString1);
    HashMap localHashMap1 = new HashMap();
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      HashMap localHashMap2 = (HashMap)paramArrayList.get(i);
      String str1 = (String)localHashMap2.get("ID");
      String str2 = (String)localHashMap2.get("NAME");
      String str3 = (String)localHashMap2.get("ABBR");
      int j = -1;
      int k = localLocale == DEFAULT_LOCALE ? 1 : 0;
      ArrayList localArrayList = null;
      if (k != 0)
      {
        HashMap localHashMap3 = (HashMap)localHashMap2.get("APPLICATION_LIST");
        if (localHashMap3 != null) {
          localArrayList = (ArrayList)localHashMap3.get("APPLICATION");
        }
      }
      try
      {
        j = Integer.parseInt(str1);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.WARNING, "The ID value '" + str1 + "' is not a valid integer. This record is skipped from initialization. Please check file " + paramString2 + ".");
        continue;
      }
      Integer localInteger = new Integer(j);
      localHashMap1.put(localInteger, str2);
      TransactionTypeInfo localTransactionTypeInfo = null;
      if (k != 0)
      {
        if (jdField_new.containsKey(localInteger)) {
          DebugLog.log(Level.WARNING, "Transaction type with ID " + j + " is defined for more than once.");
        }
        localTransactionTypeInfo = new TransactionTypeInfo();
        localTransactionTypeInfo.setID(j);
        int[] arrayOfInt;
        if ((localArrayList == null) || (localArrayList.isEmpty()))
        {
          arrayOfInt = new int[1];
          arrayOfInt[0] = 0;
          jdField_do.put(localInteger, localTransactionTypeInfo);
          jdField_int.put(localInteger, localTransactionTypeInfo);
          localTransactionTypeInfo.setApplications(arrayOfInt);
        }
        else
        {
          arrayOfInt = new int[localArrayList.size()];
          for (int m = 0; m < localArrayList.size(); m++)
          {
            String str4 = (String)localArrayList.get(m);
            if (str4.equalsIgnoreCase("Corporate"))
            {
              arrayOfInt[m] = 2;
              jdField_do.put(localInteger, localTransactionTypeInfo);
            }
            else if (str4.equalsIgnoreCase("Consumer"))
            {
              arrayOfInt[m] = 1;
              jdField_int.put(localInteger, localTransactionTypeInfo);
            }
            else if (str4.equalsIgnoreCase("All"))
            {
              arrayOfInt[m] = 0;
              jdField_do.put(localInteger, localTransactionTypeInfo);
              jdField_int.put(localInteger, localTransactionTypeInfo);
            }
            else
            {
              DebugLog.log(Level.WARNING, "Unable to interpret application name " + str4 + " for transaction type ID " + j);
              arrayOfInt[m] = -1;
            }
          }
          localTransactionTypeInfo.setApplications(arrayOfInt);
        }
      }
      else
      {
        localTransactionTypeInfo = (TransactionTypeInfo)jdField_do.get(localInteger);
        if (localTransactionTypeInfo == null) {
          localTransactionTypeInfo = (TransactionTypeInfo)jdField_int.get(localInteger);
        }
        if (localTransactionTypeInfo == null)
        {
          DebugLog.log(Level.WARNING, "Inconsistent localized transaction type XML file " + paramString2 + ". There is no ID " + j + " in the default transactionType.XML file.");
          localHashMap1.remove(localInteger);
          continue;
        }
      }
      if (str2 != null) {
        localTransactionTypeInfo.setDescription(localLocale, str2);
      }
      if (str3 != null) {
        localTransactionTypeInfo.setAbbr(localLocale, str3);
      }
    }
    jdField_new.put(paramString1, localHashMap1);
  }
  
  public static HashMap getTransactionTypes(int paramInt, HashMap paramHashMap)
    throws FFIUtilException
  {
    String str = "TransactionTypeCache.getTransactionTypes";
    try
    {
      switch (paramInt)
      {
      case 2: 
        return jdField_do;
      case 1: 
        return jdField_int;
      case 0: 
        HashMap localHashMap = (HashMap)jdField_do.clone();
        Iterator localIterator = jdField_int.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          if (!localHashMap.containsKey(localEntry.getKey())) {
            localHashMap.put(localEntry.getKey(), localEntry.getValue());
          }
        }
        return localHashMap;
      }
      return null;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      throw new FFIUtilException(0, localThrowable);
    }
  }
  
  public static HashMap getTransactionTypeCache(Locale paramLocale)
  {
    if (jdField_new == null) {
      return jdField_new;
    }
    if (paramLocale == null) {
      return (HashMap)jdField_new.get("default");
    }
    String str1 = paramLocale.getLanguage();
    String str2 = paramLocale.getCountry();
    if (jdField_new.containsKey(str1 + '_' + str2)) {
      return (HashMap)jdField_new.get(str1 + '_' + str2);
    }
    if (jdField_new.containsKey(str1)) {
      return (HashMap)jdField_new.get(str1);
    }
    return (HashMap)jdField_new.get("default");
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.TransactionTypeCache
 * JD-Core Version:    0.7.0.1
 */