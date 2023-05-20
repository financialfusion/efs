package com.ffusion.util;

public class HfnEncrypt
{
  private static final int[] a = { 141, 77, 160, 232, 207, 64, 247, 255, 195 };
  private static int jdField_if = 1024;
  
  public static byte[] decrypt(byte[] paramArrayOfByte, int paramInt)
  {
    int i = 0;
    while (i < paramInt)
    {
      int j = 255;
      int k = a.length;
      int m = 0;
      for (int i2 = 0; (i2 < jdField_if) && (i + i2 < paramInt); i2++)
      {
        if (m == k) {
          m = 0;
        }
        int i1 = a(paramArrayOfByte[(i2 + i)], 1, false);
        int n = i1;
        if (m == k - 1) {
          i1 ^= a[0];
        } else {
          i1 ^= a[(m + 1)];
        }
        i1 = a(i1, 3, false);
        if (m == 0) {
          i1 ^= a[(k - 1)];
        } else {
          i1 ^= a[(m - 1)];
        }
        i1 = a(i1, 2, false);
        i1 ^= j;
        if (i + jdField_if < paramInt) {
          i1 ^= i2 * 2 - jdField_if;
        } else {
          i1 ^= i2 * 2 - (paramInt - i);
        }
        j = n;
        paramArrayOfByte[(i2 + i)] = ((byte)i1);
        m++;
      }
      i += jdField_if;
    }
    return paramArrayOfByte;
  }
  
  public static byte[] encrypt(byte[] paramArrayOfByte, int paramInt)
  {
    int i = 0;
    while (i < paramInt)
    {
      int j = 0;
      int k = a.length;
      int m = 255;
      for (int i1 = 0; (i1 < jdField_if) && (i + i1 < paramInt); i1++)
      {
        if (j == k) {
          j = 0;
        }
        int n = paramArrayOfByte[(i + i1)];
        if (i + jdField_if < paramInt) {
          n ^= i1 * 2 - jdField_if;
        } else {
          n ^= i1 * 2 - (paramInt - i);
        }
        n ^= m;
        n = a(n, 2, true);
        if (j == 0) {
          n ^= a[(k - 1)];
        } else {
          n ^= a[(j - 1)];
        }
        n = a(n, 3, true);
        if (j == k - 1) {
          n ^= a[0];
        } else {
          n ^= a[(j + 1)];
        }
        m = n;
        n = a(n, 1, true);
        paramArrayOfByte[(i + i1)] = ((byte)n);
        j++;
      }
      i += jdField_if;
    }
    return paramArrayOfByte;
  }
  
  public static String encryptHexEncode(String paramString)
    throws Exception
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return "";
    }
    byte[] arrayOfByte = paramString.getBytes();
    return a(encrypt(arrayOfByte, arrayOfByte.length));
  }
  
  public static String decryptHexEncode(String paramString)
    throws Exception
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return "";
    }
    byte[] arrayOfByte = a(paramString);
    return new String(decrypt(arrayOfByte, arrayOfByte.length));
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
    catch (Exception localException) {}
    return null;
  }
  
  private static int a(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    do
    {
      int i;
      if (paramBoolean)
      {
        i = paramInt1 & 0x1;
        paramInt1 >>= 1;
        paramInt1 &= 0x7F;
        if (i > 0) {
          paramInt1 |= 0x80;
        }
      }
      else
      {
        i = paramInt1 & 0x80;
        paramInt1 <<= 1;
        if (i > 0) {
          paramInt1 |= 0x1;
        }
      }
      paramInt2--;
    } while (paramInt2 > 0);
    return paramInt1;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.HfnEncrypt
 * JD-Core Version:    0.7.0.1
 */