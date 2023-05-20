package com.ffusion.fx;

import com.ffusion.beans.fx.FXCurrencyFormat;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FXUtil
{
  private static HashMap jdField_if;
  private static HashMap jdField_do;
  private static final int jdField_for = 2;
  private static final String a = "default";
  
  public static void initialize(HashMap paramHashMap)
    throws FXException
  {
    try
    {
      jdField_if = new HashMap();
      jdField_do = new HashMap();
      a("currencyDecimals.xml");
      jdField_if("currencyFormat.xml");
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("Could not initialize the foreign exchange module.", localThrowable);
    }
  }
  
  private static void a(String paramString)
    throws FXException
  {
    InputStream localInputStream = null;
    String str1 = null;
    try
    {
      localInputStream = ResourceUtil.getResourceAsStream(new Object(), paramString);
      str1 = Strings.streamToString(localInputStream);
    }
    catch (Exception localException1)
    {
      throw new FXException(34018, "Could not initialize the foreign exchange module.", localException1);
    }
    finally
    {
      if (localInputStream != null)
      {
        try
        {
          localInputStream.close();
        }
        catch (Exception localException3) {}
        localInputStream = null;
      }
    }
    XMLTag localXMLTag = null;
    try
    {
      localXMLTag = new XMLTag(true);
      localXMLTag.build(str1);
      str1 = null;
    }
    catch (Exception localException2)
    {
      throw new FXException(34018, "Error when parsing xml file " + paramString, localException2);
    }
    HashMap localHashMap1 = XMLUtil.tagToHashMap(localXMLTag);
    HashMap localHashMap2 = (HashMap)localHashMap1.get("IMPLIED_DECIMALS_LIST");
    ArrayList localArrayList1 = (ArrayList)localHashMap2.get("IMPLIED_DECIMALS");
    Iterator localIterator1 = localArrayList1.iterator();
    while (localIterator1.hasNext())
    {
      HashMap localHashMap3 = (HashMap)localIterator1.next();
      Integer localInteger = new Integer((String)localHashMap3.get("NUM_DECIMALS"));
      HashMap localHashMap4 = (HashMap)localHashMap3.get("COUNTRY_CODE_LIST");
      ArrayList localArrayList2 = (ArrayList)localHashMap4.get("COUNTRY_CODE");
      Iterator localIterator2 = localArrayList2.iterator();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        jdField_if.put(str2, localInteger);
      }
      localArrayList2 = null;
      localHashMap4 = null;
      localHashMap3 = null;
    }
    localArrayList1 = null;
    localHashMap2 = null;
    localHashMap1 = null;
  }
  
  private static void jdField_if(String paramString)
    throws FXException
  {
    InputStream localInputStream = null;
    String str1 = null;
    try
    {
      localInputStream = ResourceUtil.getResourceAsStream(new Object(), paramString);
      str1 = Strings.streamToString(localInputStream);
    }
    catch (Exception localException1)
    {
      throw new FXException(34018, "Could not initialize the foreign exchange module.", localException1);
    }
    finally
    {
      if (localInputStream != null)
      {
        try
        {
          localInputStream.close();
        }
        catch (Exception localException3) {}
        localInputStream = null;
      }
    }
    XMLTag localXMLTag = null;
    try
    {
      localXMLTag = new XMLTag(true);
      localXMLTag.build(str1);
      str1 = null;
    }
    catch (Exception localException2)
    {
      throw new FXException(34018, "Error when parsing xml file " + paramString, localException2);
    }
    HashMap localHashMap1 = XMLUtil.tagToHashMap(localXMLTag);
    HashMap localHashMap2 = (HashMap)localHashMap1.get("LOCALE_CURRENCIES_LIST");
    ArrayList localArrayList1 = (ArrayList)localHashMap2.get("LOCALE_CURRENCIES");
    Iterator localIterator1 = localArrayList1.iterator();
    while (localIterator1.hasNext())
    {
      HashMap localHashMap3 = (HashMap)localIterator1.next();
      String str2 = (String)localHashMap3.get("LOCALE");
      HashMap localHashMap4 = (HashMap)localHashMap3.get("CURRENCIES_LIST");
      ArrayList localArrayList2 = (ArrayList)localHashMap4.get("CURRENCY");
      Iterator localIterator2 = localArrayList2.iterator();
      while (localIterator2.hasNext())
      {
        HashMap localHashMap5 = (HashMap)localIterator2.next();
        FXCurrencyFormat localFXCurrencyFormat = new FXCurrencyFormat();
        String str3 = (String)localHashMap5.get("CODE");
        String str4 = (String)localHashMap5.get("SYMBOL");
        if ((str4 != null) && (str4.startsWith("\\u")))
        {
          localObject3 = new char[] { (char)Integer.parseInt(str4.substring(2), 16) };
          str4 = new String((char[])localObject3);
        }
        Object localObject3 = (String)localHashMap5.get("THOUSANDS_SEP");
        if ((localObject3 != null) && (((String)localObject3).startsWith("\\u")))
        {
          localObject4 = new char[] { (char)Integer.parseInt(((String)localObject3).substring(2), 16) };
          localObject3 = new String((char[])localObject4);
        }
        Object localObject4 = (String)localHashMap5.get("DECIMAL_SEP");
        if ((localObject4 != null) && (((String)localObject4).startsWith("\\u")))
        {
          localObject5 = new char[] { (char)Integer.parseInt(((String)localObject4).substring(2), 16) };
          localObject4 = new String((char[])localObject5);
        }
        Object localObject5 = (String)localHashMap5.get("SYMBOL_POSITION");
        if (str3 == null) {
          throw new FXException(34018, "Error when parsing xml file " + paramString + ": currency code must be specified.");
        }
        localFXCurrencyFormat.setCode(str3);
        if (str4 != null) {
          localFXCurrencyFormat.setSymbol(str4);
        }
        if (localObject3 != null) {
          localFXCurrencyFormat.setThousandSeparator((String)localObject3);
        }
        if (localObject4 != null) {
          localFXCurrencyFormat.setDecimalSeparator((String)localObject4);
        }
        if (localObject5 != null) {
          localFXCurrencyFormat.setSymbolPosition((String)localObject5);
        }
        HashMap localHashMap6 = (HashMap)jdField_do.get(str2);
        if (localHashMap6 == null)
        {
          localHashMap6 = new HashMap();
          jdField_do.put(str2, localHashMap6);
        }
        localHashMap6.put(str3, localFXCurrencyFormat);
        localHashMap5 = null;
      }
      localHashMap3 = null;
      str2 = null;
      localHashMap4 = null;
      localArrayList2 = null;
      localIterator2 = null;
    }
    localIterator1 = null;
    localArrayList1 = null;
    localHashMap2 = null;
    localHashMap1 = null;
  }
  
  public static int getNumDecimals(String paramString, HashMap paramHashMap)
    throws FXException
  {
    int i = -1;
    if (paramString == null) {
      throw new FXException(34017, "Could not perform getNumDecimals() because the specified currencyCode string was null.");
    }
    Integer localInteger = (Integer)jdField_if.get(paramString);
    if (localInteger != null) {
      i = localInteger.intValue();
    } else {
      i = 2;
    }
    return i;
  }
  
  public static FXCurrencyFormat getFormat(String paramString, HashMap paramHashMap)
    throws FXException
  {
    return getFormat(paramString, "default", paramHashMap);
  }
  
  public static FXCurrencyFormat getFormat(String paramString1, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    FXCurrencyFormat localFXCurrencyFormat1 = new FXCurrencyFormat();
    if (paramString1 == null) {
      throw new FXException(34017, "Could not perform getFormat() because the specified currencyCode string was null.");
    }
    HashMap localHashMap = (HashMap)jdField_do.get(paramString2);
    FXCurrencyFormat localFXCurrencyFormat2 = null;
    if (localHashMap != null) {
      localFXCurrencyFormat2 = (FXCurrencyFormat)localHashMap.get(paramString1);
    }
    if (localFXCurrencyFormat2 != null) {
      localFXCurrencyFormat1.set(localFXCurrencyFormat2);
    } else {
      localFXCurrencyFormat1.setCode(paramString1);
    }
    return localFXCurrencyFormat1;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.fx.FXUtil
 * JD-Core Version:    0.7.0.1
 */