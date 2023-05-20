package com.ffusion.ffs.bpw.enums;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.enums.ValuedEnum;

public class BPWEnum
  extends ValuedEnum
{
  private static Map jdField_if = new HashMap();
  private static Map a = new HashMap();
  
  public BPWEnum(String paramString, int paramInt)
  {
    super(paramString, paramInt);
    Integer localInteger1 = new Integer(paramInt);
    Integer localInteger2 = (Integer)jdField_if.get(getClass());
    Integer localInteger3 = (Integer)a.get(getClass());
    if ((localInteger2 == null) || (localInteger2.compareTo(localInteger1) > 0)) {
      jdField_if.put(getClass(), localInteger1);
    }
    if ((localInteger3 == null) || (localInteger3.compareTo(localInteger1) > 0)) {
      a.put(getClass(), localInteger1);
    }
  }
  
  public static final int getMaxValue(Class paramClass)
  {
    Integer localInteger = (Integer)a.get(paramClass);
    return localInteger == null ? -1 : localInteger.intValue();
  }
  
  public static final int getMinValue(Class paramClass)
  {
    Integer localInteger = (Integer)jdField_if.get(paramClass);
    return localInteger == null ? -1 : localInteger.intValue();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.enums.BPWEnum
 * JD-Core Version:    0.7.0.1
 */