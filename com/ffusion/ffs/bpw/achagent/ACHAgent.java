package com.ffusion.ffs.bpw.achagent;

import com.ffusion.ffs.bpw.custimpl.MacGenerator;
import com.ffusion.ffs.bpw.interfaces.ACHAddendaInfo;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.ACHFileInfo;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.bpw.util.ACHAdapterUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.ACHBatchCache;
import com.ffusion.ffs.util.ACHFileCache;
import com.ffusion.ffs.util.FFSCache;
import com.ffusion.ffs.util.FFSCacheManager;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSStringTokenizer;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.factory.MBInstanceFactory;
import com.ffusion.msgbroker.factory.MBMessageFactory;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeADVBatchControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeBatchControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeFileControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.ValueSetENum_ServiceClassCode;
import com.ffusion.msgbroker.interfaces.IBuilder;
import com.ffusion.msgbroker.interfaces.IMBInstance;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.msgbroker.interfaces.IParser;
import com.ffusion.msgbroker.interfaces.MBException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

public class ACHAgent
  implements FFSConst, ACHConsts
{
  private static boolean zg = false;
  private static IMBInstance zq = null;
  private static IBuilder[] zD = null;
  private static int zx = 0;
  private static FFSCacheManager zt = null;
  private static IParser[] zm = null;
  private static int zj = 0;
  private static int zo = 0;
  private static Properties zi = null;
  private static Properties zB = null;
  private static Properties zk = null;
  private static final String zn = "ACHAgentResource.properties";
  private static final String zv = "ErrorMessageFieldName";
  private static final String zw = "ErrorMessage";
  private static final int zz = 100;
  private static final String zf = "MsgSet";
  private static final int zr = 94;
  private static final String zh = "PPD";
  private static final String REC_DELIMS = "\r\n";
  private static final String BLOCK_FILLER = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999";
  private static String zl = "000000000000001";
  private static String zy = "an atomic field \"";
  private static String zC = "an union field \"";
  private static String zA = "an composite field \"";
  private static String zu = "Unable to parse the entire message.";
  private static String zp = "Validation failed on \"";
  private static String zs = "Remaining unparsed data:";
  
  public static void start(Hashtable paramHashtable)
    throws FFSException
  {
    if (zg == true)
    {
      FFSDebug.log("ACHAgent is already started!", 6);
      return;
    }
    at();
    System.out.println("Creating MB instance...");
    if (zq != null)
    {
      FFSDebug.log("MB was already initialized.", 2);
      return;
    }
    start(createMBInstance(paramHashtable), paramHashtable);
    zg = true;
  }
  
  public static void start(IMBInstance paramIMBInstance, Hashtable paramHashtable)
    throws FFSException
  {
    if (zg == true)
    {
      FFSDebug.log("ACHAgent is already started!", 6);
      return;
    }
    at();
    a(paramIMBInstance, paramHashtable);
    zg = true;
  }
  
  public static void shutdown()
    throws FFSException
  {
    zg = false;
    zq = null;
    int i;
    if (zD != null) {
      for (i = 0; i < zD.length; i++) {
        zD[i] = null;
      }
    }
    if (zm != null) {
      for (i = 0; i < zm.length; i++) {
        zm[i] = null;
      }
    }
  }
  
  private static void a(IMBInstance paramIMBInstance, Hashtable paramHashtable)
    throws FFSException
  {
    String str1 = null;
    String str2 = null;
    FFSDebug.log("Initializing Message Broker...", 2);
    if (zq != null)
    {
      FFSDebug.log("MB was already initialized.", 2);
      return;
    }
    if (paramIMBInstance == null)
    {
      FFSDebug.log("Invalid Parameter : imbInstance Null", 0);
      throw new FFSException("Invalid Parameter : " + paramIMBInstance);
    }
    if (paramHashtable == null)
    {
      FFSDebug.log("Invalid Parameter : Null", 0);
      throw new FFSException("Invalid Parameter : " + paramHashtable);
    }
    zq = paramIMBInstance;
    zo = 100;
    str1 = (String)paramHashtable.get("POOLSIZE");
    if (str1 != null) {
      try
      {
        zo = Integer.parseInt(str1);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        FFSDebug.log("Invalid Parser & Builder Pool size :", str1, 0);
        throw new FFSException("Invalid Parser & Builder Pool size :" + str1);
      }
    }
    zD = new IBuilder[zo];
    zm = new IParser[zo];
    str2 = (String)paramHashtable.get("ACHVERSION");
    if (str2 == null) {
      str2 = "MsgSet";
    }
    try
    {
      aJ("ACH" + str2);
    }
    catch (Exception localException1)
    {
      FFSDebug.log("Unable to load message set: ACH" + str2);
    }
    String str3 = (String)paramHashtable.get("RPPSMSGSET");
    if (str3 != null) {
      try
      {
        aJ(str3);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("Unable to load message set: " + str3);
      }
    }
    FFSDebug.log("Message Broker intialized successfully.", 2);
    zt = new FFSCacheManager();
  }
  
  public static IMBInstance createMBInstance(Hashtable paramHashtable)
    throws FFSException
  {
    boolean bool1 = true;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    IMBInstance localIMBInstance = null;
    FFSDebug.log("Initializing Message Broker...", 2);
    if (paramHashtable == null)
    {
      FFSDebug.log("Invalid Parameter : Null", 0);
      throw new FFSException("Invalid Parameter : " + paramHashtable);
    }
    bool1 = true;
    str1 = (String)paramHashtable.get("DBType");
    str2 = (String)paramHashtable.get("HOST");
    str3 = (String)paramHashtable.get("PORT");
    str4 = (String)paramHashtable.get("DBName");
    str5 = (String)paramHashtable.get("DBUser");
    str6 = (String)paramHashtable.get("DBPwd");
    str7 = (String)paramHashtable.get("URL");
    if (str1 == null)
    {
      FFSDebug.log("DB Type is missing.", 0);
      throw new FFSException("DB Type is missing.");
    }
    str1 = str1.toUpperCase();
    if (str5 == null)
    {
      FFSDebug.log("User name is missing.", 0);
      throw new FFSException("User name is missing.");
    }
    if (str6 == null)
    {
      FFSDebug.log("Password is missing.", 0);
      throw new FFSException("Password is missing.");
    }
    if ((str7 != null) && (str7.trim().length() > 0)) {
      try
      {
        str7 = str7.trim();
        localIMBInstance = MBInstanceFactory.createInstanceFromURL(str5, str6, str7);
        return localIMBInstance;
      }
      catch (Exception localException1)
      {
        String str9 = FFSDebug.stackTrace(localException1);
        FFSDebug.log(str9, 0);
        throw new FFSException(localException1, jdMethod_char(23010));
      }
    }
    if (str2 == null)
    {
      FFSDebug.log("Host is missing.", 0);
      throw new FFSException("Host is missing.");
    }
    if (str3 == null)
    {
      FFSDebug.log("Port is missing.", 0);
      throw new FFSException("Port is missing.");
    }
    if (str4 == null)
    {
      FFSDebug.log("DB Name is missing.", 0);
      throw new FFSException("DB Name is missing.");
    }
    try
    {
      if ((str1 != null) && (str1.startsWith("ORACLE")))
      {
        if (str1.equalsIgnoreCase("ORACLE:oci8")) {
          bool1 = false;
        }
        localIMBInstance = MBInstanceFactory.createInstanceFromOracle(str5, str6, str2, str3, str4, bool1);
      }
      else if ((str1 != null) && (str1.startsWith("AS")))
      {
        localIMBInstance = MBInstanceFactory.createInstanceFromJConnect(str5, str6, str2, str3, str4);
      }
      else if ((str1 != null) && (str1.equalsIgnoreCase("DB2:un2")))
      {
        localIMBInstance = MBInstanceFactory.createInstanceFromDB2UniversalDriver(str5, str6, str7);
      }
      else if ((str1 != null) && (str1.startsWith("DB2")))
      {
        String str8 = str1;
        if (str8 != null) {
          str8 = str8.trim();
        }
        boolean bool2 = false;
        if ("DB2:app".equalsIgnoreCase(str8)) {
          bool2 = true;
        }
        localIMBInstance = MBInstanceFactory.createInstanceFromDB2(str5, str6, str2, str3, str4, bool2);
      }
      else if ((str1 != null) && (str1.startsWith("MSSQL")))
      {
        localIMBInstance = MBInstanceFactory.createInstanceFromMSSQL(str5, str6, str2, str3, str4);
      }
      else
      {
        throw new FFSException("Database type not supported by Message Broker!");
      }
    }
    catch (Exception localException2)
    {
      String str10 = FFSDebug.stackTrace(localException2);
      FFSDebug.log(str10, 0);
      throw new FFSException(localException2, jdMethod_char(23010));
    }
    return localIMBInstance;
  }
  
  private static IParser as()
  {
    IParser localIParser = null;
    synchronized (zm)
    {
      if (zj > 0)
      {
        localIParser = zm[(--zj)];
        zm[zj] = null;
      }
    }
    return localIParser;
  }
  
  private static void a(IParser paramIParser)
  {
    synchronized (zm)
    {
      if (zj < zo) {
        zm[(zj++)] = paramIParser;
      }
    }
  }
  
  private static IBuilder au()
  {
    IBuilder localIBuilder = null;
    synchronized (zD)
    {
      if (zx > 0)
      {
        localIBuilder = zD[(--zx)];
        zD[zx] = null;
      }
    }
    return localIBuilder;
  }
  
  private static void a(IBuilder paramIBuilder)
  {
    synchronized (zD)
    {
      if (zx < zo) {
        zD[(zx++)] = paramIBuilder;
      }
    }
  }
  
  private static void aJ(String paramString)
    throws FFSException
  {
    try
    {
      if (zq == null)
      {
        FFSDebug.log("MB is not initialized.", 2);
        throw new FFSException("MB is not initialized.");
      }
      FFSDebug.log("Loading Message set : ", paramString, ", please wait ...", 2);
      zq.loadMessageSet(paramString);
    }
    catch (Exception localException)
    {
      String str = FFSDebug.stackTrace(localException);
      FFSDebug.log("Failed to load Message set : ", paramString, "\nError: ", str, 2);
      throw new FFSException(localException, jdMethod_char(23010));
    }
  }
  
  private static void at()
    throws FFSException
  {
    zi = new Properties();
    zk = new Properties();
    zB = FFSUtil.loadPropsFromClasspath("ACHAgentResource.properties");
    if (zB == null)
    {
      FFSDebug.log("Could not load ACHAgentResource.properties for ACHAgent.", 0);
      zB = new Properties();
    }
    Enumeration localEnumeration = zB.propertyNames();
    String str = null;
    while (localEnumeration.hasMoreElements())
    {
      str = (String)localEnumeration.nextElement();
      if (str.startsWith("ErrorMessageFieldName") == true) {
        zk.setProperty(str, (String)zB.remove(str));
      } else if (str.startsWith("ErrorMessage") == true) {
        zi.setProperty(str, (String)zB.remove(str));
      }
    }
  }
  
  private static String jdMethod_char(int paramInt)
  {
    String str = "ErrorMessage" + paramInt;
    return zi.getProperty(str, "Cannot find resource for error code: " + str);
  }
  
  private static IParser av()
    throws FFSException
  {
    ar();
    IParser localIParser = as();
    if (localIParser == null) {
      try
      {
        if (FFSDebug.checkLogLevel(1000000) == true)
        {
          localIParser = zq.createDebugParser();
          FFSDebug.log("Message Broker Debug Parser created successfully.", 4);
        }
        else
        {
          localIParser = zq.createParser();
          FFSDebug.log("Message Broker Parser created successfully.", 4);
        }
      }
      catch (MBException localMBException)
      {
        String str = FFSDebug.stackTrace(localMBException);
        FFSDebug.log("Unable to create a Message Broker Parser! :", str, 0);
        throw new FFSException(localMBException, "Unable to create a Message Broker Parser!");
      }
    }
    return localIParser;
  }
  
  private static void ar()
    throws FFSException
  {
    if (zq == null)
    {
      FFSDebug.log("Message Broker was not successfully initialized!", 0);
      throw new FFSException("Message Broker was not successfully initialized!");
    }
  }
  
  private static IBuilder ap()
    throws FFSException
  {
    ar();
    IBuilder localIBuilder = au();
    if (localIBuilder == null) {
      try
      {
        localIBuilder = zq.createBuilder();
        FFSDebug.log("Message Broker Builder created successfully.", 4);
      }
      catch (MBException localMBException)
      {
        String str = FFSDebug.stackTrace(localMBException);
        FFSDebug.log("Unable to create a Message Broker Builder! :", str, 0);
        throw new FFSException(localMBException, "Unable to create a Message Broker Builder!");
      }
    }
    return localIBuilder;
  }
  
  public static void parse(ACHAddendaInfo paramACHAddendaInfo)
    throws FFSException
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    if (paramACHAddendaInfo == null)
    {
      FFSDebug.log("ACHAddendaInfo object is NULL", 0);
      return;
    }
    str3 = paramACHAddendaInfo.getAddendaContent();
    if ((str3 == null) || (str3.length() == 0))
    {
      int i = 22950;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    if (FFSStringTokenizer.hasDelimeter(str3, "\r\n")) {
      str3 = str3.substring(0, 94);
    }
    try
    {
      FFSDebug.log("achAddenda.ClassCode:", paramACHAddendaInfo.getClassCode(), 6);
      str1 = "ACH" + paramACHAddendaInfo.getAchVersion();
      str2 = a(7, paramACHAddendaInfo.getClassCode());
      FFSDebug.log("parse(ACHAddendaInfo ):: msgSetName:", str1, 6);
      FFSDebug.log("parse(ACHAddendaInfo ):: msgName:", str2, 6);
      paramACHAddendaInfo.setAddenda(parsePureRecord(str1, str2, str3));
      FFSDebug.log("Successfully parsed message:", FFSConst.LINE_SEPARATOR, str2, 6);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(a(localFFSException, null), 0);
      if (localFFSException.getErrCode() != -1)
      {
        paramACHAddendaInfo.setStatusCode(localFFSException.getErrCode());
        paramACHAddendaInfo.setStatusMsg(localFFSException.getMessage());
      }
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str4 = FFSDebug.stackTrace(localException);
      int j = 23010;
      paramACHAddendaInfo.setStatusCode(j);
      paramACHAddendaInfo.setStatusMsg(jdMethod_char(j));
      FFSDebug.log(String.valueOf(j) + ": " + jdMethod_char(j), "\n         ", str4, 0);
      throw new FFSException(localException, jdMethod_char(j));
    }
  }
  
  public static void parse(ACHRecordInfo paramACHRecordInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    FFSStringTokenizer localFFSStringTokenizer = null;
    if (paramACHRecordInfo == null)
    {
      FFSDebug.log("ACHRecordInfo object is NULL", 0);
      return;
    }
    str4 = paramACHRecordInfo.getRecordContent();
    int i;
    if ((str4 == null) || (str4.length() == 0))
    {
      i = 22950;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    if (FFSStringTokenizer.hasDelimeter(str4, "\r\n")) {
      localFFSStringTokenizer = new FFSStringTokenizer(str4, "\r\n");
    } else {
      localFFSStringTokenizer = new FFSStringTokenizer(str4, 94);
    }
    str3 = localFFSStringTokenizer.nextToken();
    FFSDebug.log("parse(ACHRecordInfo ):: msg:\n\t", str3, 6);
    try
    {
      if (paramACHRecordInfo.getRecordType() == -1) {
        paramACHRecordInfo.setRecordType(getRecordType(str3));
      }
      FFSDebug.log("achRec.ClassCode:", paramACHRecordInfo.getClassCode(), 6);
      str1 = "ACH" + paramACHRecordInfo.getAchVersion();
      str2 = a(paramACHRecordInfo.getRecordType(), paramACHRecordInfo.getClassCode());
      FFSDebug.log("parse(ACHRecordInfo ):: msgSetName:", str1, 6);
      FFSDebug.log("parse(ACHRecordInfo ):: msgTypeName:", str2, 6);
      paramACHRecordInfo.setRecord(parsePureRecord(str1, str2, str3));
      FFSDebug.log("Successfully parsed message:", FFSConst.LINE_SEPARATOR, str3, 6);
      FFSDebug.log("achRec.recordType:" + paramACHRecordInfo.getRecordType(), 6);
      if (paramACHRecordInfo.getRecordType() == 6)
      {
        i = 0;
        localObject = null;
        FFSDebug.log("Before Process Addenda records", 6);
        str2 = a(7, paramACHRecordInfo.getClassCode());
        if (paramBoolean) {
          if (addendaExists(str3))
          {
            i = localFFSStringTokenizer.countTokens();
            if (i == 0) {
              throw new FFSException(22500, jdMethod_char(22500));
            }
          }
          else if (localFFSStringTokenizer.countTokens() > 0)
          {
            throw new FFSException(22510, jdMethod_char(22510));
          }
        }
        localObject = new ACHAddendaInfo[i];
        for (j = 1; localFFSStringTokenizer.hasMoreTokens(); j++)
        {
          str3 = localFFSStringTokenizer.nextToken();
          localObject[(j - 1)] = new ACHAddendaInfo();
          localObject[(j - 1)].setAddenda(parsePureRecord(str1, str2, str3));
        }
        paramACHRecordInfo.setAddenda((ACHAddendaInfo[])localObject);
      }
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(a(localFFSException, null), 0);
      if (localFFSException.getErrCode() != -1)
      {
        paramACHRecordInfo.setStatusCode(localFFSException.getErrCode());
        paramACHRecordInfo.setStatusMsg(localFFSException.getMessage());
      }
      throw localFFSException;
    }
    catch (Exception localException)
    {
      Object localObject = FFSDebug.stackTrace(localException);
      int j = 23010;
      paramACHRecordInfo.setStatusCode(j);
      paramACHRecordInfo.setStatusMsg(jdMethod_char(j));
      FFSDebug.log(String.valueOf(j) + ": " + jdMethod_char(j), "\n         ", (String)localObject, 0);
      throw new FFSException(localException, jdMethod_char(j));
    }
  }
  
  public static Object parsePureRecord(String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    IMBMessage localIMBMessage = null;
    IParser localIParser = null;
    FFSStringTokenizer localFFSStringTokenizer = null;
    aq();
    FFSDebug.log("Checking parameters...", 6);
    if (paramString1 == null)
    {
      FFSDebug.log("Null message set", 0);
      throw new FFSException("Null message set");
    }
    if (paramString2 == null)
    {
      FFSDebug.log("Null message type", 0);
      throw new FFSException("Null message type");
    }
    if ((paramString3 == null) || (paramString3.length() == 0))
    {
      FFSDebug.log("Null/empty message string", 0);
      throw new FFSException("Null/empty message string");
    }
    try
    {
      if (FFSStringTokenizer.hasDelimeter(paramString3, "\r\n")) {
        localFFSStringTokenizer = new FFSStringTokenizer(paramString3, "\r\n");
      } else {
        localFFSStringTokenizer = new FFSStringTokenizer(paramString3, 94);
      }
      paramString3 = localFFSStringTokenizer.nextToken();
      FFSDebug.log("Parsing msg. string...", 6);
      localIParser = av();
      localIMBMessage = localIParser.parseToIDL(paramString3, paramString1, paramString2);
      FFSDebug.log("Successfully parsed message:", FFSConst.LINE_SEPARATOR, paramString3, 6);
      Object localObject1 = localIMBMessage.getIDLInstance();
      return localObject1;
    }
    catch (MBException localMBException)
    {
      str = "\t\t" + localMBException.getMessage();
      FFSDebug.log(str, 0);
      FFSDebug.log(str, 0);
      i = 22570;
      FFSException localFFSException2 = new FFSException(i, "\t\t" + str);
      FFSDebug.log(localFFSException2.getMessage(), 0);
      FFSDebug.log(a(localFFSException2, null), str, 0);
      throw localFFSException2;
    }
    catch (FFSException localFFSException1)
    {
      FFSDebug.log(a(localFFSException1, null), 0);
      throw localFFSException1;
    }
    catch (Exception localException)
    {
      String str = FFSDebug.stackTrace(localException);
      int i = 23010;
      FFSDebug.log(String.valueOf(i) + ": " + jdMethod_char(i), "\r\n", str, 0);
      throw new FFSException(localException, jdMethod_char(i));
    }
    finally
    {
      a(localIParser);
    }
  }
  
  public static void parse(ACHRecordInfo paramACHRecordInfo)
    throws FFSException
  {
    aq();
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    FFSStringTokenizer localFFSStringTokenizer = null;
    if (paramACHRecordInfo == null)
    {
      FFSDebug.log("ACHRecordInfo object is NULL", 0);
      return;
    }
    str4 = paramACHRecordInfo.getRecordContent();
    int i;
    if ((str4 == null) || (str4.length() == 0))
    {
      i = 22950;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    if (FFSStringTokenizer.hasDelimeter(str4, "\r\n")) {
      localFFSStringTokenizer = new FFSStringTokenizer(str4, "\r\n");
    } else {
      localFFSStringTokenizer = new FFSStringTokenizer(str4, 94);
    }
    str3 = localFFSStringTokenizer.nextToken();
    FFSDebug.log("parse(ACHRecordInfo ):: msg:\n\t", str3, 6);
    try
    {
      if (paramACHRecordInfo.getRecordType() == -1) {
        paramACHRecordInfo.setRecordType(getRecordType(str3));
      }
      FFSDebug.log("achRec.ClassCode:", paramACHRecordInfo.getClassCode(), 6);
      str1 = "ACH" + paramACHRecordInfo.getAchVersion();
      str2 = a(paramACHRecordInfo.getRecordType(), paramACHRecordInfo.getClassCode());
      FFSDebug.log("parse(ACHRecordInfo ):: msgSetName:", str1, 6);
      FFSDebug.log("parse(ACHRecordInfo ):: msgName:", str2, 6);
      paramACHRecordInfo.setRecord(parsePureRecord(str1, str2, str3));
      FFSDebug.log("Successfully parsed message:", FFSConst.LINE_SEPARATOR, str3, 6);
      FFSDebug.log("achRec.recordType:" + paramACHRecordInfo.getRecordType(), 6);
      if (paramACHRecordInfo.getRecordType() == 6)
      {
        i = 0;
        localObject = null;
        FFSDebug.log("Before Process Addenda records", 6);
        str2 = a(7, paramACHRecordInfo.getClassCode());
        if (addendaExists(str3))
        {
          i = localFFSStringTokenizer.countTokens();
          if (i == 0) {
            throw new FFSException(22500, jdMethod_char(22500));
          }
          localObject = new ACHAddendaInfo[i];
          for (j = 1; localFFSStringTokenizer.hasMoreTokens(); j++)
          {
            str3 = localFFSStringTokenizer.nextToken();
            localObject[(j - 1)] = new ACHAddendaInfo();
            localObject[(j - 1)].setAddenda(parsePureRecord(str1, str2, str3));
          }
          paramACHRecordInfo.setAddenda((ACHAddendaInfo[])localObject);
        }
        else if (localFFSStringTokenizer.countTokens() > 0)
        {
          throw new FFSException(22510, jdMethod_char(22510));
        }
      }
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(a(localFFSException, null), 0);
      if (localFFSException.getErrCode() != -1)
      {
        paramACHRecordInfo.setStatusCode(localFFSException.getErrCode());
        paramACHRecordInfo.setStatusMsg(localFFSException.getMessage());
      }
      throw localFFSException;
    }
    catch (Exception localException)
    {
      Object localObject = FFSDebug.stackTrace(localException);
      int j = 23010;
      paramACHRecordInfo.setStatusCode(j);
      paramACHRecordInfo.setStatusMsg(jdMethod_char(j));
      FFSDebug.log(String.valueOf(j) + ": " + jdMethod_char(j), "\n         ", (String)localObject, 0);
      throw new FFSException(localException, jdMethod_char(j));
    }
  }
  
  public static void parseValidate(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    aq();
    jdMethod_byte(paramACHBatchInfo, true);
    FFSDebug.log("parse successful ..", 6);
  }
  
  public static void parse(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    aq();
    jdMethod_byte(paramACHBatchInfo, false);
    FFSDebug.log("parse successful ..", 6);
  }
  
  private static void jdMethod_byte(ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    ArrayList localArrayList = null;
    ACHRecordInfo[] arrayOfACHRecordInfo = null;
    ACHRecordInfo localACHRecordInfo = null;
    int i = 0;
    boolean bool = false;
    int j = 0;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    ACHBatchCache localACHBatchCache = null;
    FFSStringTokenizer localFFSStringTokenizer = null;
    if (paramACHBatchInfo == null)
    {
      FFSDebug.log("ACHBatchInfo object is NULL", 0);
      return;
    }
    try
    {
      str1 = paramACHBatchInfo.getBatchContent();
      if ((str1 == null) || (str1.length() == 0))
      {
        paramACHBatchInfo.setStatusCode(22950);
        paramACHBatchInfo.setStatusMsg(jdMethod_char(22950));
        throw new FFSException(22950, jdMethod_char(22950));
      }
      if ((paramACHBatchInfo.getTotalBatchSize() == 0L) || (paramACHBatchInfo.getBatchPageSize() == 0L)) {
        bool = true;
      }
      FFSDebug.log("parseBatch:: noPaging :" + bool, 6);
      if (bool) {
        i = 1;
      } else if (paramACHBatchInfo.getRecordCursor() == paramACHBatchInfo.getBatchPageSize()) {
        i = 1;
      }
      if (paramACHBatchInfo.getBatchId() == null) {
        if (i != 0)
        {
          paramACHBatchInfo.setBatchId(FFSUtil.uniqueID());
          FFSDebug.log("Generated achBatch.batchId:", paramACHBatchInfo.getBatchId(), 6);
        }
        else
        {
          paramACHBatchInfo.setStatusCode(22370);
          paramACHBatchInfo.setStatusMsg(jdMethod_char(22370));
          throw new FFSException(22370, jdMethod_char(22370));
        }
      }
      if (!zt.cacheExists(paramACHBatchInfo.getBatchId()))
      {
        if (i == 0)
        {
          int k = 22960;
          paramACHBatchInfo.setStatusCode(k);
          paramACHBatchInfo.setStatusMsg(jdMethod_char(k));
          throw new FFSException(k, jdMethod_char(k));
        }
        localACHBatchCache = new ACHBatchCache();
        localACHBatchCache.cacheId = paramACHBatchInfo.getBatchId();
        zt.createCache(localACHBatchCache);
      }
      else
      {
        localACHBatchCache = (ACHBatchCache)zt.getCache(paramACHBatchInfo.getBatchId());
      }
      FFSDebug.log("parseBatch:: batchText B4 removing block filler:", str1, 6);
      j = str1.indexOf("9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
      if (j != -1)
      {
        if ((!FFSStringTokenizer.hasDelimeter(str1, "\r\n")) && (j % 94 != 0)) {
          j += 94 - j % 94;
        }
        str1 = str1.substring(0, j);
      }
      FFSDebug.log("parseBatch:: batchText:", str1, 6);
      if (FFSStringTokenizer.hasDelimeter(str1, "\r\n")) {
        localFFSStringTokenizer = new FFSStringTokenizer(str1, "\r\n");
      } else {
        localFFSStringTokenizer = new FFSStringTokenizer(str1, 94);
      }
      if (i != 0)
      {
        str2 = localFFSStringTokenizer.nextToken();
        if (getRecordType(str2) != 5) {
          throw new FFSException(22150, jdMethod_char(22150) + str1);
        }
        localACHRecordInfo = new ACHRecordInfo();
        localACHRecordInfo.setRecordContent(str2);
        localACHRecordInfo.setRecordType(5);
        if (paramACHBatchInfo.getClassCode() == null)
        {
          paramACHBatchInfo.setClassCode(getStdEntryClassCode(str2));
          localACHRecordInfo.setClassCode(paramACHBatchInfo.getClassCode());
        }
        else
        {
          localACHRecordInfo.setClassCode(paramACHBatchInfo.getClassCode());
        }
        parse(localACHRecordInfo);
        paramACHBatchInfo.setBatchHeader(localACHRecordInfo);
        if (paramBoolean) {
          if (!paramACHBatchInfo.getClassCode().equals("ADV"))
          {
            FFSDebug.log(aH(str2), 6);
            a(localACHBatchCache, aH(str2), aG(str2), getBatchNo(str2), getServiceClassCode(str2));
          }
          else
          {
            a(localACHBatchCache, null, aG(str2), getBatchNo(str2), getServiceClassCode(str2));
          }
        }
        localACHBatchCache.stdEntryClassCode = paramACHBatchInfo.getClassCode();
      }
      str3 = localACHBatchCache.stdEntryClassCode;
      FFSDebug.log("parseBatch:: entryClassCode from cache:", str3, 6);
      localArrayList = new ArrayList();
      while (localFFSStringTokenizer.hasMoreTokens())
      {
        localACHBatchCache.curRec += 1;
        str2 = a(localFFSStringTokenizer);
        if (getRecordType(str2) == 8)
        {
          localFFSStringTokenizer.moveToPrevious();
          break;
        }
        FFSDebug.log("parseBatch:: Current Record:" + localACHBatchCache.curRec, 6);
        localACHRecordInfo = new ACHRecordInfo();
        localACHRecordInfo.setRecordContent(str2);
        localACHRecordInfo.setClassCode(str3);
        parse(localACHRecordInfo);
        localArrayList.add(localACHRecordInfo);
        if (paramBoolean)
        {
          if (isDebit(getTransactionCode(localACHRecordInfo.getRecordContent()))) {
            localACHBatchCache.batchDebitSum = localACHBatchCache.batchDebitSum.add(getBDAmount(localACHRecordInfo.getRecordContent(), localACHRecordInfo.getClassCode()));
          } else {
            localACHBatchCache.batchCreditSum = localACHBatchCache.batchCreditSum.add(getBDAmount(localACHRecordInfo.getRecordContent(), localACHRecordInfo.getClassCode()));
          }
          localACHBatchCache.batchHash += aK(localACHRecordInfo.getRecordContent());
          if (localACHRecordInfo.getAddenda() != null) {
            localACHBatchCache.batchEntryCount += 1 + localACHRecordInfo.getAddenda().length;
          } else {
            localACHBatchCache.batchEntryCount += 1;
          }
        }
      }
      if (localFFSStringTokenizer.hasMoreTokens())
      {
        str2 = localFFSStringTokenizer.nextToken();
        FFSDebug.log("parseBatch:: batch control:", str2, 6);
        if (getRecordType(str2) != 8) {
          throw new FFSException(22170, jdMethod_char(22170) + str1);
        }
        localACHRecordInfo = new ACHRecordInfo();
        localACHRecordInfo.setRecordContent(str2);
        localACHRecordInfo.setRecordType(8);
        localACHRecordInfo.setClassCode(str3);
        parse(localACHRecordInfo);
        paramACHBatchInfo.setBatchControl(localACHRecordInfo);
        if (paramBoolean) {
          a(localACHBatchCache, paramACHBatchInfo.getBatchControl());
        }
      }
      FFSDebug.log("ParseBatch::Cursor: " + paramACHBatchInfo.getRecordCursor(), 6);
      FFSDebug.log("ParseBatch::Size: " + paramACHBatchInfo.getTotalBatchSize(), 6);
      if ((bool) || (paramACHBatchInfo.getRecordCursor() >= paramACHBatchInfo.getTotalBatchSize()))
      {
        if (paramACHBatchInfo.getBatchControl() == null) {
          throw new FFSException(22170, jdMethod_char(22170) + str1);
        }
        FFSDebug.log("ParseBatch::Calling  ffsCacheManager.removeCache", 6);
        zt.removeCache(paramACHBatchInfo.getBatchId());
      }
      arrayOfACHRecordInfo = (ACHRecordInfo[])localArrayList.toArray(new ACHRecordInfo[0]);
      paramACHBatchInfo.setRecords(arrayOfACHRecordInfo);
      paramACHBatchInfo.setStatusCode(0);
      paramACHBatchInfo.setStatusMsg(jdMethod_char(0));
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(a(localFFSException, localACHBatchCache), 0);
      zt.removeCache(paramACHBatchInfo.getBatchId());
      if (localFFSException.getErrCode() != -1)
      {
        paramACHBatchInfo.setStatusCode(localFFSException.getErrCode());
        paramACHBatchInfo.setStatusMsg(localFFSException.getMessage());
      }
      throw localFFSException;
    }
    catch (Exception localException)
    {
      FFSDebug.log(a(localException, localACHBatchCache), 0);
      zt.removeCache(paramACHBatchInfo.getBatchId());
      paramACHBatchInfo.setStatusCode(23010);
      paramACHBatchInfo.setStatusMsg(jdMethod_char(23010));
      throw new FFSException(localException, jdMethod_char(23010));
    }
  }
  
  public static void parseValidate(ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    aq();
    a(paramACHFileInfo, null, true);
  }
  
  public static void parse(ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    aq();
    a(paramACHFileInfo, null, false);
  }
  
  public static void parseValidate(ACHFileInfo paramACHFileInfo, String paramString)
    throws FFSException
  {
    aq();
    FFSDebug.log("In parseValidate(ACHFileInfo , String )", 6);
    jdMethod_if(paramACHFileInfo, paramString, true);
  }
  
  public static void parse(ACHFileInfo paramACHFileInfo, String paramString)
    throws FFSException
  {
    aq();
    FFSDebug.log("In parse(ACHFileInfo , String )", 6);
    jdMethod_if(paramACHFileInfo, paramString, false);
  }
  
  private static void jdMethod_if(ACHFileInfo paramACHFileInfo, String paramString, boolean paramBoolean)
    throws FFSException
  {
    File localFile = null;
    BufferedReader localBufferedReader = null;
    int i = 0;
    int j = 0;
    StringBuffer localStringBuffer = null;
    String str1 = null;
    long l = 0L;
    int k = 0;
    ACHFileCache localACHFileCache = null;
    if (paramACHFileInfo == null)
    {
      FFSDebug.log("ACHFileInfo object is NULL", 0);
      return;
    }
    FFSDebug.log("In parseFile(ACHFileInfo , String, boolean )::FileCursor:" + paramACHFileInfo.getFileCursor() + "FileSize:" + paramACHFileInfo.getFilePageSize(), 6);
    if ((paramACHFileInfo.getFileSize() == 0L) || (paramACHFileInfo.getFilePageSize() == 0L)) {
      j = 1;
    }
    if (j != 0) {
      i = 1;
    } else if (paramACHFileInfo.getFileCursor() == paramACHFileInfo.getFilePageSize()) {
      i = 1;
    }
    if (i != 0)
    {
      localFile = new File(paramString);
      if (!localFile.exists())
      {
        paramACHFileInfo.setStatusCode(23020);
        paramACHFileInfo.setStatusMsg(jdMethod_char(23020) + paramString);
        FFSDebug.log(String.valueOf(23020) + ": " + jdMethod_char(23020), paramString, 0);
        throw new FFSException(23020, jdMethod_char(23020) + paramString);
      }
      if (!localFile.canRead())
      {
        paramACHFileInfo.setStatusCode(23030);
        paramACHFileInfo.setStatusMsg(jdMethod_char(23030) + paramString);
        FFSDebug.log(String.valueOf(23030), ": ", jdMethod_char(23030), paramString, 0);
        throw new FFSException(23030, jdMethod_char(23030) + paramString);
      }
      try
      {
        localBufferedReader = new BufferedReader(new FileReader(localFile));
      }
      catch (Exception localException1)
      {
        paramACHFileInfo.setStatusCode(23040);
        paramACHFileInfo.setStatusMsg(jdMethod_char(23040) + paramString);
        FFSDebug.log(String.valueOf(23040), ": ", jdMethod_char(23040), paramString, 0);
        throw new FFSException(23040, jdMethod_char(23040) + paramString);
      }
      l = localFile.length() / 94L;
    }
    else
    {
      localACHFileCache = (ACHFileCache)zt.getCache(paramACHFileInfo.getFileId());
      if (localACHFileCache == null)
      {
        m = 22960;
        paramACHFileInfo.setStatusCode(m);
        paramACHFileInfo.setStatusMsg(jdMethod_char(m));
        FFSDebug.log(String.valueOf(22960) + ": ", jdMethod_char(m), 0);
        throw new FFSException(m, jdMethod_char(m));
      }
      localBufferedReader = (BufferedReader)localACHFileCache.ioObj;
    }
    localStringBuffer = new StringBuffer();
    FFSDebug.log("In parseFile(ACHFileInfo , String, boolean )::FileSize:" + paramACHFileInfo.getFileSize(), 6);
    FFSDebug.log("In parseFile(ACHFileInfo , String, boolean )::FilePageSize:" + paramACHFileInfo.getFilePageSize(), 6);
    if (paramACHFileInfo.getFilePageSize() > 0L) {
      k = (int)paramACHFileInfo.getFilePageSize();
    } else {
      k = (int)l;
    }
    FFSDebug.log("In parseFile(ACHFileInfo , String, boolean )::bufReader:" + localBufferedReader, 6);
    String str3;
    for (int m = 0; m < k; m++)
    {
      try
      {
        str1 = localBufferedReader.readLine();
      }
      catch (IOException localIOException)
      {
        try
        {
          if (localBufferedReader != null) {
            localBufferedReader.close();
          }
        }
        catch (Exception localException4)
        {
          String str4 = FFSDebug.stackTrace(localException4);
          FFSDebug.log(String.valueOf(23010) + ": ", jdMethod_char(23010), "\n         ", str4, 0);
        }
        str3 = FFSDebug.stackTrace(localIOException);
        paramACHFileInfo.setStatusCode(23050);
        paramACHFileInfo.setStatusMsg(jdMethod_char(23050) + paramString);
        FFSDebug.log(String.valueOf(23050), ": ", jdMethod_char(23050), paramString, "\n         ", str3, 0);
        throw new FFSException(localIOException, jdMethod_char(23050) + paramString);
      }
      if ((str1 == null) || (str1.equals("9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"))) {
        break;
      }
      localStringBuffer.append(str1);
    }
    paramACHFileInfo.setFileContent(localStringBuffer.toString());
    try
    {
      a(paramACHFileInfo, paramString, paramBoolean);
    }
    catch (FFSException localFFSException)
    {
      try
      {
        if (localBufferedReader != null)
        {
          FFSDebug.log("parseFile:: Closing bufReader", 6);
          localBufferedReader.close();
        }
      }
      catch (Exception localException3)
      {
        str3 = FFSDebug.stackTrace(localException3);
        FFSDebug.log(String.valueOf(23010) + ": ", jdMethod_char(23010), "\n         ", str3, 0);
      }
      throw localFFSException;
    }
    if ((i != 0) && (j == 0))
    {
      localACHFileCache = (ACHFileCache)zt.getCache(paramACHFileInfo.getFileId());
      localACHFileCache.ioObj = localBufferedReader;
    }
    if ((j != 0) || (paramACHFileInfo.getFileCursor() >= paramACHFileInfo.getFileSize())) {
      try
      {
        if (localBufferedReader != null)
        {
          FFSDebug.log("parseFile:: Closing bufReader", 6);
          localBufferedReader.close();
        }
      }
      catch (Exception localException2)
      {
        String str2 = FFSDebug.stackTrace(localException2);
        FFSDebug.log(String.valueOf(23010) + ": ", jdMethod_char(23010), "\n          ", str2, 0);
      }
    }
  }
  
  private static String jdMethod_goto(int paramInt)
  {
    if (paramInt == 1) {
      return "File Header Record";
    }
    if (paramInt == 5) {
      return "Batch Header Record";
    }
    if (paramInt == 6) {
      return "Entry Detail Record";
    }
    if (paramInt == 7) {
      return "Addenda Record";
    }
    if (paramInt == 8) {
      return "Batch Control Record";
    }
    if (paramInt == 9) {
      return "File Control Record";
    }
    return "Unknown";
  }
  
  private static String a(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5)
  {
    paramString1 = paramString1 + "\r\n";
    if ((paramString2 != null) && (paramString2.length() != 0))
    {
      if (paramString2.length() > 94) {
        paramString2 = paramString2.substring(0, 94);
      }
      paramString1 = paramString1 + "Failed record [" + paramString2 + "]" + "\r\n";
    }
    if ((paramString3 != null) && (paramString3.length() != 0)) {
      paramString1 = paramString1 + "       Standard Entry Class: " + paramString3 + "\r\n";
    }
    if (paramInt != 0)
    {
      String str = jdMethod_goto(paramInt);
      if ((str != null) && (str.length() != 0)) {
        paramString1 = paramString1 + "       Record Type: " + str + "\r\n";
      }
    }
    if ((paramString5 != null) && (paramString5.length() != 0)) {
      paramString1 = paramString1 + "       Field: " + paramString5 + "\r\n";
    }
    if ((paramString4 != null) && (paramString4.length() != 0)) {
      paramString1 = paramString1 + "       Reason: " + paramString4 + "\r\n";
    }
    return paramString1;
  }
  
  private static String a(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, String paramString4)
  {
    String str = null;
    if (paramInt2 != 0)
    {
      str = jdMethod_char(paramInt2);
      if ((paramString4 == null) || (paramString4.length() == 0)) {
        paramString4 = jdMethod_else(paramInt2);
      }
    }
    return a(paramString1, paramString2, paramString3, paramInt1, str, paramString4);
  }
  
  private static String aF(String paramString)
  {
    String str = "Type" + paramString;
    return zB.getProperty(str, paramString);
  }
  
  private static String jdMethod_else(int paramInt)
  {
    String str = "ErrorMessageFieldName" + paramInt;
    return zk.getProperty(str);
  }
  
  private static String a(String paramString1, String paramString2, String paramString3, int paramInt, FFSException paramFFSException, String paramString4)
  {
    String str1 = paramFFSException.getMessage();
    String str2 = "";
    int i = str1.indexOf(zs);
    if (i != -1) {
      str1 = str1.substring(0, i);
    }
    int j = paramFFSException.getErrCode();
    if (j == 22570)
    {
      str2 = jdMethod_char(j) + " " + str1;
      int k = -1;
      int m = str1.indexOf(zy);
      int n = str1.indexOf(zC);
      int i1 = str1.indexOf(zA);
      if (m != -1) {
        k = m + zy.length();
      }
      if (n != -1) {
        k = n + zC.length();
      }
      if (i1 != -1) {
        k = i1 + zA.length();
      }
      int i2;
      String str3;
      if (k != -1)
      {
        i2 = str1.indexOf("\"", k + 1);
        if (i2 == -1) {
          i2 = str1.length();
        }
        str3 = str1.substring(k, i2);
        paramString4 = aF(str3);
        int i3 = str1.indexOf(":", i2);
        if (i3 != -1) {
          str2 = str1.substring(i3);
        }
      }
      else
      {
        k = str1.indexOf(zp);
        if (k != -1)
        {
          k = zp.length();
          i2 = str1.indexOf(" ", k + 1);
          if (i2 != -1)
          {
            str3 = str1.substring(k, i2);
            paramString4 = aF(str3);
            str2 = "Validation rule, " + str1.substring(i2) + ", failed.";
          }
        }
        i2 = str2.indexOf(zu);
        if (i2 != -1) {
          str2 = "Record size is not correct or there are two or more invalid fields in this record.";
        }
      }
    }
    else
    {
      str2 = jdMethod_char(j);
      paramString4 = jdMethod_else(j);
    }
    return a(paramString1, paramString2, paramString3, paramInt, str2, paramString4);
  }
  
  private static void a(ACHFileInfo paramACHFileInfo, String paramString, boolean paramBoolean)
    throws FFSException
  {
    ArrayList localArrayList1 = null;
    ArrayList localArrayList2 = new ArrayList();
    ACHRecordInfo[] arrayOfACHRecordInfo = null;
    ACHRecordInfo localACHRecordInfo = null;
    int i = -1;
    String str1 = null;
    String str2 = null;
    int j = 0;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    int k = 0;
    boolean bool = false;
    ACHFileCache localACHFileCache = null;
    FFSStringTokenizer localFFSStringTokenizer1 = null;
    String str6 = null;
    String str7 = "\r\n";
    int m = 0;
    FFSDebug.log("In parseACHFile", 6);
    if (paramACHFileInfo == null)
    {
      FFSDebug.log("ACHFileInfo object is NULL", 0);
      return;
    }
    try
    {
      str4 = paramACHFileInfo.getFileContent();
      if ((str4 == null) || (str4.length() == 0))
      {
        m = 22950;
        str7 = a(str7, str2, str1, j, m, str3);
        return;
      }
      if ((paramACHFileInfo.getFileSize() == 0L) || (paramACHFileInfo.getFilePageSize() == 0L)) {
        bool = true;
      }
      FFSDebug.log("parseACHFile:: noPaging :" + bool, 6);
      if (bool) {
        k = 1;
      } else if (paramACHFileInfo.getFileCursor() == paramACHFileInfo.getFilePageSize()) {
        k = 1;
      }
      if (paramACHFileInfo.getFileId() == null) {
        if (k != 0)
        {
          paramACHFileInfo.setFileId(FFSUtil.uniqueID());
        }
        else
        {
          m = 22890;
          paramACHFileInfo.setStatusCode(m);
          str7 = a(str7, str2, str1, j, m, str3);
          paramACHFileInfo.setStatusMsg(str7);
          return;
        }
      }
      if (!zt.cacheExists(paramACHFileInfo.getFileId()))
      {
        if (k == 0)
        {
          m = 22960;
          paramACHFileInfo.setStatusCode(m);
          str7 = a(str7, str2, str1, j, m, str3);
          paramACHFileInfo.setStatusMsg(str7);
          return;
        }
        localACHFileCache = new ACHFileCache();
        localACHFileCache.cacheId = paramACHFileInfo.getFileId();
        zt.createCache(localACHFileCache);
        localACHFileCache.batchCache = new ACHBatchCache();
        if (paramString != null) {
          localACHFileCache.fileName = paramString;
        }
      }
      else
      {
        localACHFileCache = (ACHFileCache)zt.getCache(paramACHFileInfo.getFileId());
      }
      i = str4.indexOf("9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
      if (i != -1)
      {
        if ((!FFSStringTokenizer.hasDelimeter(str4, "\r\n")) && (i % 94 != 0)) {
          i += 94 - i % 94;
        }
        str6 = str4.substring(i);
        str4 = str4.substring(0, i);
      }
      FFSDebug.log("parseACHFile:: fileText:", str4, 6);
      if (FFSStringTokenizer.hasDelimeter(str4, "\r\n")) {
        localFFSStringTokenizer1 = new FFSStringTokenizer(str4, "\r\n");
      } else {
        localFFSStringTokenizer1 = new FFSStringTokenizer(str4, 94);
      }
      String str10;
      String str11;
      if (k != 0)
      {
        str2 = localFFSStringTokenizer1.nextToken();
        try
        {
          j = getRecordType(str2);
        }
        catch (FFSException localFFSException1)
        {
          m = localFFSException1.getErrCode();
          str7 = a(str7, str2, str1, j, localFFSException1, str3);
          return;
        }
        if (j != 1)
        {
          m = 22650;
          str7 = a(str7, str2, str1, j, m, str3);
          return;
        }
        if (!localFFSStringTokenizer1.hasMoreTokens())
        {
          m = 22150;
          str7 = a(str7, "", "", 0, m, str3);
          return;
        }
        str5 = localFFSStringTokenizer1.nextToken();
        localACHFileCache.curBatch += 1;
        int n = 0;
        try
        {
          n = getRecordType(str5);
        }
        catch (FFSException localFFSException3)
        {
          m = localFFSException3.getErrCode();
          str7 = a(str7, str5, str1, n, localFFSException3, str3);
          return;
        }
        if (n != 5)
        {
          m = 22150;
          str7 = a(str7, str5, str1, n, m, str3);
          return;
        }
        localACHFileCache.curBatch -= 1;
        localFFSStringTokenizer1.moveToPrevious();
        FFSDebug.log("pasreACHFile::batchHeader:", str5, 6);
        FFSDebug.log("pasreACHFile::msg :", str2, 6);
        try
        {
          str1 = getStdEntryClassCode(str5);
          FFSDebug.log("pasreACHFile:: batch SEC: ", str1, 6);
        }
        catch (FFSException localFFSException4)
        {
          m = localFFSException4.getErrCode();
          str7 = a(str7, str5, str1, n, localFFSException4, str3);
          return;
        }
        localACHRecordInfo = new ACHRecordInfo();
        if (ACHAdapterUtil.allowsMinimumHeaderControl())
        {
          if (str2.length() < 40)
          {
            m = 26024;
            str7 = "ACH file header is too short";
            return;
          }
          if (str2.length() < 94) {
            str2 = padRecord(str2);
          }
        }
        localACHRecordInfo.setRecordContent(str2);
        localACHRecordInfo.setRecordType(1);
        localACHRecordInfo.setClassCode(str1);
        FFSDebug.log("parseACHFile::achRec.ClassCode:", localACHRecordInfo.getClassCode(), 6);
        try
        {
          parse(localACHRecordInfo);
        }
        catch (FFSException localFFSException5)
        {
          m = localFFSException5.getErrCode();
          str7 = a(str7, str2, "", j, localFFSException5, str3);
          return;
        }
        paramACHFileInfo.setFileHeader(localACHRecordInfo);
        localACHFileCache.recCount = 1L;
        if ((paramBoolean == true) && (m == 0))
        {
          localObject1 = paramACHFileInfo.getFileHeaderFieldValueObject(1);
          localObject1 = ((String)localObject1).trim();
          if (!BPWUtil.validateABA((String)localObject1))
          {
            m = 24060;
            str7 = a(str7, str2, "", j, m, str3);
          }
          String str8 = paramACHFileInfo.getFileHeaderFieldValueObject(2);
          str8 = str8.trim();
          if (!BPWUtil.validateABA(str8))
          {
            m = 24070;
            str7 = a(str7, str2, "", j, m, str3);
          }
          str10 = paramACHFileInfo.getFileHeaderFieldValueObject(6);
          if (str10 != null) {
            try
            {
              int i2 = Integer.parseInt(str10.substring(0, 2));
              int i3 = Integer.parseInt(str10.substring(2));
              if ((i2 < 0) || (i2 > 23) || (i3 < 0) || (i3 > 59))
              {
                m = 24030;
                str7 = a(str7, str2, "", j, m, str3);
              }
            }
            catch (NumberFormatException localNumberFormatException)
            {
              m = 24030;
              str7 = a(str7, str2, "", j, m, str3);
            }
          }
          str11 = paramACHFileInfo.getFileHeaderFieldValueObject(4);
          if (((str11.compareTo("A") < 0) || (str11.compareTo("Z") > 0)) && ((str11.compareTo("0") < 0) || (str11.compareTo("9") > 0)))
          {
            m = 24040;
            str7 = a(str7, str2, "", j, m, str3);
          }
          if (m != 0) {
            return;
          }
        }
      }
      localArrayList1 = new ArrayList();
      ACHBatchInfo localACHBatchInfo = new ACHBatchInfo();
      Object localObject1 = new ArrayList();
      while (localFFSStringTokenizer1.hasMoreTokens())
      {
        try
        {
          str2 = a(localFFSStringTokenizer1);
        }
        catch (FFSException localFFSException6)
        {
          localFFSStringTokenizer1.moveToPrevious();
          str10 = localFFSStringTokenizer1.nextToken();
          m = localFFSException6.getErrCode();
          str7 = a(str7, str10, str1, 0, localFFSException6, str3);
          return;
        }
        FFSDebug.log("parseACHFile::In While: msg :", str2, 6);
        try
        {
          j = getRecordType(str2);
        }
        catch (FFSException localFFSException7)
        {
          m = localFFSException7.getErrCode();
          str7 = a(str7, str2, str1, j, localFFSException7, str3);
        }
        continue;
        if (j == 9)
        {
          localFFSStringTokenizer1.moveToPrevious();
          break;
        }
        if (j == 5)
        {
          localACHFileCache.batchHeaderFound = true;
          if (!localACHFileCache.batchCtrlFound)
          {
            m = 22170;
            str7 = a(str7, str2, str1, j, m, str3);
            return;
          }
          localACHFileCache.batchCtrlFound = false;
          try
          {
            str1 = getStdEntryClassCode(str2);
          }
          catch (FFSException localFFSException8)
          {
            m = localFFSException8.getErrCode();
            str7 = a(str7, str2, str1, j, localFFSException8, str3);
            return;
          }
          localACHFileCache.curBatch += 1;
          localACHFileCache.batchCache.curRec += 1;
          localACHFileCache.recCount += 1L;
          localACHRecordInfo = new ACHRecordInfo();
          localACHRecordInfo.setRecordContent(str2);
          localACHRecordInfo.setRecordType(5);
          localACHRecordInfo.setClassCode(str1);
          try
          {
            parse(localACHRecordInfo);
            if (paramBoolean)
            {
              String str9 = aG(str2);
              str10 = getServiceClassCode(str2);
              str11 = getBatchNo(str2);
              if (!str1.equals("ADV"))
              {
                String str12 = null;
                str12 = aH(str2);
                FFSDebug.log("parseACHFile::", str12, 6);
                a(localACHFileCache.batchCache, str12, str9, str11, str10);
              }
              else
              {
                a(localACHFileCache.batchCache, null, str9, str11, str10);
              }
            }
          }
          catch (FFSException localFFSException9)
          {
            m = localFFSException9.getErrCode();
            str7 = a(str7, str2, str1, j, localFFSException9, str3);
          }
          continue;
          FFSDebug.log("parseACHFile::Store batchClassCode:", str1, 6);
          localACHFileCache.batchCache.stdEntryClassCode = str1;
          localACHBatchInfo.setBatchHeader(localACHRecordInfo);
        }
        else if (j == 8)
        {
          localACHFileCache.batchCtrlFound = true;
          if (!localACHFileCache.batchHeaderFound)
          {
            localACHFileCache.curBatch += 1;
            m = 22150;
            str7 = a(str7, str2, str1, j, m, str3);
            return;
          }
          localACHFileCache.batchHeaderFound = false;
          if (str1 == null) {
            str1 = localACHFileCache.batchCache.stdEntryClassCode;
          }
          FFSDebug.log("parseACHFile::BATCH_CONTROL batchClassCode:", str1, 6);
          localACHRecordInfo = new ACHRecordInfo();
          localACHRecordInfo.setRecordContent(str2);
          localACHRecordInfo.setRecordType(8);
          localACHRecordInfo.setClassCode(str1);
          try
          {
            parse(localACHRecordInfo);
            localACHFileCache.recCount += 1L;
            if ((paramBoolean) && (m == 0))
            {
              a(localACHFileCache.batchCache, localACHRecordInfo);
              jdMethod_if(localACHFileCache);
            }
            a(localACHFileCache.batchCache);
            localACHBatchInfo.setBatchControl(localACHRecordInfo);
            localACHBatchInfo.setClassCode(str1);
            localACHBatchInfo.setRecords((ACHRecordInfo[])((ArrayList)localObject1).toArray(new ACHRecordInfo[0]));
            localArrayList2.add(localACHBatchInfo);
            localACHBatchInfo = new ACHBatchInfo();
            localObject1 = new ArrayList();
          }
          catch (FFSException localFFSException10)
          {
            m = localFFSException10.getErrCode();
            str7 = a(str7, str2, str1, j, localFFSException10, str3);
          }
        }
        else if (j == 6)
        {
          if (!localACHFileCache.batchHeaderFound)
          {
            localACHFileCache.curBatch += 1;
            m = 22170;
            str7 = a(str7, str2, str1, j, m, str3);
          }
          if (str1 == null) {
            str1 = localACHFileCache.batchCache.stdEntryClassCode;
          }
          FFSDebug.log("parseACHFile::ENTRY_DETAIL batchClassCode:", str1, 6);
          localACHFileCache.batchCache.curRec += 1;
          localACHRecordInfo = new ACHRecordInfo();
          localACHRecordInfo.setRecordContent(str2);
          localACHRecordInfo.setRecordType(6);
          localACHRecordInfo.setClassCode(str1);
          try
          {
            parse(localACHRecordInfo);
            if (localACHRecordInfo.getAddenda() == null) {
              localACHFileCache.recCount += 1L;
            } else {
              localACHFileCache.recCount += 1 + localACHRecordInfo.getAddenda().length;
            }
            if ((paramBoolean == true) && (m == 0))
            {
              if (isDebit(getTransactionCode(localACHRecordInfo.getRecordContent()))) {
                localACHFileCache.batchCache.batchDebitSum = localACHFileCache.batchCache.batchDebitSum.add(getBDAmount(localACHRecordInfo.getRecordContent(), localACHRecordInfo.getClassCode()));
              } else {
                localACHFileCache.batchCache.batchCreditSum = localACHFileCache.batchCache.batchCreditSum.add(getBDAmount(localACHRecordInfo.getRecordContent(), localACHRecordInfo.getClassCode()));
              }
              localACHFileCache.batchCache.batchHash += aK(localACHRecordInfo.getRecordContent());
              if (localACHRecordInfo.getAddenda() != null) {
                localACHFileCache.batchCache.batchEntryCount += 1 + localACHRecordInfo.getAddenda().length;
              } else {
                localACHFileCache.batchCache.batchEntryCount += 1;
              }
            }
            ((ArrayList)localObject1).add(localACHRecordInfo);
          }
          catch (FFSException localFFSException11)
          {
            m = localFFSException11.getErrCode();
            str7 = a(str7, str2, str1, j, localFFSException11, str3);
          }
          continue;
        }
        localArrayList1.add(localACHRecordInfo);
      }
      if (localFFSStringTokenizer1.hasMoreTokens())
      {
        str2 = localFFSStringTokenizer1.nextToken();
        try
        {
          j = getRecordType(str2);
        }
        catch (FFSException localFFSException12)
        {
          m = localFFSException12.getErrCode();
          str7 = a(str7, str2, "", j, localFFSException12, str3);
        }
        FFSDebug.log("parseACHFile::RecordType: " + j, 6);
        if (!localACHFileCache.batchCtrlFound)
        {
          m = 22170;
          str7 = a(str7, str2, "", j, m, str3);
        }
        if (j != 9)
        {
          m = 22670;
          str7 = a(str7, str2, "", j, m, str3);
        }
        localACHRecordInfo = new ACHRecordInfo();
        if (ACHAdapterUtil.allowsMinimumHeaderControl())
        {
          if (str2.length() < 55)
          {
            m = 26025;
            str7 = "ACH file control is too short";
            return;
          }
          if (str2.length() < 94) {
            str2 = padRecord(str2);
          }
        }
        localACHRecordInfo.setRecordContent(str2);
        localACHRecordInfo.setRecordType(9);
        localACHRecordInfo.setClassCode("PPD");
        FFSDebug.log("parseACHFile:: File control class code:", localACHRecordInfo.getClassCode(), 6);
        try
        {
          parse(localACHRecordInfo);
          paramACHFileInfo.setFileControl(localACHRecordInfo);
          localACHFileCache.recCount += 1L;
          if ((paramBoolean == true) && (m == 0)) {
            a(localACHFileCache, paramACHFileInfo);
          }
        }
        catch (FFSException localFFSException13)
        {
          m = localFFSException13.getErrCode();
          str7 = a(str7, str2, "", j, localFFSException13, str3);
        }
      }
      if ((bool) || (paramACHFileInfo.getFileCursor() >= paramACHFileInfo.getFileSize()))
      {
        if (paramACHFileInfo.getFileControl() == null)
        {
          m = 22670;
          str7 = a(str7, "", "", 0, m, str3);
        }
        FFSDebug.log("ParseACHFile::Calling  ffsCacheManager.removeCache", 6);
        zt.removeCache(paramACHFileInfo.getFileId());
      }
      if ((paramBoolean) && (str6 != null) && (m == 0))
      {
        FFSStringTokenizer localFFSStringTokenizer2 = new FFSStringTokenizer(str6, "\r\n");
        int i1 = localFFSStringTokenizer2.countTokens();
        long l = localACHFileCache.recCount + i1;
        if (l % 10L != 0L)
        {
          m = 24050;
          str7 = a(str7, "", "", 0, m, str3);
        }
      }
      if (m == 0)
      {
        arrayOfACHRecordInfo = (ACHRecordInfo[])localArrayList1.toArray(new ACHRecordInfo[0]);
        paramACHFileInfo.setRecords(arrayOfACHRecordInfo);
        paramACHFileInfo.setStatusCode(0);
        paramACHFileInfo.setStatusMsg(jdMethod_char(0));
        paramACHFileInfo.setBatches((ACHBatchInfo[])localArrayList2.toArray(new ACHBatchInfo[0]));
      }
      else
      {
        paramACHFileInfo.setStatusCode(m);
        paramACHFileInfo.setStatusMsg(jdMethod_char(m));
      }
    }
    catch (FFSException localFFSException2)
    {
      FFSDebug.log(a(localFFSException2, localACHFileCache), 0);
      zt.removeCache(paramACHFileInfo.getFileId());
      m = localFFSException2.getErrCode();
      str7 = a(str7, str2, str1, j, localFFSException2, str3);
    }
    catch (Exception localException)
    {
      FFSDebug.log(a(localException, localACHFileCache), 0);
      zt.removeCache(paramACHFileInfo.getFileId());
      m = 23010;
      str7 = a(str7, str2, str1, j, m, str3);
    }
    finally
    {
      if (m != 0)
      {
        paramACHFileInfo.setStatusCode(m);
        str7 = "Unable to process this file: \r\n" + str7 + "\r\n";
        paramACHFileInfo.setStatusMsg(str7);
        throw new FFSException(m, str7);
      }
    }
  }
  
  public static String buildPureRecord(String paramString1, String paramString2, Object paramObject)
    throws FFSException
  {
    StringBuffer localStringBuffer = null;
    IBuilder localIBuilder = null;
    IMBMessage localIMBMessage = null;
    aq();
    if (paramString1 == null)
    {
      FFSDebug.log("Null message set", 0);
      throw new FFSException("Null message set");
    }
    if (paramString2 == null)
    {
      FFSDebug.log("Null message type", 0);
      throw new FFSException("Null message type");
    }
    if (paramObject == null)
    {
      FFSDebug.log("Null record object", 0);
      throw new FFSException("Null record object");
    }
    try
    {
      localIBuilder = ap();
      localIMBMessage = MBMessageFactory.createIDLMessage(paramString1, paramString2, paramObject);
      localStringBuffer = new StringBuffer();
      localStringBuffer.append(localIBuilder.buildString(localIMBMessage));
      localStringBuffer.append(FFSConst.LINE_SEPARATOR);
      FFSDebug.log("Successfully built Entry detail record text:", FFSConst.LINE_SEPARATOR, localStringBuffer.toString(), 6);
    }
    catch (MBException localMBException)
    {
      str = FFSDebug.stackTrace(localMBException);
      i = 22580;
      FFSException localFFSException2 = new FFSException(i, jdMethod_char(i) + "\n\t\t" + str);
      FFSDebug.log(a(localFFSException2, null), str, 0);
      throw localFFSException2;
    }
    catch (FFSException localFFSException1)
    {
      str = FFSDebug.stackTrace(localFFSException1);
      FFSDebug.log(a(localFFSException1, null), str, 0);
      throw localFFSException1;
    }
    catch (Exception localException)
    {
      String str = FFSDebug.stackTrace(localException);
      int i = 23010;
      FFSDebug.log(String.valueOf(i) + ": " + jdMethod_char(i), "\n       ", str, 0);
      throw new FFSException(localException, jdMethod_char(i));
    }
    finally
    {
      a(localIBuilder);
    }
    return localStringBuffer.toString();
  }
  
  public static void build(ACHAddendaInfo paramACHAddendaInfo)
    throws FFSException
  {
    String str1 = null;
    String str2 = null;
    StringBuffer localStringBuffer = null;
    Object localObject = null;
    if (paramACHAddendaInfo == null)
    {
      FFSDebug.log("ACHAddendaInfo object is NULL", 0);
      return;
    }
    localObject = paramACHAddendaInfo.getAddenda();
    if (localObject == null)
    {
      int i = 22600;
      paramACHAddendaInfo.setStatusCode(i);
      paramACHAddendaInfo.setStatusMsg(jdMethod_char(i));
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    str1 = "ACH" + paramACHAddendaInfo.getAchVersion();
    str2 = a(7, paramACHAddendaInfo.getClassCode());
    try
    {
      localStringBuffer = new StringBuffer();
      localStringBuffer.append(buildPureRecord(str1, str2, paramACHAddendaInfo.getAddenda()));
      paramACHAddendaInfo.setAddendaContent(localStringBuffer.toString());
      FFSDebug.log("Successfully built message:", FFSConst.LINE_SEPARATOR, paramACHAddendaInfo.getAddendaContent(), 6);
      paramACHAddendaInfo.setStatusCode(0);
      paramACHAddendaInfo.setStatusMsg(jdMethod_char(0));
    }
    catch (Exception localException)
    {
      String str3 = FFSDebug.stackTrace(localException);
      int j = 23010;
      paramACHAddendaInfo.setStatusCode(j);
      paramACHAddendaInfo.setStatusMsg(jdMethod_char(j));
      FFSDebug.log(String.valueOf(j) + ": " + jdMethod_char(j), "\n       ", str3, 0);
      throw new FFSException(localException, jdMethod_char(j));
    }
  }
  
  public static void build(ACHRecordInfo paramACHRecordInfo, boolean paramBoolean)
    throws FFSException
  {
    build(paramACHRecordInfo, paramBoolean, true);
  }
  
  public static void build(ACHRecordInfo paramACHRecordInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    build(paramACHRecordInfo, paramBoolean1, paramBoolean2, true);
  }
  
  public static void build(ACHRecordInfo paramACHRecordInfo, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws FFSException
  {
    String str1 = null;
    String str2 = null;
    StringBuffer localStringBuffer = null;
    Object localObject = null;
    if (paramACHRecordInfo == null)
    {
      FFSDebug.log("ACHRecordInfo object is NULL", 0);
      return;
    }
    localObject = paramACHRecordInfo.getRecord();
    int i;
    if (localObject == null)
    {
      i = 22600;
      paramACHRecordInfo.setStatusCode(i);
      paramACHRecordInfo.setStatusMsg(jdMethod_char(i));
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    str1 = "ACH" + paramACHRecordInfo.getAchVersion();
    str2 = a(paramACHRecordInfo.getRecordType(), paramACHRecordInfo.getClassCode());
    try
    {
      ACHRecordRegulator.regulateRecordEntryHash(paramACHRecordInfo);
      ACHRecordRegulator.regulateImmediateOriginAndDestination(paramACHRecordInfo);
      if (paramBoolean3) {
        ACHRecordRegulator.regulateRecord(localObject);
      }
      localStringBuffer = new StringBuffer();
      localStringBuffer.append(buildPureRecord(str1, str2, localObject));
      FFSDebug.log("Successfully built Entry detail record text:", FFSConst.LINE_SEPARATOR, localStringBuffer.toString(), 6);
      if ((paramBoolean2 == true) && (paramACHRecordInfo.getRecordType() == 6))
      {
        if (paramBoolean1) {
          if (addendaExists(localStringBuffer.toString()))
          {
            if ((paramACHRecordInfo.getAddenda() == null) || (paramACHRecordInfo.getAddenda().length == 0)) {
              throw new FFSException(22500, jdMethod_char(22500));
            }
          }
          else if ((paramACHRecordInfo.getAddenda() != null) && (paramACHRecordInfo.getAddenda().length > 0))
          {
            i = 22510;
            FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), 0);
            throw new FFSException(i, jdMethod_char(i));
          }
        }
        FFSDebug.log("Before building addenda record text.", 6);
        ACHAddendaInfo[] arrayOfACHAddendaInfo = paramACHRecordInfo.getAddenda();
        if (arrayOfACHAddendaInfo != null)
        {
          str2 = a(7, paramACHRecordInfo.getClassCode());
          for (int j = 0; j < arrayOfACHAddendaInfo.length; j++)
          {
            String str4 = arrayOfACHAddendaInfo[j].getAddendaStatus();
            if ((str4 == null) || (str4.compareTo("CANCELEDON") != 0)) {
              localStringBuffer.append(buildPureRecord(str1, str2, arrayOfACHAddendaInfo[j].getAddenda()));
            }
          }
        }
        FFSDebug.log("Finished building addenda.", 6);
      }
      paramACHRecordInfo.setRecordContent(localStringBuffer.toString());
      FFSDebug.log("Successfully built message:", FFSConst.LINE_SEPARATOR, paramACHRecordInfo.getRecordContent(), 6);
      paramACHRecordInfo.setStatusCode(0);
      paramACHRecordInfo.setStatusMsg(jdMethod_char(0));
    }
    catch (FFSException localFFSException)
    {
      if (localFFSException.getErrCode() != -1)
      {
        paramACHRecordInfo.setStatusCode(localFFSException.getErrCode());
        paramACHRecordInfo.setStatusMsg(localFFSException.getMessage());
      }
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = FFSDebug.stackTrace(localException);
      int k = 23010;
      paramACHRecordInfo.setStatusCode(k);
      paramACHRecordInfo.setStatusMsg(jdMethod_char(k));
      FFSDebug.log(String.valueOf(k) + ": " + jdMethod_char(k), "\n       ", str3, 0);
      throw new FFSException(localException, jdMethod_char(k));
    }
  }
  
  public static void build(ACHRecordInfo paramACHRecordInfo)
    throws FFSException
  {
    build(paramACHRecordInfo, true, true, true);
  }
  
  public static void buildValidate(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    aq();
    jdMethod_case(paramACHBatchInfo, true);
  }
  
  public static void build(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    aq();
    jdMethod_case(paramACHBatchInfo, false);
  }
  
  private static void jdMethod_case(ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    ACHRecordInfo localACHRecordInfo = null;
    int i = 0;
    boolean bool = false;
    StringBuffer localStringBuffer = null;
    int j = 0;
    ACHBatchCache localACHBatchCache = null;
    FFSDebug.log("In buildBatch", 6);
    if (paramACHBatchInfo == null)
    {
      FFSDebug.log("ACHBatchInfo object is NULL", 0);
      return;
    }
    try
    {
      if ((paramACHBatchInfo.getRecords() == null) || (paramACHBatchInfo.getRecords().length == 0))
      {
        k = 22360;
        paramACHBatchInfo.setStatusCode(k);
        paramACHBatchInfo.setStatusMsg(jdMethod_char(k));
        throw new FFSException(k, jdMethod_char(k));
      }
      if ((paramACHBatchInfo.getTotalBatchSize() == 0L) || (paramACHBatchInfo.getBatchPageSize() == 0L)) {
        bool = true;
      }
      FFSDebug.log("buildBatch:: noPaging :" + bool, 6);
      if (bool) {
        i = 1;
      } else if (paramACHBatchInfo.getRecordCursor() == paramACHBatchInfo.getBatchPageSize()) {
        i = 1;
      }
      if (paramACHBatchInfo.getBatchId() == null) {
        if (i != 0)
        {
          paramACHBatchInfo.setBatchId(FFSUtil.uniqueID());
          FFSDebug.log("Generated achBatch.batchId:", paramACHBatchInfo.getBatchId(), 6);
        }
        else
        {
          paramACHBatchInfo.setStatusCode(22370);
          paramACHBatchInfo.setStatusMsg(jdMethod_char(22370));
          throw new FFSException(22370, jdMethod_char(22370));
        }
      }
      if (!zt.cacheExists(paramACHBatchInfo.getBatchId()))
      {
        if (i == 0)
        {
          k = 22960;
          paramACHBatchInfo.setStatusCode(k);
          paramACHBatchInfo.setStatusMsg(jdMethod_char(k));
          throw new FFSException(k, jdMethod_char(k));
        }
        FFSDebug.log("buildBatch:: Creating Cache ", 6);
        localACHBatchCache = new ACHBatchCache();
        localACHBatchCache.cacheId = paramACHBatchInfo.getBatchId();
        zt.createCache(localACHBatchCache);
      }
      else
      {
        FFSDebug.log("buildBatch:: Cache exists", 6);
        localACHBatchCache = (ACHBatchCache)zt.getCache(paramACHBatchInfo.getBatchId());
      }
      localStringBuffer = new StringBuffer();
      if (i != 0)
      {
        if (paramACHBatchInfo.getBatchHeader() == null) {
          throw new FFSException(22150, jdMethod_char(22150));
        }
        if (paramACHBatchInfo.getBatchHeader().getRecordType() != 5) {
          throw new FFSException(22160, jdMethod_char(22160));
        }
        if (paramACHBatchInfo.getClassCode() == null)
        {
          paramACHBatchInfo.setStatusCode(22380);
          paramACHBatchInfo.setStatusMsg(jdMethod_char(22380));
          throw new FFSException(22380, jdMethod_char(22380));
        }
        localACHRecordInfo = paramACHBatchInfo.getBatchHeader();
        if (localACHRecordInfo.getClassCode() == null) {
          localACHRecordInfo.setClassCode(paramACHBatchInfo.getClassCode());
        }
        build(localACHRecordInfo);
        localStringBuffer.append(localACHRecordInfo.getRecordContent());
        if (paramBoolean)
        {
          if (!paramACHBatchInfo.getClassCode().equals("ADV")) {
            a(localACHBatchCache, aH(localACHRecordInfo.getRecordContent()), aG(localACHRecordInfo.getRecordContent()), getBatchNo(localACHRecordInfo.getRecordContent()), getServiceClassCode(localACHRecordInfo.getRecordContent()));
          } else {
            a(localACHBatchCache, null, aG(localACHRecordInfo.getRecordContent()), getBatchNo(localACHRecordInfo.getRecordContent()), getServiceClassCode(localACHRecordInfo.getRecordContent()));
          }
          localACHBatchCache.stdEntryClassCode = paramACHBatchInfo.getClassCode();
        }
      }
      j = paramACHBatchInfo.getRecords().length;
      FFSDebug.log("buildBatch:: loopEnd:" + j, 6);
      for (int k = 0; k < j; k++)
      {
        localACHBatchCache.curRec += 1;
        localACHRecordInfo = paramACHBatchInfo.getRecordsAt(k);
        String str = localACHRecordInfo.getRecordStatus();
        if ((str == null) || (str.compareTo("CANCELEDON") != 0))
        {
          if (localACHRecordInfo.getClassCode() == null) {
            localACHRecordInfo.setClassCode(paramACHBatchInfo.getClassCode());
          }
          build(localACHRecordInfo);
          localStringBuffer.append(localACHRecordInfo.getRecordContent());
          if (paramBoolean)
          {
            if (isDebit(getTransactionCode(localACHRecordInfo.getRecordContent())))
            {
              localACHBatchCache.batchDebitSum = localACHBatchCache.batchDebitSum.add(getBDAmount(localACHRecordInfo.getRecordContent(), localACHRecordInfo.getClassCode()));
            }
            else
            {
              localACHBatchCache.batchCreditSum = localACHBatchCache.batchCreditSum.add(getBDAmount(localACHRecordInfo.getRecordContent(), localACHRecordInfo.getClassCode()));
              FFSDebug.log("buildBatch:: CreditSum:" + localACHBatchCache.batchCreditSum, 6);
            }
            localACHBatchCache.batchHash += aK(localACHRecordInfo.getRecordContent());
            if (localACHRecordInfo.getAddenda() != null) {
              localACHBatchCache.batchEntryCount += 1 + localACHRecordInfo.getAddenda().length;
            } else {
              localACHBatchCache.batchEntryCount += 1;
            }
          }
        }
      }
      FFSDebug.log("Cursor: " + paramACHBatchInfo.getRecordCursor(), 6);
      FFSDebug.log("Size: " + paramACHBatchInfo.getTotalBatchSize(), 6);
      if ((bool) || (paramACHBatchInfo.getRecordCursor() >= paramACHBatchInfo.getTotalBatchSize()))
      {
        localACHRecordInfo = paramACHBatchInfo.getBatchControl();
        if (localACHRecordInfo == null) {
          throw new FFSException(22170, jdMethod_char(22170));
        }
        if (localACHRecordInfo.getRecordType() != 8) {
          throw new FFSException(22180, jdMethod_char(22180));
        }
        if (localACHRecordInfo.getClassCode() == null) {
          localACHRecordInfo.setClassCode(paramACHBatchInfo.getClassCode());
        }
        build(localACHRecordInfo);
        localStringBuffer.append(localACHRecordInfo.getRecordContent());
        if (paramBoolean) {
          a(localACHBatchCache, paramACHBatchInfo.getBatchControl());
        }
        FFSDebug.log("Calling  ffsCacheManager.removeCache", 6);
        zt.removeCache(paramACHBatchInfo.getBatchId());
      }
      paramACHBatchInfo.setBatchContent(localStringBuffer.toString());
      paramACHBatchInfo.setStatusCode(0);
      paramACHBatchInfo.setStatusMsg(jdMethod_char(0));
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(a(localFFSException, localACHBatchCache), 0);
      zt.removeCache(paramACHBatchInfo.getBatchId());
      if (localFFSException.getErrCode() != -1)
      {
        paramACHBatchInfo.setStatusCode(localFFSException.getErrCode());
        paramACHBatchInfo.setStatusMsg(localFFSException.getMessage());
      }
      throw localFFSException;
    }
    catch (Exception localException)
    {
      FFSDebug.log(a(localException, localACHBatchCache), 0);
      zt.removeCache(paramACHBatchInfo.getBatchId());
      paramACHBatchInfo.setStatusCode(23010);
      paramACHBatchInfo.setStatusMsg(jdMethod_char(23010));
      throw new FFSException(localException, jdMethod_char(23010));
    }
  }
  
  public static void buildValidate(ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    aq();
    jdMethod_do(paramACHFileInfo, null, true);
  }
  
  public static void build(ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    aq();
    jdMethod_do(paramACHFileInfo, null, false);
  }
  
  public static void buildValidate(ACHFileInfo paramACHFileInfo, String paramString)
    throws FFSException
  {
    aq();
    jdMethod_for(paramACHFileInfo, paramString, true);
  }
  
  public static void build(ACHFileInfo paramACHFileInfo, String paramString)
    throws FFSException
  {
    aq();
    jdMethod_for(paramACHFileInfo, paramString, false);
  }
  
  public static void build(ACHFIInfo paramACHFIInfo, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    aq();
    a(paramACHFIInfo, paramACHBatchInfo);
  }
  
  private static void jdMethod_for(ACHFileInfo paramACHFileInfo, String paramString, boolean paramBoolean)
    throws FFSException
  {
    File localFile = null;
    PrintWriter localPrintWriter = null;
    int i = 0;
    int j = 0;
    String str1 = null;
    ACHFileCache localACHFileCache = null;
    if (paramACHFileInfo == null)
    {
      FFSDebug.log("ACHFileInfo object is NULL", 0);
      return;
    }
    if ((paramACHFileInfo.getFileSize() == 0L) || (paramACHFileInfo.getFilePageSize() == 0L)) {
      j = 1;
    }
    if (j != 0) {
      i = 1;
    } else if (paramACHFileInfo.getFileCursor() == paramACHFileInfo.getFilePageSize()) {
      i = 1;
    }
    if (i != 0)
    {
      localFile = new File(paramString);
      if ((localFile.exists()) && (!localFile.canWrite()))
      {
        FFSDebug.log(String.valueOf(23070), ": ", jdMethod_char(23070), paramString, 0);
        throw new FFSException(23070, jdMethod_char(23070) + paramString);
      }
      try
      {
        localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(localFile)), true);
      }
      catch (Exception localException1)
      {
        String str2 = FFSDebug.stackTrace(localException1);
        int m = 23060;
        paramACHFileInfo.setStatusCode(m);
        paramACHFileInfo.setStatusMsg(jdMethod_char(m) + paramString);
        FFSDebug.log(String.valueOf(m), ": ", jdMethod_char(m), paramString, "\n      ", str2, 0);
        throw new FFSException(localException1, jdMethod_char(m) + paramString);
      }
    }
    else
    {
      localACHFileCache = (ACHFileCache)zt.getCache(paramACHFileInfo.getFileId());
      if (localACHFileCache == null)
      {
        int k = 22960;
        paramACHFileInfo.setStatusCode(k);
        paramACHFileInfo.setStatusMsg(jdMethod_char(k));
        FFSDebug.log(String.valueOf(k) + ": ", jdMethod_char(k), 0);
        throw new FFSException(k, jdMethod_char(k));
      }
      localPrintWriter = (PrintWriter)localACHFileCache.ioObj;
    }
    try
    {
      jdMethod_do(paramACHFileInfo, paramString, paramBoolean);
    }
    catch (FFSException localFFSException)
    {
      try
      {
        if (localPrintWriter != null)
        {
          FFSDebug.log("buildFile:: Closing fileWriter", 6);
          localPrintWriter.close();
        }
      }
      catch (Exception localException3)
      {
        String str4 = FFSDebug.stackTrace(localException3);
        FFSDebug.log(String.valueOf(23010) + ": ", jdMethod_char(23010), "\n          ", str4, 0);
      }
      throw localFFSException;
    }
    str1 = paramACHFileInfo.getFileContent();
    localPrintWriter.print(str1);
    if ((i != 0) && (j == 0))
    {
      localACHFileCache = (ACHFileCache)zt.getCache(paramACHFileInfo.getFileId());
      localACHFileCache.ioObj = localPrintWriter;
    }
    if ((j != 0) || (paramACHFileInfo.getFileCursor() >= paramACHFileInfo.getFileSize())) {
      try
      {
        if (localPrintWriter != null)
        {
          FFSDebug.log("buildFile:: Closing fileWriter", 6);
          localPrintWriter.close();
        }
      }
      catch (Exception localException2)
      {
        String str3 = FFSDebug.stackTrace(localException2);
        FFSDebug.log(String.valueOf(23010) + ": ", jdMethod_char(23010), "\n          ", str3, 0);
      }
    }
  }
  
  private static void jdMethod_do(ACHFileInfo paramACHFileInfo, String paramString, boolean paramBoolean)
    throws FFSException
  {
    ACHRecordInfo localACHRecordInfo = null;
    int i = 0;
    boolean bool1 = false;
    boolean bool2 = false;
    StringBuffer localStringBuffer = null;
    ACHFileCache localACHFileCache = null;
    long l = 0L;
    if (paramACHFileInfo == null)
    {
      FFSDebug.log("ACHFileInfo object is NULL", 0);
      return;
    }
    try
    {
      if ((paramACHFileInfo.getRecords() == null) || (paramACHFileInfo.getRecords().length == 0))
      {
        j = 22880;
        paramACHFileInfo.setStatusCode(j);
        paramACHFileInfo.setStatusMsg(jdMethod_char(j));
        throw new FFSException(j, jdMethod_char(j));
      }
      if ((paramACHFileInfo.getFileSize() == 0L) || (paramACHFileInfo.getFilePageSize() == 0L)) {
        bool2 = true;
      }
      FFSDebug.log("buildACHFile:: noPaging :" + bool2, 6);
      if (bool2) {
        bool1 = true;
      } else if (paramACHFileInfo.getFileCursor() == paramACHFileInfo.getFilePageSize()) {
        bool1 = true;
      }
      if (paramACHFileInfo.getFileId() == null) {
        if (bool1)
        {
          paramACHFileInfo.setFileId(FFSUtil.uniqueID());
        }
        else
        {
          paramACHFileInfo.setStatusCode(22890);
          paramACHFileInfo.setStatusMsg(jdMethod_char(22890));
          throw new FFSException(22890, jdMethod_char(22890));
        }
      }
      if (!zt.cacheExists(paramACHFileInfo.getFileId()))
      {
        if (!bool1)
        {
          j = 22960;
          paramACHFileInfo.setStatusCode(j);
          paramACHFileInfo.setStatusMsg(jdMethod_char(j));
          throw new FFSException(j, jdMethod_char(j));
        }
        localACHFileCache = new ACHFileCache();
        localACHFileCache.cacheId = paramACHFileInfo.getFileId();
        zt.createCache(localACHFileCache);
        localACHFileCache.batchCache = new ACHBatchCache();
        if (paramString != null) {
          localACHFileCache.fileName = paramString;
        }
      }
      else
      {
        localACHFileCache = (ACHFileCache)zt.getCache(paramACHFileInfo.getFileId());
      }
      localStringBuffer = new StringBuffer();
      FFSDebug.log("buildACHFile:achFile isFirstCall:" + bool1, 6);
      if (bool1)
      {
        if (paramACHFileInfo.getFileHeader() == null) {
          throw new FFSException(22650, jdMethod_char(22650));
        }
        if (paramACHFileInfo.getFileHeader().getRecordType() != 1) {
          throw new FFSException(22680, jdMethod_char(22680));
        }
        localACHFileCache.curBatch += 1;
        if (paramACHFileInfo.getRecordsAt(0) == null) {
          throw new FFSException(22150, jdMethod_char(22150));
        }
        FFSDebug.log("buildFile:achFile record 1 getRecordType():" + paramACHFileInfo.getRecordsAt(0).getRecordType(), 6);
        if (paramACHFileInfo.getRecordsAt(0).getRecordType() != 5) {
          throw new FFSException(22150, jdMethod_char(22150));
        }
        localACHFileCache.curBatch -= 1;
        localACHRecordInfo = paramACHFileInfo.getFileHeader();
        if (localACHRecordInfo.getClassCode() == null) {
          localACHRecordInfo.setClassCode(paramACHFileInfo.getRecordsAt(0).getClassCode());
        }
        build(localACHRecordInfo);
        localStringBuffer.append(localACHRecordInfo.getRecordContent());
        localACHFileCache.recCount = 1L;
      }
      i = paramACHFileInfo.getRecords().length;
      for (int j = 0; j < i; j++)
      {
        localACHRecordInfo = paramACHFileInfo.getRecordsAt(j);
        String str1;
        if (localACHRecordInfo.getRecordType() == 5)
        {
          str1 = localACHRecordInfo.getRecordContent();
          localACHFileCache.batchHeaderFound = true;
          if (!localACHFileCache.batchCtrlFound) {
            throw new FFSException(22170, jdMethod_char(22170));
          }
          build(localACHRecordInfo);
          localACHFileCache.curBatch += 1;
          localACHFileCache.batchCache.curRec += 1;
          localACHFileCache.recCount += 1L;
          if (paramBoolean)
          {
            if (!localACHRecordInfo.getClassCode().equals("ADV")) {
              a(localACHFileCache.batchCache, aH(str1), aG(str1), getBatchNo(str1), getServiceClassCode(str1));
            } else {
              a(localACHFileCache.batchCache, null, aG(str1), getBatchNo(str1), getServiceClassCode(str1));
            }
            localACHFileCache.batchCache.stdEntryClassCode = localACHRecordInfo.getClassCode();
          }
        }
        else if (localACHRecordInfo.getRecordType() == 8)
        {
          localACHFileCache.batchCtrlFound = true;
          if (!localACHFileCache.batchHeaderFound)
          {
            localACHFileCache.curBatch += 1;
            throw new FFSException(22150, jdMethod_char(22150));
          }
          localACHFileCache.batchHeaderFound = false;
          if (localACHRecordInfo.getClassCode() == null) {
            localACHRecordInfo.setClassCode(localACHFileCache.batchCache.stdEntryClassCode);
          }
          build(localACHRecordInfo);
          localACHFileCache.recCount += 1L;
          if (paramBoolean)
          {
            a(localACHFileCache.batchCache, localACHRecordInfo);
            jdMethod_if(localACHFileCache);
          }
          a(localACHFileCache.batchCache);
        }
        else if (localACHRecordInfo.getRecordType() == 6)
        {
          str1 = null;
          String str2 = localACHRecordInfo.getRecordStatus();
          if ((str2 == null) || (str2.compareTo("CANCELEDON") != 0))
          {
            if (!localACHFileCache.batchHeaderFound)
            {
              localACHFileCache.curBatch += 1;
              throw new FFSException(22150, jdMethod_char(22150));
            }
            localACHFileCache.batchCache.curRec += 1;
            if (localACHRecordInfo.getClassCode() == null) {
              localACHRecordInfo.setClassCode(localACHFileCache.batchCache.stdEntryClassCode);
            }
            build(localACHRecordInfo);
            str1 = localACHRecordInfo.getRecordContent();
            if (localACHRecordInfo.getAddenda() == null) {
              localACHFileCache.recCount += 1L;
            } else {
              localACHFileCache.recCount += 1 + localACHRecordInfo.getAddenda().length;
            }
            if (paramBoolean)
            {
              if (isDebit(getTransactionCode(str1))) {
                localACHFileCache.batchCache.batchDebitSum = localACHFileCache.batchCache.batchDebitSum.add(getBDAmount(str1, localACHRecordInfo.getClassCode()));
              } else {
                localACHFileCache.batchCache.batchCreditSum = localACHFileCache.batchCache.batchCreditSum.add(getBDAmount(str1, localACHRecordInfo.getClassCode()));
              }
              localACHFileCache.batchCache.batchHash += aK(str1);
              if (localACHRecordInfo.getAddenda() != null) {
                localACHFileCache.batchCache.batchEntryCount += 1 + localACHRecordInfo.getAddenda().length;
              } else {
                localACHFileCache.batchCache.batchEntryCount += 1;
              }
            }
          }
        }
        FFSDebug.log("buildFile(ACHFileInfo , boolean )::fileTextBuf:" + localStringBuffer, "achRec:" + localACHRecordInfo, 6);
        localStringBuffer.append(localACHRecordInfo.getRecordContent());
      }
      if ((bool2) || (paramACHFileInfo.getFileCursor() >= paramACHFileInfo.getFileSize()))
      {
        j = 0;
        if (!localACHFileCache.batchCtrlFound) {
          throw new FFSException(22170, jdMethod_char(22170));
        }
        if (paramACHFileInfo.getFileControl() == null) {
          throw new FFSException(22670, jdMethod_char(22670));
        }
        if (paramACHFileInfo.getFileControl().getRecordType() != 9) {
          throw new FFSException(22690, jdMethod_char(22690));
        }
        localACHRecordInfo = paramACHFileInfo.getFileControl();
        localACHRecordInfo.setClassCode("PPD");
        build(localACHRecordInfo);
        localStringBuffer.append(localACHRecordInfo.getRecordContent());
        localACHFileCache.recCount += 1L;
        if (paramBoolean) {
          a(localACHFileCache, paramACHFileInfo);
        }
        localStringBuffer.toString();
        try
        {
          l = getFileBlockCount(localACHRecordInfo.getRecordContent());
        }
        catch (FFSException localFFSException2)
        {
          if (localFFSException2.getErrCode() != -1)
          {
            paramACHFileInfo.setStatusCode(localFFSException2.getErrCode());
            paramACHFileInfo.setStatusMsg(localFFSException2.getMessage());
          }
          throw localFFSException2;
        }
        j = (int)(l * 10L - localACHFileCache.recCount);
        for (int k = 0; k < j; k++)
        {
          localStringBuffer.append("9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
          localStringBuffer.append(FFSConst.LINE_SEPARATOR);
        }
        zt.removeCache(paramACHFileInfo.getFileId());
      }
      else
      {
        localStringBuffer.toString();
      }
      paramACHFileInfo.setFileContent(localStringBuffer.toString());
      paramACHFileInfo.setStatusCode(0);
      paramACHFileInfo.setStatusMsg(jdMethod_char(0));
    }
    catch (FFSException localFFSException1)
    {
      FFSDebug.log(a(localFFSException1, localACHFileCache), 0);
      zt.removeCache(paramACHFileInfo.getFileId());
      if (localFFSException1.getErrCode() != -1)
      {
        paramACHFileInfo.setStatusCode(localFFSException1.getErrCode());
        paramACHFileInfo.setStatusMsg(localFFSException1.getMessage());
      }
      throw localFFSException1;
    }
    catch (Exception localException)
    {
      FFSDebug.log(a(localException, localACHFileCache), 0);
      zt.removeCache(paramACHFileInfo.getFileId());
      paramACHFileInfo.setStatusCode(23010);
      paramACHFileInfo.setStatusMsg(jdMethod_char(23010));
      throw new FFSException(localException, jdMethod_char(23010));
    }
  }
  
  private static void a(ACHFIInfo paramACHFIInfo, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    if (paramACHFIInfo == null) {
      throw new FFSException(16000, "ACHFIInfo  is null");
    }
    if (paramACHBatchInfo == null) {
      throw new FFSException(16000, "ACHBatchInfo  is null");
    }
    Object localObject1 = null;
    StringBuffer localStringBuffer = new StringBuffer();
    boolean bool = false;
    String str = null;
    ACHFileCache localACHFileCache = new ACHFileCache();
    short s = (short)paramACHBatchInfo.getBatchHeaderFieldValueShort(2);
    if (s == 280)
    {
      bool = true;
      str = "ADV";
    }
    localObject1 = a(paramACHFIInfo, bool);
    ACHRecordInfo localACHRecordInfo1 = new ACHRecordInfo();
    localACHRecordInfo1.setRecord(localObject1);
    localACHRecordInfo1.setClassCode(str);
    localACHRecordInfo1.setRecordType(1);
    localACHFileCache.recCount += 1L;
    localACHFileCache.fileBatchCount += 1;
    build(localACHRecordInfo1, false, false);
    localStringBuffer.append(localACHRecordInfo1.getRecordContent());
    paramACHBatchInfo.setBatchHeaderFieldValueObject(12, "1");
    paramACHBatchInfo.setBatchControlFieldValueObject(12, "1");
    paramACHBatchInfo.setBatchHeaderFieldValueObject(11, paramACHFIInfo.getODFIACHId8());
    ACHRecordInfo localACHRecordInfo2 = paramACHBatchInfo.getBatchHeader();
    localACHRecordInfo2.setRecordType(5);
    localACHFileCache.recCount += 1L;
    build(localACHRecordInfo2, false, false);
    localStringBuffer.append(localACHRecordInfo2.getRecordContent());
    ACHRecordInfo[] arrayOfACHRecordInfo = paramACHBatchInfo.getRecords();
    if ((arrayOfACHRecordInfo != null) && (arrayOfACHRecordInfo.length != 0))
    {
      localObject2 = paramACHBatchInfo.getOdfiRTN();
      for (int i = 0; i < arrayOfACHRecordInfo.length; i++)
      {
        localObject3 = arrayOfACHRecordInfo[i];
        try
        {
          zl = FFSUtil.padWithChar(String.valueOf(Integer.parseInt(zl) + 1), 32, true, '0');
        }
        catch (Exception localException)
        {
          FFSDebug.log(" ACHAgent.buildFile exception: Could not generate the trace counter, " + FFSDebug.stackTrace(localException), 6);
          throw new FFSException(localException.getMessage());
        }
        localObject4 = BPWUtil.composeTraceNum((String)localObject2, zl);
        ((ACHRecordInfo)localObject3).setFieldValueObject("Trace_Number", localObject4);
        build((ACHRecordInfo)localObject3, false, false, true);
        localStringBuffer.append(((ACHRecordInfo)localObject3).getRecordContent());
        if (isDebit(getTransactionCode(((ACHRecordInfo)localObject3).getRecordContent())))
        {
          localACHFileCache.fileDebitSum = localACHFileCache.fileDebitSum.add(getBDAmount(((ACHRecordInfo)localObject3).getRecordContent(), ((ACHRecordInfo)localObject3).getClassCode()));
        }
        else
        {
          localACHFileCache.fileCreditSum = localACHFileCache.fileCreditSum.add(getBDAmount(((ACHRecordInfo)localObject3).getRecordContent(), ((ACHRecordInfo)localObject3).getClassCode()));
          FFSDebug.log("buildBatch:: CreditSum:" + localACHFileCache.fileCreditSum, 6);
        }
        localACHFileCache.fileHash += aK(((ACHRecordInfo)localObject3).getRecordContent());
        ACHAddendaInfo[] arrayOfACHAddendaInfo = arrayOfACHRecordInfo[i].getAddenda();
        if (arrayOfACHAddendaInfo != null)
        {
          int k = arrayOfACHAddendaInfo.length;
          localACHFileCache.fileEntryCount += 1 + k;
          Integer localInteger = BPWUtil.composeAddendaEntryDetailSeqNum(zl);
          for (int i1 = 0; i1 < k; i1++)
          {
            arrayOfACHAddendaInfo[i1].setFieldValueObject("Trace_Number", localObject4);
            arrayOfACHAddendaInfo[i1].setFieldValueObject("Entry_Detail_Sequence_Number", localInteger);
            arrayOfACHAddendaInfo[i1].setClassCode(arrayOfACHRecordInfo[i].getClassCode());
            build(arrayOfACHAddendaInfo[i1]);
            localStringBuffer.append(arrayOfACHAddendaInfo[i1].getAddendaContent());
          }
        }
        localACHFileCache.fileEntryCount += 1L;
      }
    }
    localACHFileCache.recCount += localACHFileCache.fileEntryCount;
    Object localObject2 = a(localACHFileCache, paramACHBatchInfo, paramACHFIInfo, s, bool);
    ACHRecordInfo localACHRecordInfo3 = new ACHRecordInfo();
    localACHRecordInfo3.setRecord(localObject2);
    localACHRecordInfo3.setClassCode(str);
    localACHRecordInfo3.setRecordType(8);
    localACHFileCache.recCount += 1L;
    build(localACHRecordInfo3, false, false);
    localStringBuffer.append(localACHRecordInfo3.getRecordContent());
    localACHFileCache.recCount += 1L;
    Object localObject3 = a(localACHFileCache);
    Object localObject4 = new ACHRecordInfo();
    ((ACHRecordInfo)localObject4).setRecord(localObject3);
    ((ACHRecordInfo)localObject4).setRecordType(9);
    ((ACHRecordInfo)localObject4).setClassCode("PPD");
    build((ACHRecordInfo)localObject4, false, false, false);
    localStringBuffer.append(((ACHRecordInfo)localObject4).getRecordContent());
    int j = 0;
    try
    {
      j = getFileBlockCount(((ACHRecordInfo)localObject4).getRecordContent());
    }
    catch (FFSException localFFSException)
    {
      if (localFFSException.getErrCode() != -1)
      {
        paramACHBatchInfo.setStatusCode(localFFSException.getErrCode());
        paramACHBatchInfo.setStatusMsg(localFFSException.getMessage());
      }
      throw localFFSException;
    }
    int m = (int)(j * 10 - localACHFileCache.recCount);
    for (int n = 0; n < m; n++)
    {
      localStringBuffer.append("9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
      localStringBuffer.append(FFSConst.LINE_SEPARATOR);
    }
    paramACHBatchInfo.setBatchContent(localStringBuffer.toString());
    paramACHBatchInfo.setStatusCode(0);
    paramACHBatchInfo.setStatusMsg("Success");
  }
  
  private static Object a(ACHFileCache paramACHFileCache, ACHBatchInfo paramACHBatchInfo, ACHFIInfo paramACHFIInfo, short paramShort, boolean paramBoolean)
    throws FFSException
  {
    String str1 = MacGenerator.generateMac(paramACHFIInfo.getODFIACHId());
    long l = ((Long)paramACHBatchInfo.getBatchHeaderFieldValueObject(12)).longValue();
    if (paramBoolean == true)
    {
      localObject = new TypeADVBatchControlRecord();
      ((TypeADVBatchControlRecord)localObject).Record_Type_Code = 8;
      ((TypeADVBatchControlRecord)localObject).Service_Class_Code = paramShort;
      ((TypeADVBatchControlRecord)localObject).Entry_Addenda_Count = ((int)paramACHFileCache.fileEntryCount);
      ((TypeADVBatchControlRecord)localObject).Entry_Hash = paramACHFileCache.fileHash;
      ((TypeADVBatchControlRecord)localObject).Total_Debits = paramACHFileCache.fileDebitSum.longValue();
      ((TypeADVBatchControlRecord)localObject).Total_Credits = paramACHFileCache.fileCreditSum.longValue();
      ((TypeADVBatchControlRecord)localObject).ACH_Operator_Data = " ";
      ((TypeADVBatchControlRecord)localObject).ACH_Operator_DataExists = true;
      ((TypeADVBatchControlRecord)localObject).Originating_DFI_Identification = paramACHFIInfo.getODFIACHId8();
      ((TypeADVBatchControlRecord)localObject).Batch_Number = l;
      return localObject;
    }
    Object localObject = new TypeBatchControlRecord();
    String str2 = (String)paramACHBatchInfo.getBatchHeaderFieldValueObject(5);
    ((TypeBatchControlRecord)localObject).Record_Type_Code = 8;
    ((TypeBatchControlRecord)localObject).Service_Class_Code = paramShort;
    ((TypeBatchControlRecord)localObject).Entry_Addenda_Count = ((int)paramACHFileCache.fileEntryCount);
    ((TypeBatchControlRecord)localObject).Entry_Hash = paramACHFileCache.fileHash;
    ((TypeBatchControlRecord)localObject).Total_Debits = paramACHFileCache.fileDebitSum.longValue();
    ((TypeBatchControlRecord)localObject).Total_Credits = paramACHFileCache.fileCreditSum.longValue();
    ((TypeBatchControlRecord)localObject).Company_Identification = str2;
    ((TypeBatchControlRecord)localObject).Message_Authentication_Code = str1;
    ((TypeBatchControlRecord)localObject).Message_Authentication_CodeExists = true;
    ((TypeBatchControlRecord)localObject).Reserved6 = " ";
    ((TypeBatchControlRecord)localObject).Originating_DFI_Identification = paramACHFIInfo.getODFIACHId8();
    ((TypeBatchControlRecord)localObject).Batch_Number = l;
    return localObject;
  }
  
  private static TypeFileControlRecord a(ACHFileCache paramACHFileCache)
    throws FFSException
  {
    int i = 0;
    int j = (int)paramACHFileCache.recCount % 10;
    if (j != 0) {
      i = 10 - j;
    }
    TypeFileControlRecord localTypeFileControlRecord = new TypeFileControlRecord();
    localTypeFileControlRecord.Record_Type_Code = 9;
    localTypeFileControlRecord.Batch_Count = paramACHFileCache.fileBatchCount;
    localTypeFileControlRecord.Block_Count = ((int)((paramACHFileCache.recCount + i) / 10L));
    localTypeFileControlRecord.FileEntry_Addenda_Count = ((int)paramACHFileCache.fileEntryCount);
    localTypeFileControlRecord.Total_Debits = paramACHFileCache.fileDebitSum.longValue();
    localTypeFileControlRecord.Total_Credits = paramACHFileCache.fileCreditSum.longValue();
    localTypeFileControlRecord.Entry_Hash = paramACHFileCache.fileHash;
    localTypeFileControlRecord.File_Reserved = " ";
    return localTypeFileControlRecord;
  }
  
  private static Object a(ACHFIInfo paramACHFIInfo, boolean paramBoolean)
    throws FFSException
  {
    String[] arrayOfString = ACHAdapterUtil.generateFileCreationDateTime();
    if ((arrayOfString != null) && (arrayOfString.length != 2)) {}
    String str1 = arrayOfString[0];
    String str2 = arrayOfString[1];
    String str3 = "A";
    Object localObject = null;
    if (paramBoolean == true) {
      localObject = ACHAdapterUtil.buildADVFileHeader(paramACHFIInfo, str1, str2, str3);
    } else {
      localObject = ACHAdapterUtil.buildNonADVFileHeader(paramACHFIInfo, str1, str2, str3);
    }
    return localObject;
  }
  
  private static String a(int paramInt, String paramString)
    throws FFSException
  {
    String str = null;
    if (paramString == null)
    {
      FFSDebug.log("ACHAgent.getMessageName:: class code is null.  Set it to default value, which is empty string", 6);
      paramString = "";
    }
    switch (paramInt)
    {
    case 1: 
      if (paramString.equalsIgnoreCase("ADV")) {
        str = paramString + "FileHeaderRecord";
      } else {
        str = "FileHeaderRecord";
      }
      break;
    case 5: 
      if ((paramString.equalsIgnoreCase("CBR")) || (paramString.equalsIgnoreCase("PBR"))) {
        str = "CBRandPBRBatchHeaderRecord";
      } else {
        str = "BatchHeaderRecord";
      }
      break;
    case 6: 
      str = paramString + "EntryDetailRecord";
      break;
    case 7: 
      str = paramString + "AddendaRecord";
      break;
    case 8: 
      if (paramString.equalsIgnoreCase("ADV")) {
        str = paramString + "BatchControlRecord";
      } else {
        str = "BatchControlRecord";
      }
      break;
    case 9: 
      str = "FileControlRecord";
      break;
    case 2: 
    case 3: 
    case 4: 
    default: 
      throw new FFSException(22590, jdMethod_char(22590) + paramInt);
    }
    return str;
  }
  
  public static boolean addendaExists(String paramString)
    throws FFSException
  {
    String str = null;
    FFSDebug.log("addendaExists:: rec=", paramString, 6);
    if (paramString.length() < 94) {
      return false;
    }
    FFSDebug.log("addendaExists:: before substring", 6);
    try
    {
      str = paramString.substring(78, 79);
    }
    catch (Exception localException)
    {
      int j = 22460;
      throw new FFSException(j, jdMethod_char(j));
    }
    FFSDebug.log("addendaExists:: addendaIndicator:", str, 6);
    if (str.equals("1")) {
      return true;
    }
    if (str.equals("0")) {
      return false;
    }
    int i = 22560;
    throw new FFSException(i, jdMethod_char(i));
  }
  
  public static int getRecordType(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0))
    {
      int i = 22450;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    String str2;
    int k;
    try
    {
      str1 = paramString.substring(0, 1);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      k = 22450;
      FFSDebug.log(String.valueOf(k) + ": ", jdMethod_char(k), "\n      ", str2, 0);
      throw new FFSException(k, jdMethod_char(k));
    }
    try
    {
      int j = Integer.parseInt(str1);
      str2 = jdMethod_goto(j);
      if (str2.compareTo("Unknown") == 0)
      {
        k = 22450;
        FFSDebug.log(String.valueOf(k) + ": ", jdMethod_char(k), 0);
        throw new FFSException(k, jdMethod_char(k));
      }
      return j;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      k = 22550;
      FFSDebug.log(String.valueOf(k) + ": ", jdMethod_char(k), "\n      ", str2, 0);
      throw new FFSException(k, jdMethod_char(k));
    }
  }
  
  public static String getStdEntryClassCode(String paramString)
    throws FFSException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return null;
    }
    String str = null;
    try
    {
      str = paramString.substring(50, 53);
      if ((str.compareTo("ACK") == 0) || (str.compareTo("ARC") == 0) || (str.compareTo("ATX") == 0) || (str.compareTo("CBR") == 0) || (str.compareTo("CCD") == 0) || (str.compareTo("CIE") == 0) || (str.compareTo("CTX") == 0) || (str.compareTo("DNE") == 0) || (str.compareTo("ENR") == 0) || (str.compareTo("MTE") == 0) || (str.compareTo("PBR") == 0) || (str.compareTo("POP") == 0) || (str.compareTo("POS") == 0) || (str.compareTo("PPD") == 0) || (str.compareTo("RCK") == 0) || (str.compareTo("SHR") == 0) || (str.compareTo("TEL") == 0) || (str.compareTo("TRC") == 0) || (str.compareTo("TRX") == 0) || (str.compareTo("WEB") == 0) || (str.compareTo("XCK") == 0)) {
        return str;
      }
      int i = 22000;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i) + ", SEC received =" + str, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      int j = 22000;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", localException.getMessage() + ", SEC received =" + str, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
  }
  
  private static String aH(String paramString)
    throws FFSException
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      int i = 22010;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    try
    {
      return paramString.substring(40, 50);
    }
    catch (Exception localException)
    {
      String str = FFSDebug.stackTrace(localException);
      int j = 22010;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
  }
  
  private static String aI(String paramString)
    throws FFSException
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      int i = 22100;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    try
    {
      return paramString.substring(44, 54);
    }
    catch (Exception localException)
    {
      String str = FFSDebug.stackTrace(localException);
      int j = 22100;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
  }
  
  private static String aG(String paramString)
    throws FFSException
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      int i = 22020;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    try
    {
      return paramString.substring(79, 87);
    }
    catch (Exception localException)
    {
      String str = FFSDebug.stackTrace(localException);
      int j = 22020;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
  }
  
  public static String getBatchNo(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0))
    {
      int i = 22090;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    String str2;
    int j;
    try
    {
      str1 = paramString.substring(87, 94);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      j = 22090;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
    try
    {
      Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      j = 22270;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
    return str1;
  }
  
  public static String getServiceClassCode(String paramString)
    throws FFSException
  {
    String str1 = null;
    short s = 0;
    if ((paramString == null) || (paramString.length() == 0))
    {
      int i = 22030;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    String str2;
    int k;
    try
    {
      str1 = paramString.substring(1, 4);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      k = 22030;
      FFSDebug.log(String.valueOf(k) + ": ", jdMethod_char(k), "\n      ", str2, 0);
      throw new FFSException(k, jdMethod_char(k));
    }
    try
    {
      s = Short.parseShort(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      k = 22350;
      FFSDebug.log(String.valueOf(k) + ": ", jdMethod_char(k), "\n      ", str2, 0);
      throw new FFSException(k, jdMethod_char(k));
    }
    if (!ValueSetENum_ServiceClassCode.contains(s))
    {
      int j = 22340;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), str1, 0);
      throw new FFSException(j, jdMethod_char(j) + str1);
    }
    return str1;
  }
  
  public static int getTransactionCode(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      return 0;
    }
    String str2;
    int i;
    try
    {
      str1 = paramString.substring(1, 3);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      i = 22470;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    try
    {
      return Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      i = 22520;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
  }
  
  public static long getAmount(String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = null;
    if ((paramString1 == null) || (paramString1.length() == 0) || (paramString2 == null) || (paramString2.length() == 0)) {
      return 0L;
    }
    String str2;
    int i;
    try
    {
      if (paramString2.equals("ADV")) {
        str1 = paramString1.substring(27, 39);
      } else {
        str1 = paramString1.substring(29, 39);
      }
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      i = 22480;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    try
    {
      return Long.parseLong(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      i = 22530;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i) + str1, "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
  }
  
  public static BigDecimal getBDAmount(String paramString1, String paramString2)
    throws FFSException
  {
    return BigDecimal.valueOf(getAmount(paramString1, paramString2));
  }
  
  private static int aK(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      return 0;
    }
    String str2;
    int i;
    try
    {
      str1 = paramString.substring(3, 11);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      i = 22490;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    try
    {
      return Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      i = 22540;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
  }
  
  public static BigDecimal getTotalDebit(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0))
    {
      int i = 22110;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    String str2;
    int j;
    try
    {
      str1 = paramString.substring(20, 32);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      j = 22110;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
    try
    {
      return FFSUtil.getBigDecimal(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      j = 22280;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
  }
  
  public static BigDecimal getTotalCredit(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0))
    {
      int i = 22120;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    String str2;
    int j;
    try
    {
      str1 = paramString.substring(32, 44);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      j = 22120;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
    try
    {
      return FFSUtil.getBigDecimal(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      j = 22290;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
  }
  
  public static BigDecimal getFileTotalDebit(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0))
    {
      int i = 22700;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    String str2;
    int j;
    try
    {
      str1 = paramString.substring(31, 43);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      j = 22700;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
    try
    {
      return FFSUtil.getBigDecimal(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      j = 22820;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
  }
  
  public static BigDecimal getFileTotalCredit(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0))
    {
      int i = 22710;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    String str2;
    int j;
    try
    {
      str1 = paramString.substring(43, 55);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      j = 22710;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
    try
    {
      return FFSUtil.getBigDecimal(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      j = 22830;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
  }
  
  public static int getEntryAddeddaCount(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0))
    {
      int i = 22130;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    String str2;
    int j;
    try
    {
      str1 = paramString.substring(4, 10);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      j = 22130;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
    try
    {
      return Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      j = 22300;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
  }
  
  public static int getEntryAddendaCount(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      return 0;
    }
    String str2;
    int i;
    try
    {
      str1 = paramString.substring(4, 10);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      i = 22130;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    try
    {
      return Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      i = 22300;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
  }
  
  public static long getFileEntryAddeddaCount(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      return 0L;
    }
    String str2;
    int i;
    try
    {
      str1 = paramString.substring(13, 21);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      i = 22720;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    try
    {
      return new Long(str1).longValue();
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      i = 22840;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
  }
  
  public static long getEntryHash(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0))
    {
      int i = 22140;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    String str2;
    int j;
    try
    {
      str1 = paramString.substring(10, 20);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      j = 22140;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
    try
    {
      return new Long(str1).longValue();
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      j = 22310;
      FFSDebug.log(String.valueOf(j) + ": ", jdMethod_char(j), "\n      ", str2, 0);
      throw new FFSException(j, jdMethod_char(j));
    }
  }
  
  public static long getFileEntryHash(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      return 0L;
    }
    String str2;
    int i;
    try
    {
      str1 = paramString.substring(21, 31);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      i = 22730;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    try
    {
      return new Long(str1).longValue();
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      i = 22850;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
  }
  
  public static int getFileBatchCount(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      return 0;
    }
    String str2;
    int i;
    try
    {
      str1 = paramString.substring(1, 7);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      i = 22740;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    try
    {
      return Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      i = 22860;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
  }
  
  public static int getFileBlockCount(String paramString)
    throws FFSException
  {
    String str1 = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      return 0;
    }
    String str2;
    int i;
    try
    {
      str1 = paramString.substring(7, 13);
    }
    catch (Exception localException)
    {
      str2 = FFSDebug.stackTrace(localException);
      i = 22750;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
    try
    {
      return Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str2 = FFSDebug.stackTrace(localNumberFormatException);
      i = 22870;
      FFSDebug.log(String.valueOf(i) + ": ", jdMethod_char(i), "\n      ", str2, 0);
      throw new FFSException(i, jdMethod_char(i));
    }
  }
  
  public static boolean isDebit(int paramInt)
  {
    int i = paramInt % 10;
    return i >= 5;
  }
  
  private static String a(FFSStringTokenizer paramFFSStringTokenizer)
    throws FFSException
  {
    StringBuffer localStringBuffer = null;
    String str = null;
    FFSDebug.log("In getRecord:: ", 6);
    if (!paramFFSStringTokenizer.hasMoreTokens()) {
      return null;
    }
    localStringBuffer = new StringBuffer();
    str = paramFFSStringTokenizer.nextToken();
    FFSDebug.log("getRecord:: rec:", str, 6);
    if (getRecordType(str) == 1) {
      return str;
    }
    if (getRecordType(str) == 9) {
      return str;
    }
    if (getRecordType(str) == 5) {
      return str;
    }
    if (getRecordType(str) == 8) {
      return str;
    }
    if (getRecordType(str) == 6)
    {
      localStringBuffer.append(str);
      addendaExists(str);
      while (paramFFSStringTokenizer.hasMoreTokens())
      {
        str = paramFFSStringTokenizer.nextToken();
        if (getRecordType(str) != 7)
        {
          paramFFSStringTokenizer.moveToPrevious();
          break;
        }
        localStringBuffer.append(str);
      }
    }
    if (localStringBuffer.length() > 0) {
      return localStringBuffer.toString();
    }
    return null;
  }
  
  private static void a(ACHBatchCache paramACHBatchCache, String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    if (paramString1 != null)
    {
      FFSDebug.log("updateBatchHeaderValidationInfo::compACHId:", paramString1, 6);
      paramACHBatchCache.compACHId = paramString1;
    }
    FFSDebug.log("updateBatchHeaderValidationInfo::Org DFI ID:", paramString2, 6);
    FFSDebug.log("updateBatchHeaderValidationInfo::Batch No:", paramString3, 6);
    FFSDebug.log("updateBatchHeaderValidationInfo::Serice Class Code:", paramString4, 6);
    paramACHBatchCache.orgDfiId = paramString2;
    paramACHBatchCache.batchNo = paramString3;
    paramACHBatchCache.serviceClassCode = paramString4;
  }
  
  private static void a(ACHBatchCache paramACHBatchCache)
    throws FFSException
  {
    FFSDebug.log("resetBatchDetailValidationInfo called", 6);
    paramACHBatchCache.batchDebitSum = FFSUtil.getBigDecimal("0");
    paramACHBatchCache.batchCreditSum = FFSUtil.getBigDecimal("0");
    paramACHBatchCache.batchEntryCount = 0;
    paramACHBatchCache.batchHash = 0L;
    paramACHBatchCache.curRec = 0;
  }
  
  private static void jdMethod_if(ACHFileCache paramACHFileCache)
    throws FFSException
  {
    paramACHFileCache.fileDebitSum = paramACHFileCache.fileDebitSum.add(paramACHFileCache.batchCache.batchDebitSum);
    FFSDebug.log("updateFileValidationInfo::fileDebitSum After Updt:" + paramACHFileCache.fileDebitSum, 6);
    paramACHFileCache.fileCreditSum = paramACHFileCache.fileCreditSum.add(paramACHFileCache.batchCache.batchCreditSum);
    FFSDebug.log("updateFileValidationInfo::fileCreditSum After Updt:" + paramACHFileCache.fileCreditSum, 6);
    paramACHFileCache.fileHash += paramACHFileCache.batchCache.batchHash;
    FFSDebug.log("updateFileValidationInfo::fileHash After Updt:" + paramACHFileCache.fileHash, 6);
    paramACHFileCache.fileEntryCount += paramACHFileCache.batchCache.batchEntryCount;
    FFSDebug.log("updateFileValidationInfo::fileEntryCount After Updt:" + paramACHFileCache.fileEntryCount, 6);
    paramACHFileCache.fileBatchCount += 1;
    FFSDebug.log("updateFileValidationInfo::batchCount After Updt:" + paramACHFileCache.fileBatchCount, 6);
  }
  
  private static void a(ACHBatchCache paramACHBatchCache, ACHRecordInfo paramACHRecordInfo)
    throws FFSException
  {
    String str = null;
    int i = -1;
    BigDecimal localBigDecimal1 = FFSUtil.getBigDecimal("0.0");
    BigDecimal localBigDecimal2 = FFSUtil.getBigDecimal("0.0");
    long l = 0L;
    int j = 0;
    if (paramACHRecordInfo == null) {
      throw new FFSException(22170, jdMethod_char(22170));
    }
    if (paramACHBatchCache.stdEntryClassCode.equals("ADV")) {
      return;
    }
    int k;
    if (!paramACHBatchCache.stdEntryClassCode.equals("ADV"))
    {
      str = aI(paramACHRecordInfo.getRecordContent());
      FFSDebug.log("ACHAgent.validateBatch:: Company ACH ID from header:", paramACHBatchCache.compACHId, " from control:", str, 6);
      if (!str.equals(paramACHBatchCache.compACHId))
      {
        k = 22190;
        throw new FFSException(k, jdMethod_char(k));
      }
    }
    str = aG(paramACHRecordInfo.getRecordContent());
    FFSDebug.log("ACHAgent.validateBatch:: ORG DFI ID from header:", paramACHBatchCache.orgDfiId, " from control:", str, 6);
    if (!str.equals(paramACHBatchCache.orgDfiId))
    {
      k = 22200;
      throw new FFSException(k, jdMethod_char(k));
    }
    str = getBatchNo(paramACHRecordInfo.getRecordContent());
    FFSDebug.log("ACHAgent.validateBatch:: Batch Number from header:", paramACHBatchCache.batchNo, " from control:", str, 6);
    if (!str.equals(paramACHBatchCache.batchNo))
    {
      k = 22210;
      throw new FFSException(k, jdMethod_char(k));
    }
    str = getServiceClassCode(paramACHRecordInfo.getRecordContent());
    FFSDebug.log("ACHAgent.validateBatch:: Service Class code from header:", paramACHBatchCache.serviceClassCode, " from control:", str, 6);
    if (!str.equals(paramACHBatchCache.serviceClassCode))
    {
      k = 22220;
      throw new FFSException(k, jdMethod_char(k));
    }
    i = Integer.parseInt(paramACHBatchCache.serviceClassCode);
    localBigDecimal1 = getTotalDebit(paramACHRecordInfo.getRecordContent());
    localBigDecimal2 = getTotalCredit(paramACHRecordInfo.getRecordContent());
    l = getEntryHash(paramACHRecordInfo.getRecordContent());
    j = getEntryAddeddaCount(paramACHRecordInfo.getRecordContent());
    switch (i)
    {
    case 220: 
      if (FFSUtil.isPositive(paramACHBatchCache.batchDebitSum))
      {
        k = 22320;
        throw new FFSException(k, jdMethod_char(k));
      }
      break;
    case 225: 
      if (FFSUtil.isPositive(paramACHBatchCache.batchCreditSum))
      {
        k = 22330;
        throw new FFSException(k, jdMethod_char(k));
      }
      break;
    case 200: 
      break;
    case 280: 
      break;
    default: 
      throw new FFSException(22340, jdMethod_char(22340));
    }
    FFSDebug.log("ACHAgent.validateBatch:: Total debit calculated:" + paramACHBatchCache.batchDebitSum, " from control:" + localBigDecimal1, 6);
    if (localBigDecimal1.compareTo(paramACHBatchCache.batchDebitSum) != 0)
    {
      k = 22230;
      throw new FFSException(k, jdMethod_char(k));
    }
    FFSDebug.log("ACHAgent.validateBatch:: Total credit calculated:" + paramACHBatchCache.batchCreditSum, " from control:" + localBigDecimal2, 6);
    if (localBigDecimal2.compareTo(paramACHBatchCache.batchCreditSum) != 0)
    {
      k = 22240;
      throw new FFSException(k, jdMethod_char(k));
    }
    FFSDebug.log("ACHAgent.validateBatch:: Total entry count calculated:" + paramACHBatchCache.batchEntryCount, " from control:" + j, 6);
    if (j != paramACHBatchCache.batchEntryCount)
    {
      k = 22250;
      throw new FFSException(k, jdMethod_char(k));
    }
    FFSDebug.log("ACHAgent.validateBatch:: Entry Hash calculated:" + paramACHBatchCache.batchHash, " from control:" + l, 6);
    if (l != paramACHBatchCache.batchHash % 10000000000L)
    {
      k = 22260;
      throw new FFSException(k, jdMethod_char(k));
    }
  }
  
  private static void a(ACHFileCache paramACHFileCache, ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    ACHRecordInfo localACHRecordInfo = null;
    BigDecimal localBigDecimal1 = FFSUtil.getBigDecimal("0");
    BigDecimal localBigDecimal2 = FFSUtil.getBigDecimal("0");
    long l1 = 0L;
    long l2 = 0L;
    int i = 0;
    int j = 0;
    if (paramACHFileCache.batchCache.stdEntryClassCode.equals("ADV")) {
      return;
    }
    localACHRecordInfo = paramACHFileInfo.getFileControl();
    localBigDecimal1 = getFileTotalDebit(localACHRecordInfo.getRecordContent());
    localBigDecimal2 = getFileTotalCredit(localACHRecordInfo.getRecordContent());
    l1 = getFileEntryHash(localACHRecordInfo.getRecordContent());
    l2 = getFileEntryAddeddaCount(localACHRecordInfo.getRecordContent());
    i = getFileBatchCount(localACHRecordInfo.getRecordContent());
    j = getFileBlockCount(localACHRecordInfo.getRecordContent());
    FFSDebug.log("ACHAgent.validateFile:: Total debit calculated:" + paramACHFileCache.fileDebitSum, " from control:" + localBigDecimal1, 6);
    int k;
    if (localBigDecimal1.compareTo(paramACHFileCache.fileDebitSum) != 0)
    {
      k = 22760;
      throw new FFSException(k, jdMethod_char(k));
    }
    FFSDebug.log("ACHAgent.validateFile:: Total credit calculated:" + paramACHFileCache.fileCreditSum, " from control:" + localBigDecimal2, 6);
    if (localBigDecimal2.compareTo(paramACHFileCache.fileCreditSum) != 0)
    {
      k = 22770;
      throw new FFSException(k, jdMethod_char(k));
    }
    FFSDebug.log("ACHAgent.validateFile:: Entry count calculated:" + paramACHFileCache.fileEntryCount, " from control:" + l2, 6);
    if (l2 != paramACHFileCache.fileEntryCount)
    {
      k = 22780;
      throw new FFSException(k, jdMethod_char(k));
    }
    FFSDebug.log("ACHAgent.validateFile:: Entry Hash calculated:" + paramACHFileCache.fileHash, " from control:" + l1, 6);
    if (l1 != paramACHFileCache.fileHash % 10000000000L)
    {
      k = 22790;
      throw new FFSException(k, jdMethod_char(k));
    }
    FFSDebug.log("ACHAgent.validateFile:: Batch count calculated:" + paramACHFileCache.fileBatchCount, " from control:" + i, 6);
    if (i != paramACHFileCache.fileBatchCount)
    {
      k = 22800;
      throw new FFSException(k, jdMethod_char(k));
    }
    FFSDebug.log("ACHAgent.validateFile:: Block count from control:" + j, 6);
    FFSDebug.log("ACHAgent.validateFile:: record count in file:" + paramACHFileCache.recCount, 6);
    if ((j * 10 < paramACHFileCache.recCount) || (j * 10 - paramACHFileCache.recCount >= 10L))
    {
      k = 22810;
      throw new FFSException(k, jdMethod_char(k));
    }
  }
  
  private static String a(Exception paramException, FFSCache paramFFSCache)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    FFSDebug.log("In prepareLogMsg..", 0);
    Object localObject;
    if ((paramException instanceof FFSException))
    {
      localObject = (FFSException)paramException;
      FFSDebug.log("prepareLogMsg:: .FFSException, code:" + ((FFSException)localObject).getErrCode(), 6);
      localStringBuffer.append(((FFSException)localObject).getErrCode()).append(":");
      if ((((FFSException)localObject).getErrCode() >= 22000) && (((FFSException)localObject).getErrCode() <= 22380))
      {
        if (paramFFSCache != null) {
          if ((paramFFSCache instanceof ACHBatchCache))
          {
            localStringBuffer.append("Error at batch with batch id :");
            localStringBuffer.append(((ACHBatchCache)paramFFSCache).cacheId);
            localStringBuffer.append("\n\t\t");
          }
          else if ((paramFFSCache instanceof ACHFileCache))
          {
            localStringBuffer.append("Error at batch number :");
            localStringBuffer.append(((ACHFileCache)paramFFSCache).curBatch);
            if (((ACHFileCache)paramFFSCache).fileName == null)
            {
              localStringBuffer.append(" of File with file id:");
              localStringBuffer.append(((ACHFileCache)paramFFSCache).cacheId);
            }
            else
            {
              localStringBuffer.append(" of File :");
              localStringBuffer.append(((ACHFileCache)paramFFSCache).fileName);
            }
            localStringBuffer.append("\n\t\t");
          }
        }
      }
      else if ((((FFSException)localObject).getErrCode() >= 22450) && (((FFSException)localObject).getErrCode() <= 22600))
      {
        if (paramFFSCache != null) {
          if ((paramFFSCache instanceof ACHBatchCache))
          {
            localStringBuffer.append("Error at recood number :");
            localStringBuffer.append(((ACHBatchCache)paramFFSCache).curRec);
            localStringBuffer.append(" of Batch with batch id:");
            localStringBuffer.append(((ACHBatchCache)paramFFSCache).cacheId);
            localStringBuffer.append("\n\t\t");
          }
          else if ((paramFFSCache instanceof ACHFileCache))
          {
            localStringBuffer.append("Error at recood number :");
            localStringBuffer.append(((ACHFileCache)paramFFSCache).batchCache.curRec);
            localStringBuffer.append(" of Batch number :");
            localStringBuffer.append(((ACHFileCache)paramFFSCache).curBatch);
            if (((ACHFileCache)paramFFSCache).fileName == null)
            {
              localStringBuffer.append(" of File with file id:");
              localStringBuffer.append(((ACHFileCache)paramFFSCache).cacheId);
            }
            else
            {
              localStringBuffer.append(" of File :");
              localStringBuffer.append(((ACHFileCache)paramFFSCache).fileName);
            }
            localStringBuffer.append("\n\t\t");
          }
        }
      }
      else if ((((FFSException)localObject).getErrCode() >= 22650) && (((FFSException)localObject).getErrCode() <= 22890))
      {
        if ((paramFFSCache != null) && ((paramFFSCache instanceof ACHFileCache)))
        {
          localStringBuffer.append("Error in File with file id:");
          localStringBuffer.append(((ACHFileCache)paramFFSCache).cacheId);
          localStringBuffer.append("\n\t\t");
        }
      }
      else if (((((FFSException)localObject).getErrCode() == 22950) || (((FFSException)localObject).getErrCode() == 22960)) && (paramFFSCache != null)) {
        if ((paramFFSCache instanceof ACHBatchCache))
        {
          localStringBuffer.append("Error at Batch with batch id:");
          localStringBuffer.append(((ACHBatchCache)paramFFSCache).cacheId);
          localStringBuffer.append("\n\t\t");
        }
        else if ((paramFFSCache instanceof ACHFileCache))
        {
          localStringBuffer.append("Error at Batch number :");
          localStringBuffer.append(((ACHFileCache)paramFFSCache).curBatch);
          if (((ACHFileCache)paramFFSCache).fileName == null)
          {
            localStringBuffer.append(" of File with file id:");
            localStringBuffer.append(((ACHFileCache)paramFFSCache).cacheId);
          }
          else
          {
            localStringBuffer.append(" of File :");
            localStringBuffer.append(((ACHFileCache)paramFFSCache).fileName);
          }
          localStringBuffer.append("\n\t\t");
        }
      }
      localStringBuffer.append(((FFSException)localObject).getMessage());
    }
    else
    {
      localObject = FFSDebug.stackTrace(paramException);
      int i = 23010;
      FFSDebug.log("prepareLogMsg:: .Exception, code:", 6);
      localStringBuffer.append(i).append(":");
      localStringBuffer.append(jdMethod_char(i));
      if (paramFFSCache != null) {
        if ((paramFFSCache instanceof ACHBatchCache))
        {
          localStringBuffer.append("at recood number :");
          localStringBuffer.append(((ACHBatchCache)paramFFSCache).curRec);
          localStringBuffer.append("\n\t\t");
        }
        else if ((paramFFSCache instanceof ACHFileCache))
        {
          localStringBuffer.append("at recood number :");
          localStringBuffer.append(((ACHFileCache)paramFFSCache).batchCache.curRec);
          localStringBuffer.append("of Batch number :");
          localStringBuffer.append(((ACHFileCache)paramFFSCache).curBatch);
          if (((ACHFileCache)paramFFSCache).fileName == null)
          {
            localStringBuffer.append(" of File with file id:");
            localStringBuffer.append(((ACHFileCache)paramFFSCache).cacheId);
          }
          else
          {
            localStringBuffer.append(" of File :");
            localStringBuffer.append(((ACHFileCache)paramFFSCache).fileName);
          }
          localStringBuffer.append("\n\t\t");
        }
      }
      localStringBuffer.append((String)localObject);
    }
    return localStringBuffer.toString();
  }
  
  public static boolean isStarted()
  {
    return zg;
  }
  
  private static void aq()
    throws FFSException
  {
    if (!isStarted()) {
      throw new FFSException(23110, "ACHAgent has not been started. Method, start(Hashtable initInfo), must be called first!");
    }
  }
  
  public static String padRecord(String paramString)
  {
    StringBuffer localStringBuffer = null;
    int i = 0;
    if (paramString == null)
    {
      localStringBuffer = new StringBuffer();
      i = 94;
    }
    else
    {
      localStringBuffer = new StringBuffer(paramString);
      i = 94 - paramString.length();
    }
    for (int j = 0; j < i; j++) {
      localStringBuffer.append(" ");
    }
    paramString = localStringBuffer.toString();
    return paramString;
  }
  
  public static String trimNewLines(String paramString)
  {
    if (paramString == null) {
      return paramString;
    }
    while ((paramString.startsWith("\r\n")) || (paramString.startsWith("\r")) || (paramString.startsWith("\n"))) {
      if (paramString.startsWith("\r\n"))
      {
        FFSDebug.log("Removed \\r\\n in front of control", 6);
        paramString = paramString.substring(2);
      }
      else if (paramString.startsWith("\r"))
      {
        FFSDebug.log("Removed \\r in front of the control", 6);
        paramString = paramString.substring(1);
      }
      else if (paramString.startsWith("\n"))
      {
        FFSDebug.log("Removed \\n in front of the control", 6);
        paramString = paramString.substring(1);
      }
    }
    int i;
    String str;
    if (paramString.endsWith(" "))
    {
      i = paramString.lastIndexOf(ACHConsts.REC_DELIMS);
      str = paramString.substring(i + 1);
      if ((str != null) && (str.trim().length() == 0)) {
        paramString = paramString.substring(0, i);
      }
    }
    while ((paramString.endsWith("\r\n")) || (paramString.endsWith("\r")) || (paramString.endsWith("\n")))
    {
      if (paramString.endsWith("\r\n"))
      {
        FFSDebug.log("Removed \\r\\n at the end of control", 6);
        paramString = paramString.substring(0, paramString.length() - 2);
      }
      else if (paramString.endsWith("\r"))
      {
        FFSDebug.log("Removed \\r at the end of the control", 6);
        paramString = paramString.substring(0, paramString.length() - 1);
      }
      else if (paramString.endsWith("\n"))
      {
        FFSDebug.log("Removed \\n at the end of the control", 6);
        paramString = paramString.substring(0, paramString.length() - 1);
      }
      if (paramString.endsWith(" "))
      {
        i = paramString.lastIndexOf(ACHConsts.REC_DELIMS);
        str = paramString.substring(i + 1);
        if ((str != null) && (str.trim().length() == 0)) {
          paramString = paramString.substring(0, i);
        }
      }
    }
    FFSDebug.log("ACHAgent.trimNewLines end: " + paramString);
    return paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.achagent.ACHAgent
 * JD-Core Version:    0.7.0.1
 */