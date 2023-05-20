package com.ffusion.fileimporter.fileadapters.banklookup;

import com.ffusion.banklookup.FinancialInstitutionException;
import com.ffusion.banklookup.datasource.adapters.SwiftAdapter;
import com.ffusion.csil.FIException;
import com.ffusion.fileimporter.fileadapters.IFileAdapter;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.util.HashMap;

public class SwiftUpdateAdapter
  implements IFileAdapter
{
  private int aQW = 100;
  private boolean aQV = true;
  private static final char aQU = '\t';
  private int aQY = 0;
  private int aQX = -1;
  final int aQT = 10;
  
  public void initialize(HashMap paramHashMap)
    throws FIException
  {
    try
    {
      
    }
    catch (Exception localException)
    {
      FIException localFIException = new FIException("com.ffusion.fileimporter.banklookup.SwiftUpdateAdapter.initialize", 3, "Unable to initialize Swift file: ", localException);
      DebugLog.throwing("com.ffusion.fileimporter.banklookup.SwiftUpdateAdapter.initialize", localFIException);
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
    //   37: invokestatic 20	com/ffusion/banklookup/datasource/adapters/SwiftAdapter:getGenericTableAdapter	()Lcom/ffusion/banklookup/adapters/GenericTableAdapter;
    //   40: astore 8
    //   42: aload 8
    //   44: ifnonnull +16 -> 60
    //   47: new 9	com/ffusion/csil/FIException
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
    //   77: ifnull +55 -> 132
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
    //   106: aload_0
    //   107: aload 4
    //   109: aload 7
    //   111: invokespecial 30	com/ffusion/fileimporter/fileadapters/banklookup/SwiftUpdateAdapter:for	(Ljava/lang/String;Ljava/sql/Connection;)V
    //   114: iload 6
    //   116: bipush 10
    //   118: irem
    //   119: ifne -49 -> 70
    //   122: aload 7
    //   124: invokeinterface 31 1 0
    //   129: goto -59 -> 70
    //   132: invokestatic 32	com/ffusion/banklookup/adapters/GenericTableAdapter:closeAllPrepareStatements	()V
    //   135: invokestatic 33	com/ffusion/banklookup/adapters/FinancialInstitutionAdapter:closeAllPrepareStatements	()V
    //   138: aload 7
    //   140: invokeinterface 31 1 0
    //   145: goto +10 -> 155
    //   148: astore 9
    //   150: aload 9
    //   152: invokevirtual 34	java/lang/Exception:printStackTrace	()V
    //   155: jsr +72 -> 227
    //   158: goto +125 -> 283
    //   161: astore 9
    //   163: aload 7
    //   165: ifnull +10 -> 175
    //   168: aload 7
    //   170: invokeinterface 31 1 0
    //   175: goto +5 -> 180
    //   178: astore 10
    //   180: new 18	java/lang/StringBuffer
    //   183: dup
    //   184: invokespecial 19	java/lang/StringBuffer:<init>	()V
    //   187: ldc 35
    //   189: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   192: aload 4
    //   194: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   197: ldc 36
    //   199: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   202: invokevirtual 28	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   205: invokestatic 29	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   208: aload 9
    //   210: invokevirtual 34	java/lang/Exception:printStackTrace	()V
    //   213: jsr +14 -> 227
    //   216: goto +67 -> 283
    //   219: astore 11
    //   221: jsr +6 -> 227
    //   224: aload 11
    //   226: athrow
    //   227: astore 12
    //   229: aload 8
    //   231: ifnull +50 -> 281
    //   234: aload 7
    //   236: iconst_2
    //   237: invokeinterface 37 2 0
    //   242: aload 8
    //   244: pop
    //   245: aload 7
    //   247: invokestatic 38	com/ffusion/banklookup/adapters/GenericTableAdapter:releaseConnection	(Ljava/sql/Connection;)V
    //   250: goto +31 -> 281
    //   253: astore 13
    //   255: new 18	java/lang/StringBuffer
    //   258: dup
    //   259: invokespecial 19	java/lang/StringBuffer:<init>	()V
    //   262: ldc 39
    //   264: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   267: aload 13
    //   269: invokevirtual 40	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   272: invokevirtual 27	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   275: invokevirtual 28	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   278: invokestatic 29	com/ffusion/util/logging/DebugLog:log	(Ljava/lang/String;)V
    //   281: ret 12
    //   283: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	284	0	this	SwiftUpdateAdapter
    //   0	284	1	paramInputStream	java.io.InputStream
    //   0	284	2	paramHashMap	HashMap
    //   15	56	3	localBufferedReader	java.io.BufferedReader
    //   17	176	4	str	String
    //   26	1	5	localStringBuffer	java.lang.StringBuffer
    //   29	90	6	i	int
    //   32	214	7	localConnection	Connection
    //   35	208	8	localGenericTableAdapter	com.ffusion.banklookup.adapters.GenericTableAdapter
    //   148	3	9	localException1	Exception
    //   161	48	9	localException2	Exception
    //   178	1	10	localException3	Exception
    //   219	6	11	localObject1	Object
    //   227	1	12	localObject2	Object
    //   253	15	13	localException4	Exception
    // Exception table:
    //   from	to	target	type
    //   138	145	148	java/lang/Exception
    //   37	155	161	java/lang/Exception
    //   163	175	178	java/lang/Exception
    //   37	158	219	finally
    //   161	216	219	finally
    //   219	224	219	finally
    //   234	250	253	java/lang/Exception
  }
  
  private void ag(String paramString)
    throws FinancialInstitutionException
  {
    if (paramString.startsWith("SECTION")) {
      return;
    }
    HashMap localHashMap1 = new HashMap(10);
    HashMap localHashMap2 = new HashMap(10);
    int i = 0;
    int j = 0;
    this.aQY = 0;
    this.aQX = -1;
    this.aQX = paramString.indexOf('\t', 0);
    if (this.aQX < 0) {
      return;
    }
    String str = paramString.substring(0, this.aQX);
    String[] arrayOfString = new String[8];
    arrayOfString[0] = str;
    str = ah(paramString);
    int k = 1;
    while (str != null)
    {
      arrayOfString[(k++)] = str;
      str = ah(paramString);
    }
    Object localObject1;
    Object localObject2;
    if ((arrayOfString[1] != null) && (!arrayOfString[1].equals("")))
    {
      localHashMap1.put("ModificationFlag", "A");
      i = 1;
      localObject1 = new String(arrayOfString[1]);
      localObject2 = "XXX";
      if (arrayOfString[1].length() > 8)
      {
        localObject1 = ((String)localObject1).substring(0, 8);
        localObject2 = arrayOfString[1].substring(8, arrayOfString[1].length());
      }
      localHashMap1.put("BIC", localObject1);
      localHashMap1.put("BICBranchCode", localObject2);
    }
    if ((arrayOfString[2] != null) && (!arrayOfString[2].equals("")))
    {
      localHashMap2.put("ModificationFlag", "D");
      j = 1;
      localObject1 = new String(arrayOfString[2]);
      localObject2 = "XXX";
      if (arrayOfString[2].length() > 8)
      {
        localObject1 = ((String)localObject1).substring(0, 8);
        localObject2 = arrayOfString[2].substring(8, arrayOfString[2].length());
      }
      localHashMap2.put("BIC", localObject1);
      localHashMap2.put("BICBranchCode", localObject2);
    }
    if (i != 0) {
      localObject1 = localHashMap1;
    } else {
      localObject1 = localHashMap2;
    }
    str = arrayOfString[3];
    if (str.length() <= 35)
    {
      ((HashMap)localObject1).put("InstitutionName1", str.substring(0, str.length()));
    }
    else if (str.length() <= 70)
    {
      ((HashMap)localObject1).put("InstitutionName1", str.substring(0, 35));
      ((HashMap)localObject1).put("InstitutionName2", str.substring(35, str.length()));
    }
    else
    {
      ((HashMap)localObject1).put("InstitutionName1", str.substring(0, 35));
      ((HashMap)localObject1).put("InstitutionName2", str.substring(35, 70));
      ((HashMap)localObject1).put("InstitutionName3", str.substring(70, str.length()));
    }
    ((HashMap)localObject1).put("SubType", arrayOfString[4]);
    ((HashMap)localObject1).put("City", arrayOfString[5]);
    str = arrayOfString[6];
    if (str.length() <= 35)
    {
      ((HashMap)localObject1).put("Country1", str.substring(0, str.length()));
    }
    else
    {
      ((HashMap)localObject1).put("Country1", str.substring(0, 35));
      ((HashMap)localObject1).put("Country2", str.substring(35, str.length()));
    }
    if ((i != 0) && (j != 0))
    {
      localHashMap2.put("InstitutionName1", ((HashMap)localObject1).get("InstitutionName1"));
      localHashMap2.put("InstitutionName2", ((HashMap)localObject1).get("InstitutionName2"));
      localHashMap2.put("InstitutionName3", ((HashMap)localObject1).get("InstitutionName3"));
      localHashMap2.put("SubType", ((HashMap)localObject1).get("SubType"));
      localHashMap2.put("City", ((HashMap)localObject1).get("City"));
      localHashMap2.put("Country1", ((HashMap)localObject1).get("Country1"));
      localHashMap2.put("Country2", ((HashMap)localObject1).get("Country2"));
      localObject2 = new HashMap[2];
      localObject2[0] = localHashMap2;
      localObject2[1] = localHashMap1;
    }
    else
    {
      localObject2 = new HashMap[1];
      localObject2[0] = localObject1;
    }
    SwiftAdapter.insert((HashMap[])localObject2, null, this.aQV);
  }
  
  private void jdMethod_for(String paramString, Connection paramConnection)
  {
    if (paramString.startsWith("SECTION")) {
      return;
    }
    HashMap localHashMap1 = new HashMap(10);
    HashMap localHashMap2 = new HashMap(10);
    int i = 0;
    int j = 0;
    this.aQY = 0;
    this.aQX = -1;
    this.aQX = paramString.indexOf('\t', 0);
    if (this.aQX < 0) {
      return;
    }
    String str = paramString.substring(0, this.aQX);
    String[] arrayOfString = new String[8];
    arrayOfString[0] = str;
    str = ah(paramString);
    int k = 1;
    while (str != null)
    {
      arrayOfString[(k++)] = str;
      str = ah(paramString);
    }
    Object localObject1;
    Object localObject2;
    if ((arrayOfString[1] != null) && (!arrayOfString[1].equals("")))
    {
      localHashMap1.put("ModificationFlag", "A");
      i = 1;
      localObject1 = new String(arrayOfString[1]);
      localObject2 = "XXX";
      if (arrayOfString[1].length() > 8)
      {
        localObject1 = ((String)localObject1).substring(0, 8);
        localObject2 = arrayOfString[1].substring(8, arrayOfString[1].length());
      }
      localHashMap1.put("BIC", localObject1);
      localHashMap1.put("BICBranchCode", localObject2);
    }
    if ((arrayOfString[2] != null) && (!arrayOfString[2].equals("")))
    {
      localHashMap2.put("ModificationFlag", "D");
      j = 1;
      localObject1 = new String(arrayOfString[2]);
      localObject2 = "XXX";
      if (arrayOfString[2].length() > 8)
      {
        localObject1 = ((String)localObject1).substring(0, 8);
        localObject2 = arrayOfString[2].substring(8, arrayOfString[2].length());
      }
      localHashMap2.put("BIC", localObject1);
      localHashMap2.put("BICBranchCode", localObject2);
    }
    if (i != 0) {
      localObject1 = localHashMap1;
    } else {
      localObject1 = localHashMap2;
    }
    str = arrayOfString[3];
    if (str.length() <= 35)
    {
      ((HashMap)localObject1).put("InstitutionName1", str.substring(0, str.length()));
    }
    else if (str.length() <= 70)
    {
      ((HashMap)localObject1).put("InstitutionName1", str.substring(0, 35));
      ((HashMap)localObject1).put("InstitutionName2", str.substring(35, str.length()));
    }
    else
    {
      ((HashMap)localObject1).put("InstitutionName1", str.substring(0, 35));
      ((HashMap)localObject1).put("InstitutionName2", str.substring(35, 70));
      ((HashMap)localObject1).put("InstitutionName3", str.substring(70, str.length()));
    }
    ((HashMap)localObject1).put("SubType", arrayOfString[4]);
    ((HashMap)localObject1).put("City", arrayOfString[5]);
    str = arrayOfString[6];
    if (str.length() <= 35)
    {
      ((HashMap)localObject1).put("Country1", str.substring(0, str.length()));
    }
    else
    {
      ((HashMap)localObject1).put("Country1", str.substring(0, 35));
      ((HashMap)localObject1).put("Country2", str.substring(35, str.length()));
    }
    if ((i != 0) && (j != 0))
    {
      localHashMap2.put("InstitutionName1", ((HashMap)localObject1).get("InstitutionName1"));
      localHashMap2.put("InstitutionName2", ((HashMap)localObject1).get("InstitutionName2"));
      localHashMap2.put("InstitutionName3", ((HashMap)localObject1).get("InstitutionName3"));
      localHashMap2.put("SubType", ((HashMap)localObject1).get("SubType"));
      localHashMap2.put("City", ((HashMap)localObject1).get("City"));
      localHashMap2.put("Country1", ((HashMap)localObject1).get("Country1"));
      localHashMap2.put("Country2", ((HashMap)localObject1).get("Country2"));
      localObject2 = new HashMap[2];
      localObject2[0] = localHashMap2;
      localObject2[1] = localHashMap1;
    }
    else
    {
      localObject2 = new HashMap[1];
      localObject2[0] = localObject1;
    }
    try
    {
      SwiftAdapter.insert((HashMap[])localObject2, null, this.aQV, paramConnection);
    }
    catch (Exception localException)
    {
      DebugLog.log("Error while processing row: " + paramString + ": " + localException.getMessage());
    }
  }
  
  private String ah(String paramString)
  {
    String str = null;
    this.aQY = (this.aQX + 1);
    if ((this.aQY > 0) && (this.aQY < paramString.length()))
    {
      this.aQX = paramString.indexOf('\t', this.aQY);
      if (this.aQX < 0) {
        str = paramString.substring(this.aQY, paramString.length());
      } else {
        str = paramString.substring(this.aQY, this.aQX);
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.banklookup.SwiftUpdateAdapter
 * JD-Core Version:    0.7.0.1
 */