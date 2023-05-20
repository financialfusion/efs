package com.ffusion.ffs.bpw.backend;

import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;

public class ToBackendRunnable
  implements Runnable
{
  private IntraTrnInfo[] a;
  private Object jdField_if;
  private Object jdField_do;
  
  public ToBackendRunnable(IntraTrnInfo[] paramArrayOfIntraTrnInfo, Object paramObject1, Object paramObject2)
  {
    this.a = paramArrayOfIntraTrnInfo;
    this.jdField_if = paramObject1;
    this.jdField_do = paramObject2;
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: new 10	com/ffusion/ffs/bpw/interfaces/IntraTrnRslt
    //   3: dup
    //   4: invokespecial 11	com/ffusion/ffs/bpw/interfaces/IntraTrnRslt:<init>	()V
    //   7: astore_1
    //   8: new 12	com/ffusion/ffs/bpw/custimpl/ToBackend
    //   11: dup
    //   12: invokespecial 13	com/ffusion/ffs/bpw/custimpl/ToBackend:<init>	()V
    //   15: astore_2
    //   16: ldc 14
    //   18: invokestatic 1	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   21: astore_3
    //   22: aload_3
    //   23: ldc 15
    //   25: iconst_1
    //   26: anewarray 16	java/lang/Class
    //   29: dup
    //   30: iconst_0
    //   31: getstatic 17	com/ffusion/ffs/bpw/backend/ToBackendRunnable:class$com$ffusion$ffs$bpw$interfaces$IntraTrnInfo	Ljava/lang/Class;
    //   34: ifnonnull +15 -> 49
    //   37: ldc 18
    //   39: invokestatic 19	com/ffusion/ffs/bpw/backend/ToBackendRunnable:class$	(Ljava/lang/String;)Ljava/lang/Class;
    //   42: dup
    //   43: putstatic 17	com/ffusion/ffs/bpw/backend/ToBackendRunnable:class$com$ffusion$ffs$bpw$interfaces$IntraTrnInfo	Ljava/lang/Class;
    //   46: goto +6 -> 52
    //   49: getstatic 17	com/ffusion/ffs/bpw/backend/ToBackendRunnable:class$com$ffusion$ffs$bpw$interfaces$IntraTrnInfo	Ljava/lang/Class;
    //   52: aastore
    //   53: invokevirtual 20	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   56: astore 4
    //   58: aload_2
    //   59: aload_0
    //   60: getfield 7	com/ffusion/ffs/bpw/backend/ToBackendRunnable:a	[Lcom/ffusion/ffs/bpw/interfaces/IntraTrnInfo;
    //   63: iconst_0
    //   64: aaload
    //   65: invokevirtual 21	com/ffusion/ffs/bpw/custimpl/ToBackend:ProcessImmediateIntraTrn	(Lcom/ffusion/ffs/bpw/interfaces/IntraTrnInfo;)Lcom/ffusion/ffs/bpw/interfaces/IntraTrnRslt;
    //   68: astore_1
    //   69: goto +17 -> 86
    //   72: astore 4
    //   74: aload_1
    //   75: aload_2
    //   76: aload_0
    //   77: getfield 7	com/ffusion/ffs/bpw/backend/ToBackendRunnable:a	[Lcom/ffusion/ffs/bpw/interfaces/IntraTrnInfo;
    //   80: invokevirtual 23	com/ffusion/ffs/bpw/custimpl/ToBackend:ProcessIntraTrn	([Lcom/ffusion/ffs/bpw/interfaces/IntraTrnInfo;)I
    //   83: putfield 24	com/ffusion/ffs/bpw/interfaces/IntraTrnRslt:status	I
    //   86: jsr +75 -> 161
    //   89: goto +111 -> 200
    //   92: astore_2
    //   93: aload_0
    //   94: aconst_null
    //   95: putfield 8	com/ffusion/ffs/bpw/backend/ToBackendRunnable:jdField_if	Ljava/lang/Object;
    //   98: aload_2
    //   99: invokestatic 26	com/ffusion/ffs/util/FFSDebug:stackTrace	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   102: astore_3
    //   103: new 27	java/lang/StringBuffer
    //   106: dup
    //   107: invokespecial 28	java/lang/StringBuffer:<init>	()V
    //   110: ldc 29
    //   112: invokevirtual 30	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   115: aload_3
    //   116: invokevirtual 30	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   119: invokevirtual 31	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   122: invokestatic 32	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;)V
    //   125: new 27	java/lang/StringBuffer
    //   128: dup
    //   129: invokespecial 28	java/lang/StringBuffer:<init>	()V
    //   132: ldc 29
    //   134: invokevirtual 30	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   137: aload_3
    //   138: invokevirtual 30	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   141: invokevirtual 31	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   144: invokestatic 33	com/ffusion/ffs/util/FFSDebug:console	(Ljava/lang/String;)V
    //   147: jsr +14 -> 161
    //   150: goto +50 -> 200
    //   153: astore 5
    //   155: jsr +6 -> 161
    //   158: aload 5
    //   160: athrow
    //   161: astore 6
    //   163: aload_0
    //   164: getfield 8	com/ffusion/ffs/bpw/backend/ToBackendRunnable:jdField_if	Ljava/lang/Object;
    //   167: ifnull +14 -> 181
    //   170: aload_0
    //   171: getfield 8	com/ffusion/ffs/bpw/backend/ToBackendRunnable:jdField_if	Ljava/lang/Object;
    //   174: checkcast 34	com/ffusion/ffs/bpw/backend/BackendStatus
    //   177: aload_1
    //   178: invokevirtual 35	com/ffusion/ffs/bpw/backend/BackendStatus:setResult	(Lcom/ffusion/ffs/bpw/interfaces/IntraTrnRslt;)V
    //   181: aload_0
    //   182: getfield 9	com/ffusion/ffs/bpw/backend/ToBackendRunnable:jdField_do	Ljava/lang/Object;
    //   185: ifnull +13 -> 198
    //   188: aload_0
    //   189: getfield 9	com/ffusion/ffs/bpw/backend/ToBackendRunnable:jdField_do	Ljava/lang/Object;
    //   192: checkcast 36	com/ffusion/ffs/bpw/backend/MonitorBackend
    //   195: invokevirtual 37	com/ffusion/ffs/bpw/backend/MonitorBackend:done	()V
    //   198: ret 6
    //   200: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	201	0	this	ToBackendRunnable
    //   7	171	1	localIntraTrnRslt	com.ffusion.ffs.bpw.interfaces.IntraTrnRslt
    //   15	61	2	localToBackend	com.ffusion.ffs.bpw.custimpl.ToBackend
    //   92	7	2	localThrowable	java.lang.Throwable
    //   21	117	3	localObject1	Object
    //   56	1	4	localMethod	java.lang.reflect.Method
    //   72	1	4	localNoSuchMethodException	java.lang.NoSuchMethodException
    //   153	6	5	localObject2	Object
    //   161	1	6	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   22	69	72	java/lang/NoSuchMethodException
    //   8	86	92	java/lang/Throwable
    //   8	89	153	finally
    //   92	150	153	finally
    //   153	158	153	finally
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.backend.ToBackendRunnable
 * JD-Core Version:    0.7.0.1
 */