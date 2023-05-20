package com.ffusion.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Codecs
{
  private static final int jdField_for = 255;
  private static final int a = 63;
  private static final int jdField_do = 15;
  private static final int jdField_if = 3;
  
  public static final String base64Encode(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return new String(base64Encode(paramString.getBytes()));
  }
  
  public static final byte[] base64Encode(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    byte[] arrayOfByte = new byte[(paramArrayOfByte.length + 2) / 3 * 4];
    int i = 0;
    int j = 0;
    while (i < paramArrayOfByte.length - 2)
    {
      arrayOfByte[(j++)] = ((byte)(paramArrayOfByte[i] >> 2 & 0x3F));
      arrayOfByte[(j++)] = ((byte)(paramArrayOfByte[(i + 1)] >> 4 & 0xF | paramArrayOfByte[i] << 4 & 0x3F));
      arrayOfByte[(j++)] = ((byte)(paramArrayOfByte[(i + 2)] >> 6 & 0x3 | paramArrayOfByte[(i + 1)] << 2 & 0x3F));
      arrayOfByte[(j++)] = ((byte)(paramArrayOfByte[(i + 2)] & 0x3F));
      i += 3;
    }
    if (i < paramArrayOfByte.length)
    {
      arrayOfByte[(j++)] = ((byte)(paramArrayOfByte[i] >> 2 & 0x3F));
      if (i < paramArrayOfByte.length - 1)
      {
        arrayOfByte[(j++)] = ((byte)(paramArrayOfByte[(i + 1)] >> 4 & 0xF | paramArrayOfByte[i] << 4 & 0x3F));
        arrayOfByte[(j++)] = ((byte)(paramArrayOfByte[(i + 1)] << 2 & 0x3F));
      }
      else
      {
        arrayOfByte[(j++)] = ((byte)(paramArrayOfByte[i] << 4 & 0x3F));
      }
    }
    for (i = 0; i < j; i++) {
      if (arrayOfByte[i] < 26) {
        arrayOfByte[i] = ((byte)(arrayOfByte[i] + 65));
      } else if (arrayOfByte[i] < 52) {
        arrayOfByte[i] = ((byte)(arrayOfByte[i] + 97 - 26));
      } else if (arrayOfByte[i] < 62) {
        arrayOfByte[i] = ((byte)(arrayOfByte[i] + 48 - 52));
      } else if (arrayOfByte[i] < 63) {
        arrayOfByte[i] = 43;
      } else {
        arrayOfByte[i] = 47;
      }
    }
    while (i < arrayOfByte.length)
    {
      arrayOfByte[i] = 61;
      i++;
    }
    return arrayOfByte;
  }
  
  public static final String base64Decode(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return new String(base64Decode(paramString.getBytes()));
  }
  
  public static final byte[] base64Decode(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(paramArrayOfByte.length);
    for (int i = 0; (i < paramArrayOfByte.length) && (paramArrayOfByte[i] != 61); i++) {
      if (paramArrayOfByte[i] == 47) {
        localByteArrayOutputStream.write(63);
      } else if (paramArrayOfByte[i] == 43) {
        localByteArrayOutputStream.write(62);
      } else if (paramArrayOfByte[i] < 58) {
        localByteArrayOutputStream.write(paramArrayOfByte[i] - 48 + 52);
      } else if (paramArrayOfByte[i] < 91) {
        localByteArrayOutputStream.write(paramArrayOfByte[i] - 65);
      } else {
        localByteArrayOutputStream.write(paramArrayOfByte[i] - 97 + 26);
      }
    }
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    localByteArrayOutputStream = new ByteArrayOutputStream((arrayOfByte.length + 3) / 4 * 3);
    for (i = 0; i < arrayOfByte.length - 3; i += 4)
    {
      localByteArrayOutputStream.write(arrayOfByte[i] << 2 & 0xFF | arrayOfByte[(i + 1)] >> 4 & 0xF);
      localByteArrayOutputStream.write(arrayOfByte[(i + 1)] << 4 & 0xFF | arrayOfByte[(i + 2)] >> 2 & 0x3F);
      localByteArrayOutputStream.write(arrayOfByte[(i + 2)] << 6 & 0xFF | arrayOfByte[(i + 3)] & 0x3F);
    }
    if (i < arrayOfByte.length)
    {
      localByteArrayOutputStream.write(arrayOfByte[i] << 2 & 0xFF | arrayOfByte[(i + 1)] >> 4 & 0xF);
      if (i < arrayOfByte.length - 2) {
        localByteArrayOutputStream.write(arrayOfByte[(i + 1)] << 4 & 0xFF | arrayOfByte[(i + 2)] >> 2 & 0x3F);
      }
    }
    return localByteArrayOutputStream.toByteArray();
  }
  
  public static void main(String[] paramArrayOfString)
  {
    if (paramArrayOfString.length != 2)
    {
      a();
      System.exit(-1);
    }
    String str = null;
    if (paramArrayOfString[0].equalsIgnoreCase("-64encode")) {
      str = base64Encode(paramArrayOfString[1]);
    } else if (paramArrayOfString[0].equalsIgnoreCase("-64decode")) {
      str = base64Decode(paramArrayOfString[1]);
    }
    System.out.println("Results:");
    System.out.println(paramArrayOfString[1] + " --> " + str);
    System.out.println();
  }
  
  private static void a()
  {
    System.out.println("Usage: java hfn.util.Codecs <action> <string value>");
    System.out.println();
    System.out.println("Available Actions:");
    System.out.println("    -64encode       Base64 encode the string value");
    System.out.println("    -64decode       Base64 decode the string value");
    System.out.println();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.Codecs
 * JD-Core Version:    0.7.0.1
 */