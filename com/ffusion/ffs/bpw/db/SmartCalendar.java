package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.SmartCalendarFile;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.banks.BankUtil;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.smartcalendar.SCBusinessDays;
import com.ffusion.util.beans.smartcalendar.SCCalendar;
import com.ffusion.util.beans.smartcalendar.SCHoliday;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

public class SmartCalendar
  implements FFSConst, DBConsts
{
  private static boolean xN = false;
  private static HashMap xP = new HashMap();
  private static final String xM = "bpw.intratransfer.ignore.smartcalendar";
  private static BPWSCAdapter xO;
  
  public SmartCalendar(String paramString)
  {
    init(paramString);
  }
  
  public static void init(String paramString)
  {
    FFSDebug.log("SmartCalendar init: for FI: " + paramString, 6);
    if ((paramString == null) || (paramString.trim().length() < 1)) {
      paramString = "1000";
    }
    paramString = paramString.trim();
    try
    {
      BPWCalendarInfo localBPWCalendarInfo = (BPWCalendarInfo)xP.get(paramString);
      if (localBPWCalendarInfo != null)
      {
        FFSDebug.log("SmartCalendar init: old calInfo: ", 6);
        printSmartCalendar(paramString);
        if (localBPWCalendarInfo.jdField_for)
        {
          FFSDebug.log("Calendar is already intialized for FI: " + paramString, 6);
          return;
        }
        xP.remove(paramString);
      }
      localBPWCalendarInfo = ax(paramString);
      SmartDate[] arrayOfSmartDate = localBPWCalendarInfo.jdField_if;
      if (arrayOfSmartDate != null)
      {
        a(arrayOfSmartDate);
        localBPWCalendarInfo.a = paramString;
        localBPWCalendarInfo.jdField_for = true;
        xP.put(paramString, localBPWCalendarInfo);
        FFSDebug.log("SmartClendar.init: Initialized sucessfully for FI: ", paramString, 6);
        FFSDebug.log("SmartCalendar init: calInfo: ", 6);
        printSmartCalendar(paramString);
      }
      else
      {
        FFSDebug.log("SmartClendar.init: Failed to initialize calendar for FI: ", paramString, 0);
      }
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("BPW Calendar initialization failed for for FI: " + paramString, 6);
      FFSDebug.log("Error: " + FFSDebug.stackTrace(localThrowable), 6);
    }
  }
  
  public static void reset(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() < 1))
    {
      FFSDebug.log("SmartCalendar.reset failed. Invalid FI Id: " + paramString, 0);
      return;
    }
    try
    {
      BPWCalendarInfo localBPWCalendarInfo = (BPWCalendarInfo)xP.get(paramString);
      if (localBPWCalendarInfo != null)
      {
        if (!localBPWCalendarInfo.jdField_for)
        {
          FFSDebug.log("SmartCalendar.reset: Calendar is not intialized for FI: " + paramString, 6);
          return;
        }
        xP.remove(paramString);
        FFSDebug.log("SmartCalendar.reset: Calendar is reset successfully for FI: " + paramString, 6);
      }
      else
      {
        FFSDebug.log("SmartCalendar.reset: Calendar is not intialized for FI: " + paramString, 6);
      }
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("SmartCalendar.reset: Failed to reset calendar for for FI: " + paramString, 6);
      FFSDebug.log("Error: " + FFSDebug.stackTrace(localThrowable), 6);
    }
  }
  
  public static void reset()
  {
    xP.clear();
    xO.reset();
    xO = null;
  }
  
  public static int getPayday(int paramInt)
    throws Exception
  {
    String str1 = "SmartCalendar.getPayday(int date) ";
    String str2 = str1 + "Error: This is an obsolete API which uses default FIID 1000.  The new API with FIID as parameter should be used.";
    FFSDebug.log(str2, 0);
    String str3 = "1000";
    return getPayday(str3, paramInt);
  }
  
  public static int getPayday(String paramString, int paramInt)
    throws Exception
  {
    String str = "System";
    return getPayday(paramString, paramInt, str);
  }
  
  public static int getPayday(String paramString1, int paramInt, String paramString2)
    throws Exception
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    localSimpleDateFormat.setLenient(false);
    Date localDate = localSimpleDateFormat.parse(Integer.toString(paramInt));
    int i = getTodayDate();
    if (paramInt < i) {
      paramInt = i;
    }
    if (jdMethod_char(paramString1, paramString2) == true) {
      return paramInt;
    }
    SmartDate[] arrayOfSmartDate = aA(paramString1);
    if (arrayOfSmartDate == null) {
      return paramInt;
    }
    for (int j = 0; j < arrayOfSmartDate.length; j++) {
      if (arrayOfSmartDate[j]._thisDay == paramInt)
      {
        if (arrayOfSmartDate[j]._payDay < i) {
          paramInt = arrayOfSmartDate[j]._sucDay;
        } else {
          return arrayOfSmartDate[j]._payDay;
        }
      }
      else if (arrayOfSmartDate[j]._thisDay > paramInt) {
        return paramInt;
      }
    }
    return paramInt;
  }
  
  public static int getBusinessDay(String paramString, int paramInt, boolean paramBoolean)
    throws Exception
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    localSimpleDateFormat.setLenient(false);
    Date localDate1 = localSimpleDateFormat.parse(Integer.toString(paramInt));
    SmartDate[] arrayOfSmartDate = aA(paramString);
    int i = 0;
    int j = 0;
    if (arrayOfSmartDate != null) {
      while (i < arrayOfSmartDate.length)
      {
        if (arrayOfSmartDate[i]._thisDay == paramInt)
        {
          if (paramBoolean == true) {
            paramInt = arrayOfSmartDate[i]._sucDay;
          } else {
            paramInt = arrayOfSmartDate[i]._preDay;
          }
          j = 1;
          break;
        }
        if (arrayOfSmartDate[i]._thisDay > paramInt) {
          break;
        }
        i++;
      }
    }
    if (j == 0)
    {
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTime(localDate1);
      if (paramBoolean == true) {
        localCalendar.add(5, 1);
      } else {
        localCalendar.add(5, -1);
      }
      Date localDate2 = localCalendar.getTime();
      String str = localSimpleDateFormat.format(localDate2);
      paramInt = Integer.parseInt(str);
      if (arrayOfSmartDate != null) {
        if (paramBoolean == true)
        {
          if ((i < arrayOfSmartDate.length) && (arrayOfSmartDate[i]._thisDay == paramInt) && (arrayOfSmartDate[i]._skipMode != 0)) {
            paramInt = arrayOfSmartDate[i]._sucDay;
          }
        }
        else if ((i > 0) && (arrayOfSmartDate[(i - 1)]._thisDay == paramInt) && (arrayOfSmartDate[(i - 1)]._skipMode != 0)) {
          paramInt = arrayOfSmartDate[(i - 1)]._preDay;
        }
      }
    }
    return paramInt;
  }
  
  private static void a(SmartDate[] paramArrayOfSmartDate)
    throws Exception
  {
    if (paramArrayOfSmartDate == null) {
      return;
    }
    for (int i = 0; i < paramArrayOfSmartDate.length; i++)
    {
      paramArrayOfSmartDate[i]._preDay = paramArrayOfSmartDate[i].computePreDay(paramArrayOfSmartDate[i]._thisDay);
      paramArrayOfSmartDate[i]._sucDay = paramArrayOfSmartDate[i].computeSucDay(paramArrayOfSmartDate[i]._thisDay);
      int j = paramArrayOfSmartDate[i]._preDay;
      int k = i - 1;
      while ((k >= 0) && (paramArrayOfSmartDate[k]._thisDay == j) && (paramArrayOfSmartDate[k]._skipMode != 0))
      {
        j = paramArrayOfSmartDate[k]._preDay;
        k -= 1;
      }
      paramArrayOfSmartDate[i]._preDay = j;
      int m = paramArrayOfSmartDate[i]._sucDay;
      int n = i + 1;
      while ((n < paramArrayOfSmartDate.length) && (paramArrayOfSmartDate[n]._thisDay == m) && (paramArrayOfSmartDate[n]._skipMode != 0))
      {
        m = paramArrayOfSmartDate[n].computeSucDay(paramArrayOfSmartDate[n]._thisDay);
        n += 1;
      }
      paramArrayOfSmartDate[i]._sucDay = m;
      switch (paramArrayOfSmartDate[i]._skipMode)
      {
      case -1: 
        paramArrayOfSmartDate[i]._payDay = j;
        j = paramArrayOfSmartDate[i].computePreDay(paramArrayOfSmartDate[i]._thisDay);
        k = i - 1;
      case 0: 
      case 1: 
        while ((k >= 0) && (paramArrayOfSmartDate[k]._thisDay == j))
        {
          if (paramArrayOfSmartDate[k]._skipMode == 1) {
            throw new Exception("*** SmartCalendar found contradiction on:" + paramArrayOfSmartDate[k]._thisDay);
          }
          if (paramArrayOfSmartDate[k]._skipMode != 0)
          {
            j = paramArrayOfSmartDate[k].computePreDay(paramArrayOfSmartDate[k]._thisDay);
            k -= 1;
            continue;
            paramArrayOfSmartDate[i]._payDay = paramArrayOfSmartDate[i]._thisDay;
            break;
            paramArrayOfSmartDate[i]._payDay = m;
            m = paramArrayOfSmartDate[i].computeSucDay(paramArrayOfSmartDate[i]._thisDay);
            n = i + 1;
            while ((n < paramArrayOfSmartDate.length) && (paramArrayOfSmartDate[n]._thisDay == m))
            {
              if (paramArrayOfSmartDate[n]._skipMode == -1) {
                throw new Exception("*** SmartCalendar found contradiction on:" + paramArrayOfSmartDate[n]._thisDay);
              }
              if (paramArrayOfSmartDate[n]._skipMode == 0) {
                break;
              }
              m = paramArrayOfSmartDate[n].computeSucDay(paramArrayOfSmartDate[n]._thisDay);
              n += 1;
            }
          }
        }
      }
    }
    if (xN) {
      printSmartCalendar();
    }
  }
  
  private static SmartDate[] ay(String paramString)
    throws Exception
  {
    BPWCalendarInfo localBPWCalendarInfo = ax(paramString);
    return localBPWCalendarInfo.jdField_if;
  }
  
  private static BPWCalendarInfo az(String paramString)
    throws Exception
  {
    BPWCalendarInfo localBPWCalendarInfo = new BPWCalendarInfo();
    int i = getCutOffDate();
    FFSDebug.log("cutOffDate: " + i, 0);
    if ((paramString == null) || (paramString.trim().length() < 1)) {
      paramString = "BPWCalendar.1000.properties";
    }
    Vector localVector = new Vector();
    InputStream localInputStream = null;
    BufferedReader localBufferedReader = null;
    try
    {
      ClassLoader localClassLoader = SmartCalendar.class.getClassLoader();
      if (localClassLoader == null) {
        localClassLoader = ClassLoader.getSystemClassLoader();
      }
      localInputStream = localClassLoader.getResourceAsStream(paramString);
      if (localInputStream == null)
      {
        FFSDebug.log("SmartCalendar.createBPWCalendarFromFile: File: " + paramString + " not found", 0);
        localObject1 = localBPWCalendarInfo;
        return localObject1;
      }
      localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));
      int k = 0;
      while (localBufferedReader.ready())
      {
        localObject1 = localBufferedReader.readLine();
        if (xN) {
          System.out.println("aline:" + (String)localObject1);
        }
        if ((localObject1 != null) && (!((String)localObject1).equals("")) && (!((String)localObject1).startsWith(" ")) && (!((String)localObject1).startsWith("#")))
        {
          int j;
          SmartDate localSmartDate;
          if (((String)localObject1).startsWith("="))
          {
            j = Integer.parseInt(((String)localObject1).substring(1, 9));
            localSmartDate = new SmartDate(j, 0);
            if (j >= i) {
              localVector.addElement(localSmartDate);
            }
          }
          else if (((String)localObject1).startsWith("-"))
          {
            j = Integer.parseInt(((String)localObject1).substring(1, 9));
            localSmartDate = new SmartDate(j, -1);
            if (j >= i) {
              localVector.addElement(localSmartDate);
            }
          }
          else if (((String)localObject1).startsWith("+"))
          {
            j = Integer.parseInt(((String)localObject1).substring(1, 9));
            localSmartDate = new SmartDate(j, 1);
            if (j >= i) {
              localVector.addElement(localSmartDate);
            }
          }
          else
          {
            if ((((String)localObject1).startsWith("bpw.intratransfer.ignore.smartcalendar =") == true) || (((String)localObject1).startsWith("bpw.intratransfer.ignore.smartcalendar=") == true))
            {
              int m = ((String)localObject1).indexOf("=", "bpw.intratransfer.ignore.smartcalendar".length());
              m += 1;
              while ((m < ((String)localObject1).length()) && (((String)localObject1).charAt(m) == ' ')) {
                m++;
              }
              int n = ((String)localObject1).indexOf(" ", m + 1);
              if (n == -1) {
                n = ((String)localObject1).length();
              }
              String str = ((String)localObject1).substring(m, n);
              if (str.equalsIgnoreCase("true") == true)
              {
                localBPWCalendarInfo.jdField_do = true;
                continue;
              }
              localBPWCalendarInfo.jdField_do = false;
              continue;
            }
            throw new Exception("*** createBPWCalendarFromFile error:  Unknown mode " + (String)localObject1);
          }
          if (j < k) {
            throw new Exception("*** createBPWCalendarFromFile error:  Smart Calendar is not sorted at" + j);
          }
          k = j;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("SmartCalendar.createBPWCalendarFromFile: File: " + paramString + " failed", " ERROR: ", FFSDebug.stackTrace(localThrowable), 0);
      Object localObject1 = localBPWCalendarInfo;
      return localObject1;
    }
    finally
    {
      if (localBufferedReader != null) {
        localBufferedReader.close();
      }
      if (localBufferedReader != null) {
        localInputStream.close();
      }
    }
    SmartDate[] arrayOfSmartDate = (SmartDate[])localVector.toArray(new SmartDate[0]);
    localBPWCalendarInfo.jdField_if = arrayOfSmartDate;
    return localBPWCalendarInfo;
  }
  
  public static void addSCCalendar(SmartCalendarFile paramSmartCalendarFile)
    throws Exception
  {
    String str1 = paramSmartCalendarFile.getFiId();
    String str2 = null;
    try
    {
      SCCalendar localSCCalendar1 = a(paramSmartCalendarFile);
      BankIdentifier localBankIdentifier = new BankIdentifier(Locale.getDefault());
      FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
      try
      {
        localFFSConnectionHolder.conn = DBUtil.getConnection();
        if (localFFSConnectionHolder.conn == null)
        {
          localObject1 = "Failed to obtain a connection from from the connection pool";
          FFSDebug.log((String)localObject1);
          throw new Exception((String)localObject1);
        }
        Object localObject1 = BPWFI.getBPWFIInfo(localFFSConnectionHolder, str1);
        if (((BPWFIInfo)localObject1).getStatusCode() == 16020)
        {
          str3 = "No financial institution record was found for fiID = " + str1;
          FFSDebug.log(str3);
          throw new Exception(str3);
        }
        if (((BPWFIInfo)localObject1).getStatusCode() == 16010)
        {
          str3 = "Cannot add smart calendar, the fiID is null.";
          FFSDebug.log(str3);
          throw new Exception(str3);
        }
        localBankIdentifier.setBankDirectoryId(((BPWFIInfo)localObject1).getFIRTN());
        str2 = q(localFFSConnectionHolder, str1);
      }
      catch (BPWException localBPWException)
      {
        localFFSConnectionHolder.conn.rollback();
        String str3 = "An error occurred while obtaining a connection from the BPTW database connection pool. ";
        FFSDebug.throwing(str3, localBPWException);
        throw new Exception(str3 + localBPWException.getStackTrace().toString());
      }
      finally
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      SCCalendar localSCCalendar2 = xO.getCalendarForBank(str2, localBankIdentifier, null);
      xO.addCalendar(str2, localSCCalendar1, null);
      xO.setCalendarForBank(str2, localBankIdentifier, localSCCalendar1, null);
      if (localSCCalendar2 != null) {
        xO.deleteCalendar(localSCCalendar2, null);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("SmartCalendar.addSCCalendar for smart calendar with FIID = " + str1 + " failed", " ERROR: ", FFSDebug.stackTrace(localException), 0);
      throw localException;
    }
  }
  
  private static SCCalendar a(SmartCalendarFile paramSmartCalendarFile)
    throws Exception
  {
    Locale localLocale = Locale.getDefault();
    SCCalendar localSCCalendar = new SCCalendar(localLocale);
    localSCCalendar.setCalendarName(BPWTrackingIDGenerator.getNextId());
    SCBusinessDays localSCBusinessDays = new SCBusinessDays(localLocale);
    localSCBusinessDays.setBusinessDays("YYYYYYY");
    localSCBusinessDays.setActions("+++++++");
    localSCCalendar.setBusinessDays(localSCBusinessDays);
    HashMap localHashMap = new HashMap();
    try
    {
      BPWCalendarInfo localBPWCalendarInfo = jdMethod_if(paramSmartCalendarFile);
      SmartDate[] arrayOfSmartDate = localBPWCalendarInfo.jdField_if;
      for (int i = 0; i < arrayOfSmartDate.length; i++) {
        if (arrayOfSmartDate[i]._skipMode != 0)
        {
          DateTime localDateTime;
          SCHoliday localSCHoliday;
          if (arrayOfSmartDate[i]._skipMode == -1)
          {
            localDateTime = new DateTime(jdMethod_try(arrayOfSmartDate[i]._thisDay), localLocale);
            localSCHoliday = new SCHoliday(localLocale);
            localSCHoliday.setDate(localDateTime);
            localSCHoliday.setAction('-');
            localHashMap.put(localDateTime, localSCHoliday);
          }
          else if (arrayOfSmartDate[i]._skipMode == 1)
          {
            localDateTime = new DateTime(jdMethod_try(arrayOfSmartDate[i]._thisDay), localLocale);
            localSCHoliday = new SCHoliday(localLocale);
            localSCHoliday.setDate(localDateTime);
            localSCHoliday.setAction('+');
            localHashMap.put(localDateTime, localSCHoliday);
          }
        }
      }
      localSCCalendar.setHolidays(localHashMap);
      localSCCalendar.setIgnoreForTransfersValue(localBPWCalendarInfo.jdField_do);
    }
    catch (Exception localException)
    {
      FFSDebug.log("SmartCalendar.createSCCalendarFromSmartCalendarProperties failed for smart calendar with FIID = " + paramSmartCalendarFile.getFiId(), " ERROR: ", FFSDebug.stackTrace(localException), 0);
      return localSCCalendar;
    }
    return localSCCalendar;
  }
  
  private static BPWCalendarInfo jdMethod_if(SmartCalendarFile paramSmartCalendarFile)
    throws Exception
  {
    BPWCalendarInfo localBPWCalendarInfo = new BPWCalendarInfo();
    int i = getCutOffDate();
    FFSDebug.log("cutOffDate: " + i, 0);
    String str1 = paramSmartCalendarFile.getSmartCalendarStr();
    if ((str1 == null) || (str1.trim().length() == 0))
    {
      FFSDebug.log("Smart calendar definition is null or empty, returning an empty BPWCalendarInfo.", 0);
      return localBPWCalendarInfo;
    }
    Vector localVector = new Vector();
    try
    {
      int k = 0;
      String str2 = null;
      StringTokenizer localStringTokenizer = new StringTokenizer(str1, DBConsts.LINE_SEPARATOR);
      while (localStringTokenizer.hasMoreTokens())
      {
        str2 = localStringTokenizer.nextToken();
        if (xN) {
          System.out.println("line:" + str2);
        }
        if ((str2 != null) && (!str2.equals("")) && (!str2.startsWith(" ")) && (!str2.startsWith("#")))
        {
          int j;
          SmartDate localSmartDate;
          if (str2.startsWith("="))
          {
            j = Integer.parseInt(str2.substring(1, 9));
            localSmartDate = new SmartDate(j, 0);
            if (j >= i) {
              localVector.addElement(localSmartDate);
            }
          }
          else if (str2.startsWith("-"))
          {
            j = Integer.parseInt(str2.substring(1, 9));
            localSmartDate = new SmartDate(j, -1);
            if (j >= i) {
              localVector.addElement(localSmartDate);
            }
          }
          else if (str2.startsWith("+"))
          {
            j = Integer.parseInt(str2.substring(1, 9));
            localSmartDate = new SmartDate(j, 1);
            if (j >= i) {
              localVector.addElement(localSmartDate);
            }
          }
          else
          {
            if ((str2.startsWith("bpw.intratransfer.ignore.smartcalendar =") == true) || (str2.startsWith("bpw.intratransfer.ignore.smartcalendar=") == true))
            {
              int m = str2.indexOf("=", "bpw.intratransfer.ignore.smartcalendar".length());
              m += 1;
              while ((m < str2.length()) && (str2.charAt(m) == ' ')) {
                m++;
              }
              int n = str2.indexOf(" ", m + 1);
              if (n == -1) {
                n = str2.length();
              }
              String str3 = str2.substring(m, n);
              if (str3.equalsIgnoreCase("true") == true)
              {
                localBPWCalendarInfo.jdField_do = true;
                continue;
              }
              localBPWCalendarInfo.jdField_do = false;
              continue;
            }
            throw new Exception("*** createBPWCalendarFromFileContent error:  Unknown mode " + str2);
          }
          if (j < k) {
            throw new Exception("*** createBPWCalendarFromFileContent error:  Smart Calendar is not sorted at" + j);
          }
          k = j;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("SmartCalendar.createBPWCalendarFromFileContent failed.  File content=" + str1, " ERROR: ", FFSDebug.stackTrace(localThrowable), 0);
      return localBPWCalendarInfo;
    }
    SmartDate[] arrayOfSmartDate = (SmartDate[])localVector.toArray(new SmartDate[0]);
    localBPWCalendarInfo.jdField_if = arrayOfSmartDate;
    return localBPWCalendarInfo;
  }
  
  private static Calendar jdMethod_try(int paramInt)
  {
    int i = paramInt;
    int j = i / 10000;
    i %= 10000;
    int k = i / 100 - 1;
    i %= 100;
    int m = i;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(1, j);
    localCalendar.set(2, k);
    localCalendar.set(5, m);
    return localCalendar;
  }
  
  public static SmartCalendarFile getSCFileFromSCDB(SmartCalendarFile paramSmartCalendarFile)
    throws Exception
  {
    String str = paramSmartCalendarFile.getFiId();
    try
    {
      BPWCalendarInfo localBPWCalendarInfo = ax(str);
      paramSmartCalendarFile = a(localBPWCalendarInfo);
      str = paramSmartCalendarFile.getFiId();
      FFSDebug.log("SmartCalendar.getSCFileFromSCDB: SmartClendarFile content: FIID =  " + str, 6);
      FFSDebug.log("SmartCalendar.getSCFileFromSCDB: SmartClendarFile content: SmartCalendarStr =  " + paramSmartCalendarFile.getSmartCalendarStr(), 6);
    }
    catch (Exception localException)
    {
      FFSDebug.log("SmartCalendar.getSCFileFromSCDB for smart calendar with FIID = " + str + " failed", " ERROR: ", FFSDebug.stackTrace(localException), 0);
      throw localException;
    }
    return paramSmartCalendarFile;
  }
  
  private static SmartCalendarFile a(BPWCalendarInfo paramBPWCalendarInfo)
    throws Exception
  {
    SmartCalendarFile localSmartCalendarFile = new SmartCalendarFile();
    localSmartCalendarFile.setFiId(paramBPWCalendarInfo.a);
    localSmartCalendarFile.setSmartCalendarStr("");
    if (paramBPWCalendarInfo.jdField_do == true) {
      localSmartCalendarFile.setSmartCalendarStr(localSmartCalendarFile.getSmartCalendarStr() + "bpw.intratransfer.ignore.smartcalendar" + "=true" + DBConsts.LINE_SEPARATOR);
    }
    SmartDate[] arrayOfSmartDate = paramBPWCalendarInfo.jdField_if;
    if (arrayOfSmartDate != null) {
      for (int i = 0; i < arrayOfSmartDate.length; i++) {
        if (arrayOfSmartDate[i]._skipMode == -1) {
          localSmartCalendarFile.setSmartCalendarStr(localSmartCalendarFile.getSmartCalendarStr() + "-" + arrayOfSmartDate[i]._thisDay + DBConsts.LINE_SEPARATOR);
        } else if (arrayOfSmartDate[i]._skipMode == 1) {
          localSmartCalendarFile.setSmartCalendarStr(localSmartCalendarFile.getSmartCalendarStr() + "+" + arrayOfSmartDate[i]._thisDay + DBConsts.LINE_SEPARATOR);
        }
      }
    }
    return localSmartCalendarFile;
  }
  
  private static BPWCalendarInfo ax(String paramString)
    throws Exception
  {
    BPWCalendarInfo localBPWCalendarInfo = new BPWCalendarInfo();
    localBPWCalendarInfo.a = paramString;
    if (xO == null)
    {
      xO = new BPWSCAdapter();
      xO.initialize(null);
    }
    BankIdentifier localBankIdentifier = new BankIdentifier(Locale.getDefault());
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        localObject1 = "Failed to obtain a connection from from the connection pool";
        FFSDebug.log((String)localObject1);
        throw new Exception((String)localObject1);
      }
      str = q(localFFSConnectionHolder, paramString);
      Object localObject1 = BPWFI.getBPWFIInfo(localFFSConnectionHolder, paramString);
      if (((BPWFIInfo)localObject1).getStatusCode() == 16020)
      {
        localObject2 = "No financial institution record was found for fiID = " + paramString;
        FFSDebug.log((String)localObject2);
        throw new Exception((String)localObject2);
      }
      if (((BPWFIInfo)localObject1).getStatusCode() == 16010)
      {
        localObject2 = "Cannot create smart calendar, the fiID is null.";
        FFSDebug.log((String)localObject2);
        throw new Exception((String)localObject2);
      }
      localBankIdentifier.setBankDirectoryId(((BPWFIInfo)localObject1).getFIRTN());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject2 = "An error occurred while obtaining a connection from the BPTW database connection pool. ";
      FFSDebug.throwing((String)localObject2, localBPWException);
      throw new Exception((String)localObject2 + localBPWException.getStackTrace().toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("cutOffDate: " + getCutOffDate(), 0);
    Calendar localCalendar1 = getCutOffDateCalendar();
    Object localObject2 = localCalendar1.getTime();
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar2.add(1, 1);
    Date localDate1 = localCalendar2.getTime();
    Vector localVector = new Vector();
    int i = 0;
    try
    {
      while (i == 0)
      {
        Date localDate2 = xO.getTransactionDay(str, localBankIdentifier, (Date)localObject2, null);
        int j = localCalendar1.get(1) * 10000 + (localCalendar1.get(2) + 1) * 100 + localCalendar1.get(5);
        if (localDate2.compareTo((Date)localObject2) != 0)
        {
          SmartDate localSmartDate;
          if (localDate2.before((Date)localObject2))
          {
            localSmartDate = new SmartDate(j, -1);
            localVector.addElement(localSmartDate);
          }
          else
          {
            localSmartDate = new SmartDate(j, 1);
            localVector.addElement(localSmartDate);
          }
        }
        localCalendar1.add(5, 1);
        localObject2 = localCalendar1.getTime();
        if (((Date)localObject2).after(localDate1)) {
          i = 1;
        }
      }
      localBPWCalendarInfo.jdField_do = xO.getIgnoreForTransfers(str, localBankIdentifier, null);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("SmartCalendar.createBPWCalendar: FIID: " + paramString + " failed", " ERROR: ", FFSDebug.stackTrace(localThrowable), 0);
      return localBPWCalendarInfo;
    }
    SmartDate[] arrayOfSmartDate = (SmartDate[])localVector.toArray(new SmartDate[0]);
    localBPWCalendarInfo.jdField_if = arrayOfSmartDate;
    return localBPWCalendarInfo;
  }
  
  private static String q(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
  {
    String str1 = null;
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str2 = localPropertyConfig.otherProperties.getProperty("bpw.server.standalone", "N");
    if (str2.equalsIgnoreCase("N")) {
      str1 = BankUtil.getServiceBureauID(paramFFSConnectionHolder.conn.getConnection(), paramString);
    } else {
      str1 = paramString;
    }
    return str1;
  }
  
  public static void printSmartCalendar()
  {
    Set localSet = xP.keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      printSmartCalendar(str);
    }
  }
  
  public static void printSmartCalendar(String paramString)
  {
    BPWCalendarInfo localBPWCalendarInfo = (BPWCalendarInfo)xP.get(paramString);
    if (localBPWCalendarInfo != null)
    {
      FFSDebug.log("\n\n========================================================");
      FFSDebug.log("\t\tBPW Calendar for FI: " + localBPWCalendarInfo.a);
      FFSDebug.log("========================================================\n\n");
      if (localBPWCalendarInfo.jdField_do == true) {
        FFSDebug.log("Configured such that Intratransfers will not use the SmartCalendar dates.");
      }
      SmartDate[] arrayOfSmartDate = aA(localBPWCalendarInfo.a);
      if (arrayOfSmartDate == null)
      {
        FFSDebug.log("*** empty SmartCalendar!");
        return;
      }
      for (int i = 0; i < arrayOfSmartDate.length; i++)
      {
        String str = "=";
        if (arrayOfSmartDate[i]._skipMode == -1) {
          str = "-";
        } else if (arrayOfSmartDate[i]._skipMode == 0) {
          str = "=";
        } else if (arrayOfSmartDate[i]._skipMode == 1) {
          str = "+";
        } else {
          str = "?";
        }
        FFSDebug.log("SmartDate[" + i + "]=" + arrayOfSmartDate[i]._thisDay + "," + str + "(" + arrayOfSmartDate[i]._preDay + "," + arrayOfSmartDate[i]._sucDay + ")," + arrayOfSmartDate[i]._payDay);
      }
    }
  }
  
  public static int getTodayDate()
    throws Exception
  {
    DateFormat localDateFormat = DateFormat.getDateInstance(2);
    String str = localDateFormat.format(new Date());
    Date localDate = localDateFormat.parse(str);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    int i = localCalendar.get(1) * 10000 + (localCalendar.get(2) + 1) * 100 + localCalendar.get(5);
    return i;
  }
  
  public static int getCutOffDate()
    throws Exception
  {
    DateFormat localDateFormat = DateFormat.getDateInstance(2);
    String str = localDateFormat.format(new Date());
    Date localDate = localDateFormat.parse(str);
    Calendar localCalendar = getCutOffDateCalendar();
    int i = localCalendar.get(1) * 10000 + (localCalendar.get(2) + 1) * 100 + localCalendar.get(5);
    return i;
  }
  
  public static Calendar getCutOffDateCalendar()
    throws Exception
  {
    DateFormat localDateFormat = DateFormat.getDateInstance(2);
    String str = localDateFormat.format(new Date());
    Date localDate = localDateFormat.parse(str);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    localCalendar.add(2, -1);
    return localCalendar;
  }
  
  public static void setDebug(boolean paramBoolean)
  {
    xN = paramBoolean;
  }
  
  private static SmartDate[] aA(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() < 1)) {
      paramString = "1000";
    }
    paramString = paramString.trim();
    try
    {
      BPWCalendarInfo localBPWCalendarInfo = (BPWCalendarInfo)xP.get(paramString);
      if ((localBPWCalendarInfo != null) && (localBPWCalendarInfo.jdField_for))
      {
        FFSDebug.log("SmartCalendar.getSmartDate: is successful for FI: " + paramString, 6);
        return localBPWCalendarInfo.jdField_if;
      }
      FFSDebug.log("SmartCalendar.getSmartDate: is not initialized for FI: " + paramString, 6);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("SmartCalendar.getSmartDate:  failed for for FI: " + paramString, 6);
      FFSDebug.log("Error: " + FFSDebug.stackTrace(localThrowable), 6);
    }
    return null;
  }
  
  public static SmartDate[] getNonBusinessDays(String paramString)
  {
    return aA(paramString);
  }
  
  public static SmartDate[] getNonBusinessDaysFromFile(String paramString)
  {
    String str = null;
    try
    {
      if (paramString != null) {
        str = "BPWCalendar." + paramString + ".properties";
      }
      SmartDate[] arrayOfSmartDate = ay(paramString);
      a(arrayOfSmartDate);
      return arrayOfSmartDate;
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("SmartCalendar.getSmartDate:  failed for for FI: " + paramString, 6);
      FFSDebug.log("Error: " + FFSDebug.stackTrace(localThrowable), 6);
    }
    return null;
  }
  
  private static boolean jdMethod_char(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.trim().length() < 1)) {
      paramString1 = "1000";
    }
    paramString1 = paramString1.trim();
    BPWCalendarInfo localBPWCalendarInfo = (BPWCalendarInfo)xP.get(paramString1);
    return (localBPWCalendarInfo != null) && (paramString2 != null) && (paramString2.equals("BookTransfer") == true) && (localBPWCalendarInfo.jdField_do == true);
  }
  
  public static boolean isTodayHoliday(String paramString)
    throws Exception
  {
    if (isTodayNonbusinessDay(paramString) == true) {
      return isTodayWeekEnd() != true;
    }
    return false;
  }
  
  public static boolean isTodayWeekEnd()
  {
    Calendar localCalendar = Calendar.getInstance();
    return (localCalendar.get(7) == 7) || (localCalendar.get(7) == 1);
  }
  
  public static boolean isTodayNonbusinessDay(String paramString)
    throws Exception
  {
    int i = getTodayDate();
    int j = getPayday(paramString, i);
    return j != i;
  }
  
  public static boolean isTodayNonACHBusinessDay(String paramString)
    throws Exception
  {
    int i = getTodayDate();
    int j = getACHPayday(paramString, i);
    return j != i;
  }
  
  public static int getNonWeekend(int paramInt, boolean paramBoolean)
  {
    Calendar localCalendar = Calendar.getInstance();
    int i = 0;
    int j = 10000;
    int k = 100;
    int m = paramInt / j;
    int n = paramInt % j / k;
    int i1 = paramInt % k;
    int i2 = convertNumberToCalendarMonth(n);
    localCalendar.set(m, i2, i1, 0, 0, 0);
    localCalendar.setTime(localCalendar.getTime());
    int i3 = localCalendar.get(7);
    switch (i3)
    {
    case 7: 
      if (paramBoolean) {
        localCalendar.add(5, 2);
      } else {
        localCalendar.add(5, -1);
      }
      i = 1;
      break;
    case 1: 
      if (paramBoolean) {
        localCalendar.add(5, 1);
      } else {
        localCalendar.add(5, -2);
      }
      i = 1;
      break;
    default: 
      i = 0;
    }
    if (i != 0)
    {
      m = localCalendar.get(1);
      n = convertCalendarMonthToNumber(localCalendar.get(2));
      i1 = localCalendar.get(5);
      paramInt = m * j + n * k + i1;
    }
    return paramInt;
  }
  
  public static int getACHPayday(String paramString, int paramInt)
    throws Exception
  {
    int i = 0;
    if (isBusinessDay(paramString, paramInt) == true)
    {
      i = getNonWeekend(paramInt, true);
      if (i == paramInt) {
        return paramInt;
      }
    }
    else
    {
      paramInt = getBusinessDay(paramString, paramInt, true);
      i = getNonWeekend(paramInt, true);
      if (i == paramInt) {
        return paramInt;
      }
    }
    while (!isBusinessDay(paramString, i))
    {
      paramInt = getBusinessDay(paramString, i, true);
      i = getNonWeekend(paramInt, true);
      if (i == paramInt) {
        return paramInt;
      }
    }
    return i;
  }
  
  public static int getACHPayDayOfToday(String paramString)
    throws Exception
  {
    int i = Integer.parseInt(FFSUtil.getDateString("yyyyMMdd"));
    return getACHPayday(paramString, i);
  }
  
  public static boolean isBusinessDay(String paramString, int paramInt)
    throws Exception
  {
    int i = getPayday(paramString, paramInt);
    return i == paramInt;
  }
  
  public static boolean isBusinessDay(String paramString1, int paramInt, String paramString2)
    throws Exception
  {
    int i = getPayday(paramString1, paramInt, paramString2);
    return i == paramInt;
  }
  
  public static int getACHBusinessDay(String paramString, int paramInt, boolean paramBoolean)
    throws Exception
  {
    int i = getBusinessDay(paramString, paramInt, paramBoolean);
    paramInt = getNonWeekend(i, paramBoolean);
    if (paramInt != i) {
      if (isBusinessDay(paramString, paramInt) == true) {
        i = paramInt;
      } else {
        i = getACHBusinessDay(paramString, paramInt, paramBoolean);
      }
    }
    return i;
  }
  
  public static boolean isACHPayDay(String paramString1, String paramString2)
    throws Exception
  {
    String str = FFSUtil.convertDateFormat("yyyy/MM/dd HH:mm", "yyyyMMdd", paramString2);
    int i = Integer.parseInt(str);
    int j = getACHPayday(paramString1, i);
    return j == i;
  }
  
  public static int convertNumberToCalendarMonth(int paramInt)
  {
    switch (paramInt)
    {
    case 1: 
      return 0;
    case 2: 
      return 1;
    case 3: 
      return 2;
    case 4: 
      return 3;
    case 5: 
      return 4;
    case 6: 
      return 5;
    case 7: 
      return 6;
    case 8: 
      return 7;
    case 9: 
      return 8;
    case 10: 
      return 9;
    case 11: 
      return 10;
    }
    return 11;
  }
  
  public static int convertCalendarMonthToNumber(int paramInt)
  {
    switch (paramInt)
    {
    case 0: 
      return 1;
    case 1: 
      return 2;
    case 2: 
      return 3;
    case 3: 
      return 4;
    case 4: 
      return 5;
    case 5: 
      return 6;
    case 6: 
      return 7;
    case 7: 
      return 8;
    case 8: 
      return 9;
    case 9: 
      return 10;
    case 10: 
      return 11;
    }
    return 12;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.SmartCalendar
 * JD-Core Version:    0.7.0.1
 */