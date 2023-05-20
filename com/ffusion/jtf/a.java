package com.ffusion.jtf;

import java.io.PrintStream;
import java.util.HashMap;

public class a
  implements UrlEncryptor
{
  private boolean jdField_new = true;
  private HashMap jdField_try = new HashMap();
  private String jdField_int = "UTF-8";
  
  public a(boolean paramBoolean)
  {
    this.jdField_new = paramBoolean;
  }
  
  public String encrypt(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    if (!this.jdField_new) {
      return paramString;
    }
    for (String str = a(); this.jdField_try.containsKey(str); str = a()) {}
    this.jdField_try.put(str, paramString);
    return str;
  }
  
  public String decrypt(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    if (!this.jdField_new) {
      return paramString;
    }
    if (!this.jdField_try.containsKey(paramString)) {
      return paramString;
    }
    return (String)this.jdField_try.get(paramString);
  }
  
  public void clear()
  {
    this.jdField_try.clear();
  }
  
  public void setDesiredEncoding(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  private String a()
  {
    return String.valueOf(Math.random());
  }
  
  public static void a(String[] paramArrayOfString)
  {
    int i = Integer.parseInt(paramArrayOfString[0]);
    boolean bool = Boolean.valueOf(paramArrayOfString[1]).booleanValue();
    String str1 = "This is a test. This is a test. This is a test. This is a test.";
    String str2 = null;
    String str3 = null;
    a locala = new a(bool);
    System.out.println("Time = " + System.currentTimeMillis());
    for (int j = 0; j < i; j++) {
      str2 = locala.encrypt(str1.toString());
    }
    System.out.println("Time = " + System.currentTimeMillis());
    for (j = 0; j < i; j++) {
      str3 = locala.decrypt(str2);
    }
    System.out.println("Time = " + System.currentTimeMillis());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.a
 * JD-Core Version:    0.7.0.1
 */