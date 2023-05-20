package com.ffusion.alert.encryption.des;

import java.io.Serializable;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESKeySet
  implements Serializable
{
  private byte[] jdField_do = null;
  private byte[] a = null;
  private byte[] jdField_new = null;
  private byte[] jdField_int = null;
  private byte[] jdField_for = null;
  private SecretKey jdField_if = null;
  private IvParameterSpec jdField_try = null;
  
  public DESKeySet(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.jdField_new = a(paramString2);
    this.jdField_int = a(paramString3);
    this.jdField_for = a(paramString4);
    this.a = new byte[24];
    for (int i = 0; i < 8; i++)
    {
      this.a[i] = this.jdField_new[i];
      this.a[(i + 8)] = this.jdField_int[i];
      this.a[(i + 16)] = this.jdField_for[i];
    }
    if (paramString1 != null) {
      this.jdField_do = a(paramString1);
    } else {
      this.jdField_do = a();
    }
    try
    {
      DESedeKeySpec localDESedeKeySpec = new DESedeKeySpec(this.a);
      SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance("DESede");
      this.jdField_if = localSecretKeyFactory.generateSecret(localDESedeKeySpec);
      this.jdField_try = new IvParameterSpec(jdField_do());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public DESKeySet(String paramString, byte[] paramArrayOfByte)
  {
    this.a = paramArrayOfByte;
    if (paramString != null) {
      this.jdField_do = a(paramString);
    } else {
      this.jdField_do = a();
    }
  }
  
  public DESKeySet(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.a = paramArrayOfByte2;
    if (paramArrayOfByte1 != null) {
      this.jdField_do = paramArrayOfByte1;
    } else {
      this.jdField_do = a();
    }
  }
  
  protected static byte[] a(String paramString)
  {
    int i = paramString.length() / 2;
    byte[] arrayOfByte = new byte[i];
    for (int j = 0; j < i; j++) {
      arrayOfByte[j] = ((byte)Integer.parseInt(paramString.substring(j * 2, j * 2 + 2), 16));
    }
    return arrayOfByte;
  }
  
  protected byte[] a()
  {
    SecureRandom localSecureRandom = null;
    Cipher localCipher = null;
    byte[] arrayOfByte = null;
    try
    {
      localCipher = Cipher.getInstance("DESede/CBC/NoPad");
      localSecureRandom = SecureRandom.getInstance("MD5Random");
      localSecureRandom.setSeed(System.currentTimeMillis());
      DESKeySpec localDESKeySpec = new DESKeySpec(this.a);
      SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance("DESede");
      SecretKey localSecretKey = localSecretKeyFactory.generateSecret(localDESKeySpec);
      localCipher.init(2, localSecretKey, localSecureRandom);
      arrayOfByte = localCipher.getIV();
    }
    catch (Exception localException)
    {
      arrayOfByte = a("1234567890123456");
    }
    return arrayOfByte;
  }
  
  public byte[] jdField_do()
  {
    return this.jdField_do;
  }
  
  public byte[] jdField_int()
  {
    return this.a;
  }
  
  public SecretKey jdField_for()
  {
    return this.jdField_if;
  }
  
  public IvParameterSpec jdField_if()
  {
    return this.jdField_try;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.encryption.des.DESKeySet
 * JD-Core Version:    0.7.0.1
 */