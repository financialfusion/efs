package com.ffusion.ffs.bpw.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FrequencyType
  extends BPWEnum
{
  private int jdField_int;
  public static final FrequencyType ANNUALLY = new FrequencyType("ANNUALLY", 0, 0);
  public static final FrequencyType BIMONTHLY = new FrequencyType("BIMONTHLY", 1, 2);
  public static final FrequencyType BIWEEKLY = new FrequencyType("BIWEEKLY", 2, 3);
  public static final FrequencyType FOURWEEKS = new FrequencyType("FOURWEEKS", 3, 4);
  public static final FrequencyType MONTHLY = new FrequencyType("MONTHLY", 4, 5);
  public static final FrequencyType QUARTERLY = new FrequencyType("QUARTERLY", 5, 6);
  public static final FrequencyType SEMIANNUALLY = new FrequencyType("SEMIANNUALLY", 6, 7);
  public static final FrequencyType TRIANNUALLY = new FrequencyType("TRIANNUALLY", 7, 8);
  public static final FrequencyType TWICEMONTHLY = new FrequencyType("TWICEMONTHLY", 8, 9);
  public static final FrequencyType WEEKLY = new FrequencyType("WEEKLY", 9, 10);
  public static final FrequencyType ONCE = new FrequencyType("ONCE", 10, -1);
  public static final FrequencyType DAILY = new FrequencyType("DAILY", 11, -1);
  public static final FrequencyType IMMEDIATELY = new FrequencyType("IMMEDIATELY", 12, -1);
  public static final FrequencyType NONE = new FrequencyType("NONE", -1, -1);
  
  public FrequencyType(String paramString, int paramInt1, int paramInt2)
  {
    super(paramString, paramInt1);
    this.jdField_int = paramInt2;
  }
  
  public static FrequencyType getEnum(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    if (paramString.equalsIgnoreCase("ANUALLY")) {
      return ANNUALLY;
    }
    if (paramString.equalsIgnoreCase("SEMIMONTHLY")) {
      return TWICEMONTHLY;
    }
    return (FrequencyType)getEnum(FrequencyType.class, paramString.toUpperCase());
  }
  
  public static FrequencyType getEnum(int paramInt)
  {
    return (FrequencyType)getEnum(FrequencyType.class, paramInt);
  }
  
  public static Map getEnumMap()
  {
    return getEnumMap(FrequencyType.class);
  }
  
  public static List getEnumList()
  {
    return getEnumList(FrequencyType.class);
  }
  
  public static Iterator iterator()
  {
    return iterator(FrequencyType.class);
  }
  
  public static int getMaxValue()
  {
    return getMaxValue(FrequencyType.class);
  }
  
  public static int getMinValue()
  {
    return getMinValue(FrequencyType.class);
  }
  
  public int getIFXFreq()
  {
    return this.jdField_int;
  }
  
  public static FrequencyType getByIFXFreq(int paramInt)
  {
    FrequencyType localFrequencyType = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localFrequencyType = (FrequencyType)localIterator.next();
      if (localFrequencyType.getIFXFreq() == paramInt) {
        return localFrequencyType;
      }
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.enums.FrequencyType
 * JD-Core Version:    0.7.0.1
 */