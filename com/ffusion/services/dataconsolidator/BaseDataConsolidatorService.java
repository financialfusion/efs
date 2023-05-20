package com.ffusion.services.dataconsolidator;

import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.beans.dataconsolidator.DCModule;
import com.ffusion.csil.core.Banking;
import com.ffusion.dataconsolidator.adapter.DCException;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class BaseDataConsolidatorService
{
  public static final String BAITYPECODES_FILE_BASENAME = "baiTypeCodes";
  public static final String BAITYPECODES_FILENAME = "baiTypeCodes.xml";
  public static final String BAITYPECODEDCMODULE_FILENAME = "baiTypeCodeDCModule.xml";
  public static final int _TRANSACTION_TYPE_UNKNOWN = 0;
  public static final int _TRANSACTION_TYPE_CREDIT = 4;
  public static final int _TRANSACTION_TYPE_DEBIT = 5;
  static HashMap jdField_int;
  static HashMap jdField_do;
  static ArrayList jdField_if = new ArrayList();
  static ArrayList jdField_for = new ArrayList();
  static ArrayList a = new ArrayList();
  
  public void initialize()
    throws DCException
  {
    try
    {
      jdField_if();
      a();
    }
    catch (Exception localException)
    {
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      localException.printStackTrace(localPrintWriter);
      DebugLog.log(Level.SEVERE, localStringWriter.toString());
      return;
    }
  }
  
  private void a()
    throws Exception
  {
    if (jdField_int == null) {
      return;
    }
    ArrayList localArrayList = null;
    int i = 0;
    localArrayList = LocaleUtil.getXMLFilesWithBase("baiTypeCodes");
    i = localArrayList.size();
    for (int j = 0; j < i; j++) {
      jdField_do((String)localArrayList.get(j));
    }
  }
  
  private void jdField_do(String paramString)
    throws Exception
  {
    String str1 = null;
    str1 = LocaleUtil.getLocaleIDFromXMLFileName(paramString, "baiTypeCodes");
    if (str1.length() == 0) {
      return;
    }
    HashMap localHashMap1 = XMLUtil.readXmlToHashMap(this, paramString);
    Object localObject1 = localHashMap1.get("TYPE_CODE_LIST");
    HashMap localHashMap2 = null;
    if ((localObject1 != null) && ((localObject1 instanceof HashMap))) {
      localHashMap2 = (HashMap)localObject1;
    } else {
      localHashMap2 = new HashMap();
    }
    ArrayList localArrayList1 = (ArrayList)localHashMap2.get("TYPE_CODE");
    if (localArrayList1 == null) {
      localArrayList1 = new ArrayList();
    }
    Object localObject2 = localHashMap1.get("CUSTOM_TYPE_CODE_LIST");
    if ((localObject2 != null & localObject2 instanceof HashMap))
    {
      HashMap localHashMap3 = (HashMap)localObject2;
      ArrayList localArrayList2 = (ArrayList)localHashMap3.get("TYPE_CODE");
      if (localArrayList2 != null) {
        localArrayList1.addAll(localArrayList2);
      }
    }
    int i = localArrayList1.size();
    Integer localInteger = null;
    String str2 = null;
    HashMap localHashMap4 = null;
    BAITypeCodeInfo localBAITypeCodeInfo = null;
    HashMap localHashMap5 = new HashMap(i);
    Iterator localIterator = null;
    for (int j = 0; j < i; j++)
    {
      localHashMap4 = (HashMap)localArrayList1.get(j);
      try
      {
        localInteger = new Integer((String)localHashMap4.get("CODE"));
      }
      catch (NumberFormatException localNumberFormatException)
      {
        continue;
      }
      str2 = (String)localHashMap4.get("DESCRIPTION");
      if (str2 != null) {
        localHashMap5.put(localInteger, str2);
      }
    }
    localIterator = jdField_int.keySet().iterator();
    while (localIterator.hasNext())
    {
      localInteger = (Integer)localIterator.next();
      localBAITypeCodeInfo = (BAITypeCodeInfo)jdField_int.get(localInteger);
      if (localHashMap5.containsKey(localInteger)) {
        localBAITypeCodeInfo.setDescription(str1, (String)localHashMap5.get(localInteger));
      } else {
        localBAITypeCodeInfo.setDescription(str1, localBAITypeCodeInfo.getDescription());
      }
    }
  }
  
  public int getBAICode(String paramString1, String paramString2)
    throws DCException
  {
    if (jdField_int == null) {
      throw new DCException("Cannot retrieve code - BAICodeCache not initialized");
    }
    DCModule localDCModule = new DCModule(paramString1, paramString2);
    BAITypeCodeInfo localBAITypeCodeInfo = (BAITypeCodeInfo)jdField_do.get(localDCModule);
    if (localBAITypeCodeInfo == null) {
      throw new DCException("BAI type code info for module " + paramString1 + ", column " + paramString2 + " not found in BAI module cache.");
    }
    return localBAITypeCodeInfo.getCode();
  }
  
  private void jdField_if()
    throws Exception
  {
    if (jdField_int != null)
    {
      DebugLog.log(Level.INFO, "BAICodeCache was already initialized (cannot be initialized again)");
      return;
    }
    HashMap localHashMap1 = Banking.getTransactionTypes(0, new HashMap(0));
    HashMap localHashMap2 = XMLUtil.readXmlToHashMap(this, "baiTypeCodes.xml");
    HashMap localHashMap3 = (HashMap)localHashMap2.get("TYPE_CODE_LIST");
    if (localHashMap3 == null) {
      throw new DCException("The TYPE_CODE_LIST tag did not exist in the document");
    }
    ArrayList localArrayList1 = (ArrayList)localHashMap3.get("TYPE_CODE");
    if (localArrayList1 == null) {
      throw new DCException("TYPE_CODE tags were not found in the XML document");
    }
    Object localObject = localHashMap2.get("CUSTOM_TYPE_CODE_LIST");
    if ((localObject != null & localObject instanceof HashMap))
    {
      localHashMap4 = (HashMap)localObject;
      ArrayList localArrayList2 = (ArrayList)localHashMap4.get("TYPE_CODE");
      if (localArrayList2 != null) {
        localArrayList1.addAll(localArrayList2);
      }
    }
    HashMap localHashMap4 = XMLUtil.readXmlToHashMap(this, "baiTypeCodeDCModule.xml");
    HashMap localHashMap5 = (HashMap)localHashMap4.get("CODE_MODULE_LIST");
    if (localHashMap5 == null) {
      throw new DCException("The CODE_MODULE_LIST tag did not exist in the document");
    }
    ArrayList localArrayList3 = (ArrayList)localHashMap5.get("CODE_MODULE");
    if (localArrayList3 == null) {
      throw new DCException("CODE_MODULE tags were not found in the XML document");
    }
    try
    {
      jdField_int = new HashMap();
      jdField_do = new HashMap();
      for (int i = 0; i < localArrayList1.size(); i++)
      {
        HashMap localHashMap6 = (HashMap)localArrayList1.get(i);
        String str1 = (String)localHashMap6.get("CODE");
        String str2 = (String)localHashMap6.get("DESCRIPTION");
        String str3 = (String)localHashMap6.get("TRANSACTION");
        String str4 = (String)localHashMap6.get("LEVEL");
        String str5 = (String)localHashMap6.get("DATA_TYPE");
        String str6 = (String)localHashMap6.get("SUBLEVEL");
        String str7 = (String)localHashMap6.get("TRANSACTION_TYPE");
        if ((str1 == null) || (str2 == null) || (str3 == null) || (str4 == null) || (str5 == null)) {
          throw new DCException("Missing data in BAI type codes xml");
        }
        if (str6 == null) {
          str6 = "Regular";
        }
        int j;
        try
        {
          j = Integer.parseInt(str1);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          throw new DCException("Non-integer BAI code " + str1 + " in BAI type codes xml");
        }
        int k = a(str3);
        int m = jdField_if(str4);
        int n = jdField_int(str5);
        int i1;
        if (n == 4)
        {
          String str8 = (String)localHashMap6.get("NUM_DECIMALS");
          if (str8 == null) {
            throw new DCException("Missing NUM_DECIMALS tag in BAI type codes xml");
          }
          try
          {
            i1 = Integer.parseInt(str8);
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            throw new DCException("Non-integer number of decimals (" + str8 + ") in BAI type codes xml");
          }
        }
        else
        {
          i1 = -1;
        }
        int i2;
        if (str7 == null) {
          i2 = a(k);
        } else if (a(str7, localHashMap1)) {
          try
          {
            i2 = Integer.parseInt(str7);
          }
          catch (NumberFormatException localNumberFormatException3)
          {
            throw new DCException("Transaction type " + str7 + " provided for BAI code " + j + " in BAI type codes xml is not valid; " + "value must be an integer");
          }
        } else {
          throw new DCException("Invalid transaction type " + str7 + " specified in BAI type codes xml");
        }
        ArrayList localArrayList4 = a(localArrayList3, str1, m);
        BAITypeCodeInfo localBAITypeCodeInfo = new BAITypeCodeInfo(j, str2, k, m, n, i1, i2, localArrayList4);
        localBAITypeCodeInfo.setSubLevel(str6);
        jdField_int.put(new Integer(j), localBAITypeCodeInfo);
        if (k == 1) {
          jdField_if.add(str1);
        } else if (k == 2) {
          jdField_for.add(str1);
        } else {
          a.add(str1);
        }
        if (2 != m) {
          for (int i3 = 0; i3 < localArrayList4.size(); i3++) {
            jdField_do.put(localArrayList4.get(i3), localBAITypeCodeInfo);
          }
        }
      }
    }
    catch (Exception localException)
    {
      jdField_int = null;
      jdField_do = null;
      throw localException;
    }
    DebugLog.log(Level.INFO, "BAICodeCache initialized");
  }
  
  private ArrayList a(ArrayList paramArrayList, String paramString, int paramInt)
    throws DCException
  {
    ArrayList localArrayList1 = new ArrayList();
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      HashMap localHashMap1 = (HashMap)paramArrayList.get(i);
      String str1 = (String)localHashMap1.get("NAME");
      if (str1 == null) {
        throw new DCException("Missing NAME tag for module in BAI type code DC module xml");
      }
      if (!jdField_for(str1)) {
        throw new DCException("Invalid name (" + str1 + ") for a module in BAI type code DC module xml");
      }
      HashMap localHashMap2 = (HashMap)localHashMap1.get("CODE_DEFINITION_LIST");
      if (localHashMap2 == null) {
        throw new DCException("The CODE_DEFINITION_LIST tag did not exist with the " + str1 + " module in the document");
      }
      ArrayList localArrayList2 = (ArrayList)localHashMap2.get("CODE_DEFINITION");
      if (localArrayList2 == null) {
        throw new DCException("CODE_DEFINITION tags were not found under the CODE_DEFINITION_LIST with the " + str1 + " module in the document");
      }
      for (int j = 0; j < localArrayList2.size(); j++)
      {
        HashMap localHashMap3 = (HashMap)localArrayList2.get(j);
        String str2 = (String)localHashMap3.get("CODE");
        String str3 = (String)localHashMap3.get("DCCOLUMN");
        if (paramString.equals(str2))
        {
          if ((str2 == null) || (str3 == null)) {
            throw new DCException("Missing data for code definition in BAI type code DC module xml");
          }
          if (("".equals(str3.trim())) && (2 != paramInt)) {
            throw new DCException("For BAITypeCode = " + paramString + ", columnName is an empty string while level is not DETAIL");
          }
          str3 = str3.toUpperCase();
          DCModule localDCModule = new DCModule(str1, str3);
          localArrayList1.add(localDCModule);
        }
      }
    }
    return localArrayList1;
  }
  
  private boolean a(String paramString, HashMap paramHashMap)
  {
    if ((paramString == null) || (paramHashMap == null)) {
      return false;
    }
    Integer localInteger = null;
    try
    {
      localInteger = new Integer(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return false;
    }
    return paramHashMap.containsKey(localInteger);
  }
  
  private int a(String paramString)
    throws DCException
  {
    if ("NA".equalsIgnoreCase(paramString)) {
      return 0;
    }
    if ("CR".equalsIgnoreCase(paramString)) {
      return 1;
    }
    if ("DB".equalsIgnoreCase(paramString)) {
      return 2;
    }
    throw new DCException("transaction string value " + paramString + " provided; " + "value must be " + "NA" + " or " + "CR" + " or " + "DB");
  }
  
  private int jdField_if(String paramString)
    throws DCException
  {
    if ("STATUS".equalsIgnoreCase(paramString)) {
      return 0;
    }
    if ("SUMMARY".equalsIgnoreCase(paramString)) {
      return 1;
    }
    if ("DETAIL".equalsIgnoreCase(paramString)) {
      return 2;
    }
    throw new DCException("level string value " + paramString + " provided; " + "value must be " + "STATUS" + " or " + "SUMMARY" + " or " + "DETAIL");
  }
  
  private int jdField_int(String paramString)
    throws DCException
  {
    if ("Amount".equalsIgnoreCase(paramString)) {
      return 0;
    }
    if ("String".equalsIgnoreCase(paramString)) {
      return 1;
    }
    if ("Date".equalsIgnoreCase(paramString)) {
      return 2;
    }
    if ("Time".equalsIgnoreCase(paramString)) {
      return 3;
    }
    if (paramString.toLowerCase().startsWith("Decimal".toLowerCase())) {
      return 4;
    }
    throw new DCException("data type string value " + paramString + " provided; " + "value must be " + "Amount" + " or " + "String" + " or " + "Date" + " or " + "Time" + " or should start with " + "Decimal");
  }
  
  private int a(int paramInt)
    throws DCException
  {
    if (paramInt == 0) {
      return 0;
    }
    if (paramInt == 1) {
      return 4;
    }
    if (paramInt == 2) {
      return 5;
    }
    throw new DCException(paramInt + " is not a valid transaction code");
  }
  
  private boolean jdField_for(String paramString)
  {
    return ("Account History".equals(paramString)) || ("Account Summary".equals(paramString)) || ("Extended Account Summary".equals(paramString)) || ("Account Transactions".equals(paramString)) || ("Lockbox Summary".equals(paramString)) || ("Lockbox Transactions".equals(paramString)) || ("Lockbox Credit Items".equals(paramString)) || ("Disbursement Summary".equals(paramString)) || ("Disbursement Transactions".equals(paramString));
  }
  
  public ArrayList getBAITypeCodesForTransaction(int paramInt, HashMap paramHashMap)
    throws DCException
  {
    if (paramInt == 1) {
      return jdField_if;
    }
    if (paramInt == 2) {
      return jdField_for;
    }
    if (paramInt == 0) {
      return a;
    }
    return new ArrayList(0);
  }
  
  public BAITypeCodeInfo getBAITypeCodeInfo(int paramInt)
    throws DCException
  {
    if (jdField_int == null) {
      throw new DCException("Cannot retrieve BAI type code info - BAICodeCache not initialized");
    }
    Integer localInteger = new Integer(paramInt);
    BAITypeCodeInfo localBAITypeCodeInfo = (BAITypeCodeInfo)jdField_int.get(localInteger);
    if (localBAITypeCodeInfo == null) {
      throw new DCException("Info for BAI code " + paramInt + " not found in BAI code cache.");
    }
    return localBAITypeCodeInfo;
  }
  
  public ArrayList getBAITypeCodeInfoList(int paramInt)
    throws DCException
  {
    if (jdField_int == null) {
      throw new DCException("Cannot retrieve BAI type code info list - BAICodeCache not initialized");
    }
    ArrayList localArrayList1 = new ArrayList(jdField_int.values());
    ArrayList localArrayList2 = new ArrayList();
    for (int i = 0; i < localArrayList1.size(); i++)
    {
      BAITypeCodeInfo localBAITypeCodeInfo = (BAITypeCodeInfo)localArrayList1.get(i);
      if (localBAITypeCodeInfo.getLevel() == paramInt) {
        localArrayList2.add(localBAITypeCodeInfo);
      }
    }
    return localArrayList2;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.dataconsolidator.BaseDataConsolidatorService
 * JD-Core Version:    0.7.0.1
 */