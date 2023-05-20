package com.ffusion.ffs.util;

import com.ffusion.ffs.bpw.enums.FrequencyType;
import com.ffusion.ffs.interfaces.FFSException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;

public class FFSUtil
  implements FFSConst
{
  public static double solarDay = 86400.0D;
  public static double epsilonDouble = 2.220446049250313E-016D;
  private static final String a = "yyyy/MM/dd HH:mm:ss";
  public static final BigDecimal BD_ZERO = new BigDecimal(BigInteger.ZERO);
  
  public static byte[] objectToBytes(Object paramObject)
    throws FFSException
  {
    ByteArrayOutputStream localByteArrayOutputStream = null;
    try
    {
      localByteArrayOutputStream = new ByteArrayOutputStream();
      ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localByteArrayOutputStream);
      localObjectOutputStream.writeObject(paramObject);
      localObjectOutputStream.flush();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      FFSDebug.log("Failed to convert object to bytes", 0);
      throw new FFSException("Failed to convert object to bytes");
    }
    if (localByteArrayOutputStream != null) {
      return localByteArrayOutputStream.toByteArray();
    }
    FFSDebug.log("objectToBytes: byte[] is null!!!!!...", 0);
    return null;
  }
  
  public static Object bytesToObject(byte[] paramArrayOfByte)
    throws FFSException
  {
    Object localObject = null;
    try
    {
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
      ObjectInputStream localObjectInputStream = new ObjectInputStream(localByteArrayInputStream);
      localObject = localObjectInputStream.readObject();
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to convert bytes to object", 0);
      localException.printStackTrace();
      throw new FFSException("Failed to convert bytes to object");
    }
    return localObject;
  }
  
  public static boolean booleanValue(String paramString)
  {
    try
    {
      return Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException)
    {
      FFSDebug.log("Exception in  parsing: ", paramString, " to boolean", 0);
      localException.printStackTrace();
    }
    return false;
  }
  
  public static int intValue(String paramString)
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Exception in  parsing: ", paramString, " to integer", 0);
      localException.printStackTrace();
    }
    return i;
  }
  
  public static long longValue(String paramString)
  {
    long l = -1L;
    try
    {
      l = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Exception in  parsing: ", paramString, " to long", 0);
      localException.printStackTrace();
    }
    return l;
  }
  
  public static float floatValue(String paramString)
  {
    float f = -1.0F;
    try
    {
      f = Float.parseFloat(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Exception in  parsing: ", paramString, " to float", 0);
      localException.printStackTrace();
    }
    return f;
  }
  
  public static String readStringFromFile(String paramString)
  {
    String str = null;
    if (paramString == null)
    {
      FFSDebug.log("File name isnull ", 0);
      return str;
    }
    int i = (int)new File(paramString).length();
    if (i <= 0)
    {
      FFSDebug.log("File: ", paramString, ", is empty, or does not exist.", 0);
      return str;
    }
    byte[] arrayOfByte = new byte[i];
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(paramString);
      if (localFileInputStream == null)
      {
        FFSDebug.log("Failed to open file: ", paramString, 6);
        return str;
      }
      int j = 0;
      int k = 0;
      while (i > 0)
      {
        k = localFileInputStream.read(arrayOfByte, j, i);
        if (k == -1)
        {
          FFSDebug.log("Unable to read file: ", paramString, ".", 0);
          return str;
        }
        i -= k;
        j += k;
      }
      localFileInputStream.close();
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      FFSDebug.log(localFileNotFoundException.getMessage(), 0);
      return str;
    }
    catch (IOException localIOException)
    {
      FFSDebug.log(localIOException.getMessage(), 0);
      return str;
    }
    return new String(arrayOfByte);
  }
  
  public static void doSystemCommand(String paramString)
    throws Exception
  {
    Process localProcess = null;
    try
    {
      System.out.println("System command: >" + paramString + "<");
      localProcess = Runtime.getRuntime().exec(paramString);
      InputStream localInputStream1 = localProcess.getErrorStream();
      System.out.println("stderr : " + streamToString(localInputStream1, -1));
      InputStream localInputStream2 = localProcess.getInputStream();
      System.out.println("stdout : " + streamToString(localInputStream2, -1));
      localProcess.waitFor();
      localProcess.destroy();
      localProcess = null;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      throw new Exception("Execution error: " + localException1.getMessage());
    }
    finally
    {
      try
      {
        if (localProcess != null)
        {
          localProcess.destroy();
          localProcess = null;
        }
      }
      catch (Exception localException2)
      {
        System.out.println(localException2.toString());
        localException2.printStackTrace();
      }
    }
  }
  
  public static String streamToString(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    InputStreamReader localInputStreamReader = new InputStreamReader(paramInputStream);
    int i = 1024;
    if ((paramInt != -1) && (paramInt <= 1024)) {
      i = paramInt;
    }
    char[] arrayOfChar = new char[i];
    StringBuffer localStringBuffer = new StringBuffer();
    for (;;)
    {
      int j = localInputStreamReader.read(arrayOfChar);
      if (j != -1)
      {
        localStringBuffer.append(arrayOfChar, 0, j);
        if (paramInt != -1) {
          break;
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  public static Properties getProperties(String paramString)
    throws FFSException
  {
    Properties localProperties = new Properties();
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(paramString);
      localProperties.load(localFileInputStream);
      localFileInputStream.close();
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      FFSDebug.log(localFileNotFoundException.getMessage(), 0);
      return localProperties;
    }
    catch (IOException localIOException)
    {
      FFSDebug.log(localIOException.getMessage(), 0);
      throw new FFSException(localIOException.getMessage());
    }
    return localProperties;
  }
  
  public static void saveProperties(Properties paramProperties, String paramString1, String paramString2)
    throws FFSException
  {
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString1);
      paramProperties.store(localFileOutputStream, paramString2);
      localFileOutputStream.close();
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      FFSDebug.log(localFileNotFoundException.getMessage(), 0);
      return;
    }
    catch (IOException localIOException)
    {
      FFSDebug.log(localIOException.getMessage(), 0);
      throw new FFSException(localIOException.getMessage());
    }
  }
  
  public static final String getDateString()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    return localSimpleDateFormat.format(new Date());
  }
  
  public static final String getDateString(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
    return localSimpleDateFormat.format(new Date());
  }
  
  public static final String getDateString(DateFormat paramDateFormat)
  {
    return paramDateFormat.format(new Date());
  }
  
  public static final String getDateString(DateFormat paramDateFormat, Date paramDate)
  {
    return paramDateFormat.format(paramDate);
  }
  
  public static final Properties loadPropsFromClasspath(String paramString)
  {
    Properties localProperties = new Properties();
    try
    {
      ClassLoader localClassLoader = FFSUtil.class.getClassLoader();
      if (localClassLoader == null) {
        localClassLoader = ClassLoader.getSystemClassLoader();
      }
      InputStream localInputStream = localClassLoader.getResourceAsStream(paramString);
      if (localInputStream == null)
      {
        System.out.println(paramString + " file not found");
        return localProperties;
      }
      localProperties.load(localInputStream);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localProperties;
  }
  
  public static String getNow(String paramString)
    throws Exception
  {
    SimpleDateFormat localSimpleDateFormat = null;
    try
    {
      if ((paramString == null) || (paramString.length() == 0)) {
        paramString = "yyyy/MM/dd HH:mm:ss";
      }
      localSimpleDateFormat = new SimpleDateFormat(paramString);
      return localSimpleDateFormat.format(new Date());
    }
    catch (Exception localException)
    {
      throw localException;
    }
  }
  
  public static final String uniqueID()
  {
    return UniqueID.getID();
  }
  
  public static String getCurrentYear()
  {
    Calendar localCalendar = Calendar.getInstance();
    return "" + localCalendar.get(1);
  }
  
  public static int getInstanceDate(int paramInt)
  {
    int i = 0;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(6, 1 * paramInt);
    i = localCalendar.get(1) * 1000000 + (localCalendar.get(2) + 1) * 10000 + localCalendar.get(5) * 100;
    return i;
  }
  
  public static String getBPWCreateDate(String paramString1, String paramString2)
  {
    String str1 = "";
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyMMddHHmm");
    Date localDate1 = localSimpleDateFormat.parse(paramString1 + paramString2, new ParsePosition(0));
    String str2 = localSimpleDateFormat.format(new Date());
    Date localDate2 = localSimpleDateFormat.parse(str2, new ParsePosition(0));
    if (localDate2.compareTo(localDate1) > 0) {
      str1 = localSimpleDateFormat.format(localDate2);
    } else {
      str1 = paramString1 + paramString2;
    }
    FFSDebug.log("FFSUtil.getBPWCreateDate: OrgCreateDate = " + paramString1 + " and BPWCreateDate = " + str1, 6);
    return str1;
  }
  
  public static boolean isOlderThanToday(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    Date localDate1 = localSimpleDateFormat.parse(paramString.substring(0, 8) + "000000", new ParsePosition(0));
    String str = localSimpleDateFormat.format(new Date());
    Date localDate2 = localSimpleDateFormat.parse(str.substring(0, 8) + "000000", new ParsePosition(0));
    return localDate2.compareTo(localDate1) > 0;
  }
  
  public static String padWithChar(String paramString, int paramInt, boolean paramBoolean, char paramChar)
    throws Exception
  {
    if (paramString == null) {
      paramString = "";
    }
    if (paramString.length() >= paramInt) {
      return paramString;
    }
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    int i = paramInt - paramString.length();
    int j;
    if (paramBoolean)
    {
      for (j = 0; j < i; j++) {
        localStringBuffer.append(paramChar);
      }
      localStringBuffer.append(paramString);
    }
    else
    {
      localStringBuffer.append(paramString);
      for (j = 0; j < i; j++) {
        localStringBuffer.append(paramChar);
      }
    }
    return localStringBuffer.toString();
  }
  
  public static short getACHMBClassFieldValueShort(Object paramObject, String paramString)
  {
    Object localObject = getACHMBClassFieldValueObject(paramObject, paramString);
    if (localObject == null) {
      return 0;
    }
    try
    {
      if ((localObject instanceof String)) {
        return Short.parseShort((String)localObject);
      }
      if ((localObject instanceof Integer)) {
        return ((Integer)localObject).shortValue();
      }
      if ((localObject instanceof Long)) {
        return ((Long)localObject).shortValue();
      }
      if ((localObject instanceof Short)) {
        return ((Short)localObject).shortValue();
      }
      return 0;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return 0;
  }
  
  public static int getACHMBClassFieldValueInt(Object paramObject, String paramString)
  {
    Object localObject = getACHMBClassFieldValueObject(paramObject, paramString);
    if (localObject == null) {
      return 0;
    }
    try
    {
      if ((localObject instanceof String)) {
        return Integer.parseInt((String)localObject);
      }
      if ((localObject instanceof Integer)) {
        return ((Integer)localObject).intValue();
      }
      if ((localObject instanceof Long)) {
        return ((Long)localObject).intValue();
      }
      if ((localObject instanceof Short)) {
        return ((Short)localObject).intValue();
      }
      return 0;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return 0;
  }
  
  public static long getACHMBClassFieldValueLong(Object paramObject, String paramString)
  {
    Object localObject = getACHMBClassFieldValueObject(paramObject, paramString);
    if (localObject == null) {
      return 0L;
    }
    try
    {
      if ((localObject instanceof String)) {
        return Long.parseLong((String)localObject);
      }
      if ((localObject instanceof Integer)) {
        return ((Integer)localObject).longValue();
      }
      if ((localObject instanceof Long)) {
        return ((Long)localObject).longValue();
      }
      if ((localObject instanceof Short)) {
        return ((Short)localObject).longValue();
      }
      return 0L;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return 0L;
  }
  
  public static double getACHMBClassFieldValueDouble(Object paramObject, String paramString)
  {
    Object localObject = getACHMBClassFieldValueObject(paramObject, paramString);
    if (localObject == null) {
      return 0.0D;
    }
    try
    {
      if ((localObject instanceof String)) {
        return Double.parseDouble((String)localObject);
      }
      if ((localObject instanceof Integer)) {
        return ((Integer)localObject).doubleValue();
      }
      if ((localObject instanceof Long)) {
        return ((Long)localObject).doubleValue();
      }
      if ((localObject instanceof Short)) {
        return ((Short)localObject).doubleValue();
      }
      return 0.0D;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return 0.0D;
  }
  
  public static String getACHMBClassFieldValueString(Object paramObject, String paramString)
  {
    Object localObject = getACHMBClassFieldValueObject(paramObject, paramString);
    if (localObject == null) {
      return null;
    }
    if ((localObject instanceof String)) {
      return (String)localObject;
    }
    if ((localObject instanceof Integer)) {
      return ((Integer)localObject).toString();
    }
    if ((localObject instanceof Long)) {
      return ((Long)localObject).toString();
    }
    if ((localObject instanceof Short))
    {
      FFSDebug.log("Short to String", 6);
      return ((Short)localObject).toString();
    }
    return null;
  }
  
  public static Object getACHMBClassFieldValueObject(Object paramObject, String paramString)
  {
    try
    {
      Field localField = paramObject.getClass().getDeclaredField(paramString);
      return localField.get(paramObject);
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      if (paramString.compareTo("Check_Digit") == 0) {
        return getACHMBClassFieldValueObject(paramObject, "CheckDigit");
      }
      return null;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static void setACHMBClassFieldValueObject(Object paramObject1, String paramString, Object paramObject2)
    throws FFSException
  {
    try
    {
      Field localField = paramObject1.getClass().getDeclaredField(paramString);
      localField.set(paramObject1, paramObject2);
    }
    catch (NoSuchFieldException localNoSuchFieldException) {}catch (Exception localException)
    {
      String str = "*** FFSUtil: setMBClassFieldValueObject: ";
      throw new FFSException(localException, str);
    }
  }
  
  public static String getFreqString(int paramInt)
  {
    FFSDebug.log("ACHBatch.getFreqString : Frequency (int) = " + paramInt, 6);
    return FrequencyType.getEnum(paramInt) != null ? FrequencyType.getEnum(paramInt).getName() : "";
  }
  
  public static int getFreqInt(String paramString)
  {
    FFSDebug.log("ACHBatch.getFreqInt : Frequency (String) = " + paramString, 6);
    return FrequencyType.getEnum(paramString) != null ? FrequencyType.getEnum(paramString).getValue() : -1;
  }
  
  public static Hashtable stringToHashtable(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    Hashtable localHashtable = new Hashtable();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    while (localStringTokenizer.hasMoreTokens())
    {
      String str1 = localStringTokenizer.nextToken().trim();
      int i = str1.indexOf("=");
      if ((str1 != null) && (str1.length() >= 2) && (i != -1))
      {
        String str2 = str1.substring(0, i);
        String str3 = null;
        if (i == str1.length()) {
          str3 = "";
        } else {
          str3 = str1.substring(i + 1);
        }
        localHashtable.put(str2, str3);
      }
    }
    return localHashtable;
  }
  
  public static String hashtableToString(Hashtable paramHashtable)
  {
    if (paramHashtable == null) {
      return null;
    }
    String str = paramHashtable.toString();
    return str.substring(1, str.length() - 1);
  }
  
  public static String getCutOffTime(int paramInt)
    throws Exception
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date localDate = null;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(12, -1 * paramInt);
    localDate = localCalendar.getTime();
    return localSimpleDateFormat.format(localDate);
  }
  
  public static String convertDateFormat(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat(paramString1);
    Date localDate = localSimpleDateFormat1.parse(paramString3, new ParsePosition(0));
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat(paramString2);
    return localSimpleDateFormat2.format(localDate);
  }
  
  public static String fileToStringFromClasspath(String paramString)
  {
    String str1 = null;
    InputStream localInputStream = null;
    if (paramString == null)
    {
      FFSDebug.log("File: ", paramString, ", is empty, or does not exist.", 0);
      return str1;
    }
    byte[] arrayOfByte = null;
    try
    {
      localInputStream = getResourceAsStream(new FFSUtil(), paramString);
      if (localInputStream == null)
      {
        FFSDebug.log("Failed to open File: ", paramString, 0);
        String str2 = str1;
        return str2;
      }
      int i = localInputStream.available();
      arrayOfByte = new byte[i];
      localInputStream.read(arrayOfByte, 0, i);
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      FFSDebug.log("Failed. Error: ", FFSDebug.stackTrace(localFileNotFoundException), 0);
      str3 = str1;
      return str3;
    }
    catch (IOException localIOException)
    {
      FFSDebug.log("Failed. Error: ", FFSDebug.stackTrace(localIOException), 0);
      str3 = str1;
      return str3;
    }
    catch (Exception localException1)
    {
      FFSDebug.log("Failed. Error: ", FFSDebug.stackTrace(localException1), 0);
      String str3 = str1;
      return str3;
    }
    finally
    {
      try
      {
        if (localInputStream != null) {
          localInputStream.close();
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("Failed to close file. Error: ", FFSDebug.stackTrace(localException2), 0);
      }
    }
    return new String(arrayOfByte);
  }
  
  public static URL findResourceURL(Object paramObject, String paramString)
  {
    URL localURL = null;
    try
    {
      localURL = paramObject.getClass().getClassLoader().getResource(paramString);
    }
    catch (Exception localException) {}
    if (localURL == null) {
      localURL = ClassLoader.getSystemResource(paramString);
    }
    if (localURL == null)
    {
      ClassLoader.getSystemClassLoader();
      localURL = ClassLoader.getSystemResource(paramString);
    }
    if (localURL == null) {
      localURL = Thread.currentThread().getContextClassLoader().getResource(paramString);
    }
    if (localURL == null)
    {
      FFSUtil localFFSUtil = new FFSUtil();
      localURL = localFFSUtil.getClass().getClassLoader().getResource(paramString);
    }
    return localURL;
  }
  
  public static InputStream getResourceAsStream(Object paramObject, String paramString)
  {
    InputStream localInputStream = null;
    try
    {
      URL localURL = findResourceURL(paramObject, paramString);
      if (localURL != null) {
        localInputStream = localURL.openStream();
      }
    }
    catch (Exception localException) {}
    return localInputStream;
  }
  
  public static boolean checkTimeBeanFormat(String paramString1, String paramString2)
  {
    SimpleDateFormat localSimpleDateFormat = null;
    if (paramString1 == null) {
      return false;
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0)) {
      return false;
    }
    if (paramString1.length() != paramString2.length()) {
      return false;
    }
    try
    {
      localSimpleDateFormat = new SimpleDateFormat(paramString2);
      ParsePosition localParsePosition = new ParsePosition(0);
      Date localDate = localSimpleDateFormat.parse(paramString1, localParsePosition);
      if (localDate == null)
      {
        FFSDebug.log("FFSUtil.checkTimeBeanFormat: Invalid time. Time: " + paramString1, 0);
        return false;
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "FFSUtil.checkTimeBeanFormat: Invalid time. Time: " + paramString1, 0);
      return false;
    }
    return true;
  }
  
  public static BigDecimal getBigDecimal(String paramString)
  {
    return getBigDecimal(paramString, 4);
  }
  
  public static BigDecimal getBigDecimal(String paramString, int paramInt)
  {
    return new BigDecimal(paramString).setScale(paramInt, 5);
  }
  
  public static BigDecimal setDefaultScale(BigDecimal paramBigDecimal)
  {
    return setScale(paramBigDecimal, 4);
  }
  
  public static BigDecimal setScale(BigDecimal paramBigDecimal, int paramInt)
  {
    return paramBigDecimal.setScale(paramInt, 5);
  }
  
  public static String formatToScale(String paramString)
  {
    return formatToScale(paramString, 4);
  }
  
  public static String formatToScale(String paramString, int paramInt)
  {
    return getBigDecimal(paramString, paramInt).toString();
  }
  
  public static int compare(String paramString1, String paramString2)
  {
    return getBigDecimal(paramString1).compareTo(getBigDecimal(paramString2));
  }
  
  public static boolean isPositive(String paramString)
  {
    return compare(paramString, "0.0") > 0;
  }
  
  public static boolean isPositive(BigDecimal paramBigDecimal)
  {
    return paramBigDecimal.compareTo(BD_ZERO) > 0;
  }
  
  public static boolean isNegative(BigDecimal paramBigDecimal)
  {
    return paramBigDecimal.compareTo(BD_ZERO) < 0;
  }
  
  public static boolean isNegative(String paramString)
  {
    return compare(paramString, "0.0") < 0;
  }
  
  public static boolean isZero(String paramString)
  {
    return isZero(getBigDecimal(paramString));
  }
  
  public static boolean isZero(BigDecimal paramBigDecimal)
  {
    return paramBigDecimal.compareTo(BD_ZERO) == 0;
  }
  
  public static boolean getBooleanFromYN(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    return paramString.equalsIgnoreCase("Y");
  }
  
  public static String getYNFromBoolean(boolean paramBoolean)
  {
    if (paramBoolean) {
      return "Y";
    }
    return "N";
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSUtil
 * JD-Core Version:    0.7.0.1
 */