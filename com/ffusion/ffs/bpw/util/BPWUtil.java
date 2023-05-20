package com.ffusion.ffs.bpw.util;

import com.ffusion.ffs.bpw.BPWServer;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfoList;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.ScheduleHist;
import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
import com.ffusion.ffs.bpw.master.BPWEngine;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.config.ConnPoolInfo;
import com.ffusion.ffs.config.DBConnInfo;
import com.ffusion.ffs.db.FFSDBProperties;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.Scheduler;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.HfnEncrypt;
import com.ffusion.util.settings.AccountSettings;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.CollationKey;
import java.text.Collator;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

public final class BPWUtil
{
  public static int INST_FIELD_NUMDER = 17;
  public static int CUTOFF_FIELD_NUMBER = 10;
  public static final DateFormat OFX_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
  public static final DateFormat OFX_DATETIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
  public static final int START_DAY_OF_WEEK = 1;
  public static final int END_DAY_OF_WEEK = 7;
  public static final int START_DAY_OF_MONTH = 1;
  public static final int END_DAY_OF_MONTH = 31;
  public static final int START_DAY_OF_YEAR = 1;
  public static final int END_DAY_OF_YEAR = 365;
  
  public static boolean setFIIDList(ArrayList paramArrayList, Object paramObject)
  {
    try
    {
      Class localClass = paramObject.getClass();
      Method[] arrayOfMethod = localClass.getMethods();
      for (int i = 0; i < arrayOfMethod.length; i++) {
        if ((arrayOfMethod[i] != null) && (arrayOfMethod[i].getName().equals("setScheduleFIIDList")))
        {
          Object[] arrayOfObject = { paramArrayList };
          arrayOfMethod[i].invoke(paramObject, arrayOfObject);
          return true;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log(localThrowable, "FFSUtil.setFIIDList: Failed", 0);
    }
    return false;
  }
  
  public static FFSDBProperties getBPWServerDBProperties()
    throws FFSException
  {
    return getBPWServerDBProperties(getPropertyConfig(), BPWRegistryUtil.getFFSProperties());
  }
  
  public static FFSDBProperties getBPWServerDBProperties(PropertyConfig paramPropertyConfig, FFSProperties paramFFSProperties)
  {
    FFSDBProperties localFFSDBProperties = new FFSDBProperties();
    ConnPoolInfo localConnPoolInfo = new ConnPoolInfo();
    DBConnInfo localDBConnInfo = a(paramPropertyConfig);
    localConnPoolInfo._dbConnInfo = localDBConnInfo;
    String str = paramFFSProperties.getProperty("BPW.DB.HAFLAG");
    if ((str != null) && (str.compareTo("1") == 0)) {
      localDBConnInfo._haFlag = true;
    }
    localDBConnInfo._url = paramFFSProperties.getProperty("BPW.DB.URL");
    localFFSDBProperties._connInfo = localConnPoolInfo;
    localFFSDBProperties._connProps = paramFFSProperties;
    localFFSDBProperties._pureProps = new FFSProperties();
    return localFFSDBProperties;
  }
  
  private static final DBConnInfo a(PropertyConfig paramPropertyConfig)
  {
    DBConnInfo localDBConnInfo = new DBConnInfo();
    localDBConnInfo._databaseName = paramPropertyConfig.BPWServ_dbName;
    localDBConnInfo._password = paramPropertyConfig.BPWServ_dbPassword;
    localDBConnInfo._dbType = paramPropertyConfig.BPWServ_dbType;
    localDBConnInfo._user = paramPropertyConfig.BPWServ_dbUser;
    localDBConnInfo._host = paramPropertyConfig.BPWServ_dbHost;
    localDBConnInfo._port = Integer.parseInt(paramPropertyConfig.BPWServ_dbPort);
    return localDBConnInfo;
  }
  
  public static PropertyConfig getPropertyConfig()
    throws FFSException
  {
    PropertyConfig localPropertyConfig = null;
    try
    {
      localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
    return localPropertyConfig;
  }
  
  public static void printPropertyConfig(PropertyConfig paramPropertyConfig)
  {
    paramPropertyConfig.print();
    printProperties(paramPropertyConfig.otherProperties);
  }
  
  public static void printPropertyConfig(PropertyConfig paramPropertyConfig, PrintWriter paramPrintWriter)
  {
    paramPropertyConfig.print(paramPrintWriter);
    printProperties(paramPropertyConfig.otherProperties, paramPrintWriter);
  }
  
  public static void printProperties(Properties paramProperties, PrintWriter paramPrintWriter)
  {
    try
    {
      if (paramProperties == null)
      {
        paramPrintWriter.println(" null property ");
        return;
      }
      Enumeration localEnumeration = paramProperties.propertyNames();
      Vector localVector = new Vector();
      while (localEnumeration.hasMoreElements()) {
        localVector.add((String)localEnumeration.nextElement());
      }
      sortStrings(localVector);
      Iterator localIterator = localVector.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        paramPrintWriter.println(str + "=" + paramProperties.getProperty(str));
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      paramPrintWriter.close();
    }
  }
  
  public static void printProperties(Properties paramProperties)
  {
    PrintStream localPrintStream = System.out;
    if (paramProperties == null)
    {
      localPrintStream.println(" # null property ");
      return;
    }
    Enumeration localEnumeration = paramProperties.propertyNames();
    Vector localVector = new Vector();
    while (localEnumeration.hasMoreElements()) {
      localVector.add((String)localEnumeration.nextElement());
    }
    sortStrings(localVector);
    Iterator localIterator = localVector.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localPrintStream.println(str + "=" + paramProperties.getProperty(str));
    }
  }
  
  private static Vector a(Vector paramVector)
  {
    int j = 0;
    for (int i = 1; i < paramVector.size(); i++)
    {
      CollationKey localCollationKey = (CollationKey)paramVector.elementAt(i);
      for (j = i - 1; j >= 0; j--) {
        if (j == 0)
        {
          if (localCollationKey.compareTo((CollationKey)paramVector.elementAt(j)) <= 0)
          {
            paramVector.removeElementAt(i);
            paramVector.insertElementAt(localCollationKey, j);
          }
          else
          {
            paramVector.removeElementAt(i);
            paramVector.insertElementAt(localCollationKey, j + 1);
          }
        }
        else if (localCollationKey.compareTo((CollationKey)paramVector.elementAt(j)) >= 0)
        {
          if (i == j + 1) {
            break;
          }
          paramVector.removeElementAt(i);
          paramVector.insertElementAt(localCollationKey, j + 1);
          break;
        }
      }
    }
    return paramVector;
  }
  
  public static Vector sortStrings(Vector paramVector)
  {
    Vector localVector = new Vector();
    Collator localCollator = Collator.getInstance();
    for (int i = 0; i < paramVector.size(); i++) {
      localVector.addElement(localCollator.getCollationKey((String)paramVector.elementAt(i)));
    }
    a(localVector);
    paramVector.removeAllElements();
    for (i = 0; i < localVector.size(); i++) {
      paramVector.addElement(((CollationKey)localVector.elementAt(i)).getSourceString());
    }
    return paramVector;
  }
  
  public static InstructionType readInstructionType(String paramString)
    throws FFSException
  {
    try
    {
      Properties localProperties = new Properties();
      localProperties.load(new FileInputStream(paramString));
      return convertPropertyToInstructionType(localProperties);
    }
    catch (Exception localException)
    {
      throw new FFSException("Failed to read instruction type :" + localException.getMessage());
    }
  }
  
  public static InstructionType convertPropertyToInstructionType(Properties paramProperties)
  {
    InstructionType localInstructionType = new InstructionType();
    localInstructionType.FIId = paramProperties.getProperty("FIID");
    localInstructionType.InstructionType = paramProperties.getProperty("InstructionType");
    localInstructionType.HandlerClassName = paramProperties.getProperty("HandlerClassName");
    localInstructionType.SchedulerStartTime = paramProperties.getProperty("SchedulerStartTime");
    localInstructionType.SchedulerInterval = Integer.parseInt(paramProperties.getProperty("SchedulerInterval"));
    localInstructionType.DispatchStartTime = paramProperties.getProperty("DispatchStartTime");
    localInstructionType.DispatchInterval = Integer.parseInt(paramProperties.getProperty("DispatchInterval"));
    localInstructionType.ResubmitEventSupported = Integer.parseInt(paramProperties.getProperty("ResubmitEventSupported"));
    localInstructionType.ChkTmAutoRecover = Integer.parseInt(paramProperties.getProperty("ChkTmAutoRecover"));
    localInstructionType.FileBasedRecovery = Integer.parseInt(paramProperties.getProperty("FileBasedRecovery"));
    localInstructionType.ActiveFlag = Integer.parseInt(paramProperties.getProperty("ActiveFlag"));
    localInstructionType.RouteID = Integer.parseInt(paramProperties.getProperty("RouteID"));
    localInstructionType.useCutOffs = Boolean.valueOf(paramProperties.getProperty("UseCutOffs")).booleanValue();
    localInstructionType.createEmptyFile = Boolean.valueOf(paramProperties.getProperty("CreateEmptyFile")).booleanValue();
    localInstructionType.processOnHolidays = Boolean.valueOf(paramProperties.getProperty("ProcessOnHolidays")).booleanValue();
    localInstructionType.category = paramProperties.getProperty("Category");
    localInstructionType.memo = paramProperties.getProperty("Memo");
    return localInstructionType;
  }
  
  public static InstructionType[] readInstructionTypes(String paramString)
  {
    String str1 = fileToString(paramString);
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, DBConsts.LINE_SEPARATOR);
    ArrayList localArrayList = new ArrayList();
    String str2 = "";
    String str3 = "";
    int i = 0;
    try
    {
      while (localStringTokenizer.hasMoreTokens())
      {
        str3 = localStringTokenizer.nextToken().trim() + DBConsts.LINE_SEPARATOR;
        if ((str3 != null) && (str3.length() > 2) && (!str3.startsWith("--")))
        {
          str2 = str2 + str3;
          i++;
          if (i == INST_FIELD_NUMDER)
          {
            i = 0;
            localArrayList.add(str2);
            str2 = "";
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    String[] arrayOfString = (String[])localArrayList.toArray(new String[0]);
    InstructionType[] arrayOfInstructionType = new InstructionType[arrayOfString.length];
    for (int j = 0; j < arrayOfString.length; j++)
    {
      Properties localProperties = strToProps(arrayOfString[j]);
      arrayOfInstructionType[j] = convertPropertyToInstructionType(localProperties);
    }
    return arrayOfInstructionType;
  }
  
  public static InputStream getFileInputStream(String paramString)
    throws Exception
  {
    return ClassLoader.getSystemResourceAsStream(paramString);
  }
  
  public static String fileToString(String paramString)
  {
    String str1 = null;
    InputStream localInputStream = null;
    if (paramString == null)
    {
      System.out.println("File name is null");
      return str1;
    }
    byte[] arrayOfByte = null;
    try
    {
      localInputStream = getFileInputStream(paramString);
      if (localInputStream == null)
      {
        System.out.println("Failed to open file: " + paramString);
        String str2 = str1;
        return str2;
      }
      int i = localInputStream.available();
      arrayOfByte = new byte[i];
      localInputStream.read(arrayOfByte, 0, i);
      str1 = new String(arrayOfByte, "UTF-8");
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      System.out.println(localFileNotFoundException.getMessage());
      str3 = str1;
      return str3;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      System.out.println(localIOException.getMessage());
      str3 = str1;
      return str3;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      System.out.println(localException1.getMessage());
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
        localException2.printStackTrace();
        System.out.println(localException2);
      }
    }
    return str1;
  }
  
  public static String fileToString(InputStream paramInputStream)
  {
    String str1 = null;
    byte[] arrayOfByte = null;
    if (paramInputStream == null)
    {
      System.out.println("InputStream passed is Null");
      return null;
    }
    try
    {
      int i = paramInputStream.available();
      arrayOfByte = new byte[i];
      paramInputStream.read(arrayOfByte, 0, i);
      str1 = new String(arrayOfByte, "UTF-8");
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      System.out.println(localIOException.getMessage());
      str2 = str1;
      return str2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      System.out.println(localException1.getMessage());
      String str2 = str1;
      return str2;
    }
    finally
    {
      try
      {
        if (paramInputStream != null) {
          paramInputStream.close();
        }
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        System.out.println(localException2);
      }
    }
    return str1;
  }
  
  public static Properties strToProps(String paramString)
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
  
  public static void printInstTypes(InstructionType[] paramArrayOfInstructionType, String paramString)
  {
    PrintWriter localPrintWriter = null;
    BufferedWriter localBufferedWriter = null;
    FileWriter localFileWriter = null;
    try
    {
      localFileWriter = new FileWriter(paramString, false);
      localBufferedWriter = new BufferedWriter(localFileWriter);
      localPrintWriter = new PrintWriter(localBufferedWriter, true);
      for (int i = 0; i < paramArrayOfInstructionType.length; i++)
      {
        localPrintWriter.println("InstructionType=" + paramArrayOfInstructionType[i].InstructionType);
        localPrintWriter.println("FIID=" + paramArrayOfInstructionType[i].FIId);
        localPrintWriter.println("HandlerClassName=" + paramArrayOfInstructionType[i].HandlerClassName);
        localPrintWriter.println("SchedulerStartTime=" + paramArrayOfInstructionType[i].SchedulerStartTime);
        localPrintWriter.println("SchedulerInterval=" + paramArrayOfInstructionType[i].SchedulerInterval);
        localPrintWriter.println("DispatchStartTime=" + paramArrayOfInstructionType[i].DispatchStartTime);
        localPrintWriter.println("DispatchInterval=" + paramArrayOfInstructionType[i].DispatchInterval);
        localPrintWriter.println("ResubmitEventSupported=" + paramArrayOfInstructionType[i].ResubmitEventSupported);
        localPrintWriter.println("ChkTmAutoRecover=" + paramArrayOfInstructionType[i].ChkTmAutoRecover);
        localPrintWriter.println("FileBasedRecovery=" + paramArrayOfInstructionType[i].FileBasedRecovery);
        localPrintWriter.println("ActiveFlag=" + paramArrayOfInstructionType[i].ActiveFlag);
        localPrintWriter.println("RouteID=" + paramArrayOfInstructionType[i].RouteID);
        localPrintWriter.println("UseCutOffs=" + paramArrayOfInstructionType[i].useCutOffs);
        localPrintWriter.println("CreateEmptyFile=" + paramArrayOfInstructionType[i].createEmptyFile);
        localPrintWriter.println("ProcessOnHolidays=" + paramArrayOfInstructionType[i].processOnHolidays);
        localPrintWriter.println("Category=" + paramArrayOfInstructionType[i].category);
        localPrintWriter.println("Memo=" + paramArrayOfInstructionType[i].memo);
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      try
      {
        localFileWriter.close();
        localBufferedWriter.close();
        localPrintWriter.close();
        localFileWriter = null;
        localBufferedWriter = null;
        localPrintWriter = null;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
  }
  
  public static void get(InstructionType[] paramArrayOfInstructionType, HashMap paramHashMap)
  {
    ArrayList localArrayList = new ArrayList(paramHashMap.values());
    paramArrayOfInstructionType = (InstructionType[])localArrayList.toArray(new InstructionType[0]);
  }
  
  public static void put(InstructionType[] paramArrayOfInstructionType, HashMap paramHashMap)
  {
    for (int i = 0; i < paramArrayOfInstructionType.length; i++) {
      paramHashMap.put(paramArrayOfInstructionType[i].InstructionType, paramArrayOfInstructionType[i]);
    }
  }
  
  public static FFSProperties convertToProperties(Properties paramProperties)
  {
    String str1 = paramProperties.getProperty("dbUser");
    Object localObject1 = paramProperties.getProperty("dbPassword");
    String str2 = paramProperties.getProperty("dbPasswordEncrypted");
    Object localObject2 = localObject1;
    if ((str2 != null) && (str2.equalsIgnoreCase("y"))) {
      try
      {
        localObject2 = HfnEncrypt.decryptHexEncode((String)localObject1);
        if (localObject2 == null)
        {
          FFSDebug.log("BPWAdminBean: : Couldn't decrypt the password, assume it is in plain text. ");
          localObject2 = localObject1;
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("BPWAdminBean: Couldn't decrypt the password. " + localException.toString());
        FFSDebug.log("BPWAdminBean: Couldn't decrypt the password, assume it is in plain text. ");
        localObject2 = localObject1;
      }
    }
    localObject1 = localObject2;
    String str3 = paramProperties.getProperty("dbName");
    String str4 = paramProperties.getProperty("dbHost");
    String str5 = paramProperties.getProperty("dbPort");
    String str6 = paramProperties.getProperty("dbType");
    String str7 = paramProperties.getProperty("AutoStart");
    String str8 = paramProperties.getProperty("ServletDebug");
    String str9 = paramProperties.getProperty("ServerName");
    if (str6.equalsIgnoreCase("ASA")) {
      str6 = "ASA";
    } else if (str6.equalsIgnoreCase("ASE")) {
      str6 = "ASE";
    } else if (str6.equalsIgnoreCase("DB2:app")) {
      str6 = "DB2:app";
    } else if (str6.equalsIgnoreCase("DB2:un2")) {
      str6 = "DB2:un2";
    } else if (str6.equalsIgnoreCase("DB2:net")) {
      str6 = "DB2:net";
    } else if (str6.equalsIgnoreCase("DB2:390")) {
      str6 = "DB2:390";
    } else if (str6.equalsIgnoreCase("ORACLE:thin")) {
      str6 = "ORACLE:thin";
    } else if (str6.equalsIgnoreCase("ORACLE:oci8")) {
      str6 = "ORACLE:oci8";
    } else if (str6.equalsIgnoreCase("MSSQL:thin")) {
      str6 = "MSSQL:thin";
    }
    ConnPoolInfo localConnPoolInfo = new ConnPoolInfo();
    DBConnInfo localDBConnInfo = localConnPoolInfo._dbConnInfo;
    localDBConnInfo.setUser(str1);
    localDBConnInfo.setPassword((String)localObject1);
    if (!str6.equalsIgnoreCase("DB2:app"))
    {
      localDBConnInfo.setHost(str4);
      localDBConnInfo.setPort(Integer.parseInt(str5));
    }
    localDBConnInfo.setDatabaseName(str3);
    localDBConnInfo.setDsn("");
    localDBConnInfo.setDbType(str6);
    String str10 = paramProperties.getProperty("haFlag");
    if ((str10 != null) && (str10.compareTo("1") == 0)) {
      localDBConnInfo.setHAFlag(true);
    }
    localDBConnInfo.setUrl(paramProperties.getProperty("databaseUrl"));
    FFSProperties localFFSProperties = localConnPoolInfo.convertToProperties();
    if ((str7 != null) && (str7.length() > 0)) {
      localFFSProperties.setProperty("AutoStart", str7);
    }
    if ((str8 != null) && (str8.length() > 0)) {
      localFFSProperties.setProperty("ServletDebug", str8);
    }
    if ((str9 != null) && (str9.length() > 0)) {
      localFFSProperties.setProperty("ServerName", str9);
    }
    return localFFSProperties;
  }
  
  public static void compareFiles(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
    throws Exception
  {
    File localFile1 = new File(paramString1);
    if (!localFile1.exists()) {
      throw new Exception("File " + paramString1 + " does not exist.");
    }
    File localFile2 = new File(paramString2);
    if (!localFile2.exists()) {
      throw new Exception("File " + paramString2 + " does not exist.");
    }
    DataInputStream localDataInputStream1 = new DataInputStream(new BufferedInputStream(new FileInputStream(localFile1)));
    byte[] arrayOfByte1 = new byte[(int)localFile1.length()];
    localDataInputStream1.readFully(arrayOfByte1);
    localDataInputStream1.close();
    String str1 = new String(arrayOfByte1, "UTF-8");
    DataInputStream localDataInputStream2 = new DataInputStream(new BufferedInputStream(new FileInputStream(localFile2)));
    byte[] arrayOfByte2 = new byte[(int)localFile2.length()];
    localDataInputStream2.readFully(arrayOfByte2);
    localDataInputStream2.close();
    String str2 = new String(arrayOfByte2, "UTF-8");
    if (paramBoolean1)
    {
      i = str1.indexOf("/*");
      j = str1.indexOf("*/");
      if ((i != -1) && (j != -1)) {
        str1 = str1.substring(0, i) + str1.substring(j + 2);
      }
      i = str2.indexOf("/*");
      j = str2.indexOf("*/");
      if ((i != -1) && (j != -1)) {
        str2 = str2.substring(0, i) + str2.substring(j + 2);
      }
    }
    int i = -1;
    int j = -1;
    int k = -1;
    int m = -1;
    if (paramBoolean2)
    {
      i = str1.indexOf("<!DOCTYPE");
      if (i != -1) {
        j = str1.indexOf('>', i) + 1;
      }
      k = str2.indexOf("<!DOCTYPE");
      if (k != -1) {
        m = str2.indexOf('>', k) + 1;
      }
    }
    int i2 = str1.length();
    int i3 = str2.length();
    if (((i2 == 0) && (i3 != 0)) || ((i3 == 0) && (i2 != 0))) {
      throw new Exception("Files do not have same content. One of the files is empty while the other is not.");
    }
    int i4 = 0;
    for (int i5 = 0; (i4 < i2) && (i5 < i3); i5++)
    {
      if (paramBoolean2) {
        if ((i4 == i) && (i5 == k))
        {
          i4 = j;
          i5 = m;
        }
        else
        {
          if (i4 == i) {
            throw new Exception("Files do not have same content.");
          }
          if (i5 == k) {
            throw new Exception("Files do not have same content.");
          }
        }
      }
      int n = str1.charAt(i4);
      int i1 = str2.charAt(i5);
      if (n != i1)
      {
        if ((n == 13) && (i1 == 10))
        {
          i4++;
          if ((i4 < i2) && (str1.charAt(i4) == '\n')) {
            break label679;
          }
        }
        else if ((n == 10) && (i1 == 13))
        {
          i5++;
          if ((i5 < i3) && (str2.charAt(i5) == '\n')) {
            break label679;
          }
        }
        throw new Exception("Files do not have same content.");
      }
      label679:
      i4++;
    }
    if (i4 != i2) {
      throw new Exception("Files do not have same content: " + paramString1 + " is bigger than " + paramString2);
    }
    if (i5 != i3) {
      throw new Exception("Files do not have same content: " + paramString2 + " is bigger than " + paramString1);
    }
  }
  
  public static void compareFiles(String paramString1, String paramString2, boolean paramBoolean)
    throws Exception
  {
    compareFiles(paramString1, paramString2, paramBoolean, false);
  }
  
  public static void compareFiles(String paramString1, String paramString2)
    throws Exception
  {
    compareFiles(paramString1, paramString2, false, false);
  }
  
  public static void replace(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    DataInputStream localDataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(paramString1)));
    byte[] arrayOfByte = new byte[(int)new File(paramString1).length()];
    localDataInputStream.readFully(arrayOfByte);
    localDataInputStream.close();
    String str = new String(arrayOfByte, "UTF-8");
    StringBuffer localStringBuffer = new StringBuffer(str);
    int i = 0;
    int j = 0;
    int k = paramString2.length();
    int m = paramString3.length() - paramString2.length();
    for (;;)
    {
      i = str.indexOf(paramString2, i);
      if (i == -1) {
        break;
      }
      i += j;
      localStringBuffer.replace(i, i + k, paramString3);
      i += k;
      j += m;
    }
    DataOutputStream localDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(paramString1)));
    localDataOutputStream.writeUTF(localStringBuffer.toString());
    localDataOutputStream.close();
  }
  
  public static String replaceString(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString1);
    int i = 0;
    int j = 0;
    int k = paramString2.length();
    int m = paramString3.length() - paramString2.length();
    for (;;)
    {
      i = paramString1.indexOf(paramString2, i);
      if (i == -1) {
        break;
      }
      i += j;
      localStringBuffer.replace(i, i + k, paramString3);
      i += k;
      j += m;
    }
    return new String(localStringBuffer);
  }
  
  public static String getVal(String paramString1, String paramString2)
  {
    int i = paramString1.indexOf(paramString2);
    if (i == -1) {
      return null;
    }
    int j = paramString1.indexOf("<", i);
    return paramString1.substring(i + paramString2.length() + 1, j).trim();
  }
  
  public static String replaceVal(String paramString1, String paramString2, String paramString3)
  {
    long l = System.currentTimeMillis();
    String str = null;
    try
    {
      str = replaceString(paramString1, paramString2 + ">" + paramString3, paramString2 + ">" + l);
    }
    catch (Exception localException) {}
    return str;
  }
  
  public static String replaceVal(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    String str = null;
    try
    {
      str = replaceString(paramString1, paramString2 + ">" + paramString3, paramString2 + ">" + paramString4);
    }
    catch (Exception localException) {}
    return str;
  }
  
  public static String formatOFXDateString(Date paramDate)
  {
    return OFX_DATE_FORMAT.format(paramDate);
  }
  
  public static String formatOFXDateTimeString(Date paramDate)
  {
    return OFX_DATETIME_FORMAT.format(paramDate);
  }
  
  public static Date parseOFXDateString(String paramString)
  {
    return OFX_DATE_FORMAT.parse(paramString, new ParsePosition(0));
  }
  
  public static Date parseOFXDateTimeString(String paramString)
  {
    return OFX_DATETIME_FORMAT.parse(paramString, new ParsePosition(0));
  }
  
  public static boolean checkDateBeanFormat(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    if (paramString.length() != 8) {
      return false;
    }
    try
    {
      Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "FFSUtil.checkDateBeanFormat: Invalid date. Date: " + paramString, 0);
      return false;
    }
    return true;
  }
  
  public static boolean checkFrontEndDateFormat(String paramString)
  {
    boolean bool = false;
    if (paramString == null)
    {
      FFSDebug.log("BPWUtil.checkFrontEndDateFormat: Invalid date. Date is Null", 0);
      return false;
    }
    if (paramString.length() == 8)
    {
      bool = true;
    }
    else if (paramString.length() == 10)
    {
      bool = true;
    }
    else
    {
      bool = false;
      FFSDebug.log("BPWUtil.checkFrontEndDateFormat: Invalid date. Date: " + paramString, 0);
    }
    if (bool) {
      try
      {
        Integer.parseInt(paramString);
      }
      catch (Exception localException)
      {
        FFSDebug.log(localException, "BPWUtil.checkFrontEndDateFormat: Invalid date. Date: " + paramString, 0);
        bool = false;
      }
    }
    FFSDebug.log("BPWUtil.checkFrontEndDateFormat: Is valid date = " + bool, 6);
    return bool;
  }
  
  public static int getDateDBFormat(String paramString)
  {
    if (paramString == null) {
      return 0;
    }
    if (paramString.trim().length() == 8) {
      paramString = paramString.trim() + "00";
    }
    int i = 0;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "BPWUtil.getDateDBFormat: Invalid date. Date: " + paramString, 0);
    }
    return i;
  }
  
  public static String getDateBeanFormat(int paramInt)
  {
    paramInt /= 100;
    return new Integer(paramInt).toString();
  }
  
  public static int getTodayDBFormat()
  {
    Calendar localCalendar = Calendar.getInstance();
    int i = localCalendar.get(1) * 1000000 + (localCalendar.get(2) + 1) * 10000 + localCalendar.get(5) * 100;
    return i;
  }
  
  public static String getAccountIDWithAccountType(String paramString1, String paramString2)
  {
    String str = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      str = Integer.toString(localBPWEngine.getAccountTypesMap().getAccountType(paramString2));
      return AccountSettings.buildAccountId(paramString1, str);
    }
    catch (BPWException localBPWException)
    {
      FFSDebug.log("BPWUtil.getAccountIDWithAccountType():: " + FFSDebug.stackTrace(localBPWException), 0);
      return paramString1 + "-" + 0;
    }
    catch (Exception localException)
    {
      FFSDebug.log("BPWUtil.getAccountIDWithAccountType():: " + FFSDebug.stackTrace(localException), 0);
    }
    return paramString1 + "-" + str;
  }
  
