package com.ffusion.ffs.bpw.util;

import com.ffusion.ffs.bpw.interfaces.BankInfo;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.util.FFSProperties;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public final class BPWRegistryUtil
{
  private static final Object jdField_do = InstructionType.class;
  private static final Object jdField_new = FulfillmentInfo.class;
  private static Hashtable jdField_if = null;
  private static FulfillmentInfo[] a = null;
  private static FFSProperties jdField_int = null;
  private static HashMap jdField_for = null;
  
  public static final InstructionType[] getAllInstructionTypes()
  {
    ArrayList localArrayList = new ArrayList();
    Enumeration localEnumeration = jdField_if.elements();
    while (localEnumeration.hasMoreElements())
    {
      InstructionType localInstructionType = (InstructionType)localEnumeration.nextElement();
      localArrayList.add(localInstructionType);
    }
    return localArrayList.isEmpty() ? null : (InstructionType[])localArrayList.toArray(new InstructionType[0]);
  }
  
  public static final int findFulfillmentRouteID(String paramString1, String paramString2)
  {
    int i = -1;
    String str = a(paramString1, paramString2);
    InstructionType localInstructionType = (InstructionType)jdField_if.get(str);
    if (localInstructionType != null) {
      i = localInstructionType.RouteID;
    }
    return i;
  }
  
  public static final String findPaymentInstructionType(String paramString, int paramInt)
  {
    String str = null;
    Enumeration localEnumeration = jdField_if.elements();
    while (localEnumeration.hasMoreElements())
    {
      InstructionType localInstructionType = (InstructionType)localEnumeration.nextElement();
      if ((localInstructionType.FIId.equals(paramString)) && (localInstructionType.RouteID == paramInt)) {
        str = localInstructionType.InstructionType;
      }
    }
    return str;
  }
  
  public static final InstructionType getInstructionType(String paramString1, String paramString2)
  {
    String str = a(paramString1, paramString2);
    InstructionType localInstructionType = (InstructionType)jdField_if.get(str);
    return localInstructionType;
  }
  
  public static final void setInstructionTypes(InstructionType[] paramArrayOfInstructionType)
  {
    jdField_if = new Hashtable();
    for (int i = 0; i < paramArrayOfInstructionType.length; i++)
    {
      String str = a(paramArrayOfInstructionType[i].FIId, paramArrayOfInstructionType[i].InstructionType);
      InstructionType localInstructionType = (InstructionType)jdField_if.get(str);
      if (localInstructionType != null) {
        str = str + i;
      }
      jdField_if.put(str, paramArrayOfInstructionType[i]);
    }
  }
  
  public static final void setInstructionTypesHash(Hashtable paramHashtable)
  {
    jdField_if = paramHashtable;
  }
  
  public static final Hashtable getInstructionTypesHash()
  {
    return jdField_if;
  }
  
  public static final void addInstructionType(InstructionType paramInstructionType)
  {
    String str = a(paramInstructionType.FIId, paramInstructionType.InstructionType);
    jdField_if.put(str, paramInstructionType);
  }
  
  public static final void deleteInstructionType(String paramString1, String paramString2)
  {
    String str = a(paramString1, paramString2);
    InstructionType localInstructionType = (InstructionType)jdField_if.remove(str);
  }
  
  public static final boolean isDuplicateRouteID(int paramInt, String paramString)
  {
    if (paramInt == 0) {
      return false;
    }
    Enumeration localEnumeration = jdField_if.elements();
    while (localEnumeration.hasMoreElements())
    {
      InstructionType localInstructionType = (InstructionType)localEnumeration.nextElement();
      if ((localInstructionType.RouteID == paramInt) && (localInstructionType.FIId.equals(paramString))) {
        return true;
      }
    }
    return false;
  }
  
  private static String a(String paramString1, String paramString2)
  {
    return paramString2 + "_" + paramString1;
  }
  
  public static final FulfillmentInfo[] getAllFunfillmentInfo()
  {
    return a;
  }
  
  public static final FulfillmentInfo getFulfillmentInfo(int paramInt)
  {
    FulfillmentInfo localFulfillmentInfo = null;
    for (int i = 0; i < a.length; i++) {
      if (a[i].RouteID == paramInt)
      {
        localFulfillmentInfo = a[i];
        break;
      }
    }
    return localFulfillmentInfo;
  }
  
  public static final FulfillmentInfo getFulfillmentInfo(String paramString)
  {
    FulfillmentInfo localFulfillmentInfo = null;
    for (int i = 0; i < a.length; i++) {
      if (a[i].FulfillmentSystemName.equals(paramString))
      {
        localFulfillmentInfo = a[i];
        break;
      }
    }
    return localFulfillmentInfo;
  }
  
  public static final FulfillmentInfo getFulfillmentInfo(Class paramClass)
  {
    FulfillmentInfo localFulfillmentInfo = null;
    for (int i = 0; i < a.length; i++) {
      if (a[i].HandlerName.equals(paramClass.getName()))
      {
        localFulfillmentInfo = a[i];
        break;
      }
    }
    return localFulfillmentInfo;
  }
  
  public static final void addFulfillment(FulfillmentInfo paramFulfillmentInfo)
  {
    if (a == null) {
      return;
    }
    int i = a.length;
    FulfillmentInfo[] arrayOfFulfillmentInfo = new FulfillmentInfo[i + 1];
    System.arraycopy(a, 0, arrayOfFulfillmentInfo, 0, i);
    arrayOfFulfillmentInfo[i] = paramFulfillmentInfo;
    a = arrayOfFulfillmentInfo;
  }
  
  public static final void updateFulfillment(FulfillmentInfo paramFulfillmentInfo)
  {
    if (a == null) {
      return;
    }
    int i = 0;
    int j = paramFulfillmentInfo.RouteID;
    for (i = 0; i < a.length; i++) {
      if (a[i].RouteID == j)
      {
        paramFulfillmentInfo = a[i];
        break;
      }
    }
    if (i >= a.length) {
      return;
    }
    a[i] = paramFulfillmentInfo;
  }
  
  public static final void deleteFulfillment(int paramInt)
  {
    if (a == null) {
      return;
    }
    int i = a.length;
    if (i <= 0) {
      return;
    }
    int j = 0;
    FulfillmentInfo[] arrayOfFulfillmentInfo = new FulfillmentInfo[i - 1];
    for (int k = 0; k < i; k++) {
      if (a[k].RouteID != paramInt) {
        arrayOfFulfillmentInfo[(j++)] = a[k];
      }
    }
    a = arrayOfFulfillmentInfo;
  }
  
  public static final void setFulfillmentInfo(FulfillmentInfo[] paramArrayOfFulfillmentInfo)
  {
    a = paramArrayOfFulfillmentInfo;
  }
  
  public static final void setFFSProperties(FFSProperties paramFFSProperties)
  {
    jdField_int = paramFFSProperties;
  }
  
  public static final FFSProperties getFFSProperties()
  {
    return jdField_int;
  }
  
  public static final String getProperty(String paramString)
  {
    return jdField_int.getProperty(paramString);
  }
  
  public static final String getProperty(String paramString1, String paramString2)
  {
    return jdField_int.getProperty(paramString1, paramString2);
  }
  
  public static final void setAllBankInfo(BankInfo[] paramArrayOfBankInfo)
  {
    jdField_for = new HashMap();
    for (int i = 0; i < paramArrayOfBankInfo.length; i++) {
      jdField_for.put(paramArrayOfBankInfo[i].bankID, paramArrayOfBankInfo[i]);
    }
  }
  
  public static final BankInfo getBankInfo(String paramString)
  {
    if (jdField_for == null) {
      return null;
    }
    BankInfo localBankInfo = (BankInfo)jdField_for.get(paramString);
    return localBankInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.BPWRegistryUtil
 * JD-Core Version:    0.7.0.1
 */