package com.ffusion.fileimporter.fileadapters.banklookup;

import com.ffusion.csil.FIException;
import com.ffusion.fileimporter.fileadapters.IFileAdapter;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;

public class FedWireAdapter
  implements IFileAdapter
{
  int aQ4 = 100;
  final int aQ3 = 10;
  
  public void initialize(HashMap paramHashMap)
    throws FIException
  {
    try
    {
      
    }
    catch (Exception localException)
    {
      FIException localFIException = new FIException("com.ffusion.fileimporter.banklookup.FedWireAdapter.initialize", 3, "Unable to initialize FedWire file: ", localException);
      DebugLog.throwing("com.ffusion.fileimporter.banklookup.FedWireAdapter.initialize", localFIException);
      throw localFIException;
    }
  }
  
  public void open(HashMap paramHashMap)
    throws FIException
  {
    try
    {
      com.ffusion.banklookup.datasource.adapters.FedWireAdapter.purge(this.aQ4);
    }
    catch (Exception localException)
    {
      FIException localFIException = new FIException("com.ffusion.fileimporter.banklookup.FedWireAdapter.open", 9500, "Unable to purge FedWire data: ", localException);
      DebugLog.throwing("com.ffusion.fileimporter.banklookup.FedWireAdapter.open", localFIException);
      throw localFIException;
    }
  }
  
  public void close(HashMap paramHashMap)
    throws FIException
  {}
  
  /* Error */
  public void processFile(java.io.InputStream paramInputStream, HashMap paramHashMap)
    throws FIException
  {
    // Byte code:
    //   0: new 14	java/io/BufferedReader
    //   3: dup
    //   4: new 15	java/io/InputStreamReader
    //   7: dup
    //   8: aload_1
    //   9: invokespecial 16	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   12: invokespecial 17	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   15: astore_3
    //   16: aconst_null
    //   17: astore 4
    //   19: new 18	java/lang/StringBuffer
    //   22: dup
    //   23: invokespecial 19	java/lang/StringBuffer:<init>	()V
    //   26: astore 5
    //   28: iconst_0
    //   29: istore 6
    //   31: aconst_null
    //   32: astore 7
    //   34: aconst_null
    //   35: astore 8
    //   37: invokestatic 20	com/ffusion/banklookup/datasource/adapters/FedWireAdapter:getGenericTableAdapter	()Lcom/ffusion/banklookup/adapters/GenericTableAdapter;
    //   40: astore 8
    //   42: aload 8
    //   44: ifnonnull +16 -> 60
    //   47: new 6	com/ffusion/csil/FIException
    //   50: dup
    //   51: ldc 21
    //   53: iconst_1
    //   54: ldc 22
    //   56: invokespecial 23	com/ffusion/csil/FIException:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   59: athrow
    //   60: aload 8
    //   62: pop
    //   63: iconst_0
    //   64: iconst_2
    //   65: invokestatic 24	com/ffusion/banklookup/adapters/GenericTableAdapter:getConnection	(ZI)Ljava/sql/Connection;
    //   68: astore 7
    //   70: aload_3
    //   71: invokevirtual 25	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   74: dup
    //   75: astore 4
    //   77: ifnull +286 -> 363
    //   80: new 18	java/lang/StringBuffer
    //   83: dup
    //   84: invokespecial 19	java/lang/StringBuffer:<init>	()V
    //   87: ldc 26
    //   89: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   92: aload 4
    //   94: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   97: invokevirtual 28	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   100: invokestatic 29	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   103: iinc 6 1
    //   106: aload 4
    //   108: invokevirtual 30	java/lang/String:length	()I
    //   111: bipush 93
    //   113: if_icmpeq +50 -> 163
    //   116: aload 4
    //   118: invokevirtual 30	java/lang/String:length	()I
    //   121: bipush 101
    //   123: if_icmpeq +40 -> 163
    //   126: new 18	java/lang/StringBuffer
    //   129: dup
    //   130: invokespecial 19	java/lang/StringBuffer:<init>	()V
    //   133: ldc 31
    //   135: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   138: aload 4
    //   140: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   143: ldc 32
    //   145: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   148: sipush 9501
    //   151: invokevirtual 33	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   154: invokevirtual 28	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   157: invokestatic 29	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   160: goto -90 -> 70
    //   163: new 34	java/util/HashMap
    //   166: dup
    //   167: bipush 10
    //   169: invokespecial 35	java/util/HashMap:<init>	(I)V
    //   172: astore 9
    //   174: aload 9
    //   176: ldc 36
    //   178: aload 4
    //   180: iconst_0
    //   181: bipush 9
    //   183: invokevirtual 37	java/lang/String:substring	(II)Ljava/lang/String;
    //   186: invokevirtual 38	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   189: pop
    //   190: aload 9
    //   192: ldc 39
    //   194: aload 4
    //   196: bipush 9
    //   198: bipush 27
    //   200: invokevirtual 37	java/lang/String:substring	(II)Ljava/lang/String;
    //   203: invokevirtual 38	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   206: pop
    //   207: aload 9
    //   209: ldc 40
    //   211: aload 4
    //   213: bipush 27
    //   215: bipush 63
    //   217: invokevirtual 37	java/lang/String:substring	(II)Ljava/lang/String;
    //   220: invokevirtual 38	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   223: pop
    //   224: aload 9
    //   226: ldc 41
    //   228: aload 4
    //   230: bipush 63
    //   232: bipush 65
    //   234: invokevirtual 37	java/lang/String:substring	(II)Ljava/lang/String;
    //   237: invokevirtual 38	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   240: pop
    //   241: aload 9
    //   243: ldc 42
    //   245: aload 4
    //   247: bipush 65
    //   249: bipush 90
    //   251: invokevirtual 37	java/lang/String:substring	(II)Ljava/lang/String;
    //   254: invokevirtual 38	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   257: pop
    //   258: aload 9
    //   260: ldc 43
    //   262: aload 4
    //   264: bipush 90
    //   266: bipush 91
    //   268: invokevirtual 37	java/lang/String:substring	(II)Ljava/lang/String;
    //   271: invokevirtual 38	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   274: pop
    //   275: aload 9
    //   277: ldc 44
    //   279: aload 4
    //   281: bipush 91
    //   283: bipush 92
    //   285: invokevirtual 37	java/lang/String:substring	(II)Ljava/lang/String;
    //   288: invokevirtual 38	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   291: pop
    //   292: aload 9
    //   294: ldc 45
    //   296: aload 4
    //   298: bipush 92
    //   300: bipush 93
    //   302: invokevirtual 37	java/lang/String:substring	(II)Ljava/lang/String;
    //   305: invokevirtual 38	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   308: pop
    //   309: aload 4
    //   311: invokevirtual 30	java/lang/String:length	()I
    //   314: bipush 101
    //   316: if_icmpne +20 -> 336
    //   319: aload 9
    //   321: ldc 46
    //   323: aload 4
    //   325: bipush 93
    //   327: bipush 101
    //   329: invokevirtual 37	java/lang/String:substring	(II)Ljava/lang/String;
    //   332: invokevirtual 38	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   335: pop
    //   336: aload 9
    //   338: aconst_null
    //   339: iconst_1
    //   340: aload 7
    //   342: invokestatic 47	com/ffusion/banklookup/datasource/adapters/FedWireAdapter:insertData	(Ljava/util/HashMap;Ljava/util/HashMap;ZLjava/sql/Connection;)V
    //   345: iload 6
    //   347: bipush 10
    //   349: irem
    //   350: ifne +10 -> 360
    //   353: aload 7
    //   355: invokeinterface 48 1 0
    //   360: goto -290 -> 70
    //   363: invokestatic 49	com/ffusion/banklookup/adapters/GenericTableAdapter:closeAllPrepareStatements	()V
    //   366: invokestatic 50	com/ffusion/banklookup/adapters/FinancialInstitutionAdapter:closeAllPrepareStatements	()V
    //   369: aload 7
    //   371: invokeinterface 48 1 0
    //   376: goto +38 -> 414
    //   379: astore 9
    //   381: new 18	java/lang/StringBuffer
    //   384: dup
    //   385: invokespecial 19	java/lang/StringBuffer:<init>	()V
    //   388: ldc 51
    //   390: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   393: aload 4
    //   395: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   398: ldc 52
    //   400: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   403: invokevirtual 28	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   406: invokestatic 29	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   409: aload 9
    //   411: invokevirtual 53	java/lang/Exception:printStackTrace	()V
    //   414: jsr +72 -> 486
    //   417: goto +125 -> 542
    //   420: astore 9
    //   422: aload 7
    //   424: ifnull +10 -> 434
    //   427: aload 7
    //   429: invokeinterface 48 1 0
    //   434: goto +5 -> 439
    //   437: astore 10
    //   439: new 18	java/lang/StringBuffer
    //   442: dup
    //   443: invokespecial 19	java/lang/StringBuffer:<init>	()V
    //   446: ldc 51
    //   448: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   451: aload 4
    //   453: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   456: ldc 52
    //   458: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   461: invokevirtual 28	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   464: invokestatic 29	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   467: aload 9
    //   469: invokevirtual 53	java/lang/Exception:printStackTrace	()V
    //   472: jsr +14 -> 486
    //   475: goto +67 -> 542
    //   478: astore 11
    //   480: jsr +6 -> 486
    //   483: aload 11
    //   485: athrow
    //   486: astore 12
    //   488: aload 8
    //   490: ifnull +50 -> 540
    //   493: aload 7
    //   495: iconst_2
    //   496: invokeinterface 54 2 0
    //   501: aload 8
    //   503: pop
    //   504: aload 7
    //   506: invokestatic 55	com/ffusion/banklookup/adapters/GenericTableAdapter:releaseConnection	(Ljava/sql/Connection;)V
    //   509: goto +31 -> 540
    //   512: astore 13
    //   514: new 18	java/lang/StringBuffer
    //   517: dup
    //   518: invokespecial 19	java/lang/StringBuffer:<init>	()V
    //   521: ldc 56
    //   523: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   526: aload 13
    //   528: invokevirtual 57	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   531: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   534: invokevirtual 28	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   537: invokestatic 29	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   540: ret 12
    //   542: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	543	0	this	FedWireAdapter
    //   0	543	1	paramInputStream	java.io.InputStream
    //   0	543	2	paramHashMap	HashMap
    //   15	56	3	localBufferedReader	java.io.BufferedReader
    //   17	435	4	str	java.lang.String
    //   26	1	5	localStringBuffer	java.lang.StringBuffer
    //   29	321	6	i	int
    //   32	473	7	localConnection	java.sql.Connection
    //   35	467	8	localGenericTableAdapter	com.ffusion.banklookup.adapters.GenericTableAdapter
    //   172	165	9	localHashMap	HashMap
    //   379	31	9	localException1	Exception
    //   420	48	9	localException2	Exception
    //   437	1	10	localException3	Exception
    //   478	6	11	localObject1	Object
    //   486	1	12	localObject2	Object
    //   512	15	13	localException4	Exception
    // Exception table:
    //   from	to	target	type
    //   369	376	379	java/lang/Exception
    //   37	414	420	java/lang/Exception
    //   422	434	437	java/lang/Exception
    //   37	417	478	finally
    //   420	475	478	finally
    //   478	483	478	finally
    //   493	509	512	java/lang/Exception
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.banklookup.FedWireAdapter
 * JD-Core Version:    0.7.0.1
 */