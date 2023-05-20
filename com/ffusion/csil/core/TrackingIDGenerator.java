package com.ffusion.csil.core;

public class TrackingIDGenerator
  extends Initialize
{
  public static void initialize(String paramString)
  {
    com.ffusion.util.logging.TrackingIDGenerator.initialize(paramString);
  }
  
  public static String GetNextID()
  {
    return com.ffusion.util.logging.TrackingIDGenerator.GetNextID();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.TrackingIDGenerator
 * JD-Core Version:    0.7.0.1
 */