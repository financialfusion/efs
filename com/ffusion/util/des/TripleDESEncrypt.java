package com.ffusion.util.des;

public class TripleDESEncrypt
{
  private static DESKeySet a = null;
  
  public static String encrypt(String paramString)
  {
    if (paramString.equals("")) {
      return "";
    }
    return TripleDES.encrypt(paramString, a);
  }
  
  public static String decrypt(String paramString)
  {
    if (paramString.equals("")) {
      return "";
    }
    return new String(TripleDES.decrypt(paramString, a));
  }
  
  static
  {
    String str1 = "0000000000000000FF12A419C63BE771F12AF419C63BE475512AC414C23B547E";
    String str2 = str1.substring(0, 16);
    String str3 = str1.substring(16, 32);
    String str4 = str1.substring(32, 48);
    String str5 = str1.substring(48, 64);
    a = new DESKeySet(str2, str3, str4, str5);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.des.TripleDESEncrypt
 * JD-Core Version:    0.7.0.1
 */