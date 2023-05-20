package com.ffusion.fileimporter.fileadapters.banklookup;

import com.ffusion.csil.FIException;
import com.ffusion.fileimporter.fileadapters.IFileAdapter;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;

public class SwiftAdapter
  implements IFileAdapter
{
  int aQ1 = 100;
  boolean aQ0 = true;
  final int aQZ = 10;
  private HashMap aQ2;
  
  public void initCountries(HashMap paramHashMap)
  {
    this.aQ2 = paramHashMap;
  }
  
  public void initialize(HashMap paramHashMap)
    throws FIException
  {
    try
    {
      
    }
    catch (Exception localException)
    {
      FIException localFIException = new FIException("com.ffusion.fileimporter.banklookup.SwiftAdapter.initialize", 3, "Unable to initialize Swift file: ", localException);
      DebugLog.throwing("com.ffusion.fileimporter.banklookup.SwiftAdapter.initialize", localFIException);
      throw localFIException;
    }
  }
  
  public void open(HashMap paramHashMap)
    throws FIException
  {}
  
  public void close(HashMap paramHashMap)
    throws FIException
  {}
  
  /* Error */
  public void processFile(java.io.InputStream paramInputStream, HashMap paramHashMap)
    throws FIException
  {
    // Byte code:
    //   0: new 13	java/io/BufferedReader
    //   3: dup
    //   4: new 14	java/io/InputStreamReader
    //   7: dup
    //   8: aload_1
    //   9: invokespecial 15	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   12: invokespecial 16	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   15: astore_3
    //   16: aconst_null
    //   17: astore 4
    //   19: new 17	java/lang/StringBuffer
    //   22: dup
    //   23: invokespecial 18	java/lang/StringBuffer:<init>	()V
    //   26: astore 5
    //   28: iconst_0
    //   29: istore 6
    //   31: aconst_null
    //   32: astore 7
    //   34: aconst_null
    //   35: astore 8
    //   37: invokestatic 19	com/ffusion/banklookup/datasource/adapters/SwiftAdapter:getGenericTableAdapter	()Lcom/ffusion/banklookup/adapters/GenericTableAdapter;
    //   40: astore 8
    //   42: aload 8
    //   44: ifnonnull +16 -> 60
    //   47: new 8	com/ffusion/csil/FIException
    //   50: dup
    //   51: ldc 20
    //   53: iconst_1
    //   54: ldc 21
    //   56: invokespecial 22	com/ffusion/csil/FIException:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   59: athrow
    //   60: aload 8
    //   62: pop
    //   63: iconst_0
    //   64: iconst_2
    //   65: invokestatic 23	com/ffusion/banklookup/adapters/GenericTableAdapter:getConnection	(ZI)Ljava/sql/Connection;
    //   68: astore 7
    //   70: aload_3
    //   71: invokevirtual 24	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   74: dup
    //   75: astore 4
    //   77: ifnull +732 -> 809
    //   80: new 17	java/lang/StringBuffer
    //   83: dup
    //   84: invokespecial 18	java/lang/StringBuffer:<init>	()V
    //   87: ldc 25
    //   89: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   92: aload 4
    //   94: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   97: invokevirtual 27	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   100: invokestatic 28	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   103: aload 4
    //   105: ldc 29
    //   107: invokevirtual 30	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   110: ifeq +673 -> 783
    //   113: aload 4
    //   115: invokevirtual 31	java/lang/String:length	()I
    //   118: sipush 848
    //   121: if_icmpge +40 -> 161
    //   124: new 17	java/lang/StringBuffer
    //   127: dup
    //   128: invokespecial 18	java/lang/StringBuffer:<init>	()V
    //   131: ldc 32
    //   133: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   136: aload 4
    //   138: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   141: ldc 33
    //   143: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   146: sipush 9501
    //   149: invokevirtual 34	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   152: invokevirtual 27	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   155: invokestatic 28	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   158: goto -88 -> 70
    //   161: iinc 6 1
    //   164: new 35	java/util/HashMap
    //   167: dup
    //   168: bipush 28
    //   170: invokespecial 36	java/util/HashMap:<init>	(I)V
    //   173: astore 9
    //   175: aload 9
    //   177: ldc 37
    //   179: aload 4
    //   181: iconst_2
    //   182: iconst_3
    //   183: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   186: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   189: pop
    //   190: aload 9
    //   192: ldc 40
    //   194: aload 4
    //   196: iconst_3
    //   197: bipush 11
    //   199: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   202: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   205: pop
    //   206: aload 9
    //   208: ldc 41
    //   210: aload 4
    //   212: bipush 11
    //   214: bipush 14
    //   216: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   219: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   222: pop
    //   223: aload 9
    //   225: ldc 42
    //   227: aload 4
    //   229: bipush 14
    //   231: bipush 49
    //   233: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   236: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   239: pop
    //   240: aload 9
    //   242: ldc 43
    //   244: aload 4
    //   246: bipush 49
    //   248: bipush 84
    //   250: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   253: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   256: pop
    //   257: aload 9
    //   259: ldc 44
    //   261: aload 4
    //   263: bipush 84
    //   265: bipush 119
    //   267: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   270: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   273: pop
    //   274: aload 9
    //   276: ldc 45
    //   278: aload 4
    //   280: bipush 119
    //   282: sipush 154
    //   285: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   288: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   291: pop
    //   292: aload 9
    //   294: ldc 46
    //   296: aload 4
    //   298: sipush 154
    //   301: sipush 189
    //   304: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   307: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   310: pop
    //   311: aload 9
    //   313: ldc 47
    //   315: aload 4
    //   317: sipush 189
    //   320: sipush 224
    //   323: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   326: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   329: pop
    //   330: aload 9
    //   332: ldc 48
    //   334: aload 4
    //   336: sipush 224
    //   339: sipush 228
    //   342: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   345: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   348: pop
    //   349: aload 9
    //   351: ldc 49
    //   353: aload 4
    //   355: sipush 228
    //   358: sipush 288
    //   361: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   364: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   367: pop
    //   368: aload 9
    //   370: ldc 50
    //   372: aload 4
    //   374: sipush 288
    //   377: sipush 323
    //   380: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   383: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   386: pop
    //   387: aload 9
    //   389: ldc 51
    //   391: aload 4
    //   393: sipush 323
    //   396: sipush 358
    //   399: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   402: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   405: pop
    //   406: aload 9
    //   408: ldc 52
    //   410: aload 4
    //   412: sipush 358
    //   415: sipush 393
    //   418: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   421: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   424: pop
    //   425: aload 9
    //   427: ldc 53
    //   429: aload 4
    //   431: sipush 393
    //   434: sipush 428
    //   437: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   440: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   443: pop
    //   444: aload 9
    //   446: ldc 54
    //   448: aload 4
    //   450: sipush 428
    //   453: sipush 463
    //   456: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   459: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   462: pop
    //   463: aload 9
    //   465: ldc 55
    //   467: aload 4
    //   469: sipush 463
    //   472: sipush 498
    //   475: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   478: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   481: pop
    //   482: aload 9
    //   484: ldc 56
    //   486: aload 4
    //   488: sipush 498
    //   491: sipush 533
    //   494: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   497: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   500: pop
    //   501: aload 9
    //   503: ldc 57
    //   505: aload 4
    //   507: sipush 533
    //   510: sipush 568
    //   513: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   516: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   519: pop
    //   520: aload 4
    //   522: sipush 568
    //   525: sipush 603
    //   528: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   531: astore 10
    //   533: aload 10
    //   535: ifnull +16 -> 551
    //   538: aload 10
    //   540: invokevirtual 58	java/lang/String:trim	()Ljava/lang/String;
    //   543: ldc 59
    //   545: invokevirtual 60	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   548: ifeq +57 -> 605
    //   551: aload 4
    //   553: bipush 7
    //   555: bipush 9
    //   557: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   560: astore 11
    //   562: aload_0
    //   563: getfield 5	com/ffusion/fileimporter/fileadapters/banklookup/SwiftAdapter:aQ2	Ljava/util/HashMap;
    //   566: ifnull +29 -> 595
    //   569: aload_0
    //   570: getfield 5	com/ffusion/fileimporter/fileadapters/banklookup/SwiftAdapter:aQ2	Ljava/util/HashMap;
    //   573: aload 11
    //   575: invokevirtual 61	java/util/HashMap:containsKey	(Ljava/lang/Object;)Z
    //   578: ifeq +17 -> 595
    //   581: aload_0
    //   582: getfield 5	com/ffusion/fileimporter/fileadapters/banklookup/SwiftAdapter:aQ2	Ljava/util/HashMap;
    //   585: aload 11
    //   587: invokevirtual 62	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   590: checkcast 63	java/lang/String
    //   593: astore 10
    //   595: goto +10 -> 605
    //   598: astore 11
    //   600: ldc 64
    //   602: invokestatic 28	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   605: aload 9
    //   607: ldc 65
    //   609: aload 10
    //   611: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   614: pop
    //   615: aload 9
    //   617: ldc 66
    //   619: aload 4
    //   621: sipush 603
    //   624: sipush 638
    //   627: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   630: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   633: pop
    //   634: aload 9
    //   636: ldc 67
    //   638: aload 4
    //   640: sipush 638
    //   643: sipush 673
    //   646: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   649: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   652: pop
    //   653: aload 9
    //   655: ldc 68
    //   657: aload 4
    //   659: sipush 673
    //   662: sipush 708
    //   665: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   668: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   671: pop
    //   672: aload 9
    //   674: ldc 69
    //   676: aload 4
    //   678: sipush 708
    //   681: sipush 743
    //   684: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   687: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   690: pop
    //   691: aload 9
    //   693: ldc 70
    //   695: aload 4
    //   697: sipush 743
    //   700: sipush 778
    //   703: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   706: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   709: pop
    //   710: aload 9
    //   712: ldc 71
    //   714: aload 4
    //   716: sipush 778
    //   719: sipush 813
    //   722: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   725: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   728: pop
    //   729: aload 9
    //   731: ldc 72
    //   733: aload 4
    //   735: sipush 813
    //   738: sipush 848
    //   741: invokevirtual 38	java/lang/String:substring	(II)Ljava/lang/String;
    //   744: invokevirtual 39	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   747: pop
    //   748: aload 9
    //   750: aconst_null
    //   751: aload_0
    //   752: getfield 3	com/ffusion/fileimporter/fileadapters/banklookup/SwiftAdapter:aQ0	Z
    //   755: aload 7
    //   757: invokestatic 73	com/ffusion/banklookup/datasource/adapters/SwiftAdapter:insertData	(Ljava/util/HashMap;Ljava/util/HashMap;ZLjava/sql/Connection;)V
    //   760: iload 6
    //   762: bipush 10
    //   764: irem
    //   765: ifne +15 -> 780
    //   768: aload 7
    //   770: invokeinterface 74 1 0
    //   775: goto +5 -> 780
    //   778: astore 11
    //   780: goto -710 -> 70
    //   783: new 17	java/lang/StringBuffer
    //   786: dup
    //   787: invokespecial 18	java/lang/StringBuffer:<init>	()V
    //   790: ldc 75
    //   792: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   795: aload 4
    //   797: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   800: invokevirtual 27	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   803: invokestatic 28	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   806: goto -736 -> 70
    //   809: invokestatic 76	com/ffusion/banklookup/adapters/GenericTableAdapter:closeAllPrepareStatements	()V
    //   812: invokestatic 77	com/ffusion/banklookup/adapters/FinancialInstitutionAdapter:closeAllPrepareStatements	()V
    //   815: aload 7
    //   817: invokeinterface 74 1 0
    //   822: goto +38 -> 860
    //   825: astore 9
    //   827: new 17	java/lang/StringBuffer
    //   830: dup
    //   831: invokespecial 18	java/lang/StringBuffer:<init>	()V
    //   834: ldc 78
    //   836: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   839: aload 4
    //   841: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   844: ldc 79
    //   846: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   849: invokevirtual 27	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   852: invokestatic 28	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   855: aload 9
    //   857: invokevirtual 80	java/lang/Exception:printStackTrace	()V
    //   860: jsr +72 -> 932
    //   863: goto +125 -> 988
    //   866: astore 9
    //   868: aload 7
    //   870: ifnull +10 -> 880
    //   873: aload 7
    //   875: invokeinterface 74 1 0
    //   880: goto +5 -> 885
    //   883: astore 10
    //   885: new 17	java/lang/StringBuffer
    //   888: dup
    //   889: invokespecial 18	java/lang/StringBuffer:<init>	()V
    //   892: ldc 78
    //   894: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   897: aload 4
    //   899: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   902: ldc 79
    //   904: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   907: invokevirtual 27	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   910: invokestatic 28	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   913: aload 9
    //   915: invokevirtual 80	java/lang/Exception:printStackTrace	()V
    //   918: jsr +14 -> 932
    //   921: goto +67 -> 988
    //   924: astore 12
    //   926: jsr +6 -> 932
    //   929: aload 12
    //   931: athrow
    //   932: astore 13
    //   934: aload 8
    //   936: ifnull +50 -> 986
    //   939: aload 7
    //   941: iconst_2
    //   942: invokeinterface 81 2 0
    //   947: aload 8
    //   949: pop
    //   950: aload 7
    //   952: invokestatic 82	com/ffusion/banklookup/adapters/GenericTableAdapter:releaseConnection	(Ljava/sql/Connection;)V
    //   955: goto +31 -> 986
    //   958: astore 14
    //   960: new 17	java/lang/StringBuffer
    //   963: dup
    //   964: invokespecial 18	java/lang/StringBuffer:<init>	()V
    //   967: ldc 83
    //   969: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   972: aload 14
    //   974: invokevirtual 84	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   977: invokevirtual 26	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   980: invokevirtual 27	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   983: invokestatic 28	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   986: ret 13
    //   988: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	989	0	this	SwiftAdapter
    //   0	989	1	paramInputStream	java.io.InputStream
    //   0	989	2	paramHashMap	HashMap
    //   15	56	3	localBufferedReader	java.io.BufferedReader
    //   17	881	4	str1	java.lang.String
    //   26	1	5	localStringBuffer	java.lang.StringBuffer
    //   29	736	6	i	int
    //   32	919	7	localConnection	java.sql.Connection
    //   35	913	8	localGenericTableAdapter	com.ffusion.banklookup.adapters.GenericTableAdapter
    //   173	576	9	localHashMap	HashMap
    //   825	31	9	localException1	Exception
    //   866	48	9	localException2	Exception
    //   531	79	10	str2	java.lang.String
    //   883	1	10	localException3	Exception
    //   560	26	11	str3	java.lang.String
    //   598	1	11	localException4	Exception
    //   778	1	11	localException5	Exception
    //   924	6	12	localObject1	Object
    //   932	1	13	localObject2	Object
    //   958	15	14	localException6	Exception
    // Exception table:
    //   from	to	target	type
    //   551	595	598	java/lang/Exception
    //   768	775	778	java/lang/Exception
    //   815	822	825	java/lang/Exception
    //   37	860	866	java/lang/Exception
    //   868	880	883	java/lang/Exception
    //   37	863	924	finally
    //   866	921	924	finally
    //   924	929	924	finally
    //   939	955	958	java/lang/Exception
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.banklookup.SwiftAdapter
 * JD-Core Version:    0.7.0.1
 */