  public static String composeTraceNumStr(String paramString1, String paramString2)
  {
    String str1 = "";
    if (paramString1 == null)
    {
      str1 = paramString2.substring(paramString2.length() - 15, paramString2.length());
    }
    else if (paramString1.length() < 15)
    {
      String str2 = paramString1.substring(0, 8);
      int i = 7;
      str1 = str2 + paramString2.substring(paramString2.length() - i, paramString2.length());
    }
    else
    {
      str1 = paramString1.substring(0, 15);
    }
    return str1;
  }
  
  public static Long composeTraceNum(String paramString1, String paramString2)
  {
    long l = 0L;
    String str = composeTraceNumStr(paramString1, paramString2);
    try
    {
      l = Long.parseLong(str);
    }
    catch (Exception localException) {}
    return new Long(l);
  }
  
  public static Integer composeAddendaEntryDetailSeqNum(String paramString)
  {
    int i = 0;
    String str = paramString.substring(paramString.length() - 7, paramString.length());
    try
    {
      i = Integer.parseInt(str);
    }
    catch (Exception localException) {}
    return new Integer(i);
  }
  
  public static String booleanToString10(boolean paramBoolean)
  {
    return paramBoolean == true ? "1" : "0";
  }
  
  public static boolean string10toBoolean(String paramString)
  {
    boolean bool;
    if ((paramString.equals(null)) || (paramString.equals("0"))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static short calculateCheckDigit(String paramString)
    throws FFSException
  {
    int i = 0;
    int j = -1;
    if ((paramString == null) || (paramString.trim().length() < 8))
    {
      FFSDebug.log("calculateCheckDigit: Invalid routing runmber: " + paramString, 0);
      throw new FFSException("calculateCheckDigit: Invalid routing runmber: " + paramString);
    }
    if (paramString.trim().length() > 8) {
      paramString = paramString.trim().substring(0, 8);
    }
    try
    {
      i = Integer.parseInt(paramString.trim());
      int k = 0;
      int[] arrayOfInt = { 3, 7, 1, 3, 7, 1, 3, 7 };
      for (int m = 0; m < 8; m++)
      {
        int n = (int)Math.pow(10.0D, 7 - m);
        k += arrayOfInt[m] * (i / n);
        i %= n;
      }
      m = k % 10;
      if (m == 0) {
        j = 0;
      } else {
        j = 10 - m;
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("calculateCheckDigit: failed: " + localException.toString(), 0);
      throw new FFSException(localException, "calculateCheckDigit: failed: " + localException.toString());
    }
    if (j > 9) {
      throw new FFSException("calculateCheckDigit: Invalid routing number: " + paramString + " Check digit is greater than 9 " + j);
    }
    return (short)j;
  }
  
  public static boolean isLarger(long paramLong, Collection paramCollection)
    throws Exception
  {
    Iterator localIterator = paramCollection.iterator();
    boolean bool = true;
    while ((localIterator.hasNext()) && (bool))
    {
      long l = ((Long)localIterator.next()).longValue();
      if (paramLong <= l) {
        bool = false;
      }
    }
    return bool;
  }
  
  public static boolean validateABA(String paramString)
    throws FFSException
  {
    if ((paramString == null) || (paramString.length() < 8) || (paramString.length() > 9)) {
      return false;
    }
    try
    {
      if (Integer.parseInt(paramString) == 0) {
        return false;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return false;
    }
    if (paramString.length() == 8) {
      return true;
    }
    String str = paramString.substring(0, 8);
    int i = calculateCheckDigit(str);
    str = str + i;
    return paramString.compareTo(str) == 0;
  }
  
  public static String convertToABA8(String paramString)
  {
    return paramString.substring(0, 8);
  }
  
  public static String convertToABA9(String paramString)
    throws FFSException
  {
    int i = calculateCheckDigit(paramString);
    return paramString + i;
  }
  
  public static boolean validateODFIName(String paramString)
  {
    return a(paramString, 23);
  }
  
  public static boolean validateRDFIName(String paramString)
  {
    return a(paramString, 23);
  }
  
  public static boolean validateCompName(String paramString)
  {
    return a(paramString, 16);
  }
  
  public static boolean validateCompACHId(String paramString)
  {
    return a(paramString, 10);
  }
  
  public static boolean validatePayAcct(String paramString)
  {
    return a(paramString, 22);
  }
  
  public static boolean validatePayeeName(String paramString)
  {
    return a(paramString, 22);
  }
  
  private static boolean a(String paramString, int paramInt)
  {
    if (paramString == null) {
      return true;
    }
    return paramString.length() <= paramInt;
  }
  
  public static String trimInstructionType(String paramString)
  {
    if (paramString == null) {
      return paramString;
    }
    String str1 = ".";
    String str2 = "";
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, str1);
    if (localStringTokenizer.hasMoreElements()) {
      str2 = localStringTokenizer.nextToken();
    } else {
      return paramString;
    }
    return str2;
  }
  
  public static HashMap convertHashTableToMap(Hashtable paramHashtable)
    throws FFSException
  {
    FFSDebug.log("BPWUtil.convertHashTableToMap start: ", 6);
    if (paramHashtable == null)
    {
      FFSDebug.log("BPWUtil.convertHashTableToMap : table is null ", 6);
      return null;
    }
    HashMap localHashMap = new HashMap();
    String str1 = null;
    Object localObject = null;
    String str2 = null;
    try
    {
      Enumeration localEnumeration = paramHashtable.keys();
      while (localEnumeration.hasMoreElements())
      {
        str1 = (String)localEnumeration.nextElement();
        localObject = paramHashtable.get(str1);
        localHashMap.put(str1, localObject);
        FFSDebug.log("BPWUtil.convertHashTableToMap : key = " + str1 + " value = " + localObject, 6);
      }
    }
    catch (Exception localException)
    {
      str2 = "BPWUtil.convertHashTableToMap : failed ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(str2 + localException.toString());
    }
    FFSDebug.log("BPWUtil.convertHashTableToMap end: ", 6);
    return localHashMap;
  }
  
  public static void printSchedulerInfo(SchedulerInfo[] paramArrayOfSchedulerInfo, String paramString)
  {
    PrintWriter localPrintWriter = null;
    BufferedWriter localBufferedWriter = null;
    FileWriter localFileWriter = null;
    try
    {
      localFileWriter = new FileWriter(paramString, false);
      localBufferedWriter = new BufferedWriter(localFileWriter);
      localPrintWriter = new PrintWriter(localBufferedWriter, true);
      if (paramArrayOfSchedulerInfo == null)
      {
        localPrintWriter.println("No schedule info found.");
        return;
      }
      for (int i = 0; i < paramArrayOfSchedulerInfo.length; i++) {
        if (paramArrayOfSchedulerInfo[i] != null) {
          paramArrayOfSchedulerInfo[i].printout(localPrintWriter);
        }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      try
      {
        localFileWriter.close();
        localBufferedWriter.close();
        localPrintWriter.close();
        localFileWriter = null;
        localBufferedWriter = null;
        localPrintWriter = null;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
  }
  
  public static void printScheduleHist(ScheduleHist[] paramArrayOfScheduleHist, String paramString)
  {
    PrintWriter localPrintWriter = null;
    BufferedWriter localBufferedWriter = null;
    FileWriter localFileWriter = null;
    try
    {
      localFileWriter = new FileWriter(paramString, false);
      localBufferedWriter = new BufferedWriter(localFileWriter);
      localPrintWriter = new PrintWriter(localBufferedWriter, true);
      if (paramArrayOfScheduleHist == null)
      {
        localPrintWriter.println("No schedule history found.");
        return;
      }
      for (int i = 0; i < paramArrayOfScheduleHist.length; i++) {
        if (paramArrayOfScheduleHist[i] != null) {
          paramArrayOfScheduleHist[i].printout(localPrintWriter);
        }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      try
      {
        localFileWriter.close();
        localBufferedWriter.close();
        localPrintWriter.close();
        localFileWriter = null;
        localBufferedWriter = null;
        localPrintWriter = null;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
  }
  
  public static CutOffInfo readCutOffInfo(String paramString)
    throws FFSException
  {
    try
    {
      Properties localProperties = new Properties();
      localProperties.load(new FileInputStream(paramString));
      return convertPropertyToCutOff(localProperties);
    }
    catch (Exception localException)
    {
      throw new FFSException("Failed to read cutoff :" + localException.getMessage());
    }
  }
  
  public static CutOffInfo convertPropertyToCutOff(Properties paramProperties)
  {
    CutOffInfo localCutOffInfo = new CutOffInfo();
    localCutOffInfo.setCutOffId(paramProperties.getProperty("CutOffId"));
    localCutOffInfo.setStatus(paramProperties.getProperty("Status"));
    localCutOffInfo.setFIId(paramProperties.getProperty("FIID"));
    localCutOffInfo.setInstructionType(paramProperties.getProperty("InstructionType"));
    localCutOffInfo.setFrequency(Integer.parseInt(paramProperties.getProperty("Frequency")));
    localCutOffInfo.setDay(Integer.parseInt(paramProperties.getProperty("Day")));
    localCutOffInfo.setProcessTime(paramProperties.getProperty("ProcessTime"));
    localCutOffInfo.setExtension(paramProperties.getProperty("Extension"));
    localCutOffInfo.setNextProcessTime(paramProperties.getProperty("NextProcessTime"));
    localCutOffInfo.setMemo(paramProperties.getProperty("Memo"));
    return localCutOffInfo;
  }
  
  public static void get(CutOffInfo[] paramArrayOfCutOffInfo, HashMap paramHashMap)
  {
    ArrayList localArrayList = new ArrayList(paramHashMap.values());
    paramArrayOfCutOffInfo = (CutOffInfo[])localArrayList.toArray(new CutOffInfo[0]);
  }
  
  public static void put(CutOffInfo[] paramArrayOfCutOffInfo, HashMap paramHashMap)
  {
    for (int i = 0; i < paramArrayOfCutOffInfo.length; i++) {
      paramHashMap.put(paramArrayOfCutOffInfo[i].getCutOffId(), paramArrayOfCutOffInfo[i]);
    }
  }
  
  public static CutOffInfo[] readCutOffs(String paramString)
  {
    String str1 = fileToString(paramString);
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, DBConsts.LINE_SEPARATOR);
    ArrayList localArrayList = new ArrayList();
    String str2 = "";
    String str3 = "";
    int i = 0;
    try
    {
      while (localStringTokenizer.hasMoreTokens())
      {
        str3 = localStringTokenizer.nextToken().trim() + DBConsts.LINE_SEPARATOR;
        if ((str3 != null) && (str3.length() > 2) && (!str3.startsWith("--")))
        {
          str2 = str2 + str3;
          i++;
          if (i == CUTOFF_FIELD_NUMBER)
          {
            i = 0;
            localArrayList.add(str2);
            str2 = "";
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    String[] arrayOfString = (String[])localArrayList.toArray(new String[0]);
    CutOffInfo[] arrayOfCutOffInfo = new CutOffInfo[arrayOfString.length];
    for (int j = 0; j < arrayOfString.length; j++)
    {
      Properties localProperties = strToProps(arrayOfString[j]);
      arrayOfCutOffInfo[j] = convertPropertyToCutOff(localProperties);
    }
    return arrayOfCutOffInfo;
  }
  
  public static void printCutOffInfo(CutOffInfo[] paramArrayOfCutOffInfo, String paramString)
  {
    PrintWriter localPrintWriter = null;
    BufferedWriter localBufferedWriter = null;
    FileWriter localFileWriter = null;
    try
    {
      localFileWriter = new FileWriter(paramString, false);
      localBufferedWriter = new BufferedWriter(localFileWriter);
      localPrintWriter = new PrintWriter(localBufferedWriter, true);
      if (paramArrayOfCutOffInfo == null)
      {
        localPrintWriter.println("No cutoff info found.");
        return;
      }
      for (int i = 0; i < paramArrayOfCutOffInfo.length; i++) {
        if (paramArrayOfCutOffInfo[i] != null) {
          printCutOffInfo(localPrintWriter, paramArrayOfCutOffInfo[i]);
        }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      try
      {
        localFileWriter.close();
        localBufferedWriter.close();
        localPrintWriter.close();
        localFileWriter = null;
        localBufferedWriter = null;
        localPrintWriter = null;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
  }
  
  public static CutOffInfoList readCutOffInfoList(String paramString)
    throws FFSException
  {
    try
    {
      Properties localProperties = new Properties();
      localProperties.load(new FileInputStream(paramString));
      return convertPropertyToCutOffInfoList(localProperties);
    }
    catch (Exception localException)
    {
      throw new FFSException("Failed to read instruction type :" + localException.getMessage());
    }
  }
  
  public static CutOffInfoList convertPropertyToCutOffInfoList(Properties paramProperties)
  {
    CutOffInfoList localCutOffInfoList = new CutOffInfoList();
    localCutOffInfoList.setFIId(paramProperties.getProperty("FIId"));
    String str1 = paramProperties.getProperty("Status");
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ";");
    ArrayList localArrayList = new ArrayList();
    String str2 = "";
    String str3 = "";
    try
    {
      while (localStringTokenizer.hasMoreTokens())
      {
        str3 = localStringTokenizer.nextToken().trim() + ";";
        if ((str3 != null) && (str3.length() > 2) && (!str3.startsWith("--")))
        {
          str2 = str2 + str3;
          localArrayList.add(str2);
          str2 = "";
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    String[] arrayOfString = (String[])localArrayList.toArray(new String[0]);
    localCutOffInfoList.setStatusList(arrayOfString);
    localCutOffInfoList.setInstructionType(paramProperties.getProperty("InstructionType"));
    return localCutOffInfoList;
  }
  
  public static void printCutOffInfoList(CutOffInfoList[] paramArrayOfCutOffInfoList, String paramString)
  {
    PrintWriter localPrintWriter = null;
    BufferedWriter localBufferedWriter = null;
    FileWriter localFileWriter = null;
    try
    {
      localFileWriter = new FileWriter(paramString, false);
      localBufferedWriter = new BufferedWriter(localFileWriter);
      localPrintWriter = new PrintWriter(localBufferedWriter, true);
      if (paramArrayOfCutOffInfoList == null)
      {
        localPrintWriter.println("No cutoff info list found.");
        return;
      }
      for (int i = 0; i < paramArrayOfCutOffInfoList.length; i++) {
        if (paramArrayOfCutOffInfoList[i] != null) {
          printCutOffInfo(localPrintWriter, paramArrayOfCutOffInfoList[i]);
        }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      try
      {
        localFileWriter.close();
        localBufferedWriter.close();
        localPrintWriter.close();
        localFileWriter = null;
        localBufferedWriter = null;
        localPrintWriter = null;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
  }
  
  public static void printCutOffInfo(PrintWriter paramPrintWriter, CutOffInfo paramCutOffInfo)
  {
    paramPrintWriter.println("CutOffId=" + paramCutOffInfo.getCutOffId());
    paramPrintWriter.println("Day=" + paramCutOffInfo.getDay());
    paramPrintWriter.println("FIID=" + paramCutOffInfo.getFIId());
    paramPrintWriter.println("Extension=" + paramCutOffInfo.getExtension());
    paramPrintWriter.println("Frequency=" + paramCutOffInfo.getFrequency());
    paramPrintWriter.println("Instruction Type=" + paramCutOffInfo.getInstructionType());
    paramPrintWriter.println("Memo=" + paramCutOffInfo.getMemo());
    paramPrintWriter.println("ProcessTime=" + paramCutOffInfo.getProcessTime());
    paramPrintWriter.println("NextProcessTime=" + paramCutOffInfo.getNextProcessTime());
    paramPrintWriter.println("Status=" + paramCutOffInfo.getStatus());
    paramPrintWriter.println("");
  }
  
  public static void printCutOffInfo(PrintWriter paramPrintWriter, CutOffInfoList paramCutOffInfoList)
  {
    paramPrintWriter.println("FIID=" + paramCutOffInfoList.getFIId());
    paramPrintWriter.println("Instruction Type=" + paramCutOffInfoList.getInstructionType());
    for (int i = 0; i < paramCutOffInfoList.getCutOffIdList().length; i++) {
      paramPrintWriter.println("CutOffId + " + i + "=" + paramCutOffInfoList.getCutOffIdList()[i]);
    }
    for (i = 0; i < paramCutOffInfoList.getStatusList().length; i++) {
      paramPrintWriter.println("Status + " + i + "=" + paramCutOffInfoList.getStatusList()[i]);
    }
    for (i = 0; i < paramCutOffInfoList.getCutOffInfoList().length; i++) {
      printCutOffInfo(paramPrintWriter, paramCutOffInfoList.getCutOffInfoList()[i]);
    }
    paramPrintWriter.println("");
  }
  
  public static void computeNextProcessTime(CutOffInfo paramCutOffInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "BPWUtil.computeNextProcessTime: ";
    FFSDebug.log(str1, "starts", 6);
    String str2 = null;
    Calendar localCalendar1 = null;
    Calendar localCalendar2 = null;
    SimpleDateFormat localSimpleDateFormat1 = null;
    SimpleDateFormat localSimpleDateFormat2 = null;
    int i = 0;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    Date localDate = null;
    int j = 0;
    try
    {
      if ((paramCutOffInfo.getExtension() != null) && (!paramCutOffInfo.getExtension().trim().equals(""))) {
        str6 = paramCutOffInfo.getExtension();
      } else {
        str6 = paramCutOffInfo.getProcessTime();
      }
      localSimpleDateFormat1 = new SimpleDateFormat("HH:mm");
      str2 = localSimpleDateFormat1.format(new Date());
      localCalendar1 = Calendar.getInstance();
      localCalendar2 = Calendar.getInstance();
      j = paramCutOffInfo.getFrequency();
      if (j == 11)
      {
        localCalendar2.add(5, 1);
      }
      else if (j == 9)
      {
        if ((paramCutOffInfo.getDay() >= 1) && (paramCutOffInfo.getDay() <= 7))
        {
          localCalendar2.add(3, 1);
          localCalendar2.set(7, paramCutOffInfo.getDay());
          i = localCalendar1.get(7);
        }
        else
        {
          paramCutOffInfo.setStatusCode(26018);
          paramCutOffInfo.setStatusMsg("Invalid day for given Frequency of CutOff");
        }
      }
      else if (j == 4)
      {
        if ((paramCutOffInfo.getDay() >= 1) && (paramCutOffInfo.getDay() <= 31))
        {
          localCalendar2.add(2, 1);
          localCalendar2.set(5, paramCutOffInfo.getDay());
          i = localCalendar1.get(5);
        }
        else
        {
          paramCutOffInfo.setStatusCode(26018);
          paramCutOffInfo.setStatusMsg("Invalid day for given Frequency of CutOff");
        }
      }
      else if ((paramCutOffInfo.getDay() >= 1) && (paramCutOffInfo.getDay() <= 365))
      {
        localCalendar2.add(1, 1);
        localCalendar2.set(6, paramCutOffInfo.getDay());
        i = localCalendar1.get(6);
      }
      else
      {
        paramCutOffInfo.setStatusCode(26018);
        paramCutOffInfo.setStatusMsg("Invalid day for given Frequency of CutOff");
        return;
      }
      FFSDebug.log(str1, "currentCalendar Time =" + localCalendar1.getTime(), 6);
      FFSDebug.log(str1, "NextCalendar Time =" + localCalendar2.getTime(), 6);
      localSimpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
      if (!paramBoolean)
      {
        FFSDebug.log(str1, "Process on Holiday Not Set ", 6);
        int k = 0;
        k = SmartCalendar.getPayday(String.valueOf(paramCutOffInfo.getFIId()), Integer.parseInt(localSimpleDateFormat1.format(localCalendar1.getTime())));
        str3 = String.valueOf(k);
        FFSDebug.log(str1, "Current Business Day =" + str3, 6);
        k = SmartCalendar.getPayday(String.valueOf(paramCutOffInfo.getFIId()), Integer.parseInt(localSimpleDateFormat1.format(localCalendar2.getTime())));
        str4 = String.valueOf(k);
        FFSDebug.log(str1, "Next Business Day =" + str4, 6);
      }
      else
      {
        FFSDebug.log(str1, "Process on Holiday Set ", 6);
        str3 = localSimpleDateFormat1.format(localCalendar1.getTime());
        FFSDebug.log(str1, "Current Business Day =" + str3, 6);
        str4 = localSimpleDateFormat1.format(localCalendar2.getTime());
        FFSDebug.log(str1, "Next Business Day =" + str4, 6);
      }
      FFSDebug.log(str1, "currentDay=" + i, 6);
      FFSDebug.log(str1, "CutOffDay=" + paramCutOffInfo.getDay(), 6);
      FFSDebug.log(str1, "currentTime=" + str2, 6);
      FFSDebug.log(str1, "effectiveProcessTime=" + str6, 6);
      if (str2.compareTo(str6) < 1)
      {
        if (i <= paramCutOffInfo.getDay()) {
          str5 = str3 + " " + str6;
        } else {
          str5 = str4 + " " + paramCutOffInfo.getProcessTime();
        }
      }
      else
      {
        if ((i <= paramCutOffInfo.getDay()) && (paramCutOffInfo.getExtension() != null) && (!paramCutOffInfo.getExtension().trim().equals("")))
        {
          paramCutOffInfo.setStatusCode(26006);
          paramCutOffInfo.setStatusMsg("Extension is earlier than current time");
          return;
        }
        str5 = str4 + " " + paramCutOffInfo.getProcessTime();
      }
      localSimpleDateFormat1 = new SimpleDateFormat("yyyyMMdd HH:mm");
      localDate = localSimpleDateFormat1.parse(str5);
      localSimpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
      str5 = localSimpleDateFormat2.format(localDate);
      FFSDebug.log(str1, "Final Next Processing Time =" + str5, 6);
      paramCutOffInfo.setNextProcessTime(str5);
      paramCutOffInfo.setStatusCode(0);
      paramCutOffInfo.setStatusMsg("Success");
      FFSDebug.log(str1, "done", 6);
    }
    catch (Exception localException)
    {
      String str7 = str1 + "failed " + localException.toString();
      FFSDebug.log(str7, FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str7);
    }
  }
  
  public static boolean isValidTime(String paramString)
  {
    String str1 = "BPWUtil.isValidTime(String ) :";
    String str2 = null;
    String str3 = null;
    int i = 0;
    int j = 0;
    try
    {
      str2 = paramString.substring(0, paramString.indexOf(":"));
      FFSDebug.log(str1, " Hour Part: " + str2, 6);
      if ((str2 == null) || (str2.trim().length() > 2)) {
        return false;
      }
      i = Integer.parseInt(str2.trim());
      str3 = paramString.substring(paramString.indexOf(":") + 1);
      FFSDebug.log(str1, " Minute Part: " + str3, 6);
      if ((str3 == null) || (str3.trim().length() > 2)) {
        return false;
      }
      j = Integer.parseInt(str3.trim());
      FFSDebug.log(str1, " Hour: " + i + " Min.: " + j, 6);
      if (j > 59) {
        return false;
      }
      if (j < 0) {
        return false;
      }
      if (i > 23) {
        return false;
      }
      return i >= 1;
    }
    catch (Exception localException)
    {
      String str4 = str1 + "failed " + localException.toString();
      FFSDebug.log(str4, FFSDebug.stackTrace(localException), 0);
    }
    return false;
  }
  
  public static boolean isTomorrow(String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "BPWUtil.isTomorrow: ";
    FFSDebug.log(str1, "start", 6);
    Calendar localCalendar = null;
    SimpleDateFormat localSimpleDateFormat1 = null;
    SimpleDateFormat localSimpleDateFormat2 = null;
    String str2 = null;
    String str3 = null;
    boolean bool = false;
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      FFSDebug.log(str1, "Date (dateStr) is missing", 0);
      throw new FFSException("Date (dateStr) is missing");
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      FFSDebug.log(str1, "Format of date is not specified", 0);
      throw new FFSException("Format of date is not specified");
    }
    try
    {
      localSimpleDateFormat1 = new SimpleDateFormat(paramString2);
      Date localDate = localSimpleDateFormat1.parse(paramString1.trim());
      localSimpleDateFormat2 = new SimpleDateFormat("yyyyMMdd");
      localCalendar = Calendar.getInstance();
      localCalendar.add(5, 1);
      str2 = localSimpleDateFormat2.format(localCalendar.getTime());
      str3 = localSimpleDateFormat2.format(localDate);
      if (str2.equalsIgnoreCase(str3)) {
        bool = true;
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log(str1, FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str1 + "failed, " + localException.getMessage());
    }
    FFSDebug.log(str1, "end, isTomorrow =" + bool, 6);
    return bool;
  }
  
  public static boolean isToday(String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "BPWUtil.isToday: ";
    FFSDebug.log(str1, "start", 6);
    Calendar localCalendar = null;
    SimpleDateFormat localSimpleDateFormat1 = null;
    SimpleDateFormat localSimpleDateFormat2 = null;
    String str2 = null;
    String str3 = null;
    boolean bool = false;
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      FFSDebug.log(str1, "Date (dateStr) is missing", 0);
      throw new FFSException("Date (dateStr) is missing");
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      FFSDebug.log(str1, "Format of date is not specified", 0);
      throw new FFSException("Format of date is not specified");
    }
    try
    {
      localSimpleDateFormat1 = new SimpleDateFormat(paramString2);
      Date localDate = localSimpleDateFormat1.parse(paramString1.trim());
      localSimpleDateFormat2 = new SimpleDateFormat("yyyyMMdd");
      localCalendar = Calendar.getInstance();
      str2 = localSimpleDateFormat2.format(localCalendar.getTime());
      str3 = localSimpleDateFormat2.format(localDate);
      if (str2.equalsIgnoreCase(str3)) {
        bool = true;
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log(str1, FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str1 + "failed, " + localException.getMessage());
    }
    FFSDebug.log(str1, "end, isToday =" + bool, 6);
    return bool;
  }
  
  public static String getDateInNewFormat(String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str1 = "BPWUtil.getDateInNewFormat: ";
    FFSDebug.log(str1, "start", 6);
    FFSDebug.log(str1, "DateStr=", paramString1, " oldFormat=", paramString2, " newFormat=", paramString3, 6);
    SimpleDateFormat localSimpleDateFormat1 = null;
    SimpleDateFormat localSimpleDateFormat2 = null;
    String str2 = null;
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      FFSDebug.log(str1, "Date (dateStr) is missing", 0);
      return null;
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      FFSDebug.log(str1, "Old format of date is not specified", 0);
      return null;
    }
    if ((paramString3 == null) || (paramString3.trim().length() == 0))
    {
      FFSDebug.log(str1, "New format of date is not specified", 0);
      return null;
    }
    try
    {
      localSimpleDateFormat1 = new SimpleDateFormat(paramString2, Locale.ENGLISH);
      Date localDate = localSimpleDateFormat1.parse(paramString1);
      localSimpleDateFormat2 = new SimpleDateFormat(paramString3);
      str2 = localSimpleDateFormat2.format(localDate);
      FFSDebug.log(str1, "newDateStr=", str2, 6);
    }
    catch (Exception localException)
    {
      FFSDebug.log(str1, FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str1 + "failed, " + localException.getMessage());
    }
    FFSDebug.log(str1, "end, newDateStr =" + str2, 6);
    return str2;
  }
  
  public static String getCurrentTimeWithInterval(int paramInt, String paramString, boolean paramBoolean)
    throws FFSException
  {
    if (paramString == null) {
      paramString = "yyyy/MM/dd HH:mm";
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
    Calendar localCalendar = Calendar.getInstance();
    if (!paramBoolean) {
      paramInt *= -1;
    }
    localCalendar.add(12, paramInt);
    Date localDate = localCalendar.getTime();
    return localSimpleDateFormat.format(localDate);
  }
  
  public static String getLeadTimeForCutOff()
    throws FFSException
  {
    String str1 = "BPWUtil.getLeadTime: ";
    FFSDebug.log(str1 + "start ", 6);
    String str2 = BPWRegistryUtil.getProperty("bpw.cutoff.lead.time", "30");
    FFSDebug.log(str1 + "CutOff lead time =" + str2, 6);
    String str3 = getCurrentTimeWithInterval(Integer.parseInt(str2), "yyyy/MM/dd HH:mm", true);
    FFSDebug.log(str1 + "end, now with lead time added =" + str3, 6);
    return str3;
  }
  
  public static int getProjectedBusinessDate(String paramString1, int paramInt, String paramString2, boolean paramBoolean)
    throws FFSException
  {
    if ((paramString1 == null) || (paramString1.length() < 8)) {
      throw new FFSException("BPWUtil.getProjectedBusinessDate Invalid date:" + paramString1);
    }
    if (paramString1.length() > 8) {
      paramString1 = paramString1.substring(0, 8);
    }
    int i = Integer.parseInt(paramString1);
    int j = i;
    try
    {
      for (int k = 0; k < paramInt; k++) {
        j = SmartCalendar.getBusinessDay(paramString2, j, paramBoolean);
      }
    }
    catch (Throwable localThrowable)
    {
      throw new FFSException(localThrowable, "BPWUtil.getProjectedBusinessDate failed to get business day.");
    }
    return j * 100;
  }
  
  public static String getFutureDate(String paramString1, String paramString2, int paramInt)
    throws Exception
  {
    if (paramString2 == null) {
      paramString2 = "yyyyMMdd";
    }
    if (paramString1 == null)
    {
      paramString2 = "yyyyMMdd";
      paramString1 = FFSUtil.getDateString(paramString2);
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localSimpleDateFormat.parse(paramString1));
    localCalendar.add(5, paramInt);
    return localSimpleDateFormat.format(localCalendar.getTime());
  }
  
  public static int getCurdefInt(String paramString)
  {
    int i = -1;
    if (paramString != null) {
      if (paramString.equalsIgnoreCase("ADP")) {
        i = 0;
      } else if (paramString.equalsIgnoreCase("AED")) {
        i = 1;
      } else if (paramString.equalsIgnoreCase("AFA")) {
        i = 2;
      } else if (paramString.equalsIgnoreCase("ALL")) {
        i = 3;
      } else if (paramString.equalsIgnoreCase("ANG")) {
        i = 4;
      } else if (paramString.equalsIgnoreCase("AOK")) {
        i = 5;
      } else if (paramString.equalsIgnoreCase("ARA")) {
        i = 6;
      } else if (paramString.equalsIgnoreCase("ATS")) {
        i = 7;
      } else if (paramString.equalsIgnoreCase("AUD")) {
        i = 8;
      } else if (paramString.equalsIgnoreCase("AWG")) {
        i = 9;
      } else if (paramString.equalsIgnoreCase("BBD")) {
        i = 10;
      } else if (paramString.equalsIgnoreCase("BDT")) {
        i = 11;
      } else if (paramString.equalsIgnoreCase("BEF")) {
        i = 12;
      } else if (paramString.equalsIgnoreCase("BGL")) {
        i = 13;
      } else if (paramString.equalsIgnoreCase("BHD")) {
        i = 14;
      } else if (paramString.equalsIgnoreCase("BIF")) {
        i = 15;
      } else if (paramString.equalsIgnoreCase("BMD")) {
        i = 16;
      } else if (paramString.equalsIgnoreCase("BND")) {
        i = 17;
      } else if (paramString.equalsIgnoreCase("BOB")) {
        i = 18;
      } else if (paramString.equalsIgnoreCase("BRC")) {
        i = 19;
      } else if (paramString.equalsIgnoreCase("BSD")) {
        i = 20;
      } else if (paramString.equalsIgnoreCase("BTN")) {
        i = 21;
      } else if (paramString.equalsIgnoreCase("BUK")) {
        i = 22;
      } else if (paramString.equalsIgnoreCase("BWP")) {
        i = 23;
      } else if (paramString.equalsIgnoreCase("BZD")) {
        i = 24;
      } else if (paramString.equalsIgnoreCase("CAD")) {
        i = 25;
      } else if (paramString.equalsIgnoreCase("CHF")) {
        i = 26;
      } else if (paramString.equalsIgnoreCase("CLF")) {
        i = 27;
      } else if (paramString.equalsIgnoreCase("CLP")) {
        i = 28;
      } else if (paramString.equalsIgnoreCase("CNY")) {
        i = 29;
      } else if (paramString.equalsIgnoreCase("COP")) {
        i = 30;
      } else if (paramString.equalsIgnoreCase("CRC")) {
        i = 31;
      } else if (paramString.equalsIgnoreCase("CSK")) {
        i = 32;
      } else if (paramString.equalsIgnoreCase("CUP")) {
        i = 33;
      } else if (paramString.equalsIgnoreCase("CVE")) {
        i = 34;
      } else if (paramString.equalsIgnoreCase("CYP")) {
        i = 35;
      } else if (paramString.equalsIgnoreCase("DDM")) {
        i = 36;
      } else if (paramString.equalsIgnoreCase("DEM")) {
        i = 37;
      } else if (paramString.equalsIgnoreCase("DJF")) {
        i = 38;
      } else if (paramString.equalsIgnoreCase("DKK")) {
        i = 39;
      } else if (paramString.equalsIgnoreCase("DOP")) {
        i = 40;
      } else if (paramString.equalsIgnoreCase("DZD")) {
        i = 41;
      } else if (paramString.equalsIgnoreCase("ECS")) {
        i = 42;
      } else if (paramString.equalsIgnoreCase("EGP")) {
        i = 43;
      } else if (paramString.equalsIgnoreCase("ESP")) {
        i = 44;
      } else if (paramString.equalsIgnoreCase("ETB")) {
        i = 45;
      } else if (paramString.equalsIgnoreCase("EUR")) {
        i = 46;
      } else if (paramString.equalsIgnoreCase("FIM")) {
        i = 47;
      } else if (paramString.equalsIgnoreCase("FJD")) {
        i = 48;
      } else if (paramString.equalsIgnoreCase("FKP")) {
        i = 49;
      } else if (paramString.equalsIgnoreCase("FRF")) {
        i = 50;
      } else if (paramString.equalsIgnoreCase("GBP")) {
        i = 51;
      } else if (paramString.equalsIgnoreCase("GHC")) {
        i = 52;
      } else if (paramString.equalsIgnoreCase("GIP")) {
        i = 53;
      } else if (paramString.equalsIgnoreCase("GMD")) {
        i = 54;
      } else if (paramString.equalsIgnoreCase("GNF")) {
        i = 55;
      } else if (paramString.equalsIgnoreCase("GRD")) {
        i = 56;
      } else if (paramString.equalsIgnoreCase("GTQ")) {
        i = 57;
      } else if (paramString.equalsIgnoreCase("GWP")) {
        i = 58;
      } else if (paramString.equalsIgnoreCase("GYD")) {
        i = 59;
      } else if (paramString.equalsIgnoreCase("HKD")) {
        i = 60;
      } else if (paramString.equalsIgnoreCase("HNL")) {
        i = 61;
      } else if (paramString.equalsIgnoreCase("HTG")) {
        i = 62;
      } else if (paramString.equalsIgnoreCase("HUF")) {
        i = 63;
      } else if (paramString.equalsIgnoreCase("IDR")) {
        i = 64;
      } else if (paramString.equalsIgnoreCase("IEP")) {
        i = 65;
      } else if (paramString.equalsIgnoreCase("ILS")) {
        i = 66;
      } else if (paramString.equalsIgnoreCase("INR")) {
        i = 67;
      } else if (paramString.equalsIgnoreCase("IQD")) {
        i = 68;
      } else if (paramString.equalsIgnoreCase("IRR")) {
        i = 69;
      } else if (paramString.equalsIgnoreCase("ISK")) {
        i = 70;
      } else if (paramString.equalsIgnoreCase("ITL")) {
        i = 71;
      } else if (paramString.equalsIgnoreCase("JMD")) {
        i = 72;
      } else if (paramString.equalsIgnoreCase("JOD")) {
        i = 73;
      } else if (paramString.equalsIgnoreCase("JPY")) {
        i = 74;
      } else if (paramString.equalsIgnoreCase("KES")) {
        i = 75;
      } else if (paramString.equalsIgnoreCase("KHR")) {
        i = 76;
      } else if (paramString.equalsIgnoreCase("KMF")) {
        i = 77;
      } else if (paramString.equalsIgnoreCase("KPW")) {
        i = 78;
      } else if (paramString.equalsIgnoreCase("KRW")) {
        i = 79;
      } else if (paramString.equalsIgnoreCase("KWD")) {
        i = 80;
      } else if (paramString.equalsIgnoreCase("KYD")) {
        i = 81;
      } else if (paramString.equalsIgnoreCase("LAK")) {
        i = 82;
      } else if (paramString.equalsIgnoreCase("LBP")) {
        i = 83;
      } else if (paramString.equalsIgnoreCase("LKR")) {
        i = 84;
      } else if (paramString.equalsIgnoreCase("LRD")) {
        i = 85;
      } else if (paramString.equalsIgnoreCase("LSL")) {
        i = 86;
      } else if (paramString.equalsIgnoreCase("LUF")) {
        i = 87;
      } else if (paramString.equalsIgnoreCase("LYD")) {
        i = 88;
      } else if (paramString.equalsIgnoreCase("MAD")) {
        i = 89;
      } else if (paramString.equalsIgnoreCase("MGF")) {
        i = 90;
      } else if (paramString.equalsIgnoreCase("MNT")) {
        i = 91;
      } else if (paramString.equalsIgnoreCase("MOP")) {
        i = 92;
      } else if (paramString.equalsIgnoreCase("MRO")) {
        i = 93;
      } else if (paramString.equalsIgnoreCase("MTL")) {
        i = 94;
      } else if (paramString.equalsIgnoreCase("MUR")) {
        i = 95;
      } else if (paramString.equalsIgnoreCase("MVR")) {
        i = 96;
      } else if (paramString.equalsIgnoreCase("MWK")) {
        i = 97;
      } else if (paramString.equalsIgnoreCase("MXK")) {
        i = 98;
      } else if (paramString.equalsIgnoreCase("MYR")) {
        i = 99;
      } else if (paramString.equalsIgnoreCase("MZM")) {
        i = 100;
      } else if (paramString.equalsIgnoreCase("NGN")) {
        i = 101;
      } else if (paramString.equalsIgnoreCase("NIC")) {
        i = 102;
      } else if (paramString.equalsIgnoreCase("NLG")) {
        i = 103;
      } else if (paramString.equalsIgnoreCase("NOK")) {
        i = 104;
      } else if (paramString.equalsIgnoreCase("NPR")) {
        i = 105;
      } else if (paramString.equalsIgnoreCase("NZD")) {
        i = 106;
      } else if (paramString.equalsIgnoreCase("OMR")) {
        i = 107;
      } else if (paramString.equalsIgnoreCase("PAB")) {
        i = 108;
      } else if (paramString.equalsIgnoreCase("PEI")) {
        i = 109;
      } else if (paramString.equalsIgnoreCase("PGK")) {
        i = 110;
      } else if (paramString.equalsIgnoreCase("PHP")) {
        i = 111;
      } else if (paramString.equalsIgnoreCase("PKR")) {
        i = 112;
      } else if (paramString.equalsIgnoreCase("PLZ")) {
        i = 113;
      } else if (paramString.equalsIgnoreCase("PTE")) {
        i = 114;
      } else if (paramString.equalsIgnoreCase("PYG")) {
        i = 115;
      } else if (paramString.equalsIgnoreCase("QAR")) {
        i = 116;
      } else if (paramString.equalsIgnoreCase("ROL")) {
        i = 117;
      } else if (paramString.equalsIgnoreCase("RWF")) {
        i = 118;
      } else if (paramString.equalsIgnoreCase("SAR")) {
        i = 119;
      } else if (paramString.equalsIgnoreCase("SBD")) {
        i = 120;
      } else if (paramString.equalsIgnoreCase("SCR")) {
        i = 121;
      } else if (paramString.equalsIgnoreCase("SDP")) {
        i = 122;
      } else if (paramString.equalsIgnoreCase("SEK")) {
        i = 123;
      } else if (paramString.equalsIgnoreCase("SGD")) {
        i = 124;
      } else if (paramString.equalsIgnoreCase("SHP")) {
        i = 125;
      } else if (paramString.equalsIgnoreCase("SLL")) {
        i = 126;
      } else if (paramString.equalsIgnoreCase("SOS")) {
        i = 127;
      } else if (paramString.equalsIgnoreCase("SRG")) {
        i = 128;
      } else if (paramString.equalsIgnoreCase("STD")) {
        i = 129;
      } else if (paramString.equalsIgnoreCase("SUR")) {
        i = 130;
      } else if (paramString.equalsIgnoreCase("SVC")) {
        i = 131;
      } else if (paramString.equalsIgnoreCase("SYP")) {
        i = 132;
      } else if (paramString.equalsIgnoreCase("SZL")) {
        i = 133;
      } else if (paramString.equalsIgnoreCase("THB")) {
        i = 134;
      } else if (paramString.equalsIgnoreCase("TND")) {
        i = 135;
      } else if (paramString.equalsIgnoreCase("TOP")) {
        i = 136;
      } else if (paramString.equalsIgnoreCase("TPE")) {
        i = 137;
      } else if (paramString.equalsIgnoreCase("TRL")) {
        i = 138;
      } else if (paramString.equalsIgnoreCase("TTD")) {
        i = 139;
      } else if (paramString.equalsIgnoreCase("TWD")) {
        i = 140;
      } else if (paramString.equalsIgnoreCase("TZS")) {
        i = 141;
      } else if (paramString.equalsIgnoreCase("UGS")) {
        i = 142;
      } else if (paramString.equalsIgnoreCase("USD")) {
        i = 143;
      } else if (paramString.equalsIgnoreCase("UYP")) {
        i = 144;
      } else if (paramString.equalsIgnoreCase("VEB")) {
        i = 145;
      } else if (paramString.equalsIgnoreCase("VND")) {
        i = 146;
      } else if (paramString.equalsIgnoreCase("VUV")) {
        i = 147;
      } else if (paramString.equalsIgnoreCase("WST")) {
        i = 148;
      } else if (paramString.equalsIgnoreCase("YDD")) {
        i = 149;
      } else if (paramString.equalsIgnoreCase("YER")) {
        i = 150;
      } else if (paramString.equalsIgnoreCase("YUD")) {
        i = 151;
      } else if (paramString.equalsIgnoreCase("ZAR")) {
        i = 152;
      } else if (paramString.equalsIgnoreCase("ZMK")) {
        i = 153;
      } else if (paramString.equalsIgnoreCase("ZRZ")) {
        i = 154;
      } else if (paramString.equalsIgnoreCase("ZWD")) {
        i = 155;
      }
    }
    return i;
  }
  
  public static SchedulerInfo getSchedulerInfo(String paramString1, String paramString2)
    throws FFSException
  {
    SchedulerInfo localSchedulerInfo = null;
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
      localSchedulerInfo = localScheduler.getSchedulerInfo(paramString2, paramString1);
    } else {
      try
      {
        BPWServer localBPWServer = BPWServer.getInstance();
        localSchedulerInfo = localBPWServer.getSchedulerInfoOnRemoteServer(paramString2, paramString1);
      }
      catch (Exception localException)
      {
        FFSDebug.log(localException, "", 0);
        return null;
      }
    }
    if (localSchedulerInfo == null) {
      FFSDebug.log(paramString2 + " " + "Instruction type does not exist" + " for FIID =" + paramString1, 0);
    }
    return localSchedulerInfo;
  }
  
  public static String[] getArrayFromString(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    while (localStringTokenizer.hasMoreElements()) {
      localArrayList.add(localStringTokenizer.nextElement());
    }
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  public static String getStringFromArray(String[] paramArrayOfString, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramArrayOfString == null) || (paramString == null)) {
      return localStringBuffer.toString();
    }
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (i == 0) {
        localStringBuffer.append(paramArrayOfString[i]);
      } else {
        localStringBuffer.append(paramString + paramArrayOfString[i]);
      }
    }
    return localStringBuffer.toString();
  }
  
  public static HashMap stringToHashMap(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      return null;
    }
    HashMap localHashMap = new HashMap();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
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
        localHashMap.put(str2, str3);
      }
    }
    return localHashMap;
  }
  
  public static int getSessionSize()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = localPropertyConfig.otherProperties.getProperty("bpw.paging.maximum.sessionsize", "1000");
    return Integer.parseInt(str);
  }
  
  public static String addIntervalToDateTime(int paramInt1, String paramString, int paramInt2)
    throws FFSException
  {
    if (paramInt1 == 0) {
      paramInt1 = 1440;
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    Date localDate1 = localSimpleDateFormat.parse(paramString, new ParsePosition(0));
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate1);
    localCalendar.add(paramInt2, paramInt1);
    Date localDate2 = localCalendar.getTime();
    return localSimpleDateFormat.format(localDate2);
  }
  
  public static boolean validateDate(String paramString1, String paramString2)
  {
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
      localSimpleDateFormat.setLenient(false);
      Date localDate = localSimpleDateFormat.parse(paramString1, new ParsePosition(0));
      localSimpleDateFormat.format(localDate);
    }
    catch (Exception localException)
    {
      return false;
    }
    return true;
  }
  
  public static Calendar dateIntToCalendar(int paramInt, String paramString)
    throws Exception
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
    Date localDate = localSimpleDateFormat.parse(String.valueOf(paramInt));
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    return localCalendar;
  }
  
  public static double getPositiveDoubleFromString(String paramString)
    throws Exception
  {
    double d = 0.0D;
    d = Double.parseDouble(paramString);
    if (d <= 0.0D) {
      throw new Exception("String does not represent a positive number.");
    }
    return d;
  }
  
  public static String validateCurrencyEnum(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException
  {
    String str = null;
    if (paramEnumCurrencyEnum == null) {
      try
      {
        str = BPWServer.getPropertyValue("bpw.transaction.base.currency", BPWLocaleUtil.getCurCode());
      }
      catch (Exception localException1)
      {
        try
        {
          str = BPWLocaleUtil.getCurCode();
        }
        catch (Exception localException2)
        {
          str = "USD";
        }
      }
    } else {
      str = MsgBuilder.getCurrencyString(paramEnumCurrencyEnum);
    }
    return str;
  }
  
  public static String validateCurrencyEnum(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException
  {
    String str = null;
    if (paramEnumCurrencyEnum == null) {
      try
      {
        str = BPWServer.getPropertyValue("bpw.transaction.base.currency", BPWLocaleUtil.getCurCode());
      }
      catch (Exception localException1)
      {
        try
        {
          str = BPWLocaleUtil.getCurCode();
        }
        catch (Exception localException2)
        {
          str = "USD";
        }
      }
    } else {
      str = MsgBuilder.getCurrencyString(paramEnumCurrencyEnum);
    }
    return str;
  }
  
  public static String validateCurrencyString(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      try
      {
        paramString = BPWServer.getPropertyValue("bpw.transaction.base.currency", BPWLocaleUtil.getCurCode());
      }
      catch (Exception localException1)
      {
        try
        {
          paramString = BPWLocaleUtil.getCurCode();
        }
        catch (Exception localException2)
        {
          paramString = "USD";
        }
      }
    }
    return paramString;
  }
  
  public static String getErrorMessageWithCode(int paramInt, String paramString)
  {
    return "[ErrorCode " + paramInt + "] " + paramString;
  }
  
  public static String ACHAmountToString(String paramString)
  {
    return FFSUtil.getBigDecimal(paramString, 2).movePointLeft(2).toString();
  }
  
  public static int calendarToDueDateFormatInt(Calendar paramCalendar)
  {
    return paramCalendar.get(1) * 10000 + (paramCalendar.get(2) + 1) * 100 + paramCalendar.get(5);
  }
  
  public static String truncateString(String paramString, int paramInt)
  {
    if ((paramString != null) && (paramString.length() > paramInt)) {
      return paramString.substring(0, paramInt);
    }
    return paramString;
  }
  
  public static int calculateTotal(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      return 0;
    }
    int i = 0;
    for (int j = 0; j < paramArrayOfInt.length; j++) {
      i += paramArrayOfInt[j];
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.BPWUtil
 * JD-Core Version:    0.7.0.1
 */