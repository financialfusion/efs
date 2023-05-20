package com.ffusion.ffs.bpw.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PaymentType
  extends BPWEnum
{
  private boolean jdField_do;
  private boolean jdField_for;
  public static final PaymentType PMTTYPE_SINGLE = new PaymentType("PAYMENT", 1, false, false);
  public static final PaymentType PMTTYPE_RECURRING = new PaymentType("Recurring", 2, false, false);
  public static final PaymentType PMTTYPE_REPETITIVE = new PaymentType("Repetitive", 3, true, false);
  public static final PaymentType PMTTYPE_RECMODEL = new PaymentType("Recmodel", 4, true, false);
  public static final PaymentType PMTTYPE_TEMPLATE = new PaymentType("TEMPLATE", 5, false, true);
  public static final PaymentType PMTTYPE_RECTEMPLATE = new PaymentType("RECTEMPLATE", 6, true, true);
  public static final PaymentType PMTTYPE_BATCHTEMPLATE = new PaymentType("BATCHTEMPLATE", 7, false, true);
  public static final PaymentType PMTTYPE_CURRENT = new PaymentType("Current", 8, false, false);
  
  private PaymentType(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramString, paramInt);
    this.jdField_do = paramBoolean1;
    this.jdField_for = paramBoolean2;
  }
  
  public static PaymentType getEnum(String paramString)
  {
    return (PaymentType)getEnum(PaymentType.class, paramString);
  }
  
  public static PaymentType getEnum(int paramInt)
  {
    return (PaymentType)getEnum(PaymentType.class, paramInt);
  }
  
  public static Map getEnumMap()
  {
    return getEnumMap(PaymentType.class);
  }
  
  public static List getEnumList()
  {
    return getEnumList(PaymentType.class);
  }
  
  public static Iterator iterator()
  {
    return iterator(PaymentType.class);
  }
  
  public static int getMaxValue()
  {
    return getMaxValue(PaymentType.class);
  }
  
  public static int getMinValue()
  {
    return getMinValue(PaymentType.class);
  }
  
  public boolean isRecurring()
  {
    return this.jdField_do;
  }
  
  public boolean isTemplate()
  {
    return this.jdField_for;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.enums.PaymentType
 * JD-Core Version:    0.7.0.1
 */