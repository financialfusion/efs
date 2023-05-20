package com.ffusion.beans.wiretransfers;

import java.util.HashMap;

public class WireAccountMap
{
  public static HashMap accountMapToInt = null;
  public static HashMap accountMapToStr = null;
  
  public static int mapAccountTypeToInt(String paramString)
  {
    if (paramString == null) {
      return 0;
    }
    paramString = paramString.toUpperCase();
    Integer localInteger = (Integer)accountMapToInt.get(paramString);
    if (localInteger == null) {
      return 0;
    }
    return localInteger.intValue();
  }
  
  public static String mapAccountTypeToStr(int paramInt)
  {
    String str = (String)accountMapToStr.get(new Integer(paramInt));
    if (str == null) {
      return "OTHER";
    }
    return str;
  }
  
  static
  {
    accountMapToInt = new HashMap();
    accountMapToStr = new HashMap();
    accountMapToInt.put("UNKNOWN", new Integer(0));
    accountMapToInt.put("CHECKING", new Integer(1));
    accountMapToInt.put("SAVINGS", new Integer(2));
    accountMapToInt.put("CREDITLINE", new Integer(7));
    accountMapToInt.put("MONEYMRKT", new Integer(12));
    accountMapToInt.put("LOAN", new Integer(4));
    accountMapToInt.put("MORTGAGE", new Integer(5));
    accountMapToInt.put("HOMEEQUITY", new Integer(6));
    accountMapToInt.put("CD", new Integer(8));
    accountMapToInt.put("IRA", new Integer(9));
    accountMapToInt.put("STOCKBOND", new Integer(10));
    accountMapToInt.put("BROKERAGE", new Integer(11));
    accountMapToInt.put("BUSINESSLOAN", new Integer(13));
    accountMapToInt.put("CREDIT", new Integer(3));
    accountMapToInt.put("CREDITCARD", new Integer(3));
    accountMapToInt.put("FIXEDDEPOSIT", new Integer(14));
    accountMapToInt.put("OTHER", new Integer(15));
    accountMapToStr.put(new Integer(0), "UNKNOWN");
    accountMapToStr.put(new Integer(1), "CHECKING");
    accountMapToStr.put(new Integer(2), "SAVINGS");
    accountMapToStr.put(new Integer(7), "CREDITLINE");
    accountMapToStr.put(new Integer(12), "MONEYMRKT");
    accountMapToStr.put(new Integer(4), "LOAN");
    accountMapToStr.put(new Integer(5), "MORTGAGE");
    accountMapToStr.put(new Integer(6), "HOMEEQUITY");
    accountMapToStr.put(new Integer(8), "CD");
    accountMapToStr.put(new Integer(9), "IRA");
    accountMapToStr.put(new Integer(10), "STOCKBOND");
    accountMapToStr.put(new Integer(11), "BROKERAGE");
    accountMapToStr.put(new Integer(13), "BUSINESSLOAN");
    accountMapToStr.put(new Integer(3), "CREDIT");
    accountMapToStr.put(new Integer(14), "FIXEDDEPOSIT");
    accountMapToStr.put(new Integer(15), "OTHER");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireAccountMap
 * JD-Core Version:    0.7.0.1
 */