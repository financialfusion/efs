package com.ffusion.ffs.bpw.ACHServices;

import com.ibm.ejs.container.EJSWrapper;
import java.rmi.RemoteException;

public class EJSRemoteStatelessACHBPWServices_e0156d61
  extends EJSWrapper
  implements ACHBPWServices
{
  public EJSRemoteStatelessACHBPWServices_e0156d61()
    throws RemoteException
  {}
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchHist getACHBatchHistory(com.ffusion.ffs.bpw.interfaces.ACHBatchHist paramACHBatchHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: iconst_0
    //   43: aload_2
    //   44: aload_3
    //   45: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   48: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   51: astore 5
    //   53: aload 5
    //   55: aload_1
    //   56: invokevirtual 42	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHBatchHistory	(Lcom/ffusion/ffs/bpw/interfaces/ACHBatchHist;)Lcom/ffusion/ffs/bpw/interfaces/ACHBatchHist;
    //   59: astore 4
    //   61: goto +82 -> 143
    //   64: astore 5
    //   66: aload_2
    //   67: aload 5
    //   69: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   72: goto +71 -> 143
    //   75: astore 5
    //   77: aload_2
    //   78: aload 5
    //   80: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   83: new 11	java/rmi/RemoteException
    //   86: dup
    //   87: ldc 53
    //   89: aload 5
    //   91: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   94: athrow
    //   95: astore 7
    //   97: jsr +6 -> 103
    //   100: aload 7
    //   102: athrow
    //   103: astore 6
    //   105: aload_0
    //   106: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   109: aload_0
    //   110: iconst_0
    //   111: aload_2
    //   112: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   115: goto +23 -> 138
    //   118: astore 9
    //   120: jsr +6 -> 126
    //   123: aload 9
    //   125: athrow
    //   126: astore 8
    //   128: aload_0
    //   129: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   132: aload_2
    //   133: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   136: ret 8
    //   138: jsr -12 -> 126
    //   141: ret 6
    //   143: jsr -40 -> 103
    //   146: aload 4
    //   148: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	149	1	paramACHBatchHist	com.ffusion.ffs.bpw.interfaces.ACHBatchHist
    //   8	125	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	32	3	arrayOfObject	java.lang.Object[]
    //   15	132	4	localACHBatchHist	com.ffusion.ffs.bpw.interfaces.ACHBatchHist
    //   51	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   64	4	5	localRemoteException	RemoteException
    //   75	15	5	localThrowable	java.lang.Throwable
    //   103	1	6	localObject1	java.lang.Object
    //   95	6	7	localObject2	java.lang.Object
    //   126	1	8	localObject3	java.lang.Object
    //   118	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	64	64	java/rmi/RemoteException
    //   17	64	75	java/lang/Throwable
    //   17	95	95	finally
    //   143	146	95	finally
    //   105	118	118	finally
    //   138	141	118	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchHist getRecACHBatchHistory(com.ffusion.ffs.bpw.interfaces.ACHBatchHist paramACHBatchHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: iconst_1
    //   43: aload_2
    //   44: aload_3
    //   45: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   48: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   51: astore 5
    //   53: aload 5
    //   55: aload_1
    //   56: invokevirtual 69	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getRecACHBatchHistory	(Lcom/ffusion/ffs/bpw/interfaces/ACHBatchHist;)Lcom/ffusion/ffs/bpw/interfaces/ACHBatchHist;
    //   59: astore 4
    //   61: goto +82 -> 143
    //   64: astore 5
    //   66: aload_2
    //   67: aload 5
    //   69: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   72: goto +71 -> 143
    //   75: astore 5
    //   77: aload_2
    //   78: aload 5
    //   80: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   83: new 11	java/rmi/RemoteException
    //   86: dup
    //   87: ldc 53
    //   89: aload 5
    //   91: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   94: athrow
    //   95: astore 7
    //   97: jsr +6 -> 103
    //   100: aload 7
    //   102: athrow
    //   103: astore 6
    //   105: aload_0
    //   106: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   109: aload_0
    //   110: iconst_1
    //   111: aload_2
    //   112: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   115: goto +23 -> 138
    //   118: astore 9
    //   120: jsr +6 -> 126
    //   123: aload 9
    //   125: athrow
    //   126: astore 8
    //   128: aload_0
    //   129: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   132: aload_2
    //   133: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   136: ret 8
    //   138: jsr -12 -> 126
    //   141: ret 6
    //   143: jsr -40 -> 103
    //   146: aload 4
    //   148: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	149	1	paramACHBatchHist	com.ffusion.ffs.bpw.interfaces.ACHBatchHist
    //   8	125	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	32	3	arrayOfObject	java.lang.Object[]
    //   15	132	4	localACHBatchHist	com.ffusion.ffs.bpw.interfaces.ACHBatchHist
    //   51	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   64	4	5	localRemoteException	RemoteException
    //   75	15	5	localThrowable	java.lang.Throwable
    //   103	1	6	localObject1	java.lang.Object
    //   95	6	7	localObject2	java.lang.Object
    //   126	1	8	localObject3	java.lang.Object
    //   118	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	64	64	java/rmi/RemoteException
    //   17	64	75	java/lang/Throwable
    //   17	95	95	finally
    //   143	146	95	finally
    //   105	118	118	finally
    //   138	141	118	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchInfo addACHBatch(com.ffusion.ffs.bpw.interfaces.ACHBatchInfo paramACHBatchInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: iconst_2
    //   43: aload_2
    //   44: aload_3
    //   45: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   48: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   51: astore 5
    //   53: aload 5
    //   55: aload_1
    //   56: invokevirtual 73	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:addACHBatch	(Lcom/ffusion/ffs/bpw/interfaces/ACHBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHBatchInfo;
    //   59: astore 4
    //   61: goto +82 -> 143
    //   64: astore 5
    //   66: aload_2
    //   67: aload 5
    //   69: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   72: goto +71 -> 143
    //   75: astore 5
    //   77: aload_2
    //   78: aload 5
    //   80: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   83: new 11	java/rmi/RemoteException
    //   86: dup
    //   87: ldc 53
    //   89: aload 5
    //   91: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   94: athrow
    //   95: astore 7
    //   97: jsr +6 -> 103
    //   100: aload 7
    //   102: athrow
    //   103: astore 6
    //   105: aload_0
    //   106: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   109: aload_0
    //   110: iconst_2
    //   111: aload_2
    //   112: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   115: goto +23 -> 138
    //   118: astore 9
    //   120: jsr +6 -> 126
    //   123: aload 9
    //   125: athrow
    //   126: astore 8
    //   128: aload_0
    //   129: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   132: aload_2
    //   133: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   136: ret 8
    //   138: jsr -12 -> 126
    //   141: ret 6
    //   143: jsr -40 -> 103
    //   146: aload 4
    //   148: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	149	1	paramACHBatchInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchInfo
    //   8	125	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	32	3	arrayOfObject	java.lang.Object[]
    //   15	132	4	localACHBatchInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchInfo
    //   51	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   64	4	5	localRemoteException	RemoteException
    //   75	15	5	localThrowable	java.lang.Throwable
    //   103	1	6	localObject1	java.lang.Object
    //   95	6	7	localObject2	java.lang.Object
    //   126	1	8	localObject3	java.lang.Object
    //   118	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	64	64	java/rmi/RemoteException
    //   17	64	75	java/lang/Throwable
    //   17	95	95	finally
    //   143	146	95	finally
    //   105	118	118	finally
    //   138	141	118	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchInfo canACHBatch(com.ffusion.ffs.bpw.interfaces.ACHBatchInfo paramACHBatchInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: iconst_3
    //   43: aload_2
    //   44: aload_3
    //   45: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   48: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   51: astore 5
    //   53: aload 5
    //   55: aload_1
    //   56: invokevirtual 76	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:canACHBatch	(Lcom/ffusion/ffs/bpw/interfaces/ACHBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHBatchInfo;
    //   59: astore 4
    //   61: goto +82 -> 143
    //   64: astore 5
    //   66: aload_2
    //   67: aload 5
    //   69: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   72: goto +71 -> 143
    //   75: astore 5
    //   77: aload_2
    //   78: aload 5
    //   80: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   83: new 11	java/rmi/RemoteException
    //   86: dup
    //   87: ldc 53
    //   89: aload 5
    //   91: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   94: athrow
    //   95: astore 7
    //   97: jsr +6 -> 103
    //   100: aload 7
    //   102: athrow
    //   103: astore 6
    //   105: aload_0
    //   106: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   109: aload_0
    //   110: iconst_3
    //   111: aload_2
    //   112: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   115: goto +23 -> 138
    //   118: astore 9
    //   120: jsr +6 -> 126
    //   123: aload 9
    //   125: athrow
    //   126: astore 8
    //   128: aload_0
    //   129: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   132: aload_2
    //   133: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   136: ret 8
    //   138: jsr -12 -> 126
    //   141: ret 6
    //   143: jsr -40 -> 103
    //   146: aload 4
    //   148: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	149	1	paramACHBatchInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchInfo
    //   8	125	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	32	3	arrayOfObject	java.lang.Object[]
    //   15	132	4	localACHBatchInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchInfo
    //   51	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   64	4	5	localRemoteException	RemoteException
    //   75	15	5	localThrowable	java.lang.Throwable
    //   103	1	6	localObject1	java.lang.Object
    //   95	6	7	localObject2	java.lang.Object
    //   126	1	8	localObject3	java.lang.Object
    //   118	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	64	64	java/rmi/RemoteException
    //   17	64	75	java/lang/Throwable
    //   17	95	95	finally
    //   143	146	95	finally
    //   105	118	118	finally
    //   138	141	118	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchInfo exportACHBatch(com.ffusion.ffs.bpw.interfaces.ACHBatchInfo paramACHBatchInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: iconst_4
    //   43: aload_2
    //   44: aload_3
    //   45: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   48: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   51: astore 5
    //   53: aload 5
    //   55: aload_1
    //   56: invokevirtual 81	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:exportACHBatch	(Lcom/ffusion/ffs/bpw/interfaces/ACHBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHBatchInfo;
    //   59: astore 4
    //   61: goto +93 -> 154
    //   64: astore 5
    //   66: aload_2
    //   67: aload 5
    //   69: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   72: goto +82 -> 154
    //   75: astore 5
    //   77: aload_2
    //   78: aload 5
    //   80: invokevirtual 84	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   83: aload 5
    //   85: athrow
    //   86: astore 5
    //   88: aload_2
    //   89: aload 5
    //   91: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   94: new 11	java/rmi/RemoteException
    //   97: dup
    //   98: ldc 53
    //   100: aload 5
    //   102: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   105: athrow
    //   106: astore 7
    //   108: jsr +6 -> 114
    //   111: aload 7
    //   113: athrow
    //   114: astore 6
    //   116: aload_0
    //   117: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: iconst_4
    //   122: aload_2
    //   123: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   126: goto +23 -> 149
    //   129: astore 9
    //   131: jsr +6 -> 137
    //   134: aload 9
    //   136: athrow
    //   137: astore 8
    //   139: aload_0
    //   140: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   143: aload_2
    //   144: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   147: ret 8
    //   149: jsr -12 -> 137
    //   152: ret 6
    //   154: jsr -40 -> 114
    //   157: aload 4
    //   159: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	160	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	160	1	paramACHBatchInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchInfo
    //   8	136	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	32	3	arrayOfObject	java.lang.Object[]
    //   15	143	4	localACHBatchInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchInfo
    //   51	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   64	4	5	localRemoteException	RemoteException
    //   75	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   86	15	5	localThrowable	java.lang.Throwable
    //   114	1	6	localObject1	java.lang.Object
    //   106	6	7	localObject2	java.lang.Object
    //   137	1	8	localObject3	java.lang.Object
    //   129	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	64	64	java/rmi/RemoteException
    //   17	64	75	com/ffusion/ffs/interfaces/FFSException
    //   17	64	86	java/lang/Throwable
    //   17	106	106	finally
    //   154	157	106	finally
    //   116	129	129	finally
    //   149	152	129	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchInfo getACHBatch(com.ffusion.ffs.bpw.interfaces.ACHBatchInfo paramACHBatchInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: iconst_5
    //   43: aload_2
    //   44: aload_3
    //   45: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   48: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   51: astore 5
    //   53: aload 5
    //   55: aload_1
    //   56: invokevirtual 87	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHBatch	(Lcom/ffusion/ffs/bpw/interfaces/ACHBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHBatchInfo;
    //   59: astore 4
    //   61: goto +82 -> 143
    //   64: astore 5
    //   66: aload_2
    //   67: aload 5
    //   69: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   72: goto +71 -> 143
    //   75: astore 5
    //   77: aload_2
    //   78: aload 5
    //   80: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   83: new 11	java/rmi/RemoteException
    //   86: dup
    //   87: ldc 53
    //   89: aload 5
    //   91: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   94: athrow
    //   95: astore 7
    //   97: jsr +6 -> 103
    //   100: aload 7
    //   102: athrow
    //   103: astore 6
    //   105: aload_0
    //   106: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   109: aload_0
    //   110: iconst_5
    //   111: aload_2
    //   112: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   115: goto +23 -> 138
    //   118: astore 9
    //   120: jsr +6 -> 126
    //   123: aload 9
    //   125: athrow
    //   126: astore 8
    //   128: aload_0
    //   129: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   132: aload_2
    //   133: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   136: ret 8
    //   138: jsr -12 -> 126
    //   141: ret 6
    //   143: jsr -40 -> 103
    //   146: aload 4
    //   148: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	149	1	paramACHBatchInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchInfo
    //   8	125	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	32	3	arrayOfObject	java.lang.Object[]
    //   15	132	4	localACHBatchInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchInfo
    //   51	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   64	4	5	localRemoteException	RemoteException
    //   75	15	5	localThrowable	java.lang.Throwable
    //   103	1	6	localObject1	java.lang.Object
    //   95	6	7	localObject2	java.lang.Object
    //   126	1	8	localObject3	java.lang.Object
    //   118	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	64	64	java/rmi/RemoteException
    //   17	64	75	java/lang/Throwable
    //   17	95	95	finally
    //   143	146	95	finally
    //   105	118	118	finally
    //   138	141	118	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchInfo modACHBatch(com.ffusion.ffs.bpw.interfaces.ACHBatchInfo paramACHBatchInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 6
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 90	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:modACHBatch	(Lcom/ffusion/ffs/bpw/interfaces/ACHBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHBatchInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 6
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHBatchInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHBatchInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo addACHBatchTemplate(com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 7
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 94	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:addACHBatchTemplate	(Lcom/ffusion/ffs/bpw/interfaces/ACHBatchTemplateInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHBatchTemplateInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 7
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHBatchTemplateInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHBatchTemplateInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo deleteACHBatchTemplate(com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 8
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 97	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:deleteACHBatchTemplate	(Lcom/ffusion/ffs/bpw/interfaces/ACHBatchTemplateInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHBatchTemplateInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 8
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHBatchTemplateInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHBatchTemplateInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo getACHBatchTemplate(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 9
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 101	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHBatchTemplate	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/ACHBatchTemplateInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 9
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHBatchTemplateInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo modACHBatchTemplate(com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 10
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 104	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:modACHBatchTemplate	(Lcom/ffusion/ffs/bpw/interfaces/ACHBatchTemplateInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHBatchTemplateInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 10
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHBatchTemplateInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHBatchTemplateInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo[] getACHBatchTemplate(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 107	[Lcom/ffusion/ffs/bpw/interfaces/ACHBatchTemplateInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   24: aload_0
    //   25: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   28: ifeq +12 -> 40
    //   31: iconst_1
    //   32: anewarray 34	java/lang/Object
    //   35: astore_3
    //   36: aload_3
    //   37: iconst_0
    //   38: aload_1
    //   39: aastore
    //   40: aload_0
    //   41: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 11
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 109	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHBatchTemplate	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/ACHBatchTemplateInfo;
    //   63: astore 4
    //   65: goto +83 -> 148
    //   68: astore 5
    //   70: aload_2
    //   71: aload 5
    //   73: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   76: goto +72 -> 148
    //   79: astore 5
    //   81: aload_2
    //   82: aload 5
    //   84: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   87: new 11	java/rmi/RemoteException
    //   90: dup
    //   91: ldc 53
    //   93: aload 5
    //   95: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   98: athrow
    //   99: astore 7
    //   101: jsr +6 -> 107
    //   104: aload 7
    //   106: athrow
    //   107: astore 6
    //   109: aload_0
    //   110: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 11
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   137: aload_2
    //   138: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   141: ret 8
    //   143: jsr -12 -> 131
    //   146: ret 6
    //   148: jsr -41 -> 107
    //   151: aload 4
    //   153: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	154	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	154	1	paramArrayOfString	java.lang.String[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfACHBatchTemplateInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo[]
    //   55	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   68	4	5	localRemoteException	RemoteException
    //   79	15	5	localThrowable	java.lang.Throwable
    //   107	1	6	localObject1	java.lang.Object
    //   99	6	7	localObject2	java.lang.Object
    //   131	1	8	localObject3	java.lang.Object
    //   123	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	68	68	java/rmi/RemoteException
    //   20	68	79	java/lang/Throwable
    //   20	99	99	finally
    //   148	151	99	finally
    //   109	123	123	finally
    //   143	146	123	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo[] getACHBatchTemplateByCompany(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 107	[Lcom/ffusion/ffs/bpw/interfaces/ACHBatchTemplateInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   24: aload_0
    //   25: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   28: ifeq +12 -> 40
    //   31: iconst_1
    //   32: anewarray 34	java/lang/Object
    //   35: astore_3
    //   36: aload_3
    //   37: iconst_0
    //   38: aload_1
    //   39: aastore
    //   40: aload_0
    //   41: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 12
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 113	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHBatchTemplateByCompany	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/ACHBatchTemplateInfo;
    //   63: astore 4
    //   65: goto +83 -> 148
    //   68: astore 5
    //   70: aload_2
    //   71: aload 5
    //   73: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   76: goto +72 -> 148
    //   79: astore 5
    //   81: aload_2
    //   82: aload 5
    //   84: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   87: new 11	java/rmi/RemoteException
    //   90: dup
    //   91: ldc 53
    //   93: aload 5
    //   95: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   98: athrow
    //   99: astore 7
    //   101: jsr +6 -> 107
    //   104: aload 7
    //   106: athrow
    //   107: astore 6
    //   109: aload_0
    //   110: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 12
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   137: aload_2
    //   138: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   141: ret 8
    //   143: jsr -12 -> 131
    //   146: ret 6
    //   148: jsr -41 -> 107
    //   151: aload 4
    //   153: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	154	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfACHBatchTemplateInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo[]
    //   55	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   68	4	5	localRemoteException	RemoteException
    //   79	15	5	localThrowable	java.lang.Throwable
    //   107	1	6	localObject1	java.lang.Object
    //   99	6	7	localObject2	java.lang.Object
    //   131	1	8	localObject3	java.lang.Object
    //   123	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	68	68	java/rmi/RemoteException
    //   20	68	79	java/lang/Throwable
    //   20	99	99	finally
    //   148	151	99	finally
    //   109	123	123	finally
    //   143	146	123	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo[] getACHBatchTemplateByGroup(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 107	[Lcom/ffusion/ffs/bpw/interfaces/ACHBatchTemplateInfo;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   25: aload_0
    //   26: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   29: ifeq +19 -> 48
    //   32: iconst_2
    //   33: anewarray 34	java/lang/Object
    //   36: astore 4
    //   38: aload 4
    //   40: iconst_0
    //   41: aload_1
    //   42: aastore
    //   43: aload 4
    //   45: iconst_1
    //   46: aload_2
    //   47: aastore
    //   48: aload_0
    //   49: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   52: aload_0
    //   53: bipush 13
    //   55: aload_3
    //   56: aload 4
    //   58: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   61: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   64: astore 6
    //   66: aload 6
    //   68: aload_1
    //   69: aload_2
    //   70: invokevirtual 117	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHBatchTemplateByGroup	(Ljava/lang/String;Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/ACHBatchTemplateInfo;
    //   73: astore 5
    //   75: goto +83 -> 158
    //   78: astore 6
    //   80: aload_3
    //   81: aload 6
    //   83: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   86: goto +72 -> 158
    //   89: astore 6
    //   91: aload_3
    //   92: aload 6
    //   94: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   97: new 11	java/rmi/RemoteException
    //   100: dup
    //   101: ldc 53
    //   103: aload 6
    //   105: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   108: athrow
    //   109: astore 8
    //   111: jsr +6 -> 117
    //   114: aload 8
    //   116: athrow
    //   117: astore 7
    //   119: aload_0
    //   120: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   123: aload_0
    //   124: bipush 13
    //   126: aload_3
    //   127: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   130: goto +23 -> 153
    //   133: astore 10
    //   135: jsr +6 -> 141
    //   138: aload 10
    //   140: athrow
    //   141: astore 9
    //   143: aload_0
    //   144: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   147: aload_3
    //   148: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   151: ret 9
    //   153: jsr -12 -> 141
    //   156: ret 7
    //   158: jsr -41 -> 117
    //   161: aload 5
    //   163: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	164	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	164	1	paramString1	java.lang.String
    //   0	164	2	paramString2	java.lang.String
    //   8	140	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	44	4	arrayOfObject	java.lang.Object[]
    //   19	143	5	arrayOfACHBatchTemplateInfo	com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo[]
    //   64	3	6	localACHBPWServicesBean	ACHBPWServicesBean
    //   78	4	6	localRemoteException	RemoteException
    //   89	15	6	localThrowable	java.lang.Throwable
    //   117	1	7	localObject1	java.lang.Object
    //   109	6	8	localObject2	java.lang.Object
    //   141	1	9	localObject3	java.lang.Object
    //   133	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   21	78	78	java/rmi/RemoteException
    //   21	78	89	java/lang/Throwable
    //   21	109	109	finally
    //   158	161	109	finally
    //   119	133	133	finally
    //   153	156	133	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo addACHCompanyOffsetAccount(com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 14
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 121	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:addACHCompanyOffsetAccount	(Lcom/ffusion/ffs/bpw/interfaces/ACHCompOffsetAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHCompOffsetAcctInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 14
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHCompOffsetAcctInfo	com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHCompOffsetAcctInfo	com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo canACHCompanyOffsetAccount(com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 15
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 124	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:canACHCompanyOffsetAccount	(Lcom/ffusion/ffs/bpw/interfaces/ACHCompOffsetAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHCompOffsetAcctInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 15
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHCompOffsetAcctInfo	com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHCompOffsetAcctInfo	com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo getACHCompanyOffsetAccountByAccountId(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 16
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 128	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHCompanyOffsetAccountByAccountId	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/ACHCompOffsetAcctInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 16
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHCompOffsetAcctInfo	com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo modACHCompanyOffsetAccount(com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 17
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 131	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:modACHCompanyOffsetAccount	(Lcom/ffusion/ffs/bpw/interfaces/ACHCompOffsetAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHCompOffsetAcctInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 17
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHCompOffsetAcctInfo	com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHCompOffsetAcctInfo	com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo[] getACHCompanyOffsetAccountByCompId(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 135	[Lcom/ffusion/ffs/bpw/interfaces/ACHCompOffsetAcctInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   24: aload_0
    //   25: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   28: ifeq +12 -> 40
    //   31: iconst_1
    //   32: anewarray 34	java/lang/Object
    //   35: astore_3
    //   36: aload_3
    //   37: iconst_0
    //   38: aload_1
    //   39: aastore
    //   40: aload_0
    //   41: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 18
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 137	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHCompanyOffsetAccountByCompId	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/ACHCompOffsetAcctInfo;
    //   63: astore 4
    //   65: goto +83 -> 148
    //   68: astore 5
    //   70: aload_2
    //   71: aload 5
    //   73: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   76: goto +72 -> 148
    //   79: astore 5
    //   81: aload_2
    //   82: aload 5
    //   84: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   87: new 11	java/rmi/RemoteException
    //   90: dup
    //   91: ldc 53
    //   93: aload 5
    //   95: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   98: athrow
    //   99: astore 7
    //   101: jsr +6 -> 107
    //   104: aload 7
    //   106: athrow
    //   107: astore 6
    //   109: aload_0
    //   110: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 18
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   137: aload_2
    //   138: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   141: ret 8
    //   143: jsr -12 -> 131
    //   146: ret 6
    //   148: jsr -41 -> 107
    //   151: aload 4
    //   153: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	154	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfACHCompOffsetAcctInfo	com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo[]
    //   55	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   68	4	5	localRemoteException	RemoteException
    //   79	15	5	localThrowable	java.lang.Throwable
    //   107	1	6	localObject1	java.lang.Object
    //   99	6	7	localObject2	java.lang.Object
    //   131	1	8	localObject3	java.lang.Object
    //   123	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	68	68	java/rmi/RemoteException
    //   20	68	79	java/lang/Throwable
    //   20	99	99	finally
    //   148	151	99	finally
    //   109	123	123	finally
    //   143	146	123	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo activateCompany(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 19
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 141	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:activateCompany	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/ACHCompanyInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 19
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHCompanyInfo	com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo addACHCompany(com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo paramACHCompanyInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 20
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 145	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:addACHCompany	(Lcom/ffusion/ffs/bpw/interfaces/ACHCompanyInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHCompanyInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 20
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHCompanyInfo	com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHCompanyInfo	com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo canACHCompany(com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo paramACHCompanyInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 21
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 148	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:canACHCompany	(Lcom/ffusion/ffs/bpw/interfaces/ACHCompanyInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHCompanyInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 21
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHCompanyInfo	com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHCompanyInfo	com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo getACHCompany(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 22
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 151	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHCompany	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/ACHCompanyInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 22
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHCompanyInfo	com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo modACHCompany(com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo paramACHCompanyInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 23
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 154	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:modACHCompany	(Lcom/ffusion/ffs/bpw/interfaces/ACHCompanyInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHCompanyInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 23
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHCompanyInfo	com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHCompanyInfo	com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHCompanyList getACHCompanyList(com.ffusion.ffs.bpw.interfaces.ACHCompanyList paramACHCompanyList)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 24
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 158	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHCompanyList	(Lcom/ffusion/ffs/bpw/interfaces/ACHCompanyList;)Lcom/ffusion/ffs/bpw/interfaces/ACHCompanyList;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 24
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHCompanyList	com.ffusion.ffs.bpw.interfaces.ACHCompanyList
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHCompanyList	com.ffusion.ffs.bpw.interfaces.ACHCompanyList
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryList getACHCompanySummaries(com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryList paramACHCompanySummaryList)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 25
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 162	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHCompanySummaries	(Lcom/ffusion/ffs/bpw/interfaces/ACHCompanySummaryList;)Lcom/ffusion/ffs/bpw/interfaces/ACHCompanySummaryList;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 25
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHCompanySummaryList	com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryList
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHCompanySummaryList	com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryList
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHFIInfo activateACHFI(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 26
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 166	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:activateACHFI	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/ACHFIInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 26
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHFIInfo	com.ffusion.ffs.bpw.interfaces.ACHFIInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHFIInfo addACHFIInfo(com.ffusion.ffs.bpw.interfaces.ACHFIInfo paramACHFIInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 27
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 170	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:addACHFIInfo	(Lcom/ffusion/ffs/bpw/interfaces/ACHFIInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHFIInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 27
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHFIInfo	com.ffusion.ffs.bpw.interfaces.ACHFIInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHFIInfo	com.ffusion.ffs.bpw.interfaces.ACHFIInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHFIInfo canACHFIInfo(com.ffusion.ffs.bpw.interfaces.ACHFIInfo paramACHFIInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 28
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 173	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:canACHFIInfo	(Lcom/ffusion/ffs/bpw/interfaces/ACHFIInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHFIInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 28
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHFIInfo	com.ffusion.ffs.bpw.interfaces.ACHFIInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHFIInfo	com.ffusion.ffs.bpw.interfaces.ACHFIInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHFIInfo getACHFIInfo(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 29
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 176	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHFIInfo	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/ACHFIInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 29
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHFIInfo	com.ffusion.ffs.bpw.interfaces.ACHFIInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHFIInfo modACHFIInfo(com.ffusion.ffs.bpw.interfaces.ACHFIInfo paramACHFIInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 30
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 179	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:modACHFIInfo	(Lcom/ffusion/ffs/bpw/interfaces/ACHFIInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHFIInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 30
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHFIInfo	com.ffusion.ffs.bpw.interfaces.ACHFIInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHFIInfo	com.ffusion.ffs.bpw.interfaces.ACHFIInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHFIInfo[] getFIInfoByRTN(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 183	[Lcom/ffusion/ffs/bpw/interfaces/ACHFIInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   24: aload_0
    //   25: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   28: ifeq +12 -> 40
    //   31: iconst_1
    //   32: anewarray 34	java/lang/Object
    //   35: astore_3
    //   36: aload_3
    //   37: iconst_0
    //   38: aload_1
    //   39: aastore
    //   40: aload_0
    //   41: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 31
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 185	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getFIInfoByRTN	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/ACHFIInfo;
    //   63: astore 4
    //   65: goto +83 -> 148
    //   68: astore 5
    //   70: aload_2
    //   71: aload 5
    //   73: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   76: goto +72 -> 148
    //   79: astore 5
    //   81: aload_2
    //   82: aload 5
    //   84: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   87: new 11	java/rmi/RemoteException
    //   90: dup
    //   91: ldc 53
    //   93: aload 5
    //   95: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   98: athrow
    //   99: astore 7
    //   101: jsr +6 -> 107
    //   104: aload 7
    //   106: athrow
    //   107: astore 6
    //   109: aload_0
    //   110: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 31
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   137: aload_2
    //   138: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   141: ret 8
    //   143: jsr -12 -> 131
    //   146: ret 6
    //   148: jsr -41 -> 107
    //   151: aload 4
    //   153: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	154	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfACHFIInfo	com.ffusion.ffs.bpw.interfaces.ACHFIInfo[]
    //   55	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   68	4	5	localRemoteException	RemoteException
    //   79	15	5	localThrowable	java.lang.Throwable
    //   107	1	6	localObject1	java.lang.Object
    //   99	6	7	localObject2	java.lang.Object
    //   131	1	8	localObject3	java.lang.Object
    //   123	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	68	68	java/rmi/RemoteException
    //   20	68	79	java/lang/Throwable
    //   20	99	99	finally
    //   148	151	99	finally
    //   109	123	123	finally
    //   143	146	123	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHFileHist getACHFileHistory(com.ffusion.ffs.bpw.interfaces.ACHFileHist paramACHFileHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 32
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 189	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHFileHistory	(Lcom/ffusion/ffs/bpw/interfaces/ACHFileHist;)Lcom/ffusion/ffs/bpw/interfaces/ACHFileHist;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 32
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHFileHist	com.ffusion.ffs.bpw.interfaces.ACHFileHist
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHFileHist	com.ffusion.ffs.bpw.interfaces.ACHFileHist
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHFileInfo addACHFile(com.ffusion.ffs.bpw.interfaces.ACHFileInfo paramACHFileInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 33
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 193	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:addACHFile	(Lcom/ffusion/ffs/bpw/interfaces/ACHFileInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHFileInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 33
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHFileInfo	com.ffusion.ffs.bpw.interfaces.ACHFileInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHFileInfo	com.ffusion.ffs.bpw.interfaces.ACHFileInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHFileInfo canACHFile(com.ffusion.ffs.bpw.interfaces.ACHFileInfo paramACHFileInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 34
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 196	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:canACHFile	(Lcom/ffusion/ffs/bpw/interfaces/ACHFileInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHFileInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 34
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHFileInfo	com.ffusion.ffs.bpw.interfaces.ACHFileInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHFileInfo	com.ffusion.ffs.bpw.interfaces.ACHFileInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHFileInfo getACHFile(com.ffusion.ffs.bpw.interfaces.ACHFileInfo paramACHFileInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 35
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 199	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHFile	(Lcom/ffusion/ffs/bpw/interfaces/ACHFileInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHFileInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 35
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHFileInfo	com.ffusion.ffs.bpw.interfaces.ACHFileInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHFileInfo	com.ffusion.ffs.bpw.interfaces.ACHFileInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo activatePayee(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 36
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 203	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:activatePayee	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 36
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHPayeeInfo	com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo addACHPayee(com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo paramACHPayeeInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 37
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 207	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:addACHPayee	(Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 37
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHPayeeInfo	com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHPayeeInfo	com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo canACHPayee(com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo paramACHPayeeInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 38
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 210	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:canACHPayee	(Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 38
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHPayeeInfo	com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHPayeeInfo	com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo getACHPayee(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 39
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 213	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHPayee	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 39
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHPayeeInfo	com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo modACHPayee(com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo paramACHPayeeInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 40
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 216	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:modACHPayee	(Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 40
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHPayeeInfo	com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHPayeeInfo	com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo updateACHPayeePrenoteStatus(com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo paramACHPayeeInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 41
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 219	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:updateACHPayeePrenoteStatus	(Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 41
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHPayeeInfo	com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHPayeeInfo	com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo[] addACHPayee(com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo[] paramArrayOfACHPayeeInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 222	[Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   24: aload_0
    //   25: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   28: ifeq +12 -> 40
    //   31: iconst_1
    //   32: anewarray 34	java/lang/Object
    //   35: astore_3
    //   36: aload_3
    //   37: iconst_0
    //   38: aload_1
    //   39: aastore
    //   40: aload_0
    //   41: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 42
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 224	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:addACHPayee	([Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeInfo;)[Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeInfo;
    //   63: astore 4
    //   65: goto +83 -> 148
    //   68: astore 5
    //   70: aload_2
    //   71: aload 5
    //   73: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   76: goto +72 -> 148
    //   79: astore 5
    //   81: aload_2
    //   82: aload 5
    //   84: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   87: new 11	java/rmi/RemoteException
    //   90: dup
    //   91: ldc 53
    //   93: aload 5
    //   95: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   98: athrow
    //   99: astore 7
    //   101: jsr +6 -> 107
    //   104: aload 7
    //   106: athrow
    //   107: astore 6
    //   109: aload_0
    //   110: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 42
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   137: aload_2
    //   138: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   141: ret 8
    //   143: jsr -12 -> 131
    //   146: ret 6
    //   148: jsr -41 -> 107
    //   151: aload 4
    //   153: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	154	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	154	1	paramArrayOfACHPayeeInfo	com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfACHPayeeInfo	com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo[]
    //   55	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   68	4	5	localRemoteException	RemoteException
    //   79	15	5	localThrowable	java.lang.Throwable
    //   107	1	6	localObject1	java.lang.Object
    //   99	6	7	localObject2	java.lang.Object
    //   131	1	8	localObject3	java.lang.Object
    //   123	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	68	68	java/rmi/RemoteException
    //   20	68	79	java/lang/Throwable
    //   20	99	99	finally
    //   148	151	99	finally
    //   109	123	123	finally
    //   143	146	123	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHPayeeList getACHPayeeList(com.ffusion.ffs.bpw.interfaces.ACHPayeeList paramACHPayeeList)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 43
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 228	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHPayeeList	(Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeList;)Lcom/ffusion/ffs/bpw/interfaces/ACHPayeeList;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 43
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramACHPayeeList	com.ffusion.ffs.bpw.interfaces.ACHPayeeList
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localACHPayeeList	com.ffusion.ffs.bpw.interfaces.ACHPayeeList
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo getACHSameDayEffDateInfo(com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 44
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 232	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHSameDayEffDateInfo	(Lcom/ffusion/ffs/bpw/interfaces/ACHSameDayEffDateInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHSameDayEffDateInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +83 -> 156
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 84	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   84: aload 5
    //   86: athrow
    //   87: astore 5
    //   89: aload_2
    //   90: aload 5
    //   92: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   95: new 11	java/rmi/RemoteException
    //   98: dup
    //   99: ldc 53
    //   101: aload 5
    //   103: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   106: athrow
    //   107: astore 7
    //   109: jsr +6 -> 115
    //   112: aload 7
    //   114: athrow
    //   115: astore 6
    //   117: aload_0
    //   118: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 44
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   145: aload_2
    //   146: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   149: ret 8
    //   151: jsr -12 -> 139
    //   154: ret 6
    //   156: jsr -41 -> 115
    //   159: aload 4
    //   161: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	162	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	162	1	paramACHSameDayEffDateInfo	com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localACHSameDayEffDateInfo	com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	com/ffusion/ffs/interfaces/FFSException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo setACHSameDayEffDateInfo(com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 45
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 235	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:setACHSameDayEffDateInfo	(Lcom/ffusion/ffs/bpw/interfaces/ACHSameDayEffDateInfo;)Lcom/ffusion/ffs/bpw/interfaces/ACHSameDayEffDateInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +83 -> 156
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 84	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   84: aload 5
    //   86: athrow
    //   87: astore 5
    //   89: aload_2
    //   90: aload 5
    //   92: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   95: new 11	java/rmi/RemoteException
    //   98: dup
    //   99: ldc 53
    //   101: aload 5
    //   103: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   106: athrow
    //   107: astore 7
    //   109: jsr +6 -> 115
    //   112: aload 7
    //   114: athrow
    //   115: astore 6
    //   117: aload_0
    //   118: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 45
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   145: aload_2
    //   146: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   149: ret 8
    //   151: jsr -12 -> 139
    //   154: ret 6
    //   156: jsr -41 -> 115
    //   159: aload 4
    //   161: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	162	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	162	1	paramACHSameDayEffDateInfo	com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localACHSameDayEffDateInfo	com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	com/ffusion/ffs/interfaces/FFSException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.BPWExtdHist getACHParticipantTotals(com.ffusion.ffs.bpw.interfaces.BPWExtdHist paramBPWExtdHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 46
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 239	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHParticipantTotals	(Lcom/ffusion/ffs/bpw/interfaces/BPWExtdHist;)Lcom/ffusion/ffs/bpw/interfaces/BPWExtdHist;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 46
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramBPWExtdHist	com.ffusion.ffs.bpw.interfaces.BPWExtdHist
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWExtdHist	com.ffusion.ffs.bpw.interfaces.BPWExtdHist
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.BPWExtdHist getACHTotals(com.ffusion.ffs.bpw.interfaces.BPWExtdHist paramBPWExtdHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 47
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 242	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getACHTotals	(Lcom/ffusion/ffs/bpw/interfaces/BPWExtdHist;)Lcom/ffusion/ffs/bpw/interfaces/BPWExtdHist;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 47
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramBPWExtdHist	com.ffusion.ffs.bpw.interfaces.BPWExtdHist
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWExtdHist	com.ffusion.ffs.bpw.interfaces.BPWExtdHist
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.PagingInfo getPagedACH(com.ffusion.ffs.bpw.interfaces.PagingInfo paramPagingInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 48
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 246	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getPagedACH	(Lcom/ffusion/ffs/bpw/interfaces/PagingInfo;)Lcom/ffusion/ffs/bpw/interfaces/PagingInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +83 -> 156
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 84	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   84: aload 5
    //   86: athrow
    //   87: astore 5
    //   89: aload_2
    //   90: aload 5
    //   92: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   95: new 11	java/rmi/RemoteException
    //   98: dup
    //   99: ldc 53
    //   101: aload 5
    //   103: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   106: athrow
    //   107: astore 7
    //   109: jsr +6 -> 115
    //   112: aload 7
    //   114: athrow
    //   115: astore 6
    //   117: aload_0
    //   118: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 48
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   145: aload_2
    //   146: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   149: ret 8
    //   151: jsr -12 -> 139
    //   154: ret 6
    //   156: jsr -41 -> 115
    //   159: aload 4
    //   161: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	162	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	162	1	paramPagingInfo	com.ffusion.ffs.bpw.interfaces.PagingInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPagingInfo	com.ffusion.ffs.bpw.interfaces.PagingInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	com/ffusion/ffs/interfaces/FFSException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo addRecACHBatch(com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo paramRecACHBatchInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 49
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 250	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:addRecACHBatch	(Lcom/ffusion/ffs/bpw/interfaces/RecACHBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/RecACHBatchInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 49
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramRecACHBatchInfo	com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecACHBatchInfo	com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo canRecACHBatch(com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo paramRecACHBatchInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 50
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 253	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:canRecACHBatch	(Lcom/ffusion/ffs/bpw/interfaces/RecACHBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/RecACHBatchInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 50
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramRecACHBatchInfo	com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecACHBatchInfo	com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo getRecACHBatch(com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo paramRecACHBatchInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 51
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 256	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getRecACHBatch	(Lcom/ffusion/ffs/bpw/interfaces/RecACHBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/RecACHBatchInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 51
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramRecACHBatchInfo	com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecACHBatchInfo	com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo modRecACHBatch(com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo paramRecACHBatchInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +12 -> 37
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload_0
    //   38: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 52
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 259	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:modRecACHBatch	(Lcom/ffusion/ffs/bpw/interfaces/RecACHBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/RecACHBatchInfo;
    //   60: astore 4
    //   62: goto +83 -> 145
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   73: goto +72 -> 145
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   84: new 11	java/rmi/RemoteException
    //   87: dup
    //   88: ldc 53
    //   90: aload 5
    //   92: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 7
    //   98: jsr +6 -> 104
    //   101: aload 7
    //   103: athrow
    //   104: astore 6
    //   106: aload_0
    //   107: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 52
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: aload 4
    //   150: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	151	1	paramRecACHBatchInfo	com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecACHBatchInfo	com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo
    //   52	3	5	localACHBPWServicesBean	ACHBPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	15	5	localThrowable	java.lang.Throwable
    //   104	1	6	localObject1	java.lang.Object
    //   96	6	7	localObject2	java.lang.Object
    //   128	1	8	localObject3	java.lang.Object
    //   120	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	java/lang/Throwable
    //   17	96	96	finally
    //   145	148	96	finally
    //   106	120	120	finally
    //   140	143	120	finally
  }
  
  /* Error */
  public java.lang.String getDefaultACHBatchEffectiveDate(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   22: aload_0
    //   23: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   26: ifeq +19 -> 45
    //   29: iconst_2
    //   30: anewarray 34	java/lang/Object
    //   33: astore 4
    //   35: aload 4
    //   37: iconst_0
    //   38: aload_1
    //   39: aastore
    //   40: aload 4
    //   42: iconst_1
    //   43: aload_2
    //   44: aastore
    //   45: aload_0
    //   46: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 53
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 263	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:getDefaultACHBatchEffectiveDate	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   70: astore 5
    //   72: goto +94 -> 166
    //   75: astore 6
    //   77: aload_3
    //   78: aload 6
    //   80: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   83: goto +83 -> 166
    //   86: astore 6
    //   88: aload_3
    //   89: aload 6
    //   91: invokevirtual 84	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   94: aload 6
    //   96: athrow
    //   97: astore 6
    //   99: aload_3
    //   100: aload 6
    //   102: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   105: new 11	java/rmi/RemoteException
    //   108: dup
    //   109: ldc 53
    //   111: aload 6
    //   113: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   116: athrow
    //   117: astore 8
    //   119: jsr +6 -> 125
    //   122: aload 8
    //   124: athrow
    //   125: astore 7
    //   127: aload_0
    //   128: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_0
    //   132: bipush 53
    //   134: aload_3
    //   135: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: goto +23 -> 161
    //   141: astore 10
    //   143: jsr +6 -> 149
    //   146: aload 10
    //   148: athrow
    //   149: astore 9
    //   151: aload_0
    //   152: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   155: aload_3
    //   156: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   159: ret 9
    //   161: jsr -12 -> 149
    //   164: ret 7
    //   166: jsr -41 -> 125
    //   169: aload 5
    //   171: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	172	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   0	172	1	paramString1	java.lang.String
    //   0	172	2	paramString2	java.lang.String
    //   8	148	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	154	5	str	java.lang.String
    //   61	3	6	localACHBPWServicesBean	ACHBPWServicesBean
    //   75	4	6	localRemoteException	RemoteException
    //   86	9	6	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   97	15	6	localThrowable	java.lang.Throwable
    //   125	1	7	localObject1	java.lang.Object
    //   117	6	8	localObject2	java.lang.Object
    //   149	1	9	localObject3	java.lang.Object
    //   141	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	75	75	java/rmi/RemoteException
    //   18	75	86	com/ffusion/ffs/interfaces/FFSException
    //   18	75	97	java/lang/Throwable
    //   18	117	117	finally
    //   166	169	117	finally
    //   127	141	141	finally
    //   161	164	141	finally
  }
  
  /* Error */
  public void disconnect()
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_1
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_2
    //   14: aload_0
    //   15: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   18: aload_0
    //   19: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   22: ifeq +8 -> 30
    //   25: iconst_0
    //   26: anewarray 34	java/lang/Object
    //   29: astore_2
    //   30: aload_0
    //   31: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   34: aload_0
    //   35: bipush 54
    //   37: aload_1
    //   38: aload_2
    //   39: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   42: checkcast 40	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean
    //   45: astore_3
    //   46: aload_3
    //   47: invokevirtual 266	com/ffusion/ffs/bpw/ACHServices/ACHBPWServicesBean:disconnect	()V
    //   50: goto +78 -> 128
    //   53: astore_3
    //   54: aload_1
    //   55: aload_3
    //   56: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   59: goto +69 -> 128
    //   62: astore_3
    //   63: aload_1
    //   64: aload_3
    //   65: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   68: new 11	java/rmi/RemoteException
    //   71: dup
    //   72: ldc 53
    //   74: aload_3
    //   75: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   78: athrow
    //   79: astore 5
    //   81: jsr +6 -> 87
    //   84: aload 5
    //   86: athrow
    //   87: astore 4
    //   89: aload_0
    //   90: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   93: aload_0
    //   94: bipush 54
    //   96: aload_1
    //   97: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   100: goto +23 -> 123
    //   103: astore 7
    //   105: jsr +6 -> 111
    //   108: aload 7
    //   110: athrow
    //   111: astore 6
    //   113: aload_0
    //   114: getfield 20	com/ffusion/ffs/bpw/ACHServices/EJSRemoteStatelessACHBPWServices_e0156d61:container	Lcom/ibm/ejs/container/EJSContainer;
    //   117: aload_1
    //   118: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   121: ret 6
    //   123: jsr -12 -> 111
    //   126: ret 4
    //   128: jsr -41 -> 87
    //   131: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	132	0	this	EJSRemoteStatelessACHBPWServices_e0156d61
    //   8	110	1	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	26	2	arrayOfObject	java.lang.Object[]
    //   45	2	3	localACHBPWServicesBean	ACHBPWServicesBean
    //   53	3	3	localRemoteException	RemoteException
    //   62	13	3	localThrowable	java.lang.Throwable
    //   87	1	4	localObject1	java.lang.Object
    //   79	6	5	localObject2	java.lang.Object
    //   111	1	6	localObject3	java.lang.Object
    //   103	6	7	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	53	53	java/rmi/RemoteException
    //   14	53	62	java/lang/Throwable
    //   14	79	79	finally
    //   128	131	79	finally
    //   89	103	103	finally
    //   123	126	103	finally
  }
}


/* Location:           D:\drops\jd\jars\Deployed_ACHBPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.ACHServices.EJSRemoteStatelessACHBPWServices_e0156d61
 * JD-Core Version:    0.7.0.1
 */