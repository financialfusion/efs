package com.ffusion.alert.shared;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AEUtils
{
  public static final long jdField_do = 1000L;
  public static final long jdField_byte = 60000L;
  public static final long jdField_new = 3600000L;
  public static final long jdField_for = 86400000L;
  public static final long a = 604800000L;
  public static final String jdField_case = "UTF-8";
  public static final String jdField_if = "com.ibm.websphere.naming.WsnInitialContextFactory";
  private static final String jdField_int = "com.ibm.websphere.naming.jndicache.cacheobject";
  private static final String jdField_try = "cleared";
  
  public static byte[] a(String paramString)
  {
    return a(paramString, "UTF-8");
  }
  
  public static byte[] a(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      return null;
    }
    byte[] arrayOfByte = null;
    try
    {
      arrayOfByte = paramString1.getBytes(paramString2);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      arrayOfByte = paramString1.getBytes();
    }
    return arrayOfByte;
  }
  
  public static String a(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, "UTF-8");
  }
  
  public static String a(byte[] paramArrayOfByte, String paramString)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    String str = null;
    try
    {
      str = new String(paramArrayOfByte, paramString);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      str = new String(paramArrayOfByte);
    }
    return str;
  }
  
  public static String a(Properties paramProperties)
  {
    if (paramProperties == null) {
      return null;
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      paramProperties.store(localByteArrayOutputStream, null);
    }
    catch (Exception localException) {}
    return localByteArrayOutputStream.toString();
  }
  
  public static Properties jdField_if(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    Properties localProperties = new Properties();
    try
    {
      localProperties.load(new ByteArrayInputStream(paramString.getBytes()));
    }
    catch (Exception localException) {}
    return localProperties;
  }
  
  public static Object jdField_if(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    Object localObject = null;
    try
    {
      localObject = new ObjectInputStream(new ByteArrayInputStream(paramArrayOfByte)).readObject();
    }
    catch (OptionalDataException localOptionalDataException) {}catch (ClassNotFoundException localClassNotFoundException) {}catch (IOException localIOException) {}
    return localObject;
  }
  
  public static byte[] a(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    byte[] arrayOfByte = null;
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      new ObjectOutputStream(localByteArrayOutputStream).writeObject(paramObject);
      arrayOfByte = localByteArrayOutputStream.toByteArray();
    }
    catch (IOException localIOException) {}
    return arrayOfByte;
  }
  
  public static boolean jdField_if(long paramLong1, long paramLong2)
  {
    return paramLong1 >= paramLong2;
  }
  
  public static boolean a(long paramLong1, long paramLong2)
  {
    return paramLong1 < paramLong2;
  }
  
  public static boolean a(long paramLong1, long paramLong2, long paramLong3)
  {
    return (jdField_if(paramLong1, paramLong2)) && (a(paramLong1, paramLong3));
  }
  
  public static boolean jdField_if(long paramLong1, long paramLong2, long paramLong3)
  {
    return a(paramLong1, paramLong2 - paramLong3, paramLong2 + paramLong3 + 1L);
  }
  
  public static Context a(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws NamingException
  {
    Properties localProperties = new Properties();
    localProperties.put("java.naming.factory.initial", paramString1);
    localProperties.put("java.naming.provider.url", paramString2);
    localProperties.put("java.naming.security.principal", paramString3);
    localProperties.put("java.naming.security.credentials", paramString4);
    if ((paramBoolean) && (paramString1.equals("com.ibm.websphere.naming.WsnInitialContextFactory"))) {
      localProperties.put("com.ibm.websphere.naming.jndicache.cacheobject", "cleared");
    }
    InitialContext localInitialContext = new InitialContext(localProperties);
    return localInitialContext;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.shared.AEUtils
 * JD-Core Version:    0.7.0.1
 */