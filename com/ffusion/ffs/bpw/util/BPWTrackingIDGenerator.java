package com.ffusion.ffs.bpw.util;

import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.util.logging.TrackingIDGenerator;

public class BPWTrackingIDGenerator
{
  private static String a = "BPW";
  
  public static void initialize()
  {
    String str = BPWRegistryUtil.getProperty("bpw.transaction.audit.log.instance.id", "01");
    str = "00" + str;
    str = str.substring(str.length() - 2, str.length());
    str = a + str;
    FFSDebug.log("BPWTrackingIDGenerator: instance id: " + str, 6);
    TrackingIDGenerator.initialize(str);
  }
  
  public static String getNextId()
  {
    return TrackingIDGenerator.GetNextID();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator
 * JD-Core Version:    0.7.0.1
 */