package com.ffusion.alert.encryption.des;

public class TripleDESEncrypt
{
  private static DESKeySet a = null;
  
  public static String jdMethod_if(String paramString)
  {
    if (paramString.equals("")) {
      return "";
    }
    return TripleDES.a(paramString, a);
  }
  
  public static String a(String paramString)
  {
    if (paramString.equals("")) {
      return "";
    }
    byte[] arrayOfByte = TripleDES.jdMethod_if(paramString, a);
    if (arrayOfByte == null) {
      return null;
    }
    return new String(arrayOfByte);
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


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.encryption.des.TripleDESEncrypt
 * JD-Core Version:    0.7.0.1
 */