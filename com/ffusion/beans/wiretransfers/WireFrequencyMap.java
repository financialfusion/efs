package com.ffusion.beans.wiretransfers;

import java.util.HashMap;

public class WireFrequencyMap
{
  public static HashMap freqMapToInt = null;
  public static HashMap freqMapToStr = null;
  
  public static int mapFreqToInt(String paramString)
  {
    Integer localInteger = (Integer)freqMapToInt.get(paramString);
    if (localInteger == null) {
      return 0;
    }
    return localInteger.intValue();
  }
  
  public static String mapFreqToStr(int paramInt)
  {
    String str = (String)freqMapToStr.get(new Integer(paramInt));
    if (str == null) {
      return "UNKNOWN";
    }
    return str;
  }
  
  static
  {
    freqMapToInt = new HashMap();
    freqMapToStr = new HashMap();
    freqMapToInt.put("WEEKLY", new Integer(1));
    freqMapToInt.put("BIWEEKLY", new Integer(2));
    freqMapToInt.put("SEMIMONTHLY", new Integer(3));
    freqMapToInt.put("MONTHLY", new Integer(4));
    freqMapToInt.put("FOURWEEKS", new Integer(5));
    freqMapToInt.put("BIMONTHLY", new Integer(6));
    freqMapToInt.put("QUARTERLY", new Integer(7));
    freqMapToInt.put("SEMIANNUALLY", new Integer(8));
    freqMapToInt.put("ANNUALLY", new Integer(9));
    freqMapToInt.put("ANUALLY", new Integer(9));
    freqMapToStr.put(new Integer(1), "WEEKLY");
    freqMapToStr.put(new Integer(2), "BIWEEKLY");
    freqMapToStr.put(new Integer(3), "SEMIMONTHLY");
    freqMapToStr.put(new Integer(4), "MONTHLY");
    freqMapToStr.put(new Integer(5), "FOURWEEKS");
    freqMapToStr.put(new Integer(6), "BIMONTHLY");
    freqMapToStr.put(new Integer(7), "QUARTERLY");
    freqMapToStr.put(new Integer(8), "SEMIANNUALLY");
    freqMapToStr.put(new Integer(9), "ANNUALLY");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireFrequencyMap
 * JD-Core Version:    0.7.0.1
 */