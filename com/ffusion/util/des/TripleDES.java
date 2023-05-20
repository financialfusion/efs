package com.ffusion.util.des;

import com.ffusion.util.logging.DebugLog;
import java.util.logging.Level;
import javax.crypto.Cipher;

public class TripleDES
{
  public static String encrypt(String paramString, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
  {
    byte[] arrayOfByte = new byte[24];
    for (int i = 0; i < 8; i++)
    {
      arrayOfByte[i] = paramArrayOfByte2[i];
      arrayOfByte[(i + 8)] = paramArrayOfByte3[i];
      arrayOfByte[(i + 16)] = paramArrayOfByte4[i];
    }
    DESKeySet localDESKeySet = new DESKeySet(paramArrayOfByte1, arrayOfByte);
    return encrypt(paramString, localDESKeySet);
  }
  
  public static String encrypt(String paramString, DESKeySet paramDESKeySet)
  {
    byte[] arrayOfByte1 = paramString.getBytes();
    byte[] arrayOfByte2 = null;
    try
    {
      Cipher localCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
      localCipher.init(1, paramDESKeySet.getSecretKey(), paramDESKeySet.getIvParmSpec());
      arrayOfByte2 = localCipher.doFinal(arrayOfByte1);
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.WARNING, "TripleDES.Encrypt Exception: " + localException.toString());
      arrayOfByte2 = null;
    }
    return a(arrayOfByte2);
  }
  
  public static byte[] decrypt(String paramString, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
  {
    byte[] arrayOfByte = new byte[24];
    for (int i = 0; i < 8; i++)
    {
      arrayOfByte[i] = paramArrayOfByte2[i];
      arrayOfByte[(i + 8)] = paramArrayOfByte3[i];
      arrayOfByte[(i + 16)] = paramArrayOfByte4[i];
    }
    DESKeySet localDESKeySet = new DESKeySet(paramArrayOfByte1, arrayOfByte);
    return decrypt(paramString, localDESKeySet);
  }
  
  public static byte[] decrypt(String paramString, DESKeySet paramDESKeySet)
  {
    byte[] arrayOfByte1 = a(paramString);
    byte[] arrayOfByte2 = null;
    try
    {
      Cipher localCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
      localCipher.init(2, paramDESKeySet.getSecretKey(), paramDESKeySet.getIvParmSpec());
      arrayOfByte2 = localCipher.doFinal(arrayOfByte1);
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.WARNING, "TripleDES.Encrypt Exception: " + localException.toString());
      arrayOfByte2 = null;
    }
    return arrayOfByte2;
  }
  
  private static String a(byte[] paramArrayOfByte)
  {
    String str = new String("0123456789abcdef");
    StringBuffer localStringBuffer = new StringBuffer();
    for (int j = 0; j < paramArrayOfByte.length; j++)
    {
      int i = paramArrayOfByte[j];
      for (int k = 1; k >= 0; k--) {
        localStringBuffer.append(String.valueOf(str.charAt(i >> k * 4 & 0xF)));
      }
    }
    return localStringBuffer.toString();
  }
  
  private static byte[] a(String paramString)
  {
    try
    {
      String str = new String("0123456789ABCDEF");
      int i = paramString.length();
      byte[] arrayOfByte = new byte[i / 2];
      int j = 0;
      for (int k = 0; k < i; k += 2)
      {
        int m = str.indexOf(Character.toUpperCase(paramString.charAt(k)));
        int n = str.indexOf(Character.toUpperCase(paramString.charAt(k + 1)));
        arrayOfByte[(j++)] = ((byte)(m * 16 + n));
      }
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.WARNING, "TripleDES.hexDecode Error " + localException.toString());
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.des.TripleDES
 * JD-Core Version:    0.7.0.1
 */