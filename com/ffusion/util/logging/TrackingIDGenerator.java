package com.ffusion.util.logging;

public class TrackingIDGenerator
{
  private static String jdField_do = "01";
  private static long a = 0L;
  private static long jdField_if = 0L;
  
  public static void initialize(String paramString)
  {
    jdField_do = paramString;
  }
  
  public static synchronized String GetNextID()
  {
    long l = System.currentTimeMillis();
    if (l == a) {
      jdField_if += 1L;
    } else {
      jdField_if = 0L;
    }
    a = l;
    if (jdField_if < 10L) {
      return jdField_do + l + "0" + jdField_if;
    }
    return jdField_do + l + jdField_if;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.TrackingIDGenerator
 * JD-Core Version:    0.7.0.1
 */