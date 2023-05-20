package com.ffusion.util.logging;

import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class PerfAdapter
{
  protected static String poolName = null;
  private static final String jdField_do = "select server_id from server_list where server_id = ?";
  private static final String a = "insert into server_list (server_id) values (?)";
  private static final String jdField_for = "insert into transaction_log (trans_type, server_id, log_time, avg_duration, success_cnt, failure_cnt) values (?,?,?,?,?,?)";
  private static String jdField_if = "00";
  private static int jdField_int = 100;
  
  public static synchronized void initialize(Properties paramProperties, String paramString)
    throws Exception
  {
    if (poolName == null)
    {
      poolName = ConnectionPool.init(paramProperties);
      if (poolName == null) {
        throw new Exception("PerfAdapter:Unable to create a DB connection");
      }
      if (paramString != null)
      {
        jdField_if = paramString;
      }
      else
      {
        String str = System.getProperties().getProperty("auditServerID");
        if ((str != null) && (str.length() > 0)) {
          jdField_if = str;
        }
      }
      addServerToDBList(jdField_if);
    }
  }
  
  protected static void addServerToDBList(String paramString)
  {
    String str = "PerfAdapter.addServerToDBList";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select server_id from server_list where server_id = ?");
      localPreparedStatement.setString(1, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select server_id from server_list where server_id = ?");
      boolean bool = localResultSet.next();
      DBUtil.closeResultSet(localResultSet);
      localResultSet = null;
      if (!bool)
      {
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into server_list (server_id) values (?)");
        localPreparedStatement.setString(1, paramString);
        DBUtil.executeUpdate(localPreparedStatement, "insert into server_list (server_id) values (?)");
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str + " Exception: ", localException);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  /* Error */
  public static void writeRecords(java.util.LinkedList paramLinkedList)
  {
    // Byte code:
    //   0: ldc 31
    //   2: astore_1
    //   3: aconst_null
    //   4: astore_2
    //   5: aconst_null
    //   6: astore_3
    //   7: aconst_null
    //   8: astore 4
    //   10: invokestatic 32	java/lang/System:currentTimeMillis	()J
    //   13: lstore 5
    //   15: getstatic 2	com/ffusion/util/logging/PerfAdapter:poolName	Ljava/lang/String;
    //   18: iconst_1
    //   19: iconst_2
    //   20: invokestatic 14	com/ffusion/util/db/DBUtil:getConnection	(Ljava/lang/String;ZI)Ljava/sql/Connection;
    //   23: astore_2
    //   24: aload_2
    //   25: ldc 33
    //   27: invokestatic 16	com/ffusion/util/db/DBUtil:prepareStatement	(Ljava/sql/Connection;Ljava/lang/String;)Ljava/sql/PreparedStatement;
    //   30: astore_3
    //   31: aconst_null
    //   32: astore 7
    //   34: aload_0
    //   35: invokevirtual 34	java/util/LinkedList:size	()I
    //   38: ifle +243 -> 281
    //   41: iconst_0
    //   42: istore 8
    //   44: iload 8
    //   46: getstatic 35	com/ffusion/util/logging/PerfAdapter:jdField_int	I
    //   49: if_icmpge +139 -> 188
    //   52: aload_0
    //   53: invokevirtual 34	java/util/LinkedList:size	()I
    //   56: ifle +132 -> 188
    //   59: aload_0
    //   60: invokevirtual 36	java/util/LinkedList:removeFirst	()Ljava/lang/Object;
    //   63: checkcast 37	com/ffusion/util/logging/PerfLogRecord
    //   66: astore 7
    //   68: aload 7
    //   70: invokevirtual 38	com/ffusion/util/logging/PerfLogRecord:getMessage	()Ljava/lang/String;
    //   73: astore 9
    //   75: aload 9
    //   77: ifnull +23 -> 100
    //   80: aload 9
    //   82: invokevirtual 11	java/lang/String:length	()I
    //   85: bipush 50
    //   87: if_icmple +13 -> 100
    //   90: aload 9
    //   92: iconst_0
    //   93: bipush 49
    //   95: invokevirtual 39	java/lang/String:substring	(II)Ljava/lang/String;
    //   98: astore 9
    //   100: aload_3
    //   101: iconst_1
    //   102: aload 9
    //   104: invokeinterface 17 3 0
    //   109: aload_3
    //   110: iconst_2
    //   111: getstatic 7	com/ffusion/util/logging/PerfAdapter:jdField_if	Ljava/lang/String;
    //   114: invokeinterface 17 3 0
    //   119: aload_3
    //   120: iconst_3
    //   121: new 40	java/sql/Timestamp
    //   124: dup
    //   125: aload 7
    //   127: invokevirtual 41	com/ffusion/util/logging/PerfLogRecord:getStartTime	()J
    //   130: invokespecial 42	java/sql/Timestamp:<init>	(J)V
    //   133: invokeinterface 43 3 0
    //   138: aload_3
    //   139: iconst_4
    //   140: aload 7
    //   142: invokevirtual 44	com/ffusion/util/logging/PerfLogRecord:getAverageResponseTime	()J
    //   145: l2i
    //   146: invokeinterface 45 3 0
    //   151: aload_3
    //   152: iconst_5
    //   153: aload 7
    //   155: invokevirtual 46	com/ffusion/util/logging/PerfLogRecord:getSuccessCount	()I
    //   158: invokeinterface 45 3 0
    //   163: aload_3
    //   164: bipush 6
    //   166: aload 7
    //   168: invokevirtual 47	com/ffusion/util/logging/PerfLogRecord:getFailCount	()I
    //   171: invokeinterface 45 3 0
    //   176: aload_3
    //   177: invokeinterface 48 1 0
    //   182: iinc 8 1
    //   185: goto -141 -> 44
    //   188: aload_3
    //   189: invokeinterface 49 1 0
    //   194: astore 8
    //   196: aload_3
    //   197: invokeinterface 50 1 0
    //   202: iconst_0
    //   203: istore 9
    //   205: iload 9
    //   207: aload 8
    //   209: arraylength
    //   210: if_icmpge +68 -> 278
    //   213: aload 8
    //   215: iload 9
    //   217: iaload
    //   218: istore 10
    //   220: iload 10
    //   222: iconst_1
    //   223: if_icmpge +49 -> 272
    //   226: iload 10
    //   228: bipush 254
    //   230: if_icmpeq +42 -> 272
    //   233: getstatic 51	java/util/logging/Level:SEVERE	Ljava/util/logging/Level;
    //   236: new 25	java/lang/StringBuffer
    //   239: dup
    //   240: invokespecial 26	java/lang/StringBuffer:<init>	()V
    //   243: ldc 52
    //   245: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   248: aload 7
    //   250: invokevirtual 38	com/ffusion/util/logging/PerfLogRecord:getMessage	()Ljava/lang/String;
    //   253: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   256: ldc 53
    //   258: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   261: iload 10
    //   263: invokevirtual 54	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   266: invokevirtual 29	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   269: invokestatic 55	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   272: iinc 9 1
    //   275: goto -70 -> 205
    //   278: goto -244 -> 34
    //   281: goto +47 -> 328
    //   284: astore 8
    //   286: getstatic 51	java/util/logging/Level:SEVERE	Ljava/util/logging/Level;
    //   289: new 25	java/lang/StringBuffer
    //   292: dup
    //   293: invokespecial 26	java/lang/StringBuffer:<init>	()V
    //   296: ldc 57
    //   298: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   301: aload 7
    //   303: invokevirtual 38	com/ffusion/util/logging/PerfLogRecord:getMessage	()Ljava/lang/String;
    //   306: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   309: ldc 58
    //   311: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   314: aload 8
    //   316: invokevirtual 59	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   319: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   322: invokevirtual 29	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   325: invokestatic 55	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   328: jsr +96 -> 424
    //   331: goto +123 -> 454
    //   334: astore 7
    //   336: getstatic 51	java/util/logging/Level:SEVERE	Ljava/util/logging/Level;
    //   339: new 25	java/lang/StringBuffer
    //   342: dup
    //   343: invokespecial 26	java/lang/StringBuffer:<init>	()V
    //   346: aload_1
    //   347: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   350: ldc 61
    //   352: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   355: aload 7
    //   357: invokevirtual 62	java/util/NoSuchElementException:toString	()Ljava/lang/String;
    //   360: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   363: invokevirtual 29	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   366: invokestatic 55	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   369: jsr +55 -> 424
    //   372: goto +82 -> 454
    //   375: astore 7
    //   377: getstatic 51	java/util/logging/Level:SEVERE	Ljava/util/logging/Level;
    //   380: new 25	java/lang/StringBuffer
    //   383: dup
    //   384: invokespecial 26	java/lang/StringBuffer:<init>	()V
    //   387: aload_1
    //   388: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   391: ldc 61
    //   393: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   396: aload 7
    //   398: invokevirtual 63	java/lang/Exception:toString	()Ljava/lang/String;
    //   401: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   404: invokevirtual 29	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   407: invokestatic 55	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   410: jsr +14 -> 424
    //   413: goto +41 -> 454
    //   416: astore 11
    //   418: jsr +6 -> 424
    //   421: aload 11
    //   423: athrow
    //   424: astore 12
    //   426: getstatic 2	com/ffusion/util/logging/PerfAdapter:poolName	Ljava/lang/String;
    //   429: aload_2
    //   430: aload_3
    //   431: aload 4
    //   433: invokestatic 24	com/ffusion/util/db/DBUtil:closeAll	(Ljava/lang/String;Ljava/sql/Connection;Ljava/sql/PreparedStatement;Ljava/sql/ResultSet;)V
    //   436: aload_0
    //   437: invokevirtual 64	java/util/LinkedList:clear	()V
    //   440: goto +5 -> 445
    //   443: astore 13
    //   445: aload_1
    //   446: lload 5
    //   448: iconst_1
    //   449: invokestatic 65	com/ffusion/util/logging/PerfLog:log	(Ljava/lang/String;JZ)V
    //   452: ret 12
    //   454: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	455	0	paramLinkedList	java.util.LinkedList
    //   2	444	1	str1	String
    //   4	426	2	localConnection	Connection
    //   6	425	3	localPreparedStatement	PreparedStatement
    //   8	424	4	localResultSet	ResultSet
    //   13	434	5	l	long
    //   32	270	7	localPerfLogRecord	PerfLogRecord
    //   334	22	7	localNoSuchElementException	java.util.NoSuchElementException
    //   375	22	7	localException	Exception
    //   42	141	8	i	int
    //   194	20	8	arrayOfInt	int[]
    //   284	31	8	localThrowable1	java.lang.Throwable
    //   73	30	9	str2	String
    //   203	70	9	j	int
    //   218	44	10	k	int
    //   416	6	11	localObject1	Object
    //   424	1	12	localObject2	Object
    //   443	1	13	localThrowable2	java.lang.Throwable
    // Exception table:
    //   from	to	target	type
    //   34	281	284	java/lang/Throwable
    //   15	328	334	java/util/NoSuchElementException
    //   15	328	375	java/lang/Exception
    //   15	331	416	finally
    //   334	372	416	finally
    //   375	413	416	finally
    //   416	421	416	finally
    //   436	440	443	java/lang/Throwable
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.PerfAdapter
 * JD-Core Version:    0.7.0.1
 */