package com.ffusion.alert.encryption;

import com.ffusion.alert.encryption.des.TripleDESEncrypt;
import java.io.PrintStream;

public class StringEncrypt
{
  public static void main(String[] paramArrayOfString)
  {
    if (paramArrayOfString.length != 1)
    {
      a();
      System.exit(-1);
    }
    String str = null;
    try
    {
      str = TripleDESEncrypt.jdMethod_if(paramArrayOfString[0]);
    }
    catch (Exception localException)
    {
      System.out.println("Unable to encrypt input value.");
      localException.printStackTrace();
      System.exit(-1);
    }
    System.out.println("Encrypted Value:");
    System.out.println(str);
    System.out.println();
  }
  
  private static void a()
  {
    System.out.println("Usage: encrypt <value to encrypt>");
    System.out.println();
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.encryption.StringEncrypt
 * JD-Core Version:    0.7.0.1
 */