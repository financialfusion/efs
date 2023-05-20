package com.ffusion.alert.shared;

public class AlertVersion
  implements AlertVersionConsts
{
  private static String jdField_byte = null;
  private static String jdField_try = null;
  
  public static final String jdMethod_if()
  {
    if (jdField_byte == null)
    {
      jdField_byte = "2.0.5.205021";
      if ("".length() > 0) {
        jdField_byte += " ";
      }
    }
    return jdField_byte;
  }
  
  public static final String a()
  {
    if (jdField_try == null) {
      jdField_try = "Universal Alert Engine " + jdMethod_if();
    }
    return jdField_try;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.shared.AlertVersion
 * JD-Core Version:    0.7.0.1
 */