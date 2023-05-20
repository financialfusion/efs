package com.ffusion.jtf;

import java.io.PrintStream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class UrlEncryptorJCE
  implements UrlEncryptor
{
  private boolean jdField_for = true;
  private Cipher jdField_if = null;
  private Cipher jdField_do = null;
  private String a = "UTF-8";
  
  public UrlEncryptorJCE(boolean paramBoolean)
  {
    this.jdField_for = paramBoolean;
    if (!this.jdField_for) {
      return;
    }
    try
    {
      byte[] arrayOfByte = { 1, 2, 3, 4, 5, 6, 7, 8 };
      SecretKeySpec localSecretKeySpec = new SecretKeySpec(arrayOfByte, "DES");
      this.jdField_if = Cipher.getInstance("DES/ECB/PKCS5Padding");
      this.jdField_if.init(1, localSecretKeySpec);
      this.jdField_do = Cipher.getInstance("DES/ECB/PKCS5Padding");
      this.jdField_do.init(2, localSecretKeySpec, this.jdField_if.getParameters());
    }
    catch (Exception localException)
    {
      System.out.println("Exception: " + localException);
      this.jdField_for = false;
    }
  }
  
  public String encrypt(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    if (!this.jdField_for) {
      return paramString;
    }
    try
    {
      byte[] arrayOfByte = this.jdField_if.doFinal(paramString.getBytes(this.a));
      return a(arrayOfByte);
    }
    catch (Exception localException) {}
    return paramString;
  }
  
  public String decrypt(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    if (!this.jdField_for) {
      return paramString;
    }
    try
    {
      byte[] arrayOfByte = this.jdField_do.doFinal(a(paramString));
      return new String(arrayOfByte, this.a);
    }
    catch (Exception localException) {}
    return paramString;
  }
  
  public void clear() {}
  
  private String a(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      localStringBuffer.append(arrayOfChar[(paramArrayOfByte[i] >> 4 & 0xF)]);
      localStringBuffer.append(arrayOfChar[(paramArrayOfByte[i] & 0xF)]);
    }
    return localStringBuffer.toString();
  }
  
  private byte[] a(String paramString)
  {
    byte[] arrayOfByte = new byte[paramString.length() / 2];
    try
    {
      int i = 0;
      for (int j = 0; i < paramString.length() / 2; j++)
      {
        arrayOfByte[i] = ((byte)(Integer.parseInt(paramString.substring(j * 2, j * 2 + 2), 16) & 0xFF));
        i++;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return null;
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      return null;
    }
    return arrayOfByte;
  }
  
  public void setDesiredEncoding(String paramString)
  {
    this.a = paramString;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    int i = Integer.parseInt(paramArrayOfString[0]);
    boolean bool = Boolean.valueOf(paramArrayOfString[1]).booleanValue();
    String str1 = "This is a test. This is a test. This is a test. This is a test.";
    String str2 = null;
    String str3 = null;
    UrlEncryptorJCE localUrlEncryptorJCE = new UrlEncryptorJCE(bool);
    System.out.println("Time = " + System.currentTimeMillis());
    for (int j = 0; j < i; j++) {
      str2 = localUrlEncryptorJCE.encrypt(str1.toString());
    }
    System.out.println("Time = " + System.currentTimeMillis());
    for (j = 0; j < i; j++) {
      str3 = localUrlEncryptorJCE.decrypt(str2);
    }
    System.out.println("Time = " + System.currentTimeMillis());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.UrlEncryptorJCE
 * JD-Core Version:    0.7.0.1
 */