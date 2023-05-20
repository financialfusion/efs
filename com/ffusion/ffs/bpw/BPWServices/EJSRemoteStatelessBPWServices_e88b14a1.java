package com.ffusion.ffs.bpw.BPWServices;

import com.ibm.ejs.container.EJSWrapper;
import java.rmi.RemoteException;

public class EJSRemoteStatelessBPWServices_e88b14a1
  extends EJSWrapper
  implements BPWServices
{
  public EJSRemoteStatelessBPWServices_e88b14a1()
    throws RemoteException
  {}
  
  /* Error */
  public boolean isWireBatchDeleteable(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: iconst_0
    //   43: aload_2
    //   44: aload_3
    //   45: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   48: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   51: astore 5
    //   53: aload 5
    //   55: aload_1
    //   56: invokevirtual 42	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:isWireBatchDeleteable	(Ljava/lang/String;)Z
    //   59: istore 4
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
    //   106: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   129: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   132: aload_2
    //   133: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   136: ret 8
    //   138: jsr -12 -> 126
    //   141: ret 6
    //   143: jsr -40 -> 103
    //   146: iload 4
    //   148: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	149	1	paramString	java.lang.String
    //   8	125	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	32	3	arrayOfObject	java.lang.Object[]
    //   15	132	4	bool	boolean
    //   51	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.AccountTransactions accountHasPendingTransfers(com.ffusion.ffs.bpw.interfaces.AccountTransactions paramAccountTransactions)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: iconst_1
    //   43: aload_2
    //   44: aload_3
    //   45: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   48: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   51: astore 5
    //   53: aload 5
    //   55: aload_1
    //   56: invokevirtual 72	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:accountHasPendingTransfers	(Lcom/ffusion/ffs/bpw/interfaces/AccountTransactions;)Lcom/ffusion/ffs/bpw/interfaces/AccountTransactions;
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
    //   80: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   117: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: iconst_1
    //   122: aload_2
    //   123: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   126: goto +23 -> 149
    //   129: astore 9
    //   131: jsr +6 -> 137
    //   134: aload 9
    //   136: athrow
    //   137: astore 8
    //   139: aload_0
    //   140: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	160	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	160	1	paramAccountTransactions	com.ffusion.ffs.bpw.interfaces.AccountTransactions
    //   8	136	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	32	3	arrayOfObject	java.lang.Object[]
    //   15	143	4	localAccountTransactions	com.ffusion.ffs.bpw.interfaces.AccountTransactions
    //   51	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWBankInfo getWireBanksByID(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: iconst_2
    //   43: aload_2
    //   44: aload_3
    //   45: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   48: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   51: astore 5
    //   53: aload 5
    //   55: aload_1
    //   56: invokevirtual 79	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWireBanksByID	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;
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
    //   106: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   129: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	149	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	149	1	paramString	java.lang.String
    //   8	125	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	32	3	arrayOfObject	java.lang.Object[]
    //   15	132	4	localBPWBankInfo	com.ffusion.ffs.bpw.interfaces.BPWBankInfo
    //   51	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWBankInfo[] addWireBanks(com.ffusion.ffs.bpw.interfaces.BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 83	[Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: iconst_3
    //   46: aload_2
    //   47: aload_3
    //   48: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   51: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   54: astore 5
    //   56: aload 5
    //   58: aload_1
    //   59: invokevirtual 85	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addWireBanks	([Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;)[Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;
    //   62: astore 4
    //   64: goto +82 -> 146
    //   67: astore 5
    //   69: aload_2
    //   70: aload 5
    //   72: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   75: goto +71 -> 146
    //   78: astore 5
    //   80: aload_2
    //   81: aload 5
    //   83: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   86: new 11	java/rmi/RemoteException
    //   89: dup
    //   90: ldc 53
    //   92: aload 5
    //   94: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   97: athrow
    //   98: astore 7
    //   100: jsr +6 -> 106
    //   103: aload 7
    //   105: athrow
    //   106: astore 6
    //   108: aload_0
    //   109: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   112: aload_0
    //   113: iconst_3
    //   114: aload_2
    //   115: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   118: goto +23 -> 141
    //   121: astore 9
    //   123: jsr +6 -> 129
    //   126: aload 9
    //   128: athrow
    //   129: astore 8
    //   131: aload_0
    //   132: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   135: aload_2
    //   136: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   139: ret 8
    //   141: jsr -12 -> 129
    //   144: ret 6
    //   146: jsr -40 -> 106
    //   149: aload 4
    //   151: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	152	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	152	1	paramArrayOfBPWBankInfo	com.ffusion.ffs.bpw.interfaces.BPWBankInfo[]
    //   8	128	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	35	3	arrayOfObject	java.lang.Object[]
    //   18	132	4	arrayOfBPWBankInfo	com.ffusion.ffs.bpw.interfaces.BPWBankInfo[]
    //   54	3	5	localBPWServicesBean	BPWServicesBean
    //   67	4	5	localRemoteException	RemoteException
    //   78	15	5	localThrowable	java.lang.Throwable
    //   106	1	6	localObject1	java.lang.Object
    //   98	6	7	localObject2	java.lang.Object
    //   129	1	8	localObject3	java.lang.Object
    //   121	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	67	67	java/rmi/RemoteException
    //   20	67	78	java/lang/Throwable
    //   20	98	98	finally
    //   146	149	98	finally
    //   108	121	121	finally
    //   141	144	121	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.BPWBankInfo[] delWireBanks(com.ffusion.ffs.bpw.interfaces.BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 83	[Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: iconst_4
    //   46: aload_2
    //   47: aload_3
    //   48: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   51: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   54: astore 5
    //   56: aload 5
    //   58: aload_1
    //   59: invokevirtual 88	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:delWireBanks	([Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;)[Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;
    //   62: astore 4
    //   64: goto +82 -> 146
    //   67: astore 5
    //   69: aload_2
    //   70: aload 5
    //   72: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   75: goto +71 -> 146
    //   78: astore 5
    //   80: aload_2
    //   81: aload 5
    //   83: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   86: new 11	java/rmi/RemoteException
    //   89: dup
    //   90: ldc 53
    //   92: aload 5
    //   94: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   97: athrow
    //   98: astore 7
    //   100: jsr +6 -> 106
    //   103: aload 7
    //   105: athrow
    //   106: astore 6
    //   108: aload_0
    //   109: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   112: aload_0
    //   113: iconst_4
    //   114: aload_2
    //   115: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   118: goto +23 -> 141
    //   121: astore 9
    //   123: jsr +6 -> 129
    //   126: aload 9
    //   128: athrow
    //   129: astore 8
    //   131: aload_0
    //   132: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   135: aload_2
    //   136: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   139: ret 8
    //   141: jsr -12 -> 129
    //   144: ret 6
    //   146: jsr -40 -> 106
    //   149: aload 4
    //   151: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	152	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	152	1	paramArrayOfBPWBankInfo	com.ffusion.ffs.bpw.interfaces.BPWBankInfo[]
    //   8	128	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	35	3	arrayOfObject	java.lang.Object[]
    //   18	132	4	arrayOfBPWBankInfo	com.ffusion.ffs.bpw.interfaces.BPWBankInfo[]
    //   54	3	5	localBPWServicesBean	BPWServicesBean
    //   67	4	5	localRemoteException	RemoteException
    //   78	15	5	localThrowable	java.lang.Throwable
    //   106	1	6	localObject1	java.lang.Object
    //   98	6	7	localObject2	java.lang.Object
    //   129	1	8	localObject3	java.lang.Object
    //   121	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	67	67	java/rmi/RemoteException
    //   20	67	78	java/lang/Throwable
    //   20	98	98	finally
    //   146	149	98	finally
    //   108	121	121	finally
    //   141	144	121	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.BPWBankInfo[] getWireBanks(java.lang.String paramString1, java.lang.String paramString2, java.lang.String paramString3, java.lang.String paramString4)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore 5
    //   10: aconst_null
    //   11: checkcast 28	[Ljava/lang/Object;
    //   14: astore 6
    //   16: aconst_null
    //   17: checkcast 83	[Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;
    //   20: astore 7
    //   22: aload_0
    //   23: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   26: aload_0
    //   27: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   30: ifeq +30 -> 60
    //   33: iconst_4
    //   34: anewarray 34	java/lang/Object
    //   37: astore 6
    //   39: aload 6
    //   41: iconst_0
    //   42: aload_1
    //   43: aastore
    //   44: aload 6
    //   46: iconst_1
    //   47: aload_2
    //   48: aastore
    //   49: aload 6
    //   51: iconst_2
    //   52: aload_3
    //   53: aastore
    //   54: aload 6
    //   56: iconst_3
    //   57: aload 4
    //   59: aastore
    //   60: aload_0
    //   61: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   64: aload_0
    //   65: iconst_5
    //   66: aload 5
    //   68: aload 6
    //   70: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   73: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   76: astore 8
    //   78: aload 8
    //   80: aload_1
    //   81: aload_2
    //   82: aload_3
    //   83: aload 4
    //   85: invokevirtual 92	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWireBanks	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;
    //   88: astore 7
    //   90: goto +86 -> 176
    //   93: astore 8
    //   95: aload 5
    //   97: aload 8
    //   99: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   102: goto +74 -> 176
    //   105: astore 8
    //   107: aload 5
    //   109: aload 8
    //   111: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   114: new 11	java/rmi/RemoteException
    //   117: dup
    //   118: ldc 53
    //   120: aload 8
    //   122: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   125: athrow
    //   126: astore 10
    //   128: jsr +6 -> 134
    //   131: aload 10
    //   133: athrow
    //   134: astore 9
    //   136: aload_0
    //   137: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   140: aload_0
    //   141: iconst_5
    //   142: aload 5
    //   144: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   147: goto +24 -> 171
    //   150: astore 12
    //   152: jsr +6 -> 158
    //   155: aload 12
    //   157: athrow
    //   158: astore 11
    //   160: aload_0
    //   161: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   164: aload 5
    //   166: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   169: ret 11
    //   171: jsr -13 -> 158
    //   174: ret 9
    //   176: jsr -42 -> 134
    //   179: aload 7
    //   181: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	182	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	182	1	paramString1	java.lang.String
    //   0	182	2	paramString2	java.lang.String
    //   0	182	3	paramString3	java.lang.String
    //   0	182	4	paramString4	java.lang.String
    //   8	157	5	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   14	55	6	arrayOfObject	java.lang.Object[]
    //   20	160	7	arrayOfBPWBankInfo	com.ffusion.ffs.bpw.interfaces.BPWBankInfo[]
    //   76	3	8	localBPWServicesBean	BPWServicesBean
    //   93	5	8	localRemoteException	RemoteException
    //   105	16	8	localThrowable	java.lang.Throwable
    //   134	1	9	localObject1	java.lang.Object
    //   126	6	10	localObject2	java.lang.Object
    //   158	1	11	localObject3	java.lang.Object
    //   150	6	12	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   22	93	93	java/rmi/RemoteException
    //   22	93	105	java/lang/Throwable
    //   22	126	126	finally
    //   176	179	126	finally
    //   136	150	150	finally
    //   171	174	150	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.BPWBankInfo[] getWireBanksByRTN(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 83	[Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 6
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 96	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWireBanksByRTN	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 6
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfBPWBankInfo	com.ffusion.ffs.bpw.interfaces.BPWBankInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWBankInfo[] modWireBanks(com.ffusion.ffs.bpw.interfaces.BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 83	[Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 7
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 99	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modWireBanks	([Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;)[Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 7
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramArrayOfBPWBankInfo	com.ffusion.ffs.bpw.interfaces.BPWBankInfo[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfBPWBankInfo	com.ffusion.ffs.bpw.interfaces.BPWBankInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWFIInfo activateBPWFI(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 8
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 103	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:activateBPWFI	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWFIInfo addBPWFIInfo(com.ffusion.ffs.bpw.interfaces.BPWFIInfo paramBPWFIInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 9
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 107	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addBPWFIInfo	(Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;)Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWFIInfo canBPWFIInfo(com.ffusion.ffs.bpw.interfaces.BPWFIInfo paramBPWFIInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 10
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 110	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:canBPWFIInfo	(Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;)Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWFIInfo getBPWFIInfo(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 11
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 113	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getBPWFIInfo	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 11
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWFIInfo getBPWFIInfoByACHId(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 12
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 116	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getBPWFIInfoByACHId	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 12
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWFIInfo getBPWFIInfoByRTN(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 13
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 119	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getBPWFIInfoByRTN	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 13
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWFIInfo modBPWFIInfo(com.ffusion.ffs.bpw.interfaces.BPWFIInfo paramBPWFIInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 14
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 122	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modBPWFIInfo	(Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;)Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWFIInfo[] getBPWFIInfo(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 125	[Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 15
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 127	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getBPWFIInfo	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 15
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramArrayOfString	java.lang.String[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWFIInfo[] getBPWFIInfoByGroup(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 125	[Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 16
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 131	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getBPWFIInfoByGroup	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 16
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWFIInfo[] getBPWFIInfoByStatus(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 125	[Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 17
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 134	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getBPWFIInfoByStatus	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 17
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWFIInfo[] getBPWFIInfoByType(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 125	[Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 18
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 137	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getBPWFIInfoByType	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/BPWFIInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfBPWFIInfo	com.ffusion.ffs.bpw.interfaces.BPWFIInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWHist getCombinedWireHistory(com.ffusion.ffs.bpw.interfaces.BPWHist paramBPWHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 19
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 141	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCombinedWireHistory	(Lcom/ffusion/ffs/bpw/interfaces/BPWHist;)Lcom/ffusion/ffs/bpw/interfaces/BPWHist;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWHist getDuplicateWires(com.ffusion.ffs.bpw.interfaces.WireInfo paramWireInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 20
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 145	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getDuplicateWires	(Lcom/ffusion/ffs/bpw/interfaces/WireInfo;)Lcom/ffusion/ffs/bpw/interfaces/BPWHist;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWHist getRecTransferHistory(com.ffusion.ffs.bpw.interfaces.BPWHist paramBPWHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 21
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 148	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRecTransferHistory	(Lcom/ffusion/ffs/bpw/interfaces/BPWHist;)Lcom/ffusion/ffs/bpw/interfaces/BPWHist;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWHist getRecTransfers(com.ffusion.ffs.bpw.interfaces.BPWHist paramBPWHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 22
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 151	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRecTransfers	(Lcom/ffusion/ffs/bpw/interfaces/BPWHist;)Lcom/ffusion/ffs/bpw/interfaces/BPWHist;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWHist getRecWireHistory(com.ffusion.ffs.bpw.interfaces.BPWHist paramBPWHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 23
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 154	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRecWireHistory	(Lcom/ffusion/ffs/bpw/interfaces/BPWHist;)Lcom/ffusion/ffs/bpw/interfaces/BPWHist;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWHist getRecWireHistoryByCustomer(com.ffusion.ffs.bpw.interfaces.BPWHist paramBPWHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 24
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 157	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRecWireHistoryByCustomer	(Lcom/ffusion/ffs/bpw/interfaces/BPWHist;)Lcom/ffusion/ffs/bpw/interfaces/BPWHist;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWHist getTransferHistory(com.ffusion.ffs.bpw.interfaces.BPWHist paramBPWHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 25
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 160	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getTransferHistory	(Lcom/ffusion/ffs/bpw/interfaces/BPWHist;)Lcom/ffusion/ffs/bpw/interfaces/BPWHist;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWHist getWireBatchHistory(com.ffusion.ffs.bpw.interfaces.BPWHist paramBPWHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 26
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 163	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWireBatchHistory	(Lcom/ffusion/ffs/bpw/interfaces/BPWHist;)Lcom/ffusion/ffs/bpw/interfaces/BPWHist;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWHist getWireHistory(com.ffusion.ffs.bpw.interfaces.BPWHist paramBPWHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 27
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 166	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWireHistory	(Lcom/ffusion/ffs/bpw/interfaces/BPWHist;)Lcom/ffusion/ffs/bpw/interfaces/BPWHist;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWHist getWireHistoryByCustomer(com.ffusion.ffs.bpw.interfaces.BPWHist paramBPWHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 28
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 169	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWireHistoryByCustomer	(Lcom/ffusion/ffs/bpw/interfaces/BPWHist;)Lcom/ffusion/ffs/bpw/interfaces/BPWHist;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWPayeeList getWirePayeeByCustomer(com.ffusion.ffs.bpw.interfaces.BPWPayeeList paramBPWPayeeList)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 29
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 173	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWirePayeeByCustomer	(Lcom/ffusion/ffs/bpw/interfaces/BPWPayeeList;)Lcom/ffusion/ffs/bpw/interfaces/BPWPayeeList;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWPayeeList	com.ffusion.ffs.bpw.interfaces.BPWPayeeList
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWPayeeList	com.ffusion.ffs.bpw.interfaces.BPWPayeeList
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWPayeeList getWirePayeeByGroup(com.ffusion.ffs.bpw.interfaces.BPWPayeeList paramBPWPayeeList)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 30
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 176	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWirePayeeByGroup	(Lcom/ffusion/ffs/bpw/interfaces/BPWPayeeList;)Lcom/ffusion/ffs/bpw/interfaces/BPWPayeeList;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWPayeeList	com.ffusion.ffs.bpw.interfaces.BPWPayeeList
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWPayeeList	com.ffusion.ffs.bpw.interfaces.BPWPayeeList
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWPayeeList getWirePayeeByStatus(com.ffusion.ffs.bpw.interfaces.BPWPayeeList paramBPWPayeeList)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 31
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 179	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWirePayeeByStatus	(Lcom/ffusion/ffs/bpw/interfaces/BPWPayeeList;)Lcom/ffusion/ffs/bpw/interfaces/BPWPayeeList;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 31
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWPayeeList	com.ffusion.ffs.bpw.interfaces.BPWPayeeList
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWPayeeList	com.ffusion.ffs.bpw.interfaces.BPWPayeeList
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWPayeeList getWirePayeeByType(com.ffusion.ffs.bpw.interfaces.BPWPayeeList paramBPWPayeeList)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 32
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 182	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWirePayeeByType	(Lcom/ffusion/ffs/bpw/interfaces/BPWPayeeList;)Lcom/ffusion/ffs/bpw/interfaces/BPWPayeeList;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramBPWPayeeList	com.ffusion.ffs.bpw.interfaces.BPWPayeeList
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localBPWPayeeList	com.ffusion.ffs.bpw.interfaces.BPWPayeeList
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BankingDays getBankingDaysInRange(com.ffusion.ffs.bpw.interfaces.BankingDays paramBankingDays, java.util.HashMap paramHashMap)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 33
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 186	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getBankingDaysInRange	(Lcom/ffusion/ffs/bpw/interfaces/BankingDays;Ljava/util/HashMap;)Lcom/ffusion/ffs/bpw/interfaces/BankingDays;
    //   70: astore 5
    //   72: goto +94 -> 166
    //   75: astore 6
    //   77: aload_3
    //   78: aload 6
    //   80: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   83: aload 6
    //   85: athrow
    //   86: astore 6
    //   88: aload_3
    //   89: aload 6
    //   91: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   94: goto +72 -> 166
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
    //   128: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_0
    //   132: bipush 33
    //   134: aload_3
    //   135: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: goto +23 -> 161
    //   141: astore 10
    //   143: jsr +6 -> 149
    //   146: aload 10
    //   148: athrow
    //   149: astore 9
    //   151: aload_0
    //   152: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	172	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	172	1	paramBankingDays	com.ffusion.ffs.bpw.interfaces.BankingDays
    //   0	172	2	paramHashMap	java.util.HashMap
    //   8	148	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	154	5	localBankingDays	com.ffusion.ffs.bpw.interfaces.BankingDays
    //   61	3	6	localBPWServicesBean	BPWServicesBean
    //   75	9	6	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   86	4	6	localRemoteException	RemoteException
    //   97	15	6	localThrowable	java.lang.Throwable
    //   125	1	7	localObject1	java.lang.Object
    //   117	6	8	localObject2	java.lang.Object
    //   149	1	9	localObject3	java.lang.Object
    //   141	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	75	75	com/ffusion/ffs/interfaces/FFSException
    //   18	75	86	java/rmi/RemoteException
    //   18	75	97	java/lang/Throwable
    //   18	117	117	finally
    //   166	169	117	finally
    //   127	141	141	finally
    //   161	164	141	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo addCCCompanyAcct(com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 34
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 190	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addCCCompanyAcct	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyAcctInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 34
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyAcctInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyAcctInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo cancelCCCompanyAcct(com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 35
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 193	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:cancelCCCompanyAcct	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyAcctInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 35
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyAcctInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyAcctInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo getCCCompanyAcct(com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 36
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 196	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCCCompanyAcct	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyAcctInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 36
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyAcctInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyAcctInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo modCCCompanyAcct(com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 37
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 199	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modCCCompanyAcct	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyAcctInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 37
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyAcctInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyAcctInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfoList getCCCompanyAcctList(com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfoList paramCCCompanyAcctInfoList)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 38
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 203	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCCCompanyAcctList	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyAcctInfoList;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyAcctInfoList;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 38
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyAcctInfoList	com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfoList
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyAcctInfoList	com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfoList
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo addCCCompanyCutOff(com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 39
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 207	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addCCCompanyCutOff	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyCutOffInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyCutOffInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 39
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyCutOffInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyCutOffInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo cancelCCCompanyCutOff(com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 40
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 210	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:cancelCCCompanyCutOff	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyCutOffInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyCutOffInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 40
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyCutOffInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyCutOffInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo getCCCompanyCutOff(com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 41
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 213	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCCCompanyCutOff	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyCutOffInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyCutOffInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 41
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyCutOffInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyCutOffInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList getCCCompanyCutOffList(com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList paramCCCompanyCutOffInfoList)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 42
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 217	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCCCompanyCutOffList	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyCutOffInfoList;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyCutOffInfoList;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 42
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyCutOffInfoList	com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyCutOffInfoList	com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyInfo addCCCompany(com.ffusion.ffs.bpw.interfaces.CCCompanyInfo paramCCCompanyInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 43
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 221	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addCCCompany	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 43
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyInfo cancelCCCompany(com.ffusion.ffs.bpw.interfaces.CCCompanyInfo paramCCCompanyInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 44
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 224	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:cancelCCCompany	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyInfo getCCCompany(com.ffusion.ffs.bpw.interfaces.CCCompanyInfo paramCCCompanyInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 45
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 227	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCCCompany	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyInfo modCCCompany(com.ffusion.ffs.bpw.interfaces.CCCompanyInfo paramCCCompanyInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 46
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 230	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modCCCompany	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 46
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyInfo	com.ffusion.ffs.bpw.interfaces.CCCompanyInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCCompanyInfoList getCCCompanyList(com.ffusion.ffs.bpw.interfaces.CCCompanyInfoList paramCCCompanyInfoList)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 47
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 234	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCCCompanyList	(Lcom/ffusion/ffs/bpw/interfaces/CCCompanyInfoList;)Lcom/ffusion/ffs/bpw/interfaces/CCCompanyInfoList;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 47
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCCompanyInfoList	com.ffusion.ffs.bpw.interfaces.CCCompanyInfoList
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCCompanyInfoList	com.ffusion.ffs.bpw.interfaces.CCCompanyInfoList
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCEntryHistInfo getCCEntryHist(com.ffusion.ffs.bpw.interfaces.CCEntryHistInfo paramCCEntryHistInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 48
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 238	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCCEntryHist	(Lcom/ffusion/ffs/bpw/interfaces/CCEntryHistInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCEntryHistInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCEntryHistInfo	com.ffusion.ffs.bpw.interfaces.CCEntryHistInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCEntryHistInfo	com.ffusion.ffs.bpw.interfaces.CCEntryHistInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCEntryInfo addCCEntry(com.ffusion.ffs.bpw.interfaces.CCEntryInfo paramCCEntryInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 49
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 242	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addCCEntry	(Lcom/ffusion/ffs/bpw/interfaces/CCEntryInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCEntryInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 49
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCEntryInfo	com.ffusion.ffs.bpw.interfaces.CCEntryInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCEntryInfo	com.ffusion.ffs.bpw.interfaces.CCEntryInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCEntryInfo cancelCCEntry(com.ffusion.ffs.bpw.interfaces.CCEntryInfo paramCCEntryInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 50
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 245	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:cancelCCEntry	(Lcom/ffusion/ffs/bpw/interfaces/CCEntryInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCEntryInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 50
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCEntryInfo	com.ffusion.ffs.bpw.interfaces.CCEntryInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCEntryInfo	com.ffusion.ffs.bpw.interfaces.CCEntryInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCEntryInfo getCCEntry(com.ffusion.ffs.bpw.interfaces.CCEntryInfo paramCCEntryInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 51
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 248	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCCEntry	(Lcom/ffusion/ffs/bpw/interfaces/CCEntryInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCEntryInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 51
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCEntryInfo	com.ffusion.ffs.bpw.interfaces.CCEntryInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCEntryInfo	com.ffusion.ffs.bpw.interfaces.CCEntryInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCEntryInfo modCCEntry(com.ffusion.ffs.bpw.interfaces.CCEntryInfo paramCCEntryInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 52
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 251	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modCCEntry	(Lcom/ffusion/ffs/bpw/interfaces/CCEntryInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCEntryInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 52
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCEntryInfo	com.ffusion.ffs.bpw.interfaces.CCEntryInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCEntryInfo	com.ffusion.ffs.bpw.interfaces.CCEntryInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfoList getCCEntrySummaryList(com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfoList paramCCEntrySummaryInfoList)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 53
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 255	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCCEntrySummaryList	(Lcom/ffusion/ffs/bpw/interfaces/CCEntrySummaryInfoList;)Lcom/ffusion/ffs/bpw/interfaces/CCEntrySummaryInfoList;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 53
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCEntrySummaryInfoList	com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfoList
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCEntrySummaryInfoList	com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfoList
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCLocationInfo addCCLocation(com.ffusion.ffs.bpw.interfaces.CCLocationInfo paramCCLocationInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 54
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 259	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addCCLocation	(Lcom/ffusion/ffs/bpw/interfaces/CCLocationInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCLocationInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 54
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCLocationInfo	com.ffusion.ffs.bpw.interfaces.CCLocationInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCLocationInfo	com.ffusion.ffs.bpw.interfaces.CCLocationInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCLocationInfo cancelCCLocation(com.ffusion.ffs.bpw.interfaces.CCLocationInfo paramCCLocationInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 55
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 262	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:cancelCCLocation	(Lcom/ffusion/ffs/bpw/interfaces/CCLocationInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCLocationInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 55
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCLocationInfo	com.ffusion.ffs.bpw.interfaces.CCLocationInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCLocationInfo	com.ffusion.ffs.bpw.interfaces.CCLocationInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCLocationInfo getCCLocation(com.ffusion.ffs.bpw.interfaces.CCLocationInfo paramCCLocationInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 56
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 265	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCCLocation	(Lcom/ffusion/ffs/bpw/interfaces/CCLocationInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCLocationInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 56
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCLocationInfo	com.ffusion.ffs.bpw.interfaces.CCLocationInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCLocationInfo	com.ffusion.ffs.bpw.interfaces.CCLocationInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCLocationInfo modCCLocation(com.ffusion.ffs.bpw.interfaces.CCLocationInfo paramCCLocationInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 57
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 268	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modCCLocation	(Lcom/ffusion/ffs/bpw/interfaces/CCLocationInfo;)Lcom/ffusion/ffs/bpw/interfaces/CCLocationInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 57
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCLocationInfo	com.ffusion.ffs.bpw.interfaces.CCLocationInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCLocationInfo	com.ffusion.ffs.bpw.interfaces.CCLocationInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CCLocationInfoList getCCLocationList(com.ffusion.ffs.bpw.interfaces.CCLocationInfoList paramCCLocationInfoList)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 58
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 272	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCCLocationList	(Lcom/ffusion/ffs/bpw/interfaces/CCLocationInfoList;)Lcom/ffusion/ffs/bpw/interfaces/CCLocationInfoList;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 58
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCCLocationInfoList	com.ffusion.ffs.bpw.interfaces.CCLocationInfoList
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCCLocationInfoList	com.ffusion.ffs.bpw.interfaces.CCLocationInfoList
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CustomerInfo[] getCustomerByCategory(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 276	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 59
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 278	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCustomerByCategory	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 59
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.CustomerInfo[] getCustomerByCategoryAndFI(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 276	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   49: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   52: aload_0
    //   53: bipush 60
    //   55: aload_3
    //   56: aload 4
    //   58: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   61: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   64: astore 6
    //   66: aload 6
    //   68: aload_1
    //   69: aload_2
    //   70: invokevirtual 282	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCustomerByCategoryAndFI	(Ljava/lang/String;Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   120: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   123: aload_0
    //   124: bipush 60
    //   126: aload_3
    //   127: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   130: goto +23 -> 153
    //   133: astore 10
    //   135: jsr +6 -> 141
    //   138: aload 10
    //   140: athrow
    //   141: astore 9
    //   143: aload_0
    //   144: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	164	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	164	1	paramString1	java.lang.String
    //   0	164	2	paramString2	java.lang.String
    //   8	140	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	44	4	arrayOfObject	java.lang.Object[]
    //   19	143	5	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   64	3	6	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.CustomerInfo[] getCustomerByFI(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 276	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 61
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 285	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCustomerByFI	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 61
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.CustomerInfo[] getCustomerByGroup(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 276	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 62
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 288	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCustomerByGroup	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 62
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.CustomerInfo[] getCustomerByGroupAndFI(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 276	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   49: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   52: aload_0
    //   53: bipush 63
    //   55: aload_3
    //   56: aload 4
    //   58: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   61: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   64: astore 6
    //   66: aload 6
    //   68: aload_1
    //   69: aload_2
    //   70: invokevirtual 291	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCustomerByGroupAndFI	(Ljava/lang/String;Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   120: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   123: aload_0
    //   124: bipush 63
    //   126: aload_3
    //   127: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   130: goto +23 -> 153
    //   133: astore 10
    //   135: jsr +6 -> 141
    //   138: aload 10
    //   140: athrow
    //   141: astore 9
    //   143: aload_0
    //   144: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	164	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	164	1	paramString1	java.lang.String
    //   0	164	2	paramString2	java.lang.String
    //   8	140	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	44	4	arrayOfObject	java.lang.Object[]
    //   19	143	5	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   64	3	6	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.CustomerInfo[] getCustomerByType(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 276	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 64
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 294	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCustomerByType	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 64
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.CustomerInfo[] getCustomerByTypeAndFI(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 276	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   49: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   52: aload_0
    //   53: bipush 65
    //   55: aload_3
    //   56: aload 4
    //   58: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   61: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   64: astore 6
    //   66: aload 6
    //   68: aload_1
    //   69: aload_2
    //   70: invokevirtual 297	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCustomerByTypeAndFI	(Ljava/lang/String;Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   120: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   123: aload_0
    //   124: bipush 65
    //   126: aload_3
    //   127: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   130: goto +23 -> 153
    //   133: astore 10
    //   135: jsr +6 -> 141
    //   138: aload 10
    //   140: athrow
    //   141: astore 9
    //   143: aload_0
    //   144: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	164	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	164	1	paramString1	java.lang.String
    //   0	164	2	paramString2	java.lang.String
    //   8	140	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	44	4	arrayOfObject	java.lang.Object[]
    //   19	143	5	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   64	3	6	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.CustomerInfo[] getCustomersInfo(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 276	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 66
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 301	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCustomersInfo	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 66
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramArrayOfString	java.lang.String[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo addCustomerPayee(com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo paramCustomerPayeeInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 67
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 305	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addCustomerPayee	(Lcom/ffusion/ffs/bpw/interfaces/CustomerPayeeInfo;)Lcom/ffusion/ffs/bpw/interfaces/CustomerPayeeInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 67
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCustomerPayeeInfo	com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCustomerPayeeInfo	com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo deleteCustomerPayee(com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo paramCustomerPayeeInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 68
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 308	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:deleteCustomerPayee	(Lcom/ffusion/ffs/bpw/interfaces/CustomerPayeeInfo;)Lcom/ffusion/ffs/bpw/interfaces/CustomerPayeeInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 68
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCustomerPayeeInfo	com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCustomerPayeeInfo	com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo updateCustomerPayee(com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo paramCustomerPayeeInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 69
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 311	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:updateCustomerPayee	(Lcom/ffusion/ffs/bpw/interfaces/CustomerPayeeInfo;)Lcom/ffusion/ffs/bpw/interfaces/CustomerPayeeInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 69
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramCustomerPayeeInfo	com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localCustomerPayeeInfo	com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo[] getCustomerPayees(com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo paramCustomerPayeeInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 315	[Lcom/ffusion/ffs/bpw/interfaces/CustomerPayeeInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 70
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 317	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getCustomerPayees	(Lcom/ffusion/ffs/bpw/interfaces/CustomerPayeeInfo;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerPayeeInfo;
    //   63: astore 4
    //   65: goto +94 -> 159
    //   68: astore 5
    //   70: aload_2
    //   71: aload 5
    //   73: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   76: aload 5
    //   78: athrow
    //   79: astore 5
    //   81: aload_2
    //   82: aload 5
    //   84: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   87: goto +72 -> 159
    //   90: astore 5
    //   92: aload_2
    //   93: aload 5
    //   95: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   98: new 11	java/rmi/RemoteException
    //   101: dup
    //   102: ldc 53
    //   104: aload 5
    //   106: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   109: athrow
    //   110: astore 7
    //   112: jsr +6 -> 118
    //   115: aload 7
    //   117: athrow
    //   118: astore 6
    //   120: aload_0
    //   121: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   124: aload_0
    //   125: bipush 70
    //   127: aload_2
    //   128: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   131: goto +23 -> 154
    //   134: astore 9
    //   136: jsr +6 -> 142
    //   139: aload 9
    //   141: athrow
    //   142: astore 8
    //   144: aload_0
    //   145: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   148: aload_2
    //   149: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   152: ret 8
    //   154: jsr -12 -> 142
    //   157: ret 6
    //   159: jsr -41 -> 118
    //   162: aload 4
    //   164: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	165	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	165	1	paramCustomerPayeeInfo	com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo
    //   8	141	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	145	4	arrayOfCustomerPayeeInfo	com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
    //   68	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   79	4	5	localRemoteException	RemoteException
    //   90	15	5	localThrowable	java.lang.Throwable
    //   118	1	6	localObject1	java.lang.Object
    //   110	6	7	localObject2	java.lang.Object
    //   142	1	8	localObject3	java.lang.Object
    //   134	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	68	68	com/ffusion/ffs/interfaces/FFSException
    //   20	68	79	java/rmi/RemoteException
    //   20	68	90	java/lang/Throwable
    //   20	110	110	finally
    //   159	162	110	finally
    //   120	134	134	finally
    //   154	157	134	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CutOffInfo getNextCashConCutOff(java.lang.String paramString1, java.lang.String paramString2)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 71
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 321	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getNextCashConCutOff	(Ljava/lang/String;Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/CutOffInfo;
    //   70: astore 5
    //   72: goto +94 -> 166
    //   75: astore 6
    //   77: aload_3
    //   78: aload 6
    //   80: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   83: aload 6
    //   85: athrow
    //   86: astore 6
    //   88: aload_3
    //   89: aload 6
    //   91: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   94: goto +72 -> 166
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
    //   128: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_0
    //   132: bipush 71
    //   134: aload_3
    //   135: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: goto +23 -> 161
    //   141: astore 10
    //   143: jsr +6 -> 149
    //   146: aload 10
    //   148: athrow
    //   149: astore 9
    //   151: aload_0
    //   152: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	172	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	172	1	paramString1	java.lang.String
    //   0	172	2	paramString2	java.lang.String
    //   8	148	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	154	5	localCutOffInfo	com.ffusion.ffs.bpw.interfaces.CutOffInfo
    //   61	3	6	localBPWServicesBean	BPWServicesBean
    //   75	9	6	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   86	4	6	localRemoteException	RemoteException
    //   97	15	6	localThrowable	java.lang.Throwable
    //   125	1	7	localObject1	java.lang.Object
    //   117	6	8	localObject2	java.lang.Object
    //   149	1	9	localObject3	java.lang.Object
    //   141	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	75	75	com/ffusion/ffs/interfaces/FFSException
    //   18	75	86	java/rmi/RemoteException
    //   18	75	97	java/lang/Throwable
    //   18	117	117	finally
    //   166	169	117	finally
    //   127	141	141	finally
    //   161	164	141	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo activateExtTransferAcct(com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 72
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 325	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:activateExtTransferAcct	(Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 72
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo addExtTransferAccount(com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 73
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 328	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addExtTransferAccount	(Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 73
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo canExtTransferAccount(com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 74
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 331	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:canExtTransferAccount	(Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 74
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo depositAmountsForVerify(java.lang.String paramString, com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 75
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 335	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:depositAmountsForVerify	(Ljava/lang/String;Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;
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
    //   91: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   128: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_0
    //   132: bipush 75
    //   134: aload_3
    //   135: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: goto +23 -> 161
    //   141: astore 10
    //   143: jsr +6 -> 149
    //   146: aload 10
    //   148: athrow
    //   149: astore 9
    //   151: aload_0
    //   152: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	172	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	172	1	paramString	java.lang.String
    //   0	172	2	paramExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   8	148	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	154	5	localExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   61	3	6	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo getExtTransferAccount(com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 76
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 338	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getExtTransferAccount	(Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 76
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo inactivateExtTransferAcct(com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 77
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 341	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:inactivateExtTransferAcct	(Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 77
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo modExtTransferAccount(com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 78
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 344	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modExtTransferAccount	(Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 78
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo modExtTransferAccountPrenoteStatus(com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 79
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 347	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modExtTransferAccountPrenoteStatus	(Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 79
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo verifyExtTransferAccount(java.lang.String paramString, com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo paramExtTransferAcctInfo, int[] paramArrayOfInt)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore 4
    //   10: aconst_null
    //   11: checkcast 28	[Ljava/lang/Object;
    //   14: astore 5
    //   16: aconst_null
    //   17: astore 6
    //   19: aload_0
    //   20: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   23: aload_0
    //   24: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   27: ifeq +24 -> 51
    //   30: iconst_3
    //   31: anewarray 34	java/lang/Object
    //   34: astore 5
    //   36: aload 5
    //   38: iconst_0
    //   39: aload_1
    //   40: aastore
    //   41: aload 5
    //   43: iconst_1
    //   44: aload_2
    //   45: aastore
    //   46: aload 5
    //   48: iconst_2
    //   49: aload_3
    //   50: aastore
    //   51: aload_0
    //   52: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   55: aload_0
    //   56: bipush 80
    //   58: aload 4
    //   60: aload 5
    //   62: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   65: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   68: astore 7
    //   70: aload 7
    //   72: aload_1
    //   73: aload_2
    //   74: aload_3
    //   75: invokevirtual 351	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:verifyExtTransferAccount	(Ljava/lang/String;Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;[I)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctInfo;
    //   78: astore 6
    //   80: goto +99 -> 179
    //   83: astore 7
    //   85: aload 4
    //   87: aload 7
    //   89: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   92: goto +87 -> 179
    //   95: astore 7
    //   97: aload 4
    //   99: aload 7
    //   101: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   104: aload 7
    //   106: athrow
    //   107: astore 7
    //   109: aload 4
    //   111: aload 7
    //   113: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   116: new 11	java/rmi/RemoteException
    //   119: dup
    //   120: ldc 53
    //   122: aload 7
    //   124: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   127: athrow
    //   128: astore 9
    //   130: jsr +6 -> 136
    //   133: aload 9
    //   135: athrow
    //   136: astore 8
    //   138: aload_0
    //   139: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   142: aload_0
    //   143: bipush 80
    //   145: aload 4
    //   147: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   150: goto +24 -> 174
    //   153: astore 11
    //   155: jsr +6 -> 161
    //   158: aload 11
    //   160: athrow
    //   161: astore 10
    //   163: aload_0
    //   164: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   167: aload 4
    //   169: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   172: ret 10
    //   174: jsr -13 -> 161
    //   177: ret 8
    //   179: jsr -43 -> 136
    //   182: aload 6
    //   184: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	185	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	185	1	paramString	java.lang.String
    //   0	185	2	paramExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   0	185	3	paramArrayOfInt	int[]
    //   8	160	4	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   14	47	5	arrayOfObject	java.lang.Object[]
    //   17	166	6	localExtTransferAcctInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
    //   68	3	7	localBPWServicesBean	BPWServicesBean
    //   83	5	7	localRemoteException	RemoteException
    //   95	10	7	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   107	16	7	localThrowable	java.lang.Throwable
    //   136	1	8	localObject1	java.lang.Object
    //   128	6	9	localObject2	java.lang.Object
    //   161	1	10	localObject3	java.lang.Object
    //   153	6	11	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   19	83	83	java/rmi/RemoteException
    //   19	83	95	com/ffusion/ffs/interfaces/FFSException
    //   19	83	107	java/lang/Throwable
    //   19	128	128	finally
    //   179	182	128	finally
    //   138	153	153	finally
    //   174	177	153	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList getExtTransferAcctList(com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList paramExtTransferAcctList)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 81
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 355	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getExtTransferAcctList	(Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctList;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferAcctList;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 81
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramExtTransferAcctList	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localExtTransferAcctList	com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo addExtTransferCompany(com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 82
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 359	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addExtTransferCompany	(Lcom/ffusion/ffs/bpw/interfaces/ExtTransferCompanyInfo;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferCompanyInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 82
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramExtTransferCompanyInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localExtTransferCompanyInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo canExtTransferCompany(com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 83
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 362	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:canExtTransferCompany	(Lcom/ffusion/ffs/bpw/interfaces/ExtTransferCompanyInfo;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferCompanyInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 83
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramExtTransferCompanyInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localExtTransferCompanyInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo getExtTransferCompany(com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 84
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 365	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getExtTransferCompany	(Lcom/ffusion/ffs/bpw/interfaces/ExtTransferCompanyInfo;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferCompanyInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 84
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramExtTransferCompanyInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localExtTransferCompanyInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo modExtTransferCompany(com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 85
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 368	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modExtTransferCompany	(Lcom/ffusion/ffs/bpw/interfaces/ExtTransferCompanyInfo;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferCompanyInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 85
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramExtTransferCompanyInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localExtTransferCompanyInfo	com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList getExtTransferCompanyList(com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList paramExtTransferCompanyList)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 86
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 372	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getExtTransferCompanyList	(Lcom/ffusion/ffs/bpw/interfaces/ExtTransferCompanyList;)Lcom/ffusion/ffs/bpw/interfaces/ExtTransferCompanyList;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 86
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramExtTransferCompanyList	com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localExtTransferCompanyList	com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.LastPaymentInfo getLastPayments(com.ffusion.ffs.bpw.interfaces.LastPaymentInfo paramLastPaymentInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 87
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 376	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getLastPayments	(Lcom/ffusion/ffs/bpw/interfaces/LastPaymentInfo;)Lcom/ffusion/ffs/bpw/interfaces/LastPaymentInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 87
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramLastPaymentInfo	com.ffusion.ffs.bpw.interfaces.LastPaymentInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localLastPaymentInfo	com.ffusion.ffs.bpw.interfaces.LastPaymentInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.NonBusinessDay[] getNonBusinessDays(java.lang.String paramString)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 380	[Lcom/ffusion/ffs/bpw/interfaces/NonBusinessDay;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 88
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 382	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getNonBusinessDays	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/NonBusinessDay;
    //   63: astore 4
    //   65: goto +94 -> 159
    //   68: astore 5
    //   70: aload_2
    //   71: aload 5
    //   73: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   76: goto +83 -> 159
    //   79: astore 5
    //   81: aload_2
    //   82: aload 5
    //   84: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   87: aload 5
    //   89: athrow
    //   90: astore 5
    //   92: aload_2
    //   93: aload 5
    //   95: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   98: new 11	java/rmi/RemoteException
    //   101: dup
    //   102: ldc 53
    //   104: aload 5
    //   106: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   109: athrow
    //   110: astore 7
    //   112: jsr +6 -> 118
    //   115: aload 7
    //   117: athrow
    //   118: astore 6
    //   120: aload_0
    //   121: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   124: aload_0
    //   125: bipush 88
    //   127: aload_2
    //   128: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   131: goto +23 -> 154
    //   134: astore 9
    //   136: jsr +6 -> 142
    //   139: aload 9
    //   141: athrow
    //   142: astore 8
    //   144: aload_0
    //   145: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   148: aload_2
    //   149: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   152: ret 8
    //   154: jsr -12 -> 142
    //   157: ret 6
    //   159: jsr -41 -> 118
    //   162: aload 4
    //   164: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	165	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	165	1	paramString	java.lang.String
    //   8	141	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	145	4	arrayOfNonBusinessDay	com.ffusion.ffs.bpw.interfaces.NonBusinessDay[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
    //   68	4	5	localRemoteException	RemoteException
    //   79	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   90	15	5	localThrowable	java.lang.Throwable
    //   118	1	6	localObject1	java.lang.Object
    //   110	6	7	localObject2	java.lang.Object
    //   142	1	8	localObject3	java.lang.Object
    //   134	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	68	68	java/rmi/RemoteException
    //   20	68	79	com/ffusion/ffs/interfaces/FFSException
    //   20	68	90	java/lang/Throwable
    //   20	110	110	finally
    //   159	162	110	finally
    //   120	134	134	finally
    //   154	157	134	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.NonBusinessDay[] getNonBusinessDaysFromFile(java.lang.String paramString)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 380	[Lcom/ffusion/ffs/bpw/interfaces/NonBusinessDay;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 89
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 385	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getNonBusinessDaysFromFile	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/NonBusinessDay;
    //   63: astore 4
    //   65: goto +94 -> 159
    //   68: astore 5
    //   70: aload_2
    //   71: aload 5
    //   73: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   76: goto +83 -> 159
    //   79: astore 5
    //   81: aload_2
    //   82: aload 5
    //   84: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   87: aload 5
    //   89: athrow
    //   90: astore 5
    //   92: aload_2
    //   93: aload 5
    //   95: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   98: new 11	java/rmi/RemoteException
    //   101: dup
    //   102: ldc 53
    //   104: aload 5
    //   106: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   109: athrow
    //   110: astore 7
    //   112: jsr +6 -> 118
    //   115: aload 7
    //   117: athrow
    //   118: astore 6
    //   120: aload_0
    //   121: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   124: aload_0
    //   125: bipush 89
    //   127: aload_2
    //   128: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   131: goto +23 -> 154
    //   134: astore 9
    //   136: jsr +6 -> 142
    //   139: aload 9
    //   141: athrow
    //   142: astore 8
    //   144: aload_0
    //   145: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   148: aload_2
    //   149: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   152: ret 8
    //   154: jsr -12 -> 142
    //   157: ret 6
    //   159: jsr -41 -> 118
    //   162: aload 4
    //   164: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	165	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	165	1	paramString	java.lang.String
    //   8	141	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	145	4	arrayOfNonBusinessDay	com.ffusion.ffs.bpw.interfaces.NonBusinessDay[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
    //   68	4	5	localRemoteException	RemoteException
    //   79	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   90	15	5	localThrowable	java.lang.Throwable
    //   118	1	6	localObject1	java.lang.Object
    //   110	6	7	localObject2	java.lang.Object
    //   142	1	8	localObject3	java.lang.Object
    //   134	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	68	68	java/rmi/RemoteException
    //   20	68	79	com/ffusion/ffs/interfaces/FFSException
    //   20	68	90	java/lang/Throwable
    //   20	110	110	finally
    //   159	162	110	finally
    //   120	134	134	finally
    //   154	157	134	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.PagingInfo getPagedBillPayments(com.ffusion.ffs.bpw.interfaces.PagingInfo paramPagingInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 90
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 389	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getPagedBillPayments	(Lcom/ffusion/ffs/bpw/interfaces/PagingInfo;)Lcom/ffusion/ffs/bpw/interfaces/PagingInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 90
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramPagingInfo	com.ffusion.ffs.bpw.interfaces.PagingInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPagingInfo	com.ffusion.ffs.bpw.interfaces.PagingInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PagingInfo getPagedCashCon(com.ffusion.ffs.bpw.interfaces.PagingInfo paramPagingInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 91
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 392	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getPagedCashCon	(Lcom/ffusion/ffs/bpw/interfaces/PagingInfo;)Lcom/ffusion/ffs/bpw/interfaces/PagingInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 91
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramPagingInfo	com.ffusion.ffs.bpw.interfaces.PagingInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPagingInfo	com.ffusion.ffs.bpw.interfaces.PagingInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PagingInfo getPagedTransfer(com.ffusion.ffs.bpw.interfaces.PagingInfo paramPagingInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 92
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 395	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getPagedTransfer	(Lcom/ffusion/ffs/bpw/interfaces/PagingInfo;)Lcom/ffusion/ffs/bpw/interfaces/PagingInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 92
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramPagingInfo	com.ffusion.ffs.bpw.interfaces.PagingInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPagingInfo	com.ffusion.ffs.bpw.interfaces.PagingInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PagingInfo getPagedWire(com.ffusion.ffs.bpw.interfaces.PagingInfo paramPagingInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 93
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 398	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getPagedWire	(Lcom/ffusion/ffs/bpw/interfaces/PagingInfo;)Lcom/ffusion/ffs/bpw/interfaces/PagingInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 93
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramPagingInfo	com.ffusion.ffs.bpw.interfaces.PagingInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPagingInfo	com.ffusion.ffs.bpw.interfaces.PagingInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PayeeInfo getGlobalPayee(java.lang.String paramString)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 94
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 402	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getGlobalPayee	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 94
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramString	java.lang.String
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PayeeInfo getPayeeByListId(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 95
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 406	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getPayeeByListId	(Ljava/lang/String;Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
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
    //   91: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   128: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_0
    //   132: bipush 95
    //   134: aload_3
    //   135: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: goto +23 -> 161
    //   141: astore 10
    //   143: jsr +6 -> 149
    //   146: aload 10
    //   148: athrow
    //   149: astore 9
    //   151: aload_0
    //   152: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	172	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	172	1	paramString1	java.lang.String
    //   0	172	2	paramString2	java.lang.String
    //   8	148	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	154	5	localPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo
    //   61	3	6	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PayeeInfo[] searchGlobalPayees(com.ffusion.ffs.bpw.interfaces.PayeeInfo paramPayeeInfo, int paramInt)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 410	[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   25: aload_0
    //   26: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   29: ifeq +26 -> 55
    //   32: iconst_2
    //   33: anewarray 34	java/lang/Object
    //   36: astore 4
    //   38: aload 4
    //   40: iconst_0
    //   41: aload_1
    //   42: aastore
    //   43: aload 4
    //   45: iconst_1
    //   46: new 412	java/lang/Integer
    //   49: dup
    //   50: iload_2
    //   51: invokespecial 415	java/lang/Integer:<init>	(I)V
    //   54: aastore
    //   55: aload_0
    //   56: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   59: aload_0
    //   60: bipush 96
    //   62: aload_3
    //   63: aload 4
    //   65: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   68: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   71: astore 6
    //   73: aload 6
    //   75: aload_1
    //   76: iload_2
    //   77: invokevirtual 417	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:searchGlobalPayees	(Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;I)[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   80: astore 5
    //   82: goto +94 -> 176
    //   85: astore 6
    //   87: aload_3
    //   88: aload 6
    //   90: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   93: aload 6
    //   95: athrow
    //   96: astore 6
    //   98: aload_3
    //   99: aload 6
    //   101: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   104: goto +72 -> 176
    //   107: astore 6
    //   109: aload_3
    //   110: aload 6
    //   112: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   115: new 11	java/rmi/RemoteException
    //   118: dup
    //   119: ldc 53
    //   121: aload 6
    //   123: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   126: athrow
    //   127: astore 8
    //   129: jsr +6 -> 135
    //   132: aload 8
    //   134: athrow
    //   135: astore 7
    //   137: aload_0
    //   138: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   141: aload_0
    //   142: bipush 96
    //   144: aload_3
    //   145: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   148: goto +23 -> 171
    //   151: astore 10
    //   153: jsr +6 -> 159
    //   156: aload 10
    //   158: athrow
    //   159: astore 9
    //   161: aload_0
    //   162: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   165: aload_3
    //   166: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   169: ret 9
    //   171: jsr -12 -> 159
    //   174: ret 7
    //   176: jsr -41 -> 135
    //   179: aload 5
    //   181: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	182	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	182	1	paramPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo
    //   0	182	2	paramInt	int
    //   8	158	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	51	4	arrayOfObject	java.lang.Object[]
    //   19	161	5	arrayOfPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo[]
    //   71	3	6	localBPWServicesBean	BPWServicesBean
    //   85	9	6	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   96	4	6	localRemoteException	RemoteException
    //   107	15	6	localThrowable	java.lang.Throwable
    //   135	1	7	localObject1	java.lang.Object
    //   127	6	8	localObject2	java.lang.Object
    //   159	1	9	localObject3	java.lang.Object
    //   151	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   21	85	85	com/ffusion/ffs/interfaces/FFSException
    //   21	85	96	java/rmi/RemoteException
    //   21	85	107	java/lang/Throwable
    //   21	127	127	finally
    //   176	179	127	finally
    //   137	151	151	finally
    //   171	174	151	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo addPaymentBatch(com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo paramPaymentBatchInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 97
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 421	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addPaymentBatch	(Lcom/ffusion/ffs/bpw/interfaces/PaymentBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/PaymentBatchInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 97
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramPaymentBatchInfo	com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPaymentBatchInfo	com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo deletePaymentBatch(com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo paramPaymentBatchInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 98
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 424	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:deletePaymentBatch	(Lcom/ffusion/ffs/bpw/interfaces/PaymentBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/PaymentBatchInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 98
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramPaymentBatchInfo	com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPaymentBatchInfo	com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo getPaymentBatchById(java.lang.String paramString)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 99
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 428	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getPaymentBatchById	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/PaymentBatchInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 99
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramString	java.lang.String
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPaymentBatchInfo	com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo modifyPaymentBatch(com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo paramPaymentBatchInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 100
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 431	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modifyPaymentBatch	(Lcom/ffusion/ffs/bpw/interfaces/PaymentBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/PaymentBatchInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 100
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramPaymentBatchInfo	com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPaymentBatchInfo	com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PmtInfo addBillPayment(com.ffusion.ffs.bpw.interfaces.PmtInfo paramPmtInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 101
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 435	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addBillPayment	(Lcom/ffusion/ffs/bpw/interfaces/PmtInfo;)Lcom/ffusion/ffs/bpw/interfaces/PmtInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 101
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramPmtInfo	com.ffusion.ffs.bpw.interfaces.PmtInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPmtInfo	com.ffusion.ffs.bpw.interfaces.PmtInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PmtInfo deleteBillPayment(com.ffusion.ffs.bpw.interfaces.PmtInfo paramPmtInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 102
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 438	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:deleteBillPayment	(Lcom/ffusion/ffs/bpw/interfaces/PmtInfo;)Lcom/ffusion/ffs/bpw/interfaces/PmtInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 102
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramPmtInfo	com.ffusion.ffs.bpw.interfaces.PmtInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPmtInfo	com.ffusion.ffs.bpw.interfaces.PmtInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PmtInfo getBillPaymentById(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 103
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 442	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getBillPaymentById	(Ljava/lang/String;Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/PmtInfo;
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
    //   91: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   128: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_0
    //   132: bipush 103
    //   134: aload_3
    //   135: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: goto +23 -> 161
    //   141: astore 10
    //   143: jsr +6 -> 149
    //   146: aload 10
    //   148: athrow
    //   149: astore 9
    //   151: aload_0
    //   152: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	172	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	172	1	paramString1	java.lang.String
    //   0	172	2	paramString2	java.lang.String
    //   8	148	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	154	5	localPmtInfo	com.ffusion.ffs.bpw.interfaces.PmtInfo
    //   61	3	6	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PmtInfo modifyBillPayment(com.ffusion.ffs.bpw.interfaces.PmtInfo paramPmtInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 104
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 445	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modifyBillPayment	(Lcom/ffusion/ffs/bpw/interfaces/PmtInfo;)Lcom/ffusion/ffs/bpw/interfaces/PmtInfo;
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
    //   81: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 104
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramPmtInfo	com.ffusion.ffs.bpw.interfaces.PmtInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPmtInfo	com.ffusion.ffs.bpw.interfaces.PmtInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RPPSBillerInfo getRPPSBillerInfoByFIAndBillerRPPSId(java.lang.String paramString1, java.lang.String paramString2)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 105
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 449	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRPPSBillerInfoByFIAndBillerRPPSId	(Ljava/lang/String;Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/RPPSBillerInfo;
    //   70: astore 5
    //   72: goto +94 -> 166
    //   75: astore 6
    //   77: aload_3
    //   78: aload 6
    //   80: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   83: aload 6
    //   85: athrow
    //   86: astore 6
    //   88: aload_3
    //   89: aload 6
    //   91: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   94: goto +72 -> 166
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
    //   128: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_0
    //   132: bipush 105
    //   134: aload_3
    //   135: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: goto +23 -> 161
    //   141: astore 10
    //   143: jsr +6 -> 149
    //   146: aload 10
    //   148: athrow
    //   149: astore 9
    //   151: aload_0
    //   152: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	172	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	172	1	paramString1	java.lang.String
    //   0	172	2	paramString2	java.lang.String
    //   8	148	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	154	5	localRPPSBillerInfo	com.ffusion.ffs.bpw.interfaces.RPPSBillerInfo
    //   61	3	6	localBPWServicesBean	BPWServicesBean
    //   75	9	6	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   86	4	6	localRemoteException	RemoteException
    //   97	15	6	localThrowable	java.lang.Throwable
    //   125	1	7	localObject1	java.lang.Object
    //   117	6	8	localObject2	java.lang.Object
    //   149	1	9	localObject3	java.lang.Object
    //   141	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	75	75	com/ffusion/ffs/interfaces/FFSException
    //   18	75	86	java/rmi/RemoteException
    //   18	75	97	java/lang/Throwable
    //   18	117	117	finally
    //   166	169	117	finally
    //   127	141	141	finally
    //   161	164	141	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RPPSBillerInfo[] getRPPSBillerInfoByFIRPPSId(java.lang.String paramString)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 453	[Lcom/ffusion/ffs/bpw/interfaces/RPPSBillerInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 106
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 455	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRPPSBillerInfoByFIRPPSId	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/RPPSBillerInfo;
    //   63: astore 4
    //   65: goto +94 -> 159
    //   68: astore 5
    //   70: aload_2
    //   71: aload 5
    //   73: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   76: aload 5
    //   78: athrow
    //   79: astore 5
    //   81: aload_2
    //   82: aload 5
    //   84: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   87: goto +72 -> 159
    //   90: astore 5
    //   92: aload_2
    //   93: aload 5
    //   95: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   98: new 11	java/rmi/RemoteException
    //   101: dup
    //   102: ldc 53
    //   104: aload 5
    //   106: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   109: athrow
    //   110: astore 7
    //   112: jsr +6 -> 118
    //   115: aload 7
    //   117: athrow
    //   118: astore 6
    //   120: aload_0
    //   121: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   124: aload_0
    //   125: bipush 106
    //   127: aload_2
    //   128: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   131: goto +23 -> 154
    //   134: astore 9
    //   136: jsr +6 -> 142
    //   139: aload 9
    //   141: athrow
    //   142: astore 8
    //   144: aload_0
    //   145: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   148: aload_2
    //   149: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   152: ret 8
    //   154: jsr -12 -> 142
    //   157: ret 6
    //   159: jsr -41 -> 118
    //   162: aload 4
    //   164: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	165	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	165	1	paramString	java.lang.String
    //   8	141	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	145	4	arrayOfRPPSBillerInfo	com.ffusion.ffs.bpw.interfaces.RPPSBillerInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
    //   68	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   79	4	5	localRemoteException	RemoteException
    //   90	15	5	localThrowable	java.lang.Throwable
    //   118	1	6	localObject1	java.lang.Object
    //   110	6	7	localObject2	java.lang.Object
    //   142	1	8	localObject3	java.lang.Object
    //   134	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	68	68	com/ffusion/ffs/interfaces/FFSException
    //   20	68	79	java/rmi/RemoteException
    //   20	68	90	java/lang/Throwable
    //   20	110	110	finally
    //   159	162	110	finally
    //   120	134	134	finally
    //   154	157	134	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RPPSFIInfo activateRPPSFI(com.ffusion.ffs.bpw.interfaces.RPPSFIInfo paramRPPSFIInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 107
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 459	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:activateRPPSFI	(Lcom/ffusion/ffs/bpw/interfaces/RPPSFIInfo;)Lcom/ffusion/ffs/bpw/interfaces/RPPSFIInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 107
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramRPPSFIInfo	com.ffusion.ffs.bpw.interfaces.RPPSFIInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localRPPSFIInfo	com.ffusion.ffs.bpw.interfaces.RPPSFIInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RPPSFIInfo addRPPSFIInfo(com.ffusion.ffs.bpw.interfaces.RPPSFIInfo paramRPPSFIInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 108
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 462	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addRPPSFIInfo	(Lcom/ffusion/ffs/bpw/interfaces/RPPSFIInfo;)Lcom/ffusion/ffs/bpw/interfaces/RPPSFIInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 108
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramRPPSFIInfo	com.ffusion.ffs.bpw.interfaces.RPPSFIInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localRPPSFIInfo	com.ffusion.ffs.bpw.interfaces.RPPSFIInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RPPSFIInfo canRPPSFIInfo(com.ffusion.ffs.bpw.interfaces.RPPSFIInfo paramRPPSFIInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 109
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 465	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:canRPPSFIInfo	(Lcom/ffusion/ffs/bpw/interfaces/RPPSFIInfo;)Lcom/ffusion/ffs/bpw/interfaces/RPPSFIInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 109
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramRPPSFIInfo	com.ffusion.ffs.bpw.interfaces.RPPSFIInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localRPPSFIInfo	com.ffusion.ffs.bpw.interfaces.RPPSFIInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RPPSFIInfo getRPPSFIInfoByFIId(java.lang.String paramString)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 110
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 469	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRPPSFIInfoByFIId	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/RPPSFIInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 110
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramString	java.lang.String
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localRPPSFIInfo	com.ffusion.ffs.bpw.interfaces.RPPSFIInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RPPSFIInfo getRPPSFIInfoByFIRPPSId(java.lang.String paramString)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 111
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 472	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRPPSFIInfoByFIRPPSId	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/RPPSFIInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 111
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramString	java.lang.String
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localRPPSFIInfo	com.ffusion.ffs.bpw.interfaces.RPPSFIInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RPPSFIInfo modRPPSFIInfo(com.ffusion.ffs.bpw.interfaces.RPPSFIInfo paramRPPSFIInfo)
    throws com.ffusion.ffs.interfaces.FFSException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 112
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 475	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modRPPSFIInfo	(Lcom/ffusion/ffs/bpw/interfaces/RPPSFIInfo;)Lcom/ffusion/ffs/bpw/interfaces/RPPSFIInfo;
    //   60: astore 4
    //   62: goto +94 -> 156
    //   65: astore 5
    //   67: aload_2
    //   68: aload 5
    //   70: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 5
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +72 -> 156
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
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 112
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	162	1	paramRPPSFIInfo	com.ffusion.ffs.bpw.interfaces.RPPSFIInfo
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localRPPSFIInfo	com.ffusion.ffs.bpw.interfaces.RPPSFIInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
    //   65	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   76	4	5	localRemoteException	RemoteException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	com/ffusion/ffs/interfaces/FFSException
    //   17	65	76	java/rmi/RemoteException
    //   17	65	87	java/lang/Throwable
    //   17	107	107	finally
    //   156	159	107	finally
    //   117	131	131	finally
    //   151	154	131	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RecTransferInfo addRecTransfer(com.ffusion.ffs.bpw.interfaces.RecTransferInfo paramRecTransferInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 113
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 479	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addRecTransfer	(Lcom/ffusion/ffs/bpw/interfaces/RecTransferInfo;)Lcom/ffusion/ffs/bpw/interfaces/RecTransferInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 113
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramRecTransferInfo	com.ffusion.ffs.bpw.interfaces.RecTransferInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecTransferInfo	com.ffusion.ffs.bpw.interfaces.RecTransferInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecTransferInfo cancRecTransfer(com.ffusion.ffs.bpw.interfaces.RecTransferInfo paramRecTransferInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 114
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 482	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:cancRecTransfer	(Lcom/ffusion/ffs/bpw/interfaces/RecTransferInfo;)Lcom/ffusion/ffs/bpw/interfaces/RecTransferInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 114
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramRecTransferInfo	com.ffusion.ffs.bpw.interfaces.RecTransferInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecTransferInfo	com.ffusion.ffs.bpw.interfaces.RecTransferInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecTransferInfo getRecTransferBySrvrTId(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 115
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 486	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRecTransferBySrvrTId	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/RecTransferInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 115
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecTransferInfo	com.ffusion.ffs.bpw.interfaces.RecTransferInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecTransferInfo getRecTransferBySrvrTId(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 116
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 489	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRecTransferBySrvrTId	(Ljava/lang/String;Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/RecTransferInfo;
    //   70: astore 5
    //   72: goto +83 -> 155
    //   75: astore 6
    //   77: aload_3
    //   78: aload 6
    //   80: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   83: goto +72 -> 155
    //   86: astore 6
    //   88: aload_3
    //   89: aload 6
    //   91: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   94: new 11	java/rmi/RemoteException
    //   97: dup
    //   98: ldc 53
    //   100: aload 6
    //   102: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   105: athrow
    //   106: astore 8
    //   108: jsr +6 -> 114
    //   111: aload 8
    //   113: athrow
    //   114: astore 7
    //   116: aload_0
    //   117: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: bipush 116
    //   123: aload_3
    //   124: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   127: goto +23 -> 150
    //   130: astore 10
    //   132: jsr +6 -> 138
    //   135: aload 10
    //   137: athrow
    //   138: astore 9
    //   140: aload_0
    //   141: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   144: aload_3
    //   145: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   148: ret 9
    //   150: jsr -12 -> 138
    //   153: ret 7
    //   155: jsr -41 -> 114
    //   158: aload 5
    //   160: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	161	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	161	1	paramString1	java.lang.String
    //   0	161	2	paramString2	java.lang.String
    //   8	137	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	143	5	localRecTransferInfo	com.ffusion.ffs.bpw.interfaces.RecTransferInfo
    //   61	3	6	localBPWServicesBean	BPWServicesBean
    //   75	4	6	localRemoteException	RemoteException
    //   86	15	6	localThrowable	java.lang.Throwable
    //   114	1	7	localObject1	java.lang.Object
    //   106	6	8	localObject2	java.lang.Object
    //   138	1	9	localObject3	java.lang.Object
    //   130	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	75	75	java/rmi/RemoteException
    //   18	75	86	java/lang/Throwable
    //   18	106	106	finally
    //   155	158	106	finally
    //   116	130	130	finally
    //   150	153	130	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RecTransferInfo getRecTransferByTrackingId(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 117
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 492	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRecTransferByTrackingId	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/RecTransferInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 117
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecTransferInfo	com.ffusion.ffs.bpw.interfaces.RecTransferInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecTransferInfo modRecTransfer(com.ffusion.ffs.bpw.interfaces.RecTransferInfo paramRecTransferInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 118
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 495	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modRecTransfer	(Lcom/ffusion/ffs/bpw/interfaces/RecTransferInfo;)Lcom/ffusion/ffs/bpw/interfaces/RecTransferInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 118
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramRecTransferInfo	com.ffusion.ffs.bpw.interfaces.RecTransferInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecTransferInfo	com.ffusion.ffs.bpw.interfaces.RecTransferInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecTransferInfo[] getRecTransfersBySrvrTId(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 499	[Lcom/ffusion/ffs/bpw/interfaces/RecTransferInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 119
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 501	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRecTransfersBySrvrTId	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/RecTransferInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 119
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramArrayOfString	java.lang.String[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfRecTransferInfo	com.ffusion.ffs.bpw.interfaces.RecTransferInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecTransferInfo[] getRecTransfersByTrackingId(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 499	[Lcom/ffusion/ffs/bpw/interfaces/RecTransferInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 120
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 504	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRecTransfersByTrackingId	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/RecTransferInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 120
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramArrayOfString	java.lang.String[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfRecTransferInfo	com.ffusion.ffs.bpw.interfaces.RecTransferInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecWireInfo addRecWireTransfer(com.ffusion.ffs.bpw.interfaces.RecWireInfo paramRecWireInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 121
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 508	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addRecWireTransfer	(Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;)Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 121
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramRecWireInfo	com.ffusion.ffs.bpw.interfaces.RecWireInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecWireInfo	com.ffusion.ffs.bpw.interfaces.RecWireInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecWireInfo cancRecWireTransfer(com.ffusion.ffs.bpw.interfaces.RecWireInfo paramRecWireInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 122
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 511	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:cancRecWireTransfer	(Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;)Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 122
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramRecWireInfo	com.ffusion.ffs.bpw.interfaces.RecWireInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecWireInfo	com.ffusion.ffs.bpw.interfaces.RecWireInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecWireInfo getRecWireTransfer(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 123
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 515	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRecWireTransfer	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 123
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecWireInfo	com.ffusion.ffs.bpw.interfaces.RecWireInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecWireInfo getRecWireTransferById(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 124
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 518	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRecWireTransferById	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 124
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecWireInfo	com.ffusion.ffs.bpw.interfaces.RecWireInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecWireInfo getRecWireTransferById(java.lang.String paramString, boolean paramBoolean)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   22: aload_0
    //   23: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   26: ifeq +26 -> 52
    //   29: iconst_2
    //   30: anewarray 34	java/lang/Object
    //   33: astore 4
    //   35: aload 4
    //   37: iconst_0
    //   38: aload_1
    //   39: aastore
    //   40: aload 4
    //   42: iconst_1
    //   43: new 521	java/lang/Boolean
    //   46: dup
    //   47: iload_2
    //   48: invokespecial 524	java/lang/Boolean:<init>	(Z)V
    //   51: aastore
    //   52: aload_0
    //   53: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   56: aload_0
    //   57: bipush 125
    //   59: aload_3
    //   60: aload 4
    //   62: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   65: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   68: astore 6
    //   70: aload 6
    //   72: aload_1
    //   73: iload_2
    //   74: invokevirtual 526	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRecWireTransferById	(Ljava/lang/String;Z)Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;
    //   77: astore 5
    //   79: goto +83 -> 162
    //   82: astore 6
    //   84: aload_3
    //   85: aload 6
    //   87: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   90: goto +72 -> 162
    //   93: astore 6
    //   95: aload_3
    //   96: aload 6
    //   98: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   101: new 11	java/rmi/RemoteException
    //   104: dup
    //   105: ldc 53
    //   107: aload 6
    //   109: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   112: athrow
    //   113: astore 8
    //   115: jsr +6 -> 121
    //   118: aload 8
    //   120: athrow
    //   121: astore 7
    //   123: aload_0
    //   124: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   127: aload_0
    //   128: bipush 125
    //   130: aload_3
    //   131: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   134: goto +23 -> 157
    //   137: astore 10
    //   139: jsr +6 -> 145
    //   142: aload 10
    //   144: athrow
    //   145: astore 9
    //   147: aload_0
    //   148: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   151: aload_3
    //   152: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   155: ret 9
    //   157: jsr -12 -> 145
    //   160: ret 7
    //   162: jsr -41 -> 121
    //   165: aload 5
    //   167: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	168	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	168	1	paramString	java.lang.String
    //   0	168	2	paramBoolean	boolean
    //   8	144	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	48	4	arrayOfObject	java.lang.Object[]
    //   16	150	5	localRecWireInfo	com.ffusion.ffs.bpw.interfaces.RecWireInfo
    //   68	3	6	localBPWServicesBean	BPWServicesBean
    //   82	4	6	localRemoteException	RemoteException
    //   93	15	6	localThrowable	java.lang.Throwable
    //   121	1	7	localObject1	java.lang.Object
    //   113	6	8	localObject2	java.lang.Object
    //   145	1	9	localObject3	java.lang.Object
    //   137	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	82	82	java/rmi/RemoteException
    //   18	82	93	java/lang/Throwable
    //   18	113	113	finally
    //   162	165	113	finally
    //   123	137	137	finally
    //   157	160	137	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.RecWireInfo modRecWireTransfer(com.ffusion.ffs.bpw.interfaces.RecWireInfo paramRecWireInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 126
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 529	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modRecWireTransfer	(Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;)Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;
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
    //   107: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 126
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	151	1	paramRecWireInfo	com.ffusion.ffs.bpw.interfaces.RecWireInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localRecWireInfo	com.ffusion.ffs.bpw.interfaces.RecWireInfo
    //   52	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecWireInfo[] addRecWireTransfers(com.ffusion.ffs.bpw.interfaces.RecWireInfo[] paramArrayOfRecWireInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 533	[Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 127
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 535	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addRecWireTransfers	([Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;)[Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;
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
    //   110: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 127
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	154	1	paramArrayOfRecWireInfo	com.ffusion.ffs.bpw.interfaces.RecWireInfo[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfRecWireInfo	com.ffusion.ffs.bpw.interfaces.RecWireInfo[]
    //   55	3	5	localBPWServicesBean	BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecWireInfo[] getRecWireTransfers(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 533	[Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: sipush 128
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   53: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   56: astore 5
    //   58: aload 5
    //   60: aload_1
    //   61: invokevirtual 539	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getRecWireTransfers	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/RecWireInfo;
    //   64: astore 4
    //   66: goto +84 -> 150
    //   69: astore 5
    //   71: aload_2
    //   72: aload 5
    //   74: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   77: goto +73 -> 150
    //   80: astore 5
    //   82: aload_2
    //   83: aload 5
    //   85: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   88: new 11	java/rmi/RemoteException
    //   91: dup
    //   92: ldc 53
    //   94: aload 5
    //   96: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   99: athrow
    //   100: astore 7
    //   102: jsr +6 -> 108
    //   105: aload 7
    //   107: athrow
    //   108: astore 6
    //   110: aload_0
    //   111: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   114: aload_0
    //   115: sipush 128
    //   118: aload_2
    //   119: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   122: goto +23 -> 145
    //   125: astore 9
    //   127: jsr +6 -> 133
    //   130: aload 9
    //   132: athrow
    //   133: astore 8
    //   135: aload_0
    //   136: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   139: aload_2
    //   140: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   143: ret 8
    //   145: jsr -12 -> 133
    //   148: ret 6
    //   150: jsr -42 -> 108
    //   153: aload 4
    //   155: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	156	1	paramArrayOfString	java.lang.String[]
    //   8	132	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	37	3	arrayOfObject	java.lang.Object[]
    //   18	136	4	arrayOfRecWireInfo	com.ffusion.ffs.bpw.interfaces.RecWireInfo[]
    //   56	3	5	localBPWServicesBean	BPWServicesBean
    //   69	4	5	localRemoteException	RemoteException
    //   80	15	5	localThrowable	java.lang.Throwable
    //   108	1	6	localObject1	java.lang.Object
    //   100	6	7	localObject2	java.lang.Object
    //   133	1	8	localObject3	java.lang.Object
    //   125	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	69	69	java/rmi/RemoteException
    //   20	69	80	java/lang/Throwable
    //   20	100	100	finally
    //   150	153	100	finally
    //   110	125	125	finally
    //   145	148	125	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferBatchInfo addTransferBatch(com.ffusion.ffs.bpw.interfaces.TransferBatchInfo paramTransferBatchInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 129
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 543	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addTransferBatch	(Lcom/ffusion/ffs/bpw/interfaces/TransferBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/TransferBatchInfo;
    //   61: astore 4
    //   63: goto +95 -> 158
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +84 -> 158
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   85: aload 5
    //   87: athrow
    //   88: astore 5
    //   90: aload_2
    //   91: aload 5
    //   93: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   96: new 11	java/rmi/RemoteException
    //   99: dup
    //   100: ldc 53
    //   102: aload 5
    //   104: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   107: athrow
    //   108: astore 7
    //   110: jsr +6 -> 116
    //   113: aload 7
    //   115: athrow
    //   116: astore 6
    //   118: aload_0
    //   119: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   122: aload_0
    //   123: sipush 129
    //   126: aload_2
    //   127: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   130: goto +23 -> 153
    //   133: astore 9
    //   135: jsr +6 -> 141
    //   138: aload 9
    //   140: athrow
    //   141: astore 8
    //   143: aload_0
    //   144: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   147: aload_2
    //   148: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   151: ret 8
    //   153: jsr -12 -> 141
    //   156: ret 6
    //   158: jsr -42 -> 116
    //   161: aload 4
    //   163: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	164	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	164	1	paramTransferBatchInfo	com.ffusion.ffs.bpw.interfaces.TransferBatchInfo
    //   8	140	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	147	4	localTransferBatchInfo	com.ffusion.ffs.bpw.interfaces.TransferBatchInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   88	15	5	localThrowable	java.lang.Throwable
    //   116	1	6	localObject1	java.lang.Object
    //   108	6	7	localObject2	java.lang.Object
    //   141	1	8	localObject3	java.lang.Object
    //   133	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	com/ffusion/ffs/interfaces/FFSException
    //   17	66	88	java/lang/Throwable
    //   17	108	108	finally
    //   158	161	108	finally
    //   118	133	133	finally
    //   153	156	133	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferBatchInfo cancelTransferBatch(com.ffusion.ffs.bpw.interfaces.TransferBatchInfo paramTransferBatchInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 130
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 546	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:cancelTransferBatch	(Lcom/ffusion/ffs/bpw/interfaces/TransferBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/TransferBatchInfo;
    //   61: astore 4
    //   63: goto +95 -> 158
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +84 -> 158
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   85: aload 5
    //   87: athrow
    //   88: astore 5
    //   90: aload_2
    //   91: aload 5
    //   93: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   96: new 11	java/rmi/RemoteException
    //   99: dup
    //   100: ldc 53
    //   102: aload 5
    //   104: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   107: athrow
    //   108: astore 7
    //   110: jsr +6 -> 116
    //   113: aload 7
    //   115: athrow
    //   116: astore 6
    //   118: aload_0
    //   119: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   122: aload_0
    //   123: sipush 130
    //   126: aload_2
    //   127: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   130: goto +23 -> 153
    //   133: astore 9
    //   135: jsr +6 -> 141
    //   138: aload 9
    //   140: athrow
    //   141: astore 8
    //   143: aload_0
    //   144: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   147: aload_2
    //   148: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   151: ret 8
    //   153: jsr -12 -> 141
    //   156: ret 6
    //   158: jsr -42 -> 116
    //   161: aload 4
    //   163: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	164	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	164	1	paramTransferBatchInfo	com.ffusion.ffs.bpw.interfaces.TransferBatchInfo
    //   8	140	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	147	4	localTransferBatchInfo	com.ffusion.ffs.bpw.interfaces.TransferBatchInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   88	15	5	localThrowable	java.lang.Throwable
    //   116	1	6	localObject1	java.lang.Object
    //   108	6	7	localObject2	java.lang.Object
    //   141	1	8	localObject3	java.lang.Object
    //   133	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	com/ffusion/ffs/interfaces/FFSException
    //   17	66	88	java/lang/Throwable
    //   17	108	108	finally
    //   158	161	108	finally
    //   118	133	133	finally
    //   153	156	133	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferBatchInfo getTransferBatchById(java.lang.String paramString)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 131
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 550	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getTransferBatchById	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/TransferBatchInfo;
    //   61: astore 4
    //   63: goto +95 -> 158
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +84 -> 158
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   85: aload 5
    //   87: athrow
    //   88: astore 5
    //   90: aload_2
    //   91: aload 5
    //   93: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   96: new 11	java/rmi/RemoteException
    //   99: dup
    //   100: ldc 53
    //   102: aload 5
    //   104: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   107: athrow
    //   108: astore 7
    //   110: jsr +6 -> 116
    //   113: aload 7
    //   115: athrow
    //   116: astore 6
    //   118: aload_0
    //   119: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   122: aload_0
    //   123: sipush 131
    //   126: aload_2
    //   127: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   130: goto +23 -> 153
    //   133: astore 9
    //   135: jsr +6 -> 141
    //   138: aload 9
    //   140: athrow
    //   141: astore 8
    //   143: aload_0
    //   144: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   147: aload_2
    //   148: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   151: ret 8
    //   153: jsr -12 -> 141
    //   156: ret 6
    //   158: jsr -42 -> 116
    //   161: aload 4
    //   163: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	164	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	164	1	paramString	java.lang.String
    //   8	140	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	147	4	localTransferBatchInfo	com.ffusion.ffs.bpw.interfaces.TransferBatchInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   88	15	5	localThrowable	java.lang.Throwable
    //   116	1	6	localObject1	java.lang.Object
    //   108	6	7	localObject2	java.lang.Object
    //   141	1	8	localObject3	java.lang.Object
    //   133	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	com/ffusion/ffs/interfaces/FFSException
    //   17	66	88	java/lang/Throwable
    //   17	108	108	finally
    //   158	161	108	finally
    //   118	133	133	finally
    //   153	156	133	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferBatchInfo modifyTransferBatch(com.ffusion.ffs.bpw.interfaces.TransferBatchInfo paramTransferBatchInfo)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 132
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 553	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modifyTransferBatch	(Lcom/ffusion/ffs/bpw/interfaces/TransferBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/TransferBatchInfo;
    //   61: astore 4
    //   63: goto +95 -> 158
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +84 -> 158
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   85: aload 5
    //   87: athrow
    //   88: astore 5
    //   90: aload_2
    //   91: aload 5
    //   93: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   96: new 11	java/rmi/RemoteException
    //   99: dup
    //   100: ldc 53
    //   102: aload 5
    //   104: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   107: athrow
    //   108: astore 7
    //   110: jsr +6 -> 116
    //   113: aload 7
    //   115: athrow
    //   116: astore 6
    //   118: aload_0
    //   119: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   122: aload_0
    //   123: sipush 132
    //   126: aload_2
    //   127: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   130: goto +23 -> 153
    //   133: astore 9
    //   135: jsr +6 -> 141
    //   138: aload 9
    //   140: athrow
    //   141: astore 8
    //   143: aload_0
    //   144: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   147: aload_2
    //   148: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   151: ret 8
    //   153: jsr -12 -> 141
    //   156: ret 6
    //   158: jsr -42 -> 116
    //   161: aload 4
    //   163: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	164	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	164	1	paramTransferBatchInfo	com.ffusion.ffs.bpw.interfaces.TransferBatchInfo
    //   8	140	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	147	4	localTransferBatchInfo	com.ffusion.ffs.bpw.interfaces.TransferBatchInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	9	5	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   88	15	5	localThrowable	java.lang.Throwable
    //   116	1	6	localObject1	java.lang.Object
    //   108	6	7	localObject2	java.lang.Object
    //   141	1	8	localObject3	java.lang.Object
    //   133	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	com/ffusion/ffs/interfaces/FFSException
    //   17	66	88	java/lang/Throwable
    //   17	108	108	finally
    //   158	161	108	finally
    //   118	133	133	finally
    //   153	156	133	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferInfo addTransfer(com.ffusion.ffs.bpw.interfaces.TransferInfo paramTransferInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 133
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 557	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addTransfer	(Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;)Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 133
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferInfo cancTransfer(com.ffusion.ffs.bpw.interfaces.TransferInfo paramTransferInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 134
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 560	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:cancTransfer	(Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;)Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 134
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferInfo getTransferBySrvrTId(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 135
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 564	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getTransferBySrvrTId	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 135
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramString	java.lang.String
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferInfo getTransferBySrvrTId(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: sipush 136
    //   53: aload_3
    //   54: aload 4
    //   56: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   59: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   62: astore 6
    //   64: aload 6
    //   66: aload_1
    //   67: aload_2
    //   68: invokevirtual 567	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getTransferBySrvrTId	(Ljava/lang/String;Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   71: astore 5
    //   73: goto +84 -> 157
    //   76: astore 6
    //   78: aload_3
    //   79: aload 6
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +73 -> 157
    //   87: astore 6
    //   89: aload_3
    //   90: aload 6
    //   92: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   95: new 11	java/rmi/RemoteException
    //   98: dup
    //   99: ldc 53
    //   101: aload 6
    //   103: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   106: athrow
    //   107: astore 8
    //   109: jsr +6 -> 115
    //   112: aload 8
    //   114: athrow
    //   115: astore 7
    //   117: aload_0
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: sipush 136
    //   125: aload_3
    //   126: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   129: goto +23 -> 152
    //   132: astore 10
    //   134: jsr +6 -> 140
    //   137: aload 10
    //   139: athrow
    //   140: astore 9
    //   142: aload_0
    //   143: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   146: aload_3
    //   147: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   150: ret 9
    //   152: jsr -12 -> 140
    //   155: ret 7
    //   157: jsr -42 -> 115
    //   160: aload 5
    //   162: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	163	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	163	1	paramString1	java.lang.String
    //   0	163	2	paramString2	java.lang.String
    //   8	139	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	42	4	arrayOfObject	java.lang.Object[]
    //   16	145	5	localTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo
    //   62	3	6	localBPWServicesBean	BPWServicesBean
    //   76	4	6	localRemoteException	RemoteException
    //   87	15	6	localThrowable	java.lang.Throwable
    //   115	1	7	localObject1	java.lang.Object
    //   107	6	8	localObject2	java.lang.Object
    //   140	1	9	localObject3	java.lang.Object
    //   132	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	76	76	java/rmi/RemoteException
    //   18	76	87	java/lang/Throwable
    //   18	107	107	finally
    //   157	160	107	finally
    //   117	132	132	finally
    //   152	155	132	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferInfo getTransferByTrackingId(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 137
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 570	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getTransferByTrackingId	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 137
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramString	java.lang.String
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferInfo modTransfer(com.ffusion.ffs.bpw.interfaces.TransferInfo paramTransferInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 138
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 573	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modTransfer	(Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;)Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 138
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferInfo[] addTransfers(com.ffusion.ffs.bpw.interfaces.TransferInfo[] paramArrayOfTransferInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 577	[Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: sipush 139
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   53: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   56: astore 5
    //   58: aload 5
    //   60: aload_1
    //   61: invokevirtual 579	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addTransfers	([Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;)[Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   64: astore 4
    //   66: goto +84 -> 150
    //   69: astore 5
    //   71: aload_2
    //   72: aload 5
    //   74: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   77: goto +73 -> 150
    //   80: astore 5
    //   82: aload_2
    //   83: aload 5
    //   85: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   88: new 11	java/rmi/RemoteException
    //   91: dup
    //   92: ldc 53
    //   94: aload 5
    //   96: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   99: athrow
    //   100: astore 7
    //   102: jsr +6 -> 108
    //   105: aload 7
    //   107: athrow
    //   108: astore 6
    //   110: aload_0
    //   111: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   114: aload_0
    //   115: sipush 139
    //   118: aload_2
    //   119: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   122: goto +23 -> 145
    //   125: astore 9
    //   127: jsr +6 -> 133
    //   130: aload 9
    //   132: athrow
    //   133: astore 8
    //   135: aload_0
    //   136: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   139: aload_2
    //   140: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   143: ret 8
    //   145: jsr -12 -> 133
    //   148: ret 6
    //   150: jsr -42 -> 108
    //   153: aload 4
    //   155: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	156	1	paramArrayOfTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo[]
    //   8	132	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	37	3	arrayOfObject	java.lang.Object[]
    //   18	136	4	arrayOfTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo[]
    //   56	3	5	localBPWServicesBean	BPWServicesBean
    //   69	4	5	localRemoteException	RemoteException
    //   80	15	5	localThrowable	java.lang.Throwable
    //   108	1	6	localObject1	java.lang.Object
    //   100	6	7	localObject2	java.lang.Object
    //   133	1	8	localObject3	java.lang.Object
    //   125	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	69	69	java/rmi/RemoteException
    //   20	69	80	java/lang/Throwable
    //   20	100	100	finally
    //   150	153	100	finally
    //   110	125	125	finally
    //   145	148	125	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferInfo[] getTransfersByRecSrvrTId(java.lang.String paramString, boolean paramBoolean)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 577	[Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   25: aload_0
    //   26: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   29: ifeq +26 -> 55
    //   32: iconst_2
    //   33: anewarray 34	java/lang/Object
    //   36: astore 4
    //   38: aload 4
    //   40: iconst_0
    //   41: aload_1
    //   42: aastore
    //   43: aload 4
    //   45: iconst_1
    //   46: new 521	java/lang/Boolean
    //   49: dup
    //   50: iload_2
    //   51: invokespecial 524	java/lang/Boolean:<init>	(Z)V
    //   54: aastore
    //   55: aload_0
    //   56: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   59: aload_0
    //   60: sipush 140
    //   63: aload_3
    //   64: aload 4
    //   66: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   69: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   72: astore 6
    //   74: aload 6
    //   76: aload_1
    //   77: iload_2
    //   78: invokevirtual 583	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getTransfersByRecSrvrTId	(Ljava/lang/String;Z)[Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   81: astore 5
    //   83: goto +84 -> 167
    //   86: astore 6
    //   88: aload_3
    //   89: aload 6
    //   91: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   94: goto +73 -> 167
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
    //   128: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_0
    //   132: sipush 140
    //   135: aload_3
    //   136: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   139: goto +23 -> 162
    //   142: astore 10
    //   144: jsr +6 -> 150
    //   147: aload 10
    //   149: athrow
    //   150: astore 9
    //   152: aload_0
    //   153: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   156: aload_3
    //   157: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   160: ret 9
    //   162: jsr -12 -> 150
    //   165: ret 7
    //   167: jsr -42 -> 125
    //   170: aload 5
    //   172: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	173	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	173	1	paramString	java.lang.String
    //   0	173	2	paramBoolean	boolean
    //   8	149	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	52	4	arrayOfObject	java.lang.Object[]
    //   19	152	5	arrayOfTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo[]
    //   72	3	6	localBPWServicesBean	BPWServicesBean
    //   86	4	6	localRemoteException	RemoteException
    //   97	15	6	localThrowable	java.lang.Throwable
    //   125	1	7	localObject1	java.lang.Object
    //   117	6	8	localObject2	java.lang.Object
    //   150	1	9	localObject3	java.lang.Object
    //   142	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   21	86	86	java/rmi/RemoteException
    //   21	86	97	java/lang/Throwable
    //   21	117	117	finally
    //   167	170	117	finally
    //   127	142	142	finally
    //   162	165	142	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferInfo[] getTransfersByRecSrvrTId(java.lang.String[] paramArrayOfString, boolean paramBoolean)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 577	[Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   25: aload_0
    //   26: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   29: ifeq +26 -> 55
    //   32: iconst_2
    //   33: anewarray 34	java/lang/Object
    //   36: astore 4
    //   38: aload 4
    //   40: iconst_0
    //   41: aload_1
    //   42: aastore
    //   43: aload 4
    //   45: iconst_1
    //   46: new 521	java/lang/Boolean
    //   49: dup
    //   50: iload_2
    //   51: invokespecial 524	java/lang/Boolean:<init>	(Z)V
    //   54: aastore
    //   55: aload_0
    //   56: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   59: aload_0
    //   60: sipush 141
    //   63: aload_3
    //   64: aload 4
    //   66: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   69: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   72: astore 6
    //   74: aload 6
    //   76: aload_1
    //   77: iload_2
    //   78: invokevirtual 586	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getTransfersByRecSrvrTId	([Ljava/lang/String;Z)[Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   81: astore 5
    //   83: goto +84 -> 167
    //   86: astore 6
    //   88: aload_3
    //   89: aload 6
    //   91: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   94: goto +73 -> 167
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
    //   128: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_0
    //   132: sipush 141
    //   135: aload_3
    //   136: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   139: goto +23 -> 162
    //   142: astore 10
    //   144: jsr +6 -> 150
    //   147: aload 10
    //   149: athrow
    //   150: astore 9
    //   152: aload_0
    //   153: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   156: aload_3
    //   157: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   160: ret 9
    //   162: jsr -12 -> 150
    //   165: ret 7
    //   167: jsr -42 -> 125
    //   170: aload 5
    //   172: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	173	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	173	1	paramArrayOfString	java.lang.String[]
    //   0	173	2	paramBoolean	boolean
    //   8	149	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	52	4	arrayOfObject	java.lang.Object[]
    //   19	152	5	arrayOfTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo[]
    //   72	3	6	localBPWServicesBean	BPWServicesBean
    //   86	4	6	localRemoteException	RemoteException
    //   97	15	6	localThrowable	java.lang.Throwable
    //   125	1	7	localObject1	java.lang.Object
    //   117	6	8	localObject2	java.lang.Object
    //   150	1	9	localObject3	java.lang.Object
    //   142	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   21	86	86	java/rmi/RemoteException
    //   21	86	97	java/lang/Throwable
    //   21	117	117	finally
    //   167	170	117	finally
    //   127	142	142	finally
    //   162	165	142	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferInfo[] getTransfersBySrvrTId(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 577	[Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: sipush 142
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   53: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   56: astore 5
    //   58: aload 5
    //   60: aload_1
    //   61: invokevirtual 590	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getTransfersBySrvrTId	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   64: astore 4
    //   66: goto +84 -> 150
    //   69: astore 5
    //   71: aload_2
    //   72: aload 5
    //   74: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   77: goto +73 -> 150
    //   80: astore 5
    //   82: aload_2
    //   83: aload 5
    //   85: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   88: new 11	java/rmi/RemoteException
    //   91: dup
    //   92: ldc 53
    //   94: aload 5
    //   96: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   99: athrow
    //   100: astore 7
    //   102: jsr +6 -> 108
    //   105: aload 7
    //   107: athrow
    //   108: astore 6
    //   110: aload_0
    //   111: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   114: aload_0
    //   115: sipush 142
    //   118: aload_2
    //   119: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   122: goto +23 -> 145
    //   125: astore 9
    //   127: jsr +6 -> 133
    //   130: aload 9
    //   132: athrow
    //   133: astore 8
    //   135: aload_0
    //   136: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   139: aload_2
    //   140: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   143: ret 8
    //   145: jsr -12 -> 133
    //   148: ret 6
    //   150: jsr -42 -> 108
    //   153: aload 4
    //   155: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	156	1	paramArrayOfString	java.lang.String[]
    //   8	132	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	37	3	arrayOfObject	java.lang.Object[]
    //   18	136	4	arrayOfTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo[]
    //   56	3	5	localBPWServicesBean	BPWServicesBean
    //   69	4	5	localRemoteException	RemoteException
    //   80	15	5	localThrowable	java.lang.Throwable
    //   108	1	6	localObject1	java.lang.Object
    //   100	6	7	localObject2	java.lang.Object
    //   133	1	8	localObject3	java.lang.Object
    //   125	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	69	69	java/rmi/RemoteException
    //   20	69	80	java/lang/Throwable
    //   20	100	100	finally
    //   150	153	100	finally
    //   110	125	125	finally
    //   145	148	125	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.TransferInfo[] getTransfersByTrackingId(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 577	[Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: sipush 143
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   53: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   56: astore 5
    //   58: aload 5
    //   60: aload_1
    //   61: invokevirtual 593	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getTransfersByTrackingId	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;
    //   64: astore 4
    //   66: goto +84 -> 150
    //   69: astore 5
    //   71: aload_2
    //   72: aload 5
    //   74: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   77: goto +73 -> 150
    //   80: astore 5
    //   82: aload_2
    //   83: aload 5
    //   85: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   88: new 11	java/rmi/RemoteException
    //   91: dup
    //   92: ldc 53
    //   94: aload 5
    //   96: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   99: athrow
    //   100: astore 7
    //   102: jsr +6 -> 108
    //   105: aload 7
    //   107: athrow
    //   108: astore 6
    //   110: aload_0
    //   111: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   114: aload_0
    //   115: sipush 143
    //   118: aload_2
    //   119: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   122: goto +23 -> 145
    //   125: astore 9
    //   127: jsr +6 -> 133
    //   130: aload 9
    //   132: athrow
    //   133: astore 8
    //   135: aload_0
    //   136: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   139: aload_2
    //   140: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   143: ret 8
    //   145: jsr -12 -> 133
    //   148: ret 6
    //   150: jsr -42 -> 108
    //   153: aload 4
    //   155: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	156	1	paramArrayOfString	java.lang.String[]
    //   8	132	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	37	3	arrayOfObject	java.lang.Object[]
    //   18	136	4	arrayOfTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo[]
    //   56	3	5	localBPWServicesBean	BPWServicesBean
    //   69	4	5	localRemoteException	RemoteException
    //   80	15	5	localThrowable	java.lang.Throwable
    //   108	1	6	localObject1	java.lang.Object
    //   100	6	7	localObject2	java.lang.Object
    //   133	1	8	localObject3	java.lang.Object
    //   125	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	69	69	java/rmi/RemoteException
    //   20	69	80	java/lang/Throwable
    //   20	100	100	finally
    //   150	153	100	finally
    //   110	125	125	finally
    //   145	148	125	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireBatchInfo addWireTransferBatch(com.ffusion.ffs.bpw.interfaces.WireBatchInfo paramWireBatchInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 144
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 597	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addWireTransferBatch	(Lcom/ffusion/ffs/bpw/interfaces/WireBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/WireBatchInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 144
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramWireBatchInfo	com.ffusion.ffs.bpw.interfaces.WireBatchInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWireBatchInfo	com.ffusion.ffs.bpw.interfaces.WireBatchInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireBatchInfo canWireTransferBatch(com.ffusion.ffs.bpw.interfaces.WireBatchInfo paramWireBatchInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 145
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 600	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:canWireTransferBatch	(Lcom/ffusion/ffs/bpw/interfaces/WireBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/WireBatchInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 145
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramWireBatchInfo	com.ffusion.ffs.bpw.interfaces.WireBatchInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWireBatchInfo	com.ffusion.ffs.bpw.interfaces.WireBatchInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireBatchInfo modWireTransferBatch(com.ffusion.ffs.bpw.interfaces.WireBatchInfo paramWireBatchInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 146
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 603	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modWireTransferBatch	(Lcom/ffusion/ffs/bpw/interfaces/WireBatchInfo;)Lcom/ffusion/ffs/bpw/interfaces/WireBatchInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 146
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramWireBatchInfo	com.ffusion.ffs.bpw.interfaces.WireBatchInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWireBatchInfo	com.ffusion.ffs.bpw.interfaces.WireBatchInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireBatchInfo[] getWireTransferBatch(com.ffusion.ffs.bpw.interfaces.WireBatchInfo paramWireBatchInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 607	[Lcom/ffusion/ffs/bpw/interfaces/WireBatchInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: sipush 147
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   53: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   56: astore 5
    //   58: aload 5
    //   60: aload_1
    //   61: invokevirtual 609	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWireTransferBatch	(Lcom/ffusion/ffs/bpw/interfaces/WireBatchInfo;)[Lcom/ffusion/ffs/bpw/interfaces/WireBatchInfo;
    //   64: astore 4
    //   66: goto +84 -> 150
    //   69: astore 5
    //   71: aload_2
    //   72: aload 5
    //   74: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   77: goto +73 -> 150
    //   80: astore 5
    //   82: aload_2
    //   83: aload 5
    //   85: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   88: new 11	java/rmi/RemoteException
    //   91: dup
    //   92: ldc 53
    //   94: aload 5
    //   96: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   99: athrow
    //   100: astore 7
    //   102: jsr +6 -> 108
    //   105: aload 7
    //   107: athrow
    //   108: astore 6
    //   110: aload_0
    //   111: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   114: aload_0
    //   115: sipush 147
    //   118: aload_2
    //   119: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   122: goto +23 -> 145
    //   125: astore 9
    //   127: jsr +6 -> 133
    //   130: aload 9
    //   132: athrow
    //   133: astore 8
    //   135: aload_0
    //   136: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   139: aload_2
    //   140: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   143: ret 8
    //   145: jsr -12 -> 133
    //   148: ret 6
    //   150: jsr -42 -> 108
    //   153: aload 4
    //   155: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	156	1	paramWireBatchInfo	com.ffusion.ffs.bpw.interfaces.WireBatchInfo
    //   8	132	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	37	3	arrayOfObject	java.lang.Object[]
    //   18	136	4	arrayOfWireBatchInfo	com.ffusion.ffs.bpw.interfaces.WireBatchInfo[]
    //   56	3	5	localBPWServicesBean	BPWServicesBean
    //   69	4	5	localRemoteException	RemoteException
    //   80	15	5	localThrowable	java.lang.Throwable
    //   108	1	6	localObject1	java.lang.Object
    //   100	6	7	localObject2	java.lang.Object
    //   133	1	8	localObject3	java.lang.Object
    //   125	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	69	69	java/rmi/RemoteException
    //   20	69	80	java/lang/Throwable
    //   20	100	100	finally
    //   150	153	100	finally
    //   110	125	125	finally
    //   145	148	125	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireInfo addWireTransfer(com.ffusion.ffs.bpw.interfaces.WireInfo paramWireInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 148
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 613	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addWireTransfer	(Lcom/ffusion/ffs/bpw/interfaces/WireInfo;)Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 148
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireInfo cancWireTransfer(com.ffusion.ffs.bpw.interfaces.WireInfo paramWireInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 149
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 616	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:cancWireTransfer	(Lcom/ffusion/ffs/bpw/interfaces/WireInfo;)Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 149
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireInfo getWireTransfer(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 150
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 620	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWireTransfer	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 150
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramString	java.lang.String
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireInfo getWireTransferById(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 151
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 623	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWireTransferById	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 151
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramString	java.lang.String
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireInfo modWireTransfer(com.ffusion.ffs.bpw.interfaces.WireInfo paramWireInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 152
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 626	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modWireTransfer	(Lcom/ffusion/ffs/bpw/interfaces/WireInfo;)Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 152
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireInfo[] addWireTransfers(com.ffusion.ffs.bpw.interfaces.WireInfo[] paramArrayOfWireInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 630	[Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: sipush 153
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   53: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   56: astore 5
    //   58: aload 5
    //   60: aload_1
    //   61: invokevirtual 632	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addWireTransfers	([Lcom/ffusion/ffs/bpw/interfaces/WireInfo;)[Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   64: astore 4
    //   66: goto +84 -> 150
    //   69: astore 5
    //   71: aload_2
    //   72: aload 5
    //   74: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   77: goto +73 -> 150
    //   80: astore 5
    //   82: aload_2
    //   83: aload 5
    //   85: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   88: new 11	java/rmi/RemoteException
    //   91: dup
    //   92: ldc 53
    //   94: aload 5
    //   96: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   99: athrow
    //   100: astore 7
    //   102: jsr +6 -> 108
    //   105: aload 7
    //   107: athrow
    //   108: astore 6
    //   110: aload_0
    //   111: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   114: aload_0
    //   115: sipush 153
    //   118: aload_2
    //   119: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   122: goto +23 -> 145
    //   125: astore 9
    //   127: jsr +6 -> 133
    //   130: aload 9
    //   132: athrow
    //   133: astore 8
    //   135: aload_0
    //   136: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   139: aload_2
    //   140: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   143: ret 8
    //   145: jsr -12 -> 133
    //   148: ret 6
    //   150: jsr -42 -> 108
    //   153: aload 4
    //   155: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	156	1	paramArrayOfWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo[]
    //   8	132	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	37	3	arrayOfObject	java.lang.Object[]
    //   18	136	4	arrayOfWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo[]
    //   56	3	5	localBPWServicesBean	BPWServicesBean
    //   69	4	5	localRemoteException	RemoteException
    //   80	15	5	localThrowable	java.lang.Throwable
    //   108	1	6	localObject1	java.lang.Object
    //   100	6	7	localObject2	java.lang.Object
    //   133	1	8	localObject3	java.lang.Object
    //   125	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	69	69	java/rmi/RemoteException
    //   20	69	80	java/lang/Throwable
    //   20	100	100	finally
    //   150	153	100	finally
    //   110	125	125	finally
    //   145	148	125	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireInfo[] getAuditWireTransfer(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 630	[Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: sipush 154
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   53: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   56: astore 5
    //   58: aload 5
    //   60: aload_1
    //   61: invokevirtual 636	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getAuditWireTransfer	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   64: astore 4
    //   66: goto +84 -> 150
    //   69: astore 5
    //   71: aload_2
    //   72: aload 5
    //   74: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   77: goto +73 -> 150
    //   80: astore 5
    //   82: aload_2
    //   83: aload 5
    //   85: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   88: new 11	java/rmi/RemoteException
    //   91: dup
    //   92: ldc 53
    //   94: aload 5
    //   96: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   99: athrow
    //   100: astore 7
    //   102: jsr +6 -> 108
    //   105: aload 7
    //   107: athrow
    //   108: astore 6
    //   110: aload_0
    //   111: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   114: aload_0
    //   115: sipush 154
    //   118: aload_2
    //   119: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   122: goto +23 -> 145
    //   125: astore 9
    //   127: jsr +6 -> 133
    //   130: aload 9
    //   132: athrow
    //   133: astore 8
    //   135: aload_0
    //   136: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   139: aload_2
    //   140: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   143: ret 8
    //   145: jsr -12 -> 133
    //   148: ret 6
    //   150: jsr -42 -> 108
    //   153: aload 4
    //   155: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	156	1	paramString	java.lang.String
    //   8	132	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	37	3	arrayOfObject	java.lang.Object[]
    //   18	136	4	arrayOfWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo[]
    //   56	3	5	localBPWServicesBean	BPWServicesBean
    //   69	4	5	localRemoteException	RemoteException
    //   80	15	5	localThrowable	java.lang.Throwable
    //   108	1	6	localObject1	java.lang.Object
    //   100	6	7	localObject2	java.lang.Object
    //   133	1	8	localObject3	java.lang.Object
    //   125	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	69	69	java/rmi/RemoteException
    //   20	69	80	java/lang/Throwable
    //   20	100	100	finally
    //   150	153	100	finally
    //   110	125	125	finally
    //   145	148	125	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireInfo[] getAuditWireTransferByExtId(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 630	[Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: sipush 155
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   53: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   56: astore 5
    //   58: aload 5
    //   60: aload_1
    //   61: invokevirtual 639	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getAuditWireTransferByExtId	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   64: astore 4
    //   66: goto +84 -> 150
    //   69: astore 5
    //   71: aload_2
    //   72: aload 5
    //   74: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   77: goto +73 -> 150
    //   80: astore 5
    //   82: aload_2
    //   83: aload 5
    //   85: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   88: new 11	java/rmi/RemoteException
    //   91: dup
    //   92: ldc 53
    //   94: aload 5
    //   96: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   99: athrow
    //   100: astore 7
    //   102: jsr +6 -> 108
    //   105: aload 7
    //   107: athrow
    //   108: astore 6
    //   110: aload_0
    //   111: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   114: aload_0
    //   115: sipush 155
    //   118: aload_2
    //   119: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   122: goto +23 -> 145
    //   125: astore 9
    //   127: jsr +6 -> 133
    //   130: aload 9
    //   132: athrow
    //   133: astore 8
    //   135: aload_0
    //   136: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   139: aload_2
    //   140: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   143: ret 8
    //   145: jsr -12 -> 133
    //   148: ret 6
    //   150: jsr -42 -> 108
    //   153: aload 4
    //   155: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	156	1	paramString	java.lang.String
    //   8	132	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	37	3	arrayOfObject	java.lang.Object[]
    //   18	136	4	arrayOfWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo[]
    //   56	3	5	localBPWServicesBean	BPWServicesBean
    //   69	4	5	localRemoteException	RemoteException
    //   80	15	5	localThrowable	java.lang.Throwable
    //   108	1	6	localObject1	java.lang.Object
    //   100	6	7	localObject2	java.lang.Object
    //   133	1	8	localObject3	java.lang.Object
    //   125	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	69	69	java/rmi/RemoteException
    //   20	69	80	java/lang/Throwable
    //   20	100	100	finally
    //   150	153	100	finally
    //   110	125	125	finally
    //   145	148	125	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireInfo[] getBatchWires(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 630	[Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: sipush 156
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   53: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   56: astore 5
    //   58: aload 5
    //   60: aload_1
    //   61: invokevirtual 642	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getBatchWires	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   64: astore 4
    //   66: goto +84 -> 150
    //   69: astore 5
    //   71: aload_2
    //   72: aload 5
    //   74: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   77: goto +73 -> 150
    //   80: astore 5
    //   82: aload_2
    //   83: aload 5
    //   85: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   88: new 11	java/rmi/RemoteException
    //   91: dup
    //   92: ldc 53
    //   94: aload 5
    //   96: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   99: athrow
    //   100: astore 7
    //   102: jsr +6 -> 108
    //   105: aload 7
    //   107: athrow
    //   108: astore 6
    //   110: aload_0
    //   111: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   114: aload_0
    //   115: sipush 156
    //   118: aload_2
    //   119: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   122: goto +23 -> 145
    //   125: astore 9
    //   127: jsr +6 -> 133
    //   130: aload 9
    //   132: athrow
    //   133: astore 8
    //   135: aload_0
    //   136: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   139: aload_2
    //   140: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   143: ret 8
    //   145: jsr -12 -> 133
    //   148: ret 6
    //   150: jsr -42 -> 108
    //   153: aload 4
    //   155: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	156	1	paramString	java.lang.String
    //   8	132	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	37	3	arrayOfObject	java.lang.Object[]
    //   18	136	4	arrayOfWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo[]
    //   56	3	5	localBPWServicesBean	BPWServicesBean
    //   69	4	5	localRemoteException	RemoteException
    //   80	15	5	localThrowable	java.lang.Throwable
    //   108	1	6	localObject1	java.lang.Object
    //   100	6	7	localObject2	java.lang.Object
    //   133	1	8	localObject3	java.lang.Object
    //   125	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	69	69	java/rmi/RemoteException
    //   20	69	80	java/lang/Throwable
    //   20	100	100	finally
    //   150	153	100	finally
    //   110	125	125	finally
    //   145	148	125	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireInfo[] getWireTransfers(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 630	[Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: sipush 157
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   53: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   56: astore 5
    //   58: aload 5
    //   60: aload_1
    //   61: invokevirtual 646	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWireTransfers	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   64: astore 4
    //   66: goto +84 -> 150
    //   69: astore 5
    //   71: aload_2
    //   72: aload 5
    //   74: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   77: goto +73 -> 150
    //   80: astore 5
    //   82: aload_2
    //   83: aload 5
    //   85: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   88: new 11	java/rmi/RemoteException
    //   91: dup
    //   92: ldc 53
    //   94: aload 5
    //   96: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   99: athrow
    //   100: astore 7
    //   102: jsr +6 -> 108
    //   105: aload 7
    //   107: athrow
    //   108: astore 6
    //   110: aload_0
    //   111: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   114: aload_0
    //   115: sipush 157
    //   118: aload_2
    //   119: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   122: goto +23 -> 145
    //   125: astore 9
    //   127: jsr +6 -> 133
    //   130: aload 9
    //   132: athrow
    //   133: astore 8
    //   135: aload_0
    //   136: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   139: aload_2
    //   140: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   143: ret 8
    //   145: jsr -12 -> 133
    //   148: ret 6
    //   150: jsr -42 -> 108
    //   153: aload 4
    //   155: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	156	1	paramArrayOfString	java.lang.String[]
    //   8	132	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	37	3	arrayOfObject	java.lang.Object[]
    //   18	136	4	arrayOfWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo[]
    //   56	3	5	localBPWServicesBean	BPWServicesBean
    //   69	4	5	localRemoteException	RemoteException
    //   80	15	5	localThrowable	java.lang.Throwable
    //   108	1	6	localObject1	java.lang.Object
    //   100	6	7	localObject2	java.lang.Object
    //   133	1	8	localObject3	java.lang.Object
    //   125	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	69	69	java/rmi/RemoteException
    //   20	69	80	java/lang/Throwable
    //   20	100	100	finally
    //   150	153	100	finally
    //   110	125	125	finally
    //   145	148	125	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireInfo[] releaseWireTransfer(com.ffusion.ffs.bpw.interfaces.WireInfo[] paramArrayOfWireInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 630	[Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: sipush 158
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   53: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   56: astore 5
    //   58: aload 5
    //   60: aload_1
    //   61: invokevirtual 649	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:releaseWireTransfer	([Lcom/ffusion/ffs/bpw/interfaces/WireInfo;)[Lcom/ffusion/ffs/bpw/interfaces/WireInfo;
    //   64: astore 4
    //   66: goto +84 -> 150
    //   69: astore 5
    //   71: aload_2
    //   72: aload 5
    //   74: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   77: goto +73 -> 150
    //   80: astore 5
    //   82: aload_2
    //   83: aload 5
    //   85: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   88: new 11	java/rmi/RemoteException
    //   91: dup
    //   92: ldc 53
    //   94: aload 5
    //   96: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   99: athrow
    //   100: astore 7
    //   102: jsr +6 -> 108
    //   105: aload 7
    //   107: athrow
    //   108: astore 6
    //   110: aload_0
    //   111: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   114: aload_0
    //   115: sipush 158
    //   118: aload_2
    //   119: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   122: goto +23 -> 145
    //   125: astore 9
    //   127: jsr +6 -> 133
    //   130: aload 9
    //   132: athrow
    //   133: astore 8
    //   135: aload_0
    //   136: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   139: aload_2
    //   140: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   143: ret 8
    //   145: jsr -12 -> 133
    //   148: ret 6
    //   150: jsr -42 -> 108
    //   153: aload 4
    //   155: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	156	1	paramArrayOfWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo[]
    //   8	132	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	37	3	arrayOfObject	java.lang.Object[]
    //   18	136	4	arrayOfWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo[]
    //   56	3	5	localBPWServicesBean	BPWServicesBean
    //   69	4	5	localRemoteException	RemoteException
    //   80	15	5	localThrowable	java.lang.Throwable
    //   108	1	6	localObject1	java.lang.Object
    //   100	6	7	localObject2	java.lang.Object
    //   133	1	8	localObject3	java.lang.Object
    //   125	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	69	69	java/rmi/RemoteException
    //   20	69	80	java/lang/Throwable
    //   20	100	100	finally
    //   150	153	100	finally
    //   110	125	125	finally
    //   145	148	125	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WirePayeeInfo addWirePayee(com.ffusion.ffs.bpw.interfaces.WirePayeeInfo paramWirePayeeInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 159
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 653	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addWirePayee	(Lcom/ffusion/ffs/bpw/interfaces/WirePayeeInfo;)Lcom/ffusion/ffs/bpw/interfaces/WirePayeeInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 159
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramWirePayeeInfo	com.ffusion.ffs.bpw.interfaces.WirePayeeInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWirePayeeInfo	com.ffusion.ffs.bpw.interfaces.WirePayeeInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WirePayeeInfo canWirePayee(com.ffusion.ffs.bpw.interfaces.WirePayeeInfo paramWirePayeeInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 160
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 656	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:canWirePayee	(Lcom/ffusion/ffs/bpw/interfaces/WirePayeeInfo;)Lcom/ffusion/ffs/bpw/interfaces/WirePayeeInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 160
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramWirePayeeInfo	com.ffusion.ffs.bpw.interfaces.WirePayeeInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWirePayeeInfo	com.ffusion.ffs.bpw.interfaces.WirePayeeInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WirePayeeInfo getWirePayee(com.ffusion.ffs.bpw.interfaces.WirePayeeInfo paramWirePayeeInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 161
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 659	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWirePayee	(Lcom/ffusion/ffs/bpw/interfaces/WirePayeeInfo;)Lcom/ffusion/ffs/bpw/interfaces/WirePayeeInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 161
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramWirePayeeInfo	com.ffusion.ffs.bpw.interfaces.WirePayeeInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWirePayeeInfo	com.ffusion.ffs.bpw.interfaces.WirePayeeInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WirePayeeInfo getWirePayee(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 162
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 662	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWirePayee	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/WirePayeeInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 162
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramString	java.lang.String
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWirePayeeInfo	com.ffusion.ffs.bpw.interfaces.WirePayeeInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WirePayeeInfo modWirePayee(com.ffusion.ffs.bpw.interfaces.WirePayeeInfo paramWirePayeeInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 163
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 665	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modWirePayee	(Lcom/ffusion/ffs/bpw/interfaces/WirePayeeInfo;)Lcom/ffusion/ffs/bpw/interfaces/WirePayeeInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 163
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramWirePayeeInfo	com.ffusion.ffs.bpw.interfaces.WirePayeeInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWirePayeeInfo	com.ffusion.ffs.bpw.interfaces.WirePayeeInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WirePayeeInfo[] addWirePayee(com.ffusion.ffs.bpw.interfaces.WirePayeeInfo[] paramArrayOfWirePayeeInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 668	[Lcom/ffusion/ffs/bpw/interfaces/WirePayeeInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: sipush 164
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   53: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   56: astore 5
    //   58: aload 5
    //   60: aload_1
    //   61: invokevirtual 670	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addWirePayee	([Lcom/ffusion/ffs/bpw/interfaces/WirePayeeInfo;)[Lcom/ffusion/ffs/bpw/interfaces/WirePayeeInfo;
    //   64: astore 4
    //   66: goto +84 -> 150
    //   69: astore 5
    //   71: aload_2
    //   72: aload 5
    //   74: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   77: goto +73 -> 150
    //   80: astore 5
    //   82: aload_2
    //   83: aload 5
    //   85: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   88: new 11	java/rmi/RemoteException
    //   91: dup
    //   92: ldc 53
    //   94: aload 5
    //   96: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   99: athrow
    //   100: astore 7
    //   102: jsr +6 -> 108
    //   105: aload 7
    //   107: athrow
    //   108: astore 6
    //   110: aload_0
    //   111: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   114: aload_0
    //   115: sipush 164
    //   118: aload_2
    //   119: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   122: goto +23 -> 145
    //   125: astore 9
    //   127: jsr +6 -> 133
    //   130: aload 9
    //   132: athrow
    //   133: astore 8
    //   135: aload_0
    //   136: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   139: aload_2
    //   140: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   143: ret 8
    //   145: jsr -12 -> 133
    //   148: ret 6
    //   150: jsr -42 -> 108
    //   153: aload 4
    //   155: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	156	1	paramArrayOfWirePayeeInfo	com.ffusion.ffs.bpw.interfaces.WirePayeeInfo[]
    //   8	132	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	37	3	arrayOfObject	java.lang.Object[]
    //   18	136	4	arrayOfWirePayeeInfo	com.ffusion.ffs.bpw.interfaces.WirePayeeInfo[]
    //   56	3	5	localBPWServicesBean	BPWServicesBean
    //   69	4	5	localRemoteException	RemoteException
    //   80	15	5	localThrowable	java.lang.Throwable
    //   108	1	6	localObject1	java.lang.Object
    //   100	6	7	localObject2	java.lang.Object
    //   133	1	8	localObject3	java.lang.Object
    //   125	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	69	69	java/rmi/RemoteException
    //   20	69	80	java/lang/Throwable
    //   20	100	100	finally
    //   150	153	100	finally
    //   110	125	125	finally
    //   145	148	125	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.WireReleaseInfo getWireReleaseCount(com.ffusion.ffs.bpw.interfaces.WireReleaseInfo paramWireReleaseInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 165
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 674	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWireReleaseCount	(Lcom/ffusion/ffs/bpw/interfaces/WireReleaseInfo;)Lcom/ffusion/ffs/bpw/interfaces/WireReleaseInfo;
    //   61: astore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 165
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: aload 4
    //   152: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramWireReleaseInfo	com.ffusion.ffs.bpw.interfaces.WireReleaseInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	localWireReleaseInfo	com.ffusion.ffs.bpw.interfaces.WireReleaseInfo
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.util.AccountTypesMap getAccountTypesMap()
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_1
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_2
    //   14: aconst_null
    //   15: astore_3
    //   16: aload_0
    //   17: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   20: aload_0
    //   21: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   24: ifeq +8 -> 32
    //   27: iconst_0
    //   28: anewarray 34	java/lang/Object
    //   31: astore_2
    //   32: aload_0
    //   33: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   36: aload_0
    //   37: sipush 166
    //   40: aload_1
    //   41: aload_2
    //   42: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   45: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   48: astore 4
    //   50: aload 4
    //   52: invokevirtual 678	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getAccountTypesMap	()Lcom/ffusion/ffs/bpw/util/AccountTypesMap;
    //   55: astore_3
    //   56: goto +95 -> 151
    //   59: astore 4
    //   61: aload_1
    //   62: aload 4
    //   64: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   67: goto +84 -> 151
    //   70: astore 4
    //   72: aload_1
    //   73: aload 4
    //   75: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   78: aload 4
    //   80: athrow
    //   81: astore 4
    //   83: aload_1
    //   84: aload 4
    //   86: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   89: new 11	java/rmi/RemoteException
    //   92: dup
    //   93: ldc 53
    //   95: aload 4
    //   97: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   100: athrow
    //   101: astore 6
    //   103: jsr +6 -> 109
    //   106: aload 6
    //   108: athrow
    //   109: astore 5
    //   111: aload_0
    //   112: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   115: aload_0
    //   116: sipush 166
    //   119: aload_1
    //   120: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   123: goto +23 -> 146
    //   126: astore 8
    //   128: jsr +6 -> 134
    //   131: aload 8
    //   133: athrow
    //   134: astore 7
    //   136: aload_0
    //   137: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   140: aload_1
    //   141: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   144: ret 7
    //   146: jsr -12 -> 134
    //   149: ret 5
    //   151: jsr -42 -> 109
    //   154: aload_3
    //   155: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   8	133	1	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	29	2	arrayOfObject	java.lang.Object[]
    //   15	140	3	localAccountTypesMap	com.ffusion.ffs.bpw.util.AccountTypesMap
    //   48	3	4	localBPWServicesBean	BPWServicesBean
    //   59	4	4	localRemoteException	RemoteException
    //   70	9	4	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   81	15	4	localThrowable	java.lang.Throwable
    //   109	1	5	localObject1	java.lang.Object
    //   101	6	6	localObject2	java.lang.Object
    //   134	1	7	localObject3	java.lang.Object
    //   126	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   16	59	59	java/rmi/RemoteException
    //   16	59	70	com/ffusion/ffs/interfaces/FFSException
    //   16	59	81	java/lang/Throwable
    //   16	101	101	finally
    //   151	154	101	finally
    //   111	126	126	finally
    //   146	149	126	finally
  }
  
  /* Error */
  public int activateCustomers(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 167
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 682	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:activateCustomers	([Ljava/lang/String;)I
    //   61: istore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 167
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: iload 4
    //   152: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramArrayOfString	java.lang.String[]
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	i	int
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public int addCustomers(com.ffusion.ffs.bpw.interfaces.CustomerInfo[] paramArrayOfCustomerInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 168
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 686	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addCustomers	([Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;)I
    //   61: istore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 168
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: iload 4
    //   152: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramArrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	i	int
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public int addIntermediaryBanksToBeneficiary(java.lang.String paramString, com.ffusion.ffs.bpw.interfaces.BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: iconst_0
    //   16: istore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: sipush 169
    //   53: aload_3
    //   54: aload 4
    //   56: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   59: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   62: astore 6
    //   64: aload 6
    //   66: aload_1
    //   67: aload_2
    //   68: invokevirtual 690	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:addIntermediaryBanksToBeneficiary	(Ljava/lang/String;[Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;)I
    //   71: istore 5
    //   73: goto +84 -> 157
    //   76: astore 6
    //   78: aload_3
    //   79: aload 6
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +73 -> 157
    //   87: astore 6
    //   89: aload_3
    //   90: aload 6
    //   92: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   95: new 11	java/rmi/RemoteException
    //   98: dup
    //   99: ldc 53
    //   101: aload 6
    //   103: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   106: athrow
    //   107: astore 8
    //   109: jsr +6 -> 115
    //   112: aload 8
    //   114: athrow
    //   115: astore 7
    //   117: aload_0
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: sipush 169
    //   125: aload_3
    //   126: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   129: goto +23 -> 152
    //   132: astore 10
    //   134: jsr +6 -> 140
    //   137: aload 10
    //   139: athrow
    //   140: astore 9
    //   142: aload_0
    //   143: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   146: aload_3
    //   147: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   150: ret 9
    //   152: jsr -12 -> 140
    //   155: ret 7
    //   157: jsr -42 -> 115
    //   160: iload 5
    //   162: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	163	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	163	1	paramString	java.lang.String
    //   0	163	2	paramArrayOfBPWBankInfo	com.ffusion.ffs.bpw.interfaces.BPWBankInfo[]
    //   8	139	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	42	4	arrayOfObject	java.lang.Object[]
    //   16	145	5	i	int
    //   62	3	6	localBPWServicesBean	BPWServicesBean
    //   76	4	6	localRemoteException	RemoteException
    //   87	15	6	localThrowable	java.lang.Throwable
    //   115	1	7	localObject1	java.lang.Object
    //   107	6	8	localObject2	java.lang.Object
    //   140	1	9	localObject3	java.lang.Object
    //   132	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	76	76	java/rmi/RemoteException
    //   18	76	87	java/lang/Throwable
    //   18	107	107	finally
    //   157	160	107	finally
    //   117	132	132	finally
    //   152	155	132	finally
  }
  
  /* Error */
  public int deactivateCustomers(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 170
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 693	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:deactivateCustomers	([Ljava/lang/String;)I
    //   61: istore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 170
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: iload 4
    //   152: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramArrayOfString	java.lang.String[]
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	i	int
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public int delIntermediaryBanksFromBeneficiary(java.lang.String paramString, com.ffusion.ffs.bpw.interfaces.BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: iconst_0
    //   16: istore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: sipush 171
    //   53: aload_3
    //   54: aload 4
    //   56: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   59: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   62: astore 6
    //   64: aload 6
    //   66: aload_1
    //   67: aload_2
    //   68: invokevirtual 696	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:delIntermediaryBanksFromBeneficiary	(Ljava/lang/String;[Lcom/ffusion/ffs/bpw/interfaces/BPWBankInfo;)I
    //   71: istore 5
    //   73: goto +84 -> 157
    //   76: astore 6
    //   78: aload_3
    //   79: aload 6
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +73 -> 157
    //   87: astore 6
    //   89: aload_3
    //   90: aload 6
    //   92: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   95: new 11	java/rmi/RemoteException
    //   98: dup
    //   99: ldc 53
    //   101: aload 6
    //   103: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   106: athrow
    //   107: astore 8
    //   109: jsr +6 -> 115
    //   112: aload 8
    //   114: athrow
    //   115: astore 7
    //   117: aload_0
    //   118: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: sipush 171
    //   125: aload_3
    //   126: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   129: goto +23 -> 152
    //   132: astore 10
    //   134: jsr +6 -> 140
    //   137: aload 10
    //   139: athrow
    //   140: astore 9
    //   142: aload_0
    //   143: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   146: aload_3
    //   147: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   150: ret 9
    //   152: jsr -12 -> 140
    //   155: ret 7
    //   157: jsr -42 -> 115
    //   160: iload 5
    //   162: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	163	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	163	1	paramString	java.lang.String
    //   0	163	2	paramArrayOfBPWBankInfo	com.ffusion.ffs.bpw.interfaces.BPWBankInfo[]
    //   8	139	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	42	4	arrayOfObject	java.lang.Object[]
    //   16	145	5	i	int
    //   62	3	6	localBPWServicesBean	BPWServicesBean
    //   76	4	6	localRemoteException	RemoteException
    //   87	15	6	localThrowable	java.lang.Throwable
    //   115	1	7	localObject1	java.lang.Object
    //   107	6	8	localObject2	java.lang.Object
    //   140	1	9	localObject3	java.lang.Object
    //   132	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	76	76	java/rmi/RemoteException
    //   18	76	87	java/lang/Throwable
    //   18	107	107	finally
    //   157	160	107	finally
    //   117	132	132	finally
    //   152	155	132	finally
  }
  
  /* Error */
  public int deleteCustomers(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 172
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 699	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:deleteCustomers	([Ljava/lang/String;)I
    //   61: istore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 172
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: iload 4
    //   152: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramArrayOfString	java.lang.String[]
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	i	int
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public int deleteCustomers(java.lang.String[] paramArrayOfString, int paramInt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: iconst_0
    //   16: istore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   22: aload_0
    //   23: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   26: ifeq +26 -> 52
    //   29: iconst_2
    //   30: anewarray 34	java/lang/Object
    //   33: astore 4
    //   35: aload 4
    //   37: iconst_0
    //   38: aload_1
    //   39: aastore
    //   40: aload 4
    //   42: iconst_1
    //   43: new 412	java/lang/Integer
    //   46: dup
    //   47: iload_2
    //   48: invokespecial 415	java/lang/Integer:<init>	(I)V
    //   51: aastore
    //   52: aload_0
    //   53: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   56: aload_0
    //   57: sipush 173
    //   60: aload_3
    //   61: aload 4
    //   63: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   66: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   69: astore 6
    //   71: aload 6
    //   73: aload_1
    //   74: iload_2
    //   75: invokevirtual 702	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:deleteCustomers	([Ljava/lang/String;I)I
    //   78: istore 5
    //   80: goto +84 -> 164
    //   83: astore 6
    //   85: aload_3
    //   86: aload 6
    //   88: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   91: goto +73 -> 164
    //   94: astore 6
    //   96: aload_3
    //   97: aload 6
    //   99: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   102: new 11	java/rmi/RemoteException
    //   105: dup
    //   106: ldc 53
    //   108: aload 6
    //   110: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   113: athrow
    //   114: astore 8
    //   116: jsr +6 -> 122
    //   119: aload 8
    //   121: athrow
    //   122: astore 7
    //   124: aload_0
    //   125: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   128: aload_0
    //   129: sipush 173
    //   132: aload_3
    //   133: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   136: goto +23 -> 159
    //   139: astore 10
    //   141: jsr +6 -> 147
    //   144: aload 10
    //   146: athrow
    //   147: astore 9
    //   149: aload_0
    //   150: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   153: aload_3
    //   154: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   157: ret 9
    //   159: jsr -12 -> 147
    //   162: ret 7
    //   164: jsr -42 -> 122
    //   167: iload 5
    //   169: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	170	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	170	1	paramArrayOfString	java.lang.String[]
    //   0	170	2	paramInt	int
    //   8	146	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	49	4	arrayOfObject	java.lang.Object[]
    //   16	152	5	i	int
    //   69	3	6	localBPWServicesBean	BPWServicesBean
    //   83	4	6	localRemoteException	RemoteException
    //   94	15	6	localThrowable	java.lang.Throwable
    //   122	1	7	localObject1	java.lang.Object
    //   114	6	8	localObject2	java.lang.Object
    //   147	1	9	localObject3	java.lang.Object
    //   139	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	83	83	java/rmi/RemoteException
    //   18	83	94	java/lang/Throwable
    //   18	114	114	finally
    //   164	167	114	finally
    //   124	139	139	finally
    //   159	162	139	finally
  }
  
  /* Error */
  public int getSmartDate(java.lang.String paramString, int paramInt)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: iconst_0
    //   16: istore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   22: aload_0
    //   23: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   26: ifeq +26 -> 52
    //   29: iconst_2
    //   30: anewarray 34	java/lang/Object
    //   33: astore 4
    //   35: aload 4
    //   37: iconst_0
    //   38: aload_1
    //   39: aastore
    //   40: aload 4
    //   42: iconst_1
    //   43: new 412	java/lang/Integer
    //   46: dup
    //   47: iload_2
    //   48: invokespecial 415	java/lang/Integer:<init>	(I)V
    //   51: aastore
    //   52: aload_0
    //   53: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   56: aload_0
    //   57: sipush 174
    //   60: aload_3
    //   61: aload 4
    //   63: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   66: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   69: astore 6
    //   71: aload 6
    //   73: aload_1
    //   74: iload_2
    //   75: invokevirtual 708	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getSmartDate	(Ljava/lang/String;I)I
    //   78: istore 5
    //   80: goto +95 -> 175
    //   83: astore 6
    //   85: aload_3
    //   86: aload 6
    //   88: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   91: goto +84 -> 175
    //   94: astore 6
    //   96: aload_3
    //   97: aload 6
    //   99: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   102: goto +73 -> 175
    //   105: astore 6
    //   107: aload_3
    //   108: aload 6
    //   110: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   113: new 11	java/rmi/RemoteException
    //   116: dup
    //   117: ldc 53
    //   119: aload 6
    //   121: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   124: athrow
    //   125: astore 8
    //   127: jsr +6 -> 133
    //   130: aload 8
    //   132: athrow
    //   133: astore 7
    //   135: aload_0
    //   136: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   139: aload_0
    //   140: sipush 174
    //   143: aload_3
    //   144: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   147: goto +23 -> 170
    //   150: astore 10
    //   152: jsr +6 -> 158
    //   155: aload 10
    //   157: athrow
    //   158: astore 9
    //   160: aload_0
    //   161: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   164: aload_3
    //   165: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   168: ret 9
    //   170: jsr -12 -> 158
    //   173: ret 7
    //   175: jsr -42 -> 133
    //   178: iload 5
    //   180: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	181	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	181	1	paramString	java.lang.String
    //   0	181	2	paramInt	int
    //   8	157	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	49	4	arrayOfObject	java.lang.Object[]
    //   16	163	5	i	int
    //   69	3	6	localBPWServicesBean	BPWServicesBean
    //   83	4	6	localRemoteException	RemoteException
    //   94	4	6	localEJBException	javax.ejb.EJBException
    //   105	15	6	localThrowable	java.lang.Throwable
    //   133	1	7	localObject1	java.lang.Object
    //   125	6	8	localObject2	java.lang.Object
    //   158	1	9	localObject3	java.lang.Object
    //   150	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	83	83	java/rmi/RemoteException
    //   18	83	94	javax/ejb/EJBException
    //   18	83	105	java/lang/Throwable
    //   18	125	125	finally
    //   175	178	125	finally
    //   135	150	150	finally
    //   170	173	150	finally
  }
  
  /* Error */
  public int getValidTransferDateDue(com.ffusion.ffs.bpw.interfaces.TransferInfo paramTransferInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 175
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 712	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getValidTransferDateDue	(Lcom/ffusion/ffs/bpw/interfaces/TransferInfo;)I
    //   61: istore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 175
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: iload 4
    //   152: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramTransferInfo	com.ffusion.ffs.bpw.interfaces.TransferInfo
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	i	int
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public int modifyCustomers(com.ffusion.ffs.bpw.interfaces.CustomerInfo[] paramArrayOfCustomerInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: sipush 176
    //   45: aload_2
    //   46: aload_3
    //   47: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   50: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   53: astore 5
    //   55: aload 5
    //   57: aload_1
    //   58: invokevirtual 715	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:modifyCustomers	([Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;)I
    //   61: istore 4
    //   63: goto +84 -> 147
    //   66: astore 5
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   74: goto +73 -> 147
    //   77: astore 5
    //   79: aload_2
    //   80: aload 5
    //   82: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   85: new 11	java/rmi/RemoteException
    //   88: dup
    //   89: ldc 53
    //   91: aload 5
    //   93: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: athrow
    //   97: astore 7
    //   99: jsr +6 -> 105
    //   102: aload 7
    //   104: athrow
    //   105: astore 6
    //   107: aload_0
    //   108: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   111: aload_0
    //   112: sipush 176
    //   115: aload_2
    //   116: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 9
    //   124: jsr +6 -> 130
    //   127: aload 9
    //   129: athrow
    //   130: astore 8
    //   132: aload_0
    //   133: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_2
    //   137: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 8
    //   142: jsr -12 -> 130
    //   145: ret 6
    //   147: jsr -42 -> 105
    //   150: iload 4
    //   152: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	153	1	paramArrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   8	129	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	34	3	arrayOfObject	java.lang.Object[]
    //   15	136	4	i	int
    //   53	3	5	localBPWServicesBean	BPWServicesBean
    //   66	4	5	localRemoteException	RemoteException
    //   77	15	5	localThrowable	java.lang.Throwable
    //   105	1	6	localObject1	java.lang.Object
    //   97	6	7	localObject2	java.lang.Object
    //   130	1	8	localObject3	java.lang.Object
    //   122	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	66	66	java/rmi/RemoteException
    //   17	66	77	java/lang/Throwable
    //   17	97	97	finally
    //   147	150	97	finally
    //   107	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public java.lang.String getBPWProperty(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: sipush 177
    //   53: aload_3
    //   54: aload 4
    //   56: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   59: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   62: astore 6
    //   64: aload 6
    //   66: aload_1
    //   67: aload_2
    //   68: invokevirtual 719	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getBPWProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   71: astore 5
    //   73: goto +95 -> 168
    //   76: astore 6
    //   78: aload_3
    //   79: aload 6
    //   81: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   84: goto +84 -> 168
    //   87: astore 6
    //   89: aload_3
    //   90: aload 6
    //   92: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   95: aload 6
    //   97: athrow
    //   98: astore 6
    //   100: aload_3
    //   101: aload 6
    //   103: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   106: new 11	java/rmi/RemoteException
    //   109: dup
    //   110: ldc 53
    //   112: aload 6
    //   114: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   117: athrow
    //   118: astore 8
    //   120: jsr +6 -> 126
    //   123: aload 8
    //   125: athrow
    //   126: astore 7
    //   128: aload_0
    //   129: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   132: aload_0
    //   133: sipush 177
    //   136: aload_3
    //   137: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: goto +23 -> 163
    //   143: astore 10
    //   145: jsr +6 -> 151
    //   148: aload 10
    //   150: athrow
    //   151: astore 9
    //   153: aload_0
    //   154: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   157: aload_3
    //   158: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   161: ret 9
    //   163: jsr -12 -> 151
    //   166: ret 7
    //   168: jsr -42 -> 126
    //   171: aload 5
    //   173: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	174	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	174	1	paramString1	java.lang.String
    //   0	174	2	paramString2	java.lang.String
    //   8	150	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	42	4	arrayOfObject	java.lang.Object[]
    //   16	156	5	str	java.lang.String
    //   62	3	6	localBPWServicesBean	BPWServicesBean
    //   76	4	6	localRemoteException	RemoteException
    //   87	9	6	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   98	15	6	localThrowable	java.lang.Throwable
    //   126	1	7	localObject1	java.lang.Object
    //   118	6	8	localObject2	java.lang.Object
    //   151	1	9	localObject3	java.lang.Object
    //   143	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	76	76	java/rmi/RemoteException
    //   18	76	87	com/ffusion/ffs/interfaces/FFSException
    //   18	76	98	java/lang/Throwable
    //   18	118	118	finally
    //   168	171	118	finally
    //   128	143	143	finally
    //   163	166	143	finally
  }
  
  /* Error */
  public java.util.HashMap getWiresConfiguration()
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_1
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_2
    //   14: aconst_null
    //   15: astore_3
    //   16: aload_0
    //   17: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   20: aload_0
    //   21: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   24: ifeq +8 -> 32
    //   27: iconst_0
    //   28: anewarray 34	java/lang/Object
    //   31: astore_2
    //   32: aload_0
    //   33: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   36: aload_0
    //   37: sipush 178
    //   40: aload_1
    //   41: aload_2
    //   42: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   45: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   48: astore 4
    //   50: aload 4
    //   52: invokevirtual 723	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:getWiresConfiguration	()Ljava/util/HashMap;
    //   55: astore_3
    //   56: goto +84 -> 140
    //   59: astore 4
    //   61: aload_1
    //   62: aload 4
    //   64: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   67: goto +73 -> 140
    //   70: astore 4
    //   72: aload_1
    //   73: aload 4
    //   75: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   78: new 11	java/rmi/RemoteException
    //   81: dup
    //   82: ldc 53
    //   84: aload 4
    //   86: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   89: athrow
    //   90: astore 6
    //   92: jsr +6 -> 98
    //   95: aload 6
    //   97: athrow
    //   98: astore 5
    //   100: aload_0
    //   101: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   104: aload_0
    //   105: sipush 178
    //   108: aload_1
    //   109: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   112: goto +23 -> 135
    //   115: astore 8
    //   117: jsr +6 -> 123
    //   120: aload 8
    //   122: athrow
    //   123: astore 7
    //   125: aload_0
    //   126: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   129: aload_1
    //   130: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   133: ret 7
    //   135: jsr -12 -> 123
    //   138: ret 5
    //   140: jsr -42 -> 98
    //   143: aload_3
    //   144: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	145	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   8	122	1	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	29	2	arrayOfObject	java.lang.Object[]
    //   15	129	3	localHashMap	java.util.HashMap
    //   48	3	4	localBPWServicesBean	BPWServicesBean
    //   59	4	4	localRemoteException	RemoteException
    //   70	15	4	localThrowable	java.lang.Throwable
    //   98	1	5	localObject1	java.lang.Object
    //   90	6	6	localObject2	java.lang.Object
    //   123	1	7	localObject3	java.lang.Object
    //   115	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   16	59	59	java/rmi/RemoteException
    //   16	59	70	java/lang/Throwable
    //   16	90	90	finally
    //   140	143	90	finally
    //   100	115	115	finally
    //   135	138	115	finally
  }
  
  /* Error */
  public void disconnect()
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_1
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_2
    //   14: aload_0
    //   15: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   18: aload_0
    //   19: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   22: ifeq +8 -> 30
    //   25: iconst_0
    //   26: anewarray 34	java/lang/Object
    //   29: astore_2
    //   30: aload_0
    //   31: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   34: aload_0
    //   35: sipush 179
    //   38: aload_1
    //   39: aload_2
    //   40: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   43: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   46: astore_3
    //   47: aload_3
    //   48: invokevirtual 726	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:disconnect	()V
    //   51: goto +79 -> 130
    //   54: astore_3
    //   55: aload_1
    //   56: aload_3
    //   57: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   60: goto +70 -> 130
    //   63: astore_3
    //   64: aload_1
    //   65: aload_3
    //   66: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   69: new 11	java/rmi/RemoteException
    //   72: dup
    //   73: ldc 53
    //   75: aload_3
    //   76: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   79: athrow
    //   80: astore 5
    //   82: jsr +6 -> 88
    //   85: aload 5
    //   87: athrow
    //   88: astore 4
    //   90: aload_0
    //   91: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   94: aload_0
    //   95: sipush 179
    //   98: aload_1
    //   99: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   102: goto +23 -> 125
    //   105: astore 7
    //   107: jsr +6 -> 113
    //   110: aload 7
    //   112: athrow
    //   113: astore 6
    //   115: aload_0
    //   116: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   119: aload_1
    //   120: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   123: ret 6
    //   125: jsr -12 -> 113
    //   128: ret 4
    //   130: jsr -42 -> 88
    //   133: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	134	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   8	112	1	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	27	2	arrayOfObject	java.lang.Object[]
    //   46	2	3	localBPWServicesBean	BPWServicesBean
    //   54	3	3	localRemoteException	RemoteException
    //   63	13	3	localThrowable	java.lang.Throwable
    //   88	1	4	localObject1	java.lang.Object
    //   80	6	5	localObject2	java.lang.Object
    //   113	1	6	localObject3	java.lang.Object
    //   105	6	7	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	54	54	java/rmi/RemoteException
    //   14	54	63	java/lang/Throwable
    //   14	80	80	finally
    //   130	133	80	finally
    //   90	105	105	finally
    //   125	128	105	finally
  }
  
  /* Error */
  public void processApprovalResult(java.lang.String paramString1, java.lang.String paramString2, java.lang.String paramString3, java.util.HashMap paramHashMap)
    throws RemoteException, javax.ejb.EJBException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore 5
    //   10: aconst_null
    //   11: checkcast 28	[Ljava/lang/Object;
    //   14: astore 6
    //   16: aload_0
    //   17: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   20: aload_0
    //   21: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   24: ifeq +30 -> 54
    //   27: iconst_4
    //   28: anewarray 34	java/lang/Object
    //   31: astore 6
    //   33: aload 6
    //   35: iconst_0
    //   36: aload_1
    //   37: aastore
    //   38: aload 6
    //   40: iconst_1
    //   41: aload_2
    //   42: aastore
    //   43: aload 6
    //   45: iconst_2
    //   46: aload_3
    //   47: aastore
    //   48: aload 6
    //   50: iconst_3
    //   51: aload 4
    //   53: aastore
    //   54: aload_0
    //   55: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   58: aload_0
    //   59: sipush 180
    //   62: aload 5
    //   64: aload 6
    //   66: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   69: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   72: astore 7
    //   74: aload 7
    //   76: aload_1
    //   77: aload_2
    //   78: aload_3
    //   79: aload 4
    //   81: invokevirtual 730	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:processApprovalResult	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/HashMap;)V
    //   84: goto +112 -> 196
    //   87: astore 7
    //   89: aload 5
    //   91: aload 7
    //   93: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   96: goto +100 -> 196
    //   99: astore 7
    //   101: aload 5
    //   103: aload 7
    //   105: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   108: goto +88 -> 196
    //   111: astore 7
    //   113: aload 5
    //   115: aload 7
    //   117: invokevirtual 75	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   120: aload 7
    //   122: athrow
    //   123: astore 7
    //   125: aload 5
    //   127: aload 7
    //   129: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   132: new 11	java/rmi/RemoteException
    //   135: dup
    //   136: ldc 53
    //   138: aload 7
    //   140: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   143: athrow
    //   144: astore 9
    //   146: jsr +6 -> 152
    //   149: aload 9
    //   151: athrow
    //   152: astore 8
    //   154: aload_0
    //   155: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   158: aload_0
    //   159: sipush 180
    //   162: aload 5
    //   164: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   167: goto +24 -> 191
    //   170: astore 11
    //   172: jsr +6 -> 178
    //   175: aload 11
    //   177: athrow
    //   178: astore 10
    //   180: aload_0
    //   181: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   184: aload 5
    //   186: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   189: ret 10
    //   191: jsr -13 -> 178
    //   194: ret 8
    //   196: jsr -44 -> 152
    //   199: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	200	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	200	1	paramString1	java.lang.String
    //   0	200	2	paramString2	java.lang.String
    //   0	200	3	paramString3	java.lang.String
    //   0	200	4	paramHashMap	java.util.HashMap
    //   8	177	5	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   14	51	6	arrayOfObject	java.lang.Object[]
    //   72	3	7	localBPWServicesBean	BPWServicesBean
    //   87	5	7	localRemoteException	RemoteException
    //   99	5	7	localEJBException	javax.ejb.EJBException
    //   111	10	7	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   123	16	7	localThrowable	java.lang.Throwable
    //   152	1	8	localObject1	java.lang.Object
    //   144	6	9	localObject2	java.lang.Object
    //   178	1	10	localObject3	java.lang.Object
    //   170	6	11	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   16	87	87	java/rmi/RemoteException
    //   16	87	99	javax/ejb/EJBException
    //   16	87	111	com/ffusion/ffs/interfaces/FFSException
    //   16	87	123	java/lang/Throwable
    //   16	144	144	finally
    //   196	199	144	finally
    //   154	170	170	finally
    //   191	194	170	finally
  }
  
  /* Error */
  public void processPmtTrnRslt(com.ffusion.ffs.bpw.interfaces.PmtTrnRslt[] paramArrayOfPmtTrnRslt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   18: aload_0
    //   19: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   22: ifeq +12 -> 34
    //   25: iconst_1
    //   26: anewarray 34	java/lang/Object
    //   29: astore_3
    //   30: aload_3
    //   31: iconst_0
    //   32: aload_1
    //   33: aastore
    //   34: aload_0
    //   35: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: sipush 181
    //   42: aload_2
    //   43: aload_3
    //   44: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   47: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   50: astore 4
    //   52: aload 4
    //   54: aload_1
    //   55: invokevirtual 734	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:processPmtTrnRslt	([Lcom/ffusion/ffs/bpw/interfaces/PmtTrnRslt;)V
    //   58: goto +84 -> 142
    //   61: astore 4
    //   63: aload_2
    //   64: aload 4
    //   66: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   69: goto +73 -> 142
    //   72: astore 4
    //   74: aload_2
    //   75: aload 4
    //   77: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   80: new 11	java/rmi/RemoteException
    //   83: dup
    //   84: ldc 53
    //   86: aload 4
    //   88: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   91: athrow
    //   92: astore 6
    //   94: jsr +6 -> 100
    //   97: aload 6
    //   99: athrow
    //   100: astore 5
    //   102: aload_0
    //   103: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   106: aload_0
    //   107: sipush 181
    //   110: aload_2
    //   111: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   114: goto +23 -> 137
    //   117: astore 8
    //   119: jsr +6 -> 125
    //   122: aload 8
    //   124: athrow
    //   125: astore 7
    //   127: aload_0
    //   128: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_2
    //   132: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   135: ret 7
    //   137: jsr -12 -> 125
    //   140: ret 5
    //   142: jsr -42 -> 100
    //   145: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	146	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	146	1	paramArrayOfPmtTrnRslt	com.ffusion.ffs.bpw.interfaces.PmtTrnRslt[]
    //   8	124	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	31	3	arrayOfObject	java.lang.Object[]
    //   50	3	4	localBPWServicesBean	BPWServicesBean
    //   61	4	4	localRemoteException	RemoteException
    //   72	15	4	localThrowable	java.lang.Throwable
    //   100	1	5	localObject1	java.lang.Object
    //   92	6	6	localObject2	java.lang.Object
    //   125	1	7	localObject3	java.lang.Object
    //   117	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	61	61	java/rmi/RemoteException
    //   14	61	72	java/lang/Throwable
    //   14	92	92	finally
    //   142	145	92	finally
    //   102	117	117	finally
    //   137	140	117	finally
  }
  
  /* Error */
  public void processWireApprovalRevertRslt(com.ffusion.ffs.bpw.interfaces.WireInfo[] paramArrayOfWireInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   18: aload_0
    //   19: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   22: ifeq +12 -> 34
    //   25: iconst_1
    //   26: anewarray 34	java/lang/Object
    //   29: astore_3
    //   30: aload_3
    //   31: iconst_0
    //   32: aload_1
    //   33: aastore
    //   34: aload_0
    //   35: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: sipush 182
    //   42: aload_2
    //   43: aload_3
    //   44: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   47: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   50: astore 4
    //   52: aload 4
    //   54: aload_1
    //   55: invokevirtual 738	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:processWireApprovalRevertRslt	([Lcom/ffusion/ffs/bpw/interfaces/WireInfo;)V
    //   58: goto +84 -> 142
    //   61: astore 4
    //   63: aload_2
    //   64: aload 4
    //   66: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   69: goto +73 -> 142
    //   72: astore 4
    //   74: aload_2
    //   75: aload 4
    //   77: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   80: new 11	java/rmi/RemoteException
    //   83: dup
    //   84: ldc 53
    //   86: aload 4
    //   88: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   91: athrow
    //   92: astore 6
    //   94: jsr +6 -> 100
    //   97: aload 6
    //   99: athrow
    //   100: astore 5
    //   102: aload_0
    //   103: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   106: aload_0
    //   107: sipush 182
    //   110: aload_2
    //   111: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   114: goto +23 -> 137
    //   117: astore 8
    //   119: jsr +6 -> 125
    //   122: aload 8
    //   124: athrow
    //   125: astore 7
    //   127: aload_0
    //   128: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_2
    //   132: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   135: ret 7
    //   137: jsr -12 -> 125
    //   140: ret 5
    //   142: jsr -42 -> 100
    //   145: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	146	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	146	1	paramArrayOfWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo[]
    //   8	124	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	31	3	arrayOfObject	java.lang.Object[]
    //   50	3	4	localBPWServicesBean	BPWServicesBean
    //   61	4	4	localRemoteException	RemoteException
    //   72	15	4	localThrowable	java.lang.Throwable
    //   100	1	5	localObject1	java.lang.Object
    //   92	6	6	localObject2	java.lang.Object
    //   125	1	7	localObject3	java.lang.Object
    //   117	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	61	61	java/rmi/RemoteException
    //   14	61	72	java/lang/Throwable
    //   14	92	92	finally
    //   142	145	92	finally
    //   102	117	117	finally
    //   137	140	117	finally
  }
  
  /* Error */
  public void processWireApprovalRslt(com.ffusion.ffs.bpw.interfaces.WireInfo[] paramArrayOfWireInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   18: aload_0
    //   19: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   22: ifeq +12 -> 34
    //   25: iconst_1
    //   26: anewarray 34	java/lang/Object
    //   29: astore_3
    //   30: aload_3
    //   31: iconst_0
    //   32: aload_1
    //   33: aastore
    //   34: aload_0
    //   35: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: sipush 183
    //   42: aload_2
    //   43: aload_3
    //   44: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   47: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   50: astore 4
    //   52: aload 4
    //   54: aload_1
    //   55: invokevirtual 741	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:processWireApprovalRslt	([Lcom/ffusion/ffs/bpw/interfaces/WireInfo;)V
    //   58: goto +84 -> 142
    //   61: astore 4
    //   63: aload_2
    //   64: aload 4
    //   66: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   69: goto +73 -> 142
    //   72: astore 4
    //   74: aload_2
    //   75: aload 4
    //   77: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   80: new 11	java/rmi/RemoteException
    //   83: dup
    //   84: ldc 53
    //   86: aload 4
    //   88: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   91: athrow
    //   92: astore 6
    //   94: jsr +6 -> 100
    //   97: aload 6
    //   99: athrow
    //   100: astore 5
    //   102: aload_0
    //   103: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   106: aload_0
    //   107: sipush 183
    //   110: aload_2
    //   111: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   114: goto +23 -> 137
    //   117: astore 8
    //   119: jsr +6 -> 125
    //   122: aload 8
    //   124: athrow
    //   125: astore 7
    //   127: aload_0
    //   128: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_2
    //   132: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   135: ret 7
    //   137: jsr -12 -> 125
    //   140: ret 5
    //   142: jsr -42 -> 100
    //   145: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	146	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	146	1	paramArrayOfWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo[]
    //   8	124	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	31	3	arrayOfObject	java.lang.Object[]
    //   50	3	4	localBPWServicesBean	BPWServicesBean
    //   61	4	4	localRemoteException	RemoteException
    //   72	15	4	localThrowable	java.lang.Throwable
    //   100	1	5	localObject1	java.lang.Object
    //   92	6	6	localObject2	java.lang.Object
    //   125	1	7	localObject3	java.lang.Object
    //   117	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	61	61	java/rmi/RemoteException
    //   14	61	72	java/lang/Throwable
    //   14	92	92	finally
    //   142	145	92	finally
    //   102	117	117	finally
    //   137	140	117	finally
  }
  
  /* Error */
  public void processWireBackendlRslt(com.ffusion.ffs.bpw.interfaces.WireInfo[] paramArrayOfWireInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   18: aload_0
    //   19: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   22: ifeq +12 -> 34
    //   25: iconst_1
    //   26: anewarray 34	java/lang/Object
    //   29: astore_3
    //   30: aload_3
    //   31: iconst_0
    //   32: aload_1
    //   33: aastore
    //   34: aload_0
    //   35: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: sipush 184
    //   42: aload_2
    //   43: aload_3
    //   44: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   47: checkcast 40	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean
    //   50: astore 4
    //   52: aload 4
    //   54: aload_1
    //   55: invokevirtual 744	com/ffusion/ffs/bpw/BPWServices/BPWServicesBean:processWireBackendlRslt	([Lcom/ffusion/ffs/bpw/interfaces/WireInfo;)V
    //   58: goto +84 -> 142
    //   61: astore 4
    //   63: aload_2
    //   64: aload 4
    //   66: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   69: goto +73 -> 142
    //   72: astore 4
    //   74: aload_2
    //   75: aload 4
    //   77: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   80: new 11	java/rmi/RemoteException
    //   83: dup
    //   84: ldc 53
    //   86: aload 4
    //   88: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   91: athrow
    //   92: astore 6
    //   94: jsr +6 -> 100
    //   97: aload 6
    //   99: athrow
    //   100: astore 5
    //   102: aload_0
    //   103: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   106: aload_0
    //   107: sipush 184
    //   110: aload_2
    //   111: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   114: goto +23 -> 137
    //   117: astore 8
    //   119: jsr +6 -> 125
    //   122: aload 8
    //   124: athrow
    //   125: astore 7
    //   127: aload_0
    //   128: getfield 20	com/ffusion/ffs/bpw/BPWServices/EJSRemoteStatelessBPWServices_e88b14a1:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_2
    //   132: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   135: ret 7
    //   137: jsr -12 -> 125
    //   140: ret 5
    //   142: jsr -42 -> 100
    //   145: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	146	0	this	EJSRemoteStatelessBPWServices_e88b14a1
    //   0	146	1	paramArrayOfWireInfo	com.ffusion.ffs.bpw.interfaces.WireInfo[]
    //   8	124	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	31	3	arrayOfObject	java.lang.Object[]
    //   50	3	4	localBPWServicesBean	BPWServicesBean
    //   61	4	4	localRemoteException	RemoteException
    //   72	15	4	localThrowable	java.lang.Throwable
    //   100	1	5	localObject1	java.lang.Object
    //   92	6	6	localObject2	java.lang.Object
    //   125	1	7	localObject3	java.lang.Object
    //   117	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	61	61	java/rmi/RemoteException
    //   14	61	72	java/lang/Throwable
    //   14	92	92	finally
    //   142	145	92	finally
    //   102	117	117	finally
    //   137	140	117	finally
  }
}


/* Location:           D:\drops\jd\jars\Deployed_BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServices.EJSRemoteStatelessBPWServices_e88b14a1
 * JD-Core Version:    0.7.0.1
 */