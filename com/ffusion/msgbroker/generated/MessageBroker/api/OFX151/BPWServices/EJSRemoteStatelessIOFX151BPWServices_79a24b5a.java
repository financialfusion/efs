package com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices;

import com.ibm.ejs.container.EJSWrapper;
import java.rmi.RemoteException;

public class EJSRemoteStatelessIOFX151BPWServices_79a24b5a
  extends EJSWrapper
  implements IOFX151BPWServices
{
  public EJSRemoteStatelessIOFX151BPWServices_79a24b5a()
    throws RemoteException
  {}
  
  /* Error */
  public boolean checkPayeeEditMask(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: iconst_0
    //   16: istore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: iconst_0
    //   51: aload_3
    //   52: aload 4
    //   54: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   57: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   60: astore 6
    //   62: aload 6
    //   64: aload_1
    //   65: aload_2
    //   66: invokevirtual 42	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:checkPayeeEditMask	(Ljava/lang/String;Ljava/lang/String;)Z
    //   69: istore 5
    //   71: goto +82 -> 153
    //   74: astore 6
    //   76: aload_3
    //   77: aload 6
    //   79: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   82: goto +71 -> 153
    //   85: astore 6
    //   87: aload_3
    //   88: aload 6
    //   90: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   93: new 11	java/rmi/RemoteException
    //   96: dup
    //   97: ldc 53
    //   99: aload 6
    //   101: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   104: athrow
    //   105: astore 8
    //   107: jsr +6 -> 113
    //   110: aload 8
    //   112: athrow
    //   113: astore 7
    //   115: aload_0
    //   116: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   119: aload_0
    //   120: iconst_0
    //   121: aload_3
    //   122: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   125: goto +23 -> 148
    //   128: astore 10
    //   130: jsr +6 -> 136
    //   133: aload 10
    //   135: athrow
    //   136: astore 9
    //   138: aload_0
    //   139: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   142: aload_3
    //   143: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   146: ret 9
    //   148: jsr -12 -> 136
    //   151: ret 7
    //   153: jsr -40 -> 113
    //   156: iload 5
    //   158: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	159	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	159	1	paramString1	java.lang.String
    //   0	159	2	paramString2	java.lang.String
    //   8	135	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	40	4	arrayOfObject	java.lang.Object[]
    //   16	141	5	bool	boolean
    //   60	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   74	4	6	localRemoteException	RemoteException
    //   85	15	6	localThrowable	java.lang.Throwable
    //   113	1	7	localObject1	java.lang.Object
    //   105	6	8	localObject2	java.lang.Object
    //   136	1	9	localObject3	java.lang.Object
    //   128	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	74	74	java/rmi/RemoteException
    //   18	74	85	java/lang/Throwable
    //   18	105	105	finally
    //   153	156	105	finally
    //   115	128	128	finally
    //   148	151	128	finally
  }
  
  /* Error */
  public boolean validateMetavanteCustAcctByConsumerID(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: iconst_0
    //   16: istore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: iconst_1
    //   51: aload_3
    //   52: aload 4
    //   54: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   57: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   60: astore 6
    //   62: aload 6
    //   64: aload_1
    //   65: aload_2
    //   66: invokevirtual 69	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:validateMetavanteCustAcctByConsumerID	(Ljava/lang/String;Ljava/lang/String;)Z
    //   69: istore 5
    //   71: goto +82 -> 153
    //   74: astore 6
    //   76: aload_3
    //   77: aload 6
    //   79: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   82: goto +71 -> 153
    //   85: astore 6
    //   87: aload_3
    //   88: aload 6
    //   90: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   93: new 11	java/rmi/RemoteException
    //   96: dup
    //   97: ldc 53
    //   99: aload 6
    //   101: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   104: athrow
    //   105: astore 8
    //   107: jsr +6 -> 113
    //   110: aload 8
    //   112: athrow
    //   113: astore 7
    //   115: aload_0
    //   116: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   119: aload_0
    //   120: iconst_1
    //   121: aload_3
    //   122: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   125: goto +23 -> 148
    //   128: astore 10
    //   130: jsr +6 -> 136
    //   133: aload 10
    //   135: athrow
    //   136: astore 9
    //   138: aload_0
    //   139: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   142: aload_3
    //   143: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   146: ret 9
    //   148: jsr -12 -> 136
    //   151: ret 7
    //   153: jsr -40 -> 113
    //   156: iload 5
    //   158: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	159	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	159	1	paramString1	java.lang.String
    //   0	159	2	paramString2	java.lang.String
    //   8	135	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	40	4	arrayOfObject	java.lang.Object[]
    //   16	141	5	bool	boolean
    //   60	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   74	4	6	localRemoteException	RemoteException
    //   85	15	6	localThrowable	java.lang.Throwable
    //   113	1	7	localObject1	java.lang.Object
    //   105	6	8	localObject2	java.lang.Object
    //   136	1	9	localObject3	java.lang.Object
    //   128	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	74	74	java/rmi/RemoteException
    //   18	74	85	java/lang/Throwable
    //   18	105	105	finally
    //   153	156	105	finally
    //   115	128	128	finally
    //   148	151	128	finally
  }
  
  /* Error */
  public boolean validateMetavanteCustAcctByCustomerID(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: iconst_0
    //   16: istore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: iconst_2
    //   51: aload_3
    //   52: aload 4
    //   54: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   57: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   60: astore 6
    //   62: aload 6
    //   64: aload_1
    //   65: aload_2
    //   66: invokevirtual 72	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:validateMetavanteCustAcctByCustomerID	(Ljava/lang/String;Ljava/lang/String;)Z
    //   69: istore 5
    //   71: goto +82 -> 153
    //   74: astore 6
    //   76: aload_3
    //   77: aload 6
    //   79: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   82: goto +71 -> 153
    //   85: astore 6
    //   87: aload_3
    //   88: aload 6
    //   90: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   93: new 11	java/rmi/RemoteException
    //   96: dup
    //   97: ldc 53
    //   99: aload 6
    //   101: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   104: athrow
    //   105: astore 8
    //   107: jsr +6 -> 113
    //   110: aload 8
    //   112: athrow
    //   113: astore 7
    //   115: aload_0
    //   116: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   119: aload_0
    //   120: iconst_2
    //   121: aload_3
    //   122: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   125: goto +23 -> 148
    //   128: astore 10
    //   130: jsr +6 -> 136
    //   133: aload 10
    //   135: athrow
    //   136: astore 9
    //   138: aload_0
    //   139: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   142: aload_3
    //   143: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   146: ret 9
    //   148: jsr -12 -> 136
    //   151: ret 7
    //   153: jsr -40 -> 113
    //   156: iload 5
    //   158: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	159	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	159	1	paramString1	java.lang.String
    //   0	159	2	paramString2	java.lang.String
    //   8	135	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	40	4	arrayOfObject	java.lang.Object[]
    //   16	141	5	bool	boolean
    //   60	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   74	4	6	localRemoteException	RemoteException
    //   85	15	6	localThrowable	java.lang.Throwable
    //   113	1	7	localObject1	java.lang.Object
    //   105	6	8	localObject2	java.lang.Object
    //   136	1	9	localObject3	java.lang.Object
    //   128	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	74	74	java/rmi/RemoteException
    //   18	74	85	java/lang/Throwable
    //   18	105	105	finally
    //   153	156	105	finally
    //   115	128	128	finally
    //   148	151	128	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.BPWHist getIntraHistory(com.ffusion.ffs.bpw.interfaces.BPWHist paramBPWHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: iconst_3
    //   43: aload_2
    //   44: aload_3
    //   45: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   48: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   51: astore 5
    //   53: aload 5
    //   55: aload_1
    //   56: invokevirtual 76	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getIntraHistory	(Lcom/ffusion/ffs/bpw/interfaces/BPWHist;)Lcom/ffusion/ffs/bpw/interfaces/BPWHist;
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
    //   106: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   129: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	149	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	149	1	paramBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   8	125	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	32	3	arrayOfObject	java.lang.Object[]
    //   15	132	4	localBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   51	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.BPWHist getPmtHistory(com.ffusion.ffs.bpw.interfaces.BPWHist paramBPWHist)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: iconst_4
    //   43: aload_2
    //   44: aload_3
    //   45: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   48: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   51: astore 5
    //   53: aload 5
    //   55: aload_1
    //   56: invokevirtual 79	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPmtHistory	(Lcom/ffusion/ffs/bpw/interfaces/BPWHist;)Lcom/ffusion/ffs/bpw/interfaces/BPWHist;
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
    //   106: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   109: aload_0
    //   110: iconst_4
    //   111: aload_2
    //   112: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   115: goto +23 -> 138
    //   118: astore 9
    //   120: jsr +6 -> 126
    //   123: aload 9
    //   125: athrow
    //   126: astore 8
    //   128: aload_0
    //   129: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	149	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	149	1	paramBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   8	125	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	32	3	arrayOfObject	java.lang.Object[]
    //   15	132	4	localBPWHist	com.ffusion.ffs.bpw.interfaces.BPWHist
    //   51	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfo[] getConsumerCrossRef(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 83	[Lcom/ffusion/ffs/bpw/interfaces/ConsumerCrossRefInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: iconst_5
    //   46: aload_2
    //   47: aload_3
    //   48: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   51: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   54: astore 5
    //   56: aload 5
    //   58: aload_1
    //   59: invokevirtual 85	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getConsumerCrossRef	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/ConsumerCrossRefInfo;
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
    //   109: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   112: aload_0
    //   113: iconst_5
    //   114: aload_2
    //   115: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   118: goto +23 -> 141
    //   121: astore 9
    //   123: jsr +6 -> 129
    //   126: aload 9
    //   128: athrow
    //   129: astore 8
    //   131: aload_0
    //   132: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	152	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	152	1	paramArrayOfString	java.lang.String[]
    //   8	128	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	35	3	arrayOfObject	java.lang.Object[]
    //   18	132	4	arrayOfConsumerCrossRefInfo	com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfo[]
    //   54	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.CustomerBankInfo[] getCustomerBankInfo(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 89	[Lcom/ffusion/ffs/bpw/interfaces/CustomerBankInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 6
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 91	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getCustomerBankInfo	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerBankInfo;
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
    //   110: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	154	1	paramArrayOfString	java.lang.String[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfCustomerBankInfo	com.ffusion.ffs.bpw.interfaces.CustomerBankInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.CustomerInfo[] getCustomerByCategory(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 95	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 7
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 97	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getCustomerByCategory	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   110: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 95	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   49: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   52: aload_0
    //   53: bipush 8
    //   55: aload_3
    //   56: aload 4
    //   58: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   61: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   64: astore 6
    //   66: aload 6
    //   68: aload_1
    //   69: aload_2
    //   70: invokevirtual 101	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getCustomerByCategoryAndFI	(Ljava/lang/String;Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   120: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   123: aload_0
    //   124: bipush 8
    //   126: aload_3
    //   127: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   130: goto +23 -> 153
    //   133: astore 10
    //   135: jsr +6 -> 141
    //   138: aload 10
    //   140: athrow
    //   141: astore 9
    //   143: aload_0
    //   144: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	164	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	164	1	paramString1	java.lang.String
    //   0	164	2	paramString2	java.lang.String
    //   8	140	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	44	4	arrayOfObject	java.lang.Object[]
    //   19	143	5	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   64	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 95	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 9
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 104	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getCustomerByFI	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   110: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 9
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 95	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 10
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 107	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getCustomerByGroup	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   110: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 10
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 95	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   49: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   52: aload_0
    //   53: bipush 11
    //   55: aload_3
    //   56: aload 4
    //   58: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   61: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   64: astore 6
    //   66: aload 6
    //   68: aload_1
    //   69: aload_2
    //   70: invokevirtual 110	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getCustomerByGroupAndFI	(Ljava/lang/String;Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   120: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   123: aload_0
    //   124: bipush 11
    //   126: aload_3
    //   127: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   130: goto +23 -> 153
    //   133: astore 10
    //   135: jsr +6 -> 141
    //   138: aload 10
    //   140: athrow
    //   141: astore 9
    //   143: aload_0
    //   144: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	164	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	164	1	paramString1	java.lang.String
    //   0	164	2	paramString2	java.lang.String
    //   8	140	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	44	4	arrayOfObject	java.lang.Object[]
    //   19	143	5	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   64	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 95	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 12
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 113	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getCustomerByType	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   110: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 95	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   49: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   52: aload_0
    //   53: bipush 13
    //   55: aload_3
    //   56: aload 4
    //   58: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   61: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   64: astore 6
    //   66: aload 6
    //   68: aload_1
    //   69: aload_2
    //   70: invokevirtual 116	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getCustomerByTypeAndFI	(Ljava/lang/String;Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   120: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   144: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	164	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	164	1	paramString1	java.lang.String
    //   0	164	2	paramString2	java.lang.String
    //   8	140	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	44	4	arrayOfObject	java.lang.Object[]
    //   19	143	5	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   64	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 95	[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 14
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 120	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getCustomersInfo	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;
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
    //   110: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 14
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	154	1	paramArrayOfString	java.lang.String[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfo[] getCustomerProductAccessInfo(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 124	[Lcom/ffusion/ffs/bpw/interfaces/CustomerProductAccessInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 15
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 126	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getCustomerProductAccessInfo	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/CustomerProductAccessInfo;
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
    //   110: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	154	1	paramArrayOfString	java.lang.String[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfCustomerProductAccessInfo	com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.IntraTrnInfo getIntraById(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 16
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 130	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getIntraById	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/IntraTrnInfo;
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localIntraTrnInfo	com.ffusion.ffs.bpw.interfaces.IntraTrnInfo
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.IntraTrnInfo[] getIntraById(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 133	[Lcom/ffusion/ffs/bpw/interfaces/IntraTrnInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 17
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 135	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getIntraById	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/IntraTrnInfo;
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
    //   110: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	154	1	paramArrayOfString	java.lang.String[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfIntraTrnInfo	com.ffusion.ffs.bpw.interfaces.IntraTrnInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PayeeInfo findPayeeByID(java.lang.String paramString)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 18
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 141	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:findPayeeByID	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
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
    //   118: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   121: aload_0
    //   122: bipush 18
    //   124: aload_2
    //   125: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   128: goto +23 -> 151
    //   131: astore 9
    //   133: jsr +6 -> 139
    //   136: aload 9
    //   138: athrow
    //   139: astore 8
    //   141: aload_0
    //   142: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	162	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	162	1	paramString	java.lang.String
    //   8	138	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	145	4	localPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   65	4	5	localRemoteException	RemoteException
    //   76	4	5	localEJBException	javax.ejb.EJBException
    //   87	15	5	localThrowable	java.lang.Throwable
    //   115	1	6	localObject1	java.lang.Object
    //   107	6	7	localObject2	java.lang.Object
    //   139	1	8	localObject3	java.lang.Object
    //   131	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	65	65	java/rmi/RemoteException
    //   17	65	76	javax/ejb/EJBException
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
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 19
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 147	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPayeeByListId	(Ljava/lang/String;Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
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
    //   91: invokevirtual 150	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
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
    //   128: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   131: aload_0
    //   132: bipush 19
    //   134: aload_3
    //   135: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: goto +23 -> 161
    //   141: astore 10
    //   143: jsr +6 -> 149
    //   146: aload 10
    //   148: athrow
    //   149: astore 9
    //   151: aload_0
    //   152: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	172	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	172	1	paramString1	java.lang.String
    //   0	172	2	paramString2	java.lang.String
    //   8	148	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	154	5	localPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo
    //   61	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PayeeInfo[] getGlobalPayees(java.lang.String paramString, int paramInt)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 154	[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: new 156	java/lang/Integer
    //   49: dup
    //   50: iload_2
    //   51: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   54: aastore
    //   55: aload_0
    //   56: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   59: aload_0
    //   60: bipush 20
    //   62: aload_3
    //   63: aload 4
    //   65: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   68: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   71: astore 6
    //   73: aload 6
    //   75: aload_1
    //   76: iload_2
    //   77: invokevirtual 161	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getGlobalPayees	(Ljava/lang/String;I)[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   80: astore 5
    //   82: goto +94 -> 176
    //   85: astore 6
    //   87: aload_3
    //   88: aload 6
    //   90: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   93: goto +83 -> 176
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
    //   138: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   141: aload_0
    //   142: bipush 20
    //   144: aload_3
    //   145: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   148: goto +23 -> 171
    //   151: astore 10
    //   153: jsr +6 -> 159
    //   156: aload 10
    //   158: athrow
    //   159: astore 9
    //   161: aload_0
    //   162: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	182	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	182	1	paramString	java.lang.String
    //   0	182	2	paramInt	int
    //   8	158	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	51	4	arrayOfObject	java.lang.Object[]
    //   19	161	5	arrayOfPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo[]
    //   71	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   85	4	6	localRemoteException	RemoteException
    //   96	4	6	localEJBException	javax.ejb.EJBException
    //   107	15	6	localThrowable	java.lang.Throwable
    //   135	1	7	localObject1	java.lang.Object
    //   127	6	8	localObject2	java.lang.Object
    //   159	1	9	localObject3	java.lang.Object
    //   151	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   21	85	85	java/rmi/RemoteException
    //   21	85	96	javax/ejb/EJBException
    //   21	85	107	java/lang/Throwable
    //   21	127	127	finally
    //   176	179	127	finally
    //   137	151	151	finally
    //   171	174	151	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.PayeeInfo[] getLinkedPayees()
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_1
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_2
    //   14: aconst_null
    //   15: checkcast 154	[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   18: astore_3
    //   19: aload_0
    //   20: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   23: aload_0
    //   24: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   27: ifeq +8 -> 35
    //   30: iconst_0
    //   31: anewarray 34	java/lang/Object
    //   34: astore_2
    //   35: aload_0
    //   36: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   39: aload_0
    //   40: bipush 21
    //   42: aload_1
    //   43: aload_2
    //   44: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   47: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   50: astore 4
    //   52: aload 4
    //   54: invokevirtual 165	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getLinkedPayees	()[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   57: astore_3
    //   58: goto +83 -> 141
    //   61: astore 4
    //   63: aload_1
    //   64: aload 4
    //   66: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   69: goto +72 -> 141
    //   72: astore 4
    //   74: aload_1
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
    //   103: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   106: aload_0
    //   107: bipush 21
    //   109: aload_1
    //   110: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   113: goto +23 -> 136
    //   116: astore 8
    //   118: jsr +6 -> 124
    //   121: aload 8
    //   123: athrow
    //   124: astore 7
    //   126: aload_0
    //   127: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   130: aload_1
    //   131: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   134: ret 7
    //   136: jsr -12 -> 124
    //   139: ret 5
    //   141: jsr -41 -> 100
    //   144: aload_3
    //   145: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	146	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   8	123	1	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	31	2	arrayOfObject	java.lang.Object[]
    //   18	127	3	arrayOfPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo[]
    //   50	3	4	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   61	4	4	localRemoteException	RemoteException
    //   72	15	4	localThrowable	java.lang.Throwable
    //   100	1	5	localObject1	java.lang.Object
    //   92	6	6	localObject2	java.lang.Object
    //   124	1	7	localObject3	java.lang.Object
    //   116	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   19	61	61	java/rmi/RemoteException
    //   19	61	72	java/lang/Throwable
    //   19	92	92	finally
    //   141	144	92	finally
    //   102	116	116	finally
    //   136	139	116	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.PayeeInfo[] getMostUsedPayees(int paramInt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 154	[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   24: aload_0
    //   25: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   28: ifeq +19 -> 47
    //   31: iconst_1
    //   32: anewarray 34	java/lang/Object
    //   35: astore_3
    //   36: aload_3
    //   37: iconst_0
    //   38: new 156	java/lang/Integer
    //   41: dup
    //   42: iload_1
    //   43: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   46: aastore
    //   47: aload_0
    //   48: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   51: aload_0
    //   52: bipush 22
    //   54: aload_2
    //   55: aload_3
    //   56: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   59: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   62: astore 5
    //   64: aload 5
    //   66: iload_1
    //   67: invokevirtual 169	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getMostUsedPayees	(I)[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   70: astore 4
    //   72: goto +83 -> 155
    //   75: astore 5
    //   77: aload_2
    //   78: aload 5
    //   80: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   83: goto +72 -> 155
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
    //   117: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: bipush 22
    //   123: aload_2
    //   124: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   127: goto +23 -> 150
    //   130: astore 9
    //   132: jsr +6 -> 138
    //   135: aload 9
    //   137: athrow
    //   138: astore 8
    //   140: aload_0
    //   141: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   144: aload_2
    //   145: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   148: ret 8
    //   150: jsr -12 -> 138
    //   153: ret 6
    //   155: jsr -41 -> 114
    //   158: aload 4
    //   160: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	161	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	161	1	paramInt	int
    //   8	137	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	43	3	arrayOfObject	java.lang.Object[]
    //   18	141	4	arrayOfPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo[]
    //   62	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   75	4	5	localRemoteException	RemoteException
    //   86	15	5	localThrowable	java.lang.Throwable
    //   114	1	6	localObject1	java.lang.Object
    //   106	6	7	localObject2	java.lang.Object
    //   138	1	8	localObject3	java.lang.Object
    //   130	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	75	75	java/rmi/RemoteException
    //   20	75	86	java/lang/Throwable
    //   20	106	106	finally
    //   155	158	106	finally
    //   116	130	130	finally
    //   150	153	130	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.PayeeInfo[] getPayees(java.lang.String paramString)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 154	[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 23
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 173	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPayees	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
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
    //   121: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   124: aload_0
    //   125: bipush 23
    //   127: aload_2
    //   128: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   131: goto +23 -> 154
    //   134: astore 9
    //   136: jsr +6 -> 142
    //   139: aload 9
    //   141: athrow
    //   142: astore 8
    //   144: aload_0
    //   145: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	165	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	165	1	paramString	java.lang.String
    //   8	141	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	145	4	arrayOfPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   68	4	5	localRemoteException	RemoteException
    //   79	4	5	localEJBException	javax.ejb.EJBException
    //   90	15	5	localThrowable	java.lang.Throwable
    //   118	1	6	localObject1	java.lang.Object
    //   110	6	7	localObject2	java.lang.Object
    //   142	1	8	localObject3	java.lang.Object
    //   134	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	68	68	java/rmi/RemoteException
    //   20	68	79	javax/ejb/EJBException
    //   20	68	90	java/lang/Throwable
    //   20	110	110	finally
    //   159	162	110	finally
    //   120	134	134	finally
    //   154	157	134	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.PayeeInfo[] getPayees(java.lang.String paramString, int paramInt)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 154	[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: new 156	java/lang/Integer
    //   49: dup
    //   50: iload_2
    //   51: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   54: aastore
    //   55: aload_0
    //   56: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   59: aload_0
    //   60: bipush 24
    //   62: aload_3
    //   63: aload 4
    //   65: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   68: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   71: astore 6
    //   73: aload 6
    //   75: aload_1
    //   76: iload_2
    //   77: invokevirtual 175	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPayees	(Ljava/lang/String;I)[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   80: astore 5
    //   82: goto +94 -> 176
    //   85: astore 6
    //   87: aload_3
    //   88: aload 6
    //   90: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   93: goto +83 -> 176
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
    //   138: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   141: aload_0
    //   142: bipush 24
    //   144: aload_3
    //   145: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   148: goto +23 -> 171
    //   151: astore 10
    //   153: jsr +6 -> 159
    //   156: aload 10
    //   158: athrow
    //   159: astore 9
    //   161: aload_0
    //   162: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	182	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	182	1	paramString	java.lang.String
    //   0	182	2	paramInt	int
    //   8	158	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	51	4	arrayOfObject	java.lang.Object[]
    //   19	161	5	arrayOfPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo[]
    //   71	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   85	4	6	localRemoteException	RemoteException
    //   96	4	6	localEJBException	javax.ejb.EJBException
    //   107	15	6	localThrowable	java.lang.Throwable
    //   135	1	7	localObject1	java.lang.Object
    //   127	6	8	localObject2	java.lang.Object
    //   159	1	9	localObject3	java.lang.Object
    //   151	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   21	85	85	java/rmi/RemoteException
    //   21	85	96	javax/ejb/EJBException
    //   21	85	107	java/lang/Throwable
    //   21	127	127	finally
    //   176	179	127	finally
    //   137	151	151	finally
    //   171	174	151	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.PayeeInfo[] getPreferredPayees(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 154	[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 25
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 178	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPreferredPayees	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
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
    //   110: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 25
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	154	1	paramString	java.lang.String
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PayeeInfo[] searchGlobalPayees(java.lang.String paramString)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 154	[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 26
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 181	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:searchGlobalPayees	(Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
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
    //   121: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   124: aload_0
    //   125: bipush 26
    //   127: aload_2
    //   128: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   131: goto +23 -> 154
    //   134: astore 9
    //   136: jsr +6 -> 142
    //   139: aload 9
    //   141: athrow
    //   142: astore 8
    //   144: aload_0
    //   145: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	165	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	165	1	paramString	java.lang.String
    //   8	141	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	145	4	arrayOfPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   68	4	5	localRemoteException	RemoteException
    //   79	4	5	localEJBException	javax.ejb.EJBException
    //   90	15	5	localThrowable	java.lang.Throwable
    //   118	1	6	localObject1	java.lang.Object
    //   110	6	7	localObject2	java.lang.Object
    //   142	1	8	localObject3	java.lang.Object
    //   134	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	68	68	java/rmi/RemoteException
    //   20	68	79	javax/ejb/EJBException
    //   20	68	90	java/lang/Throwable
    //   20	110	110	finally
    //   159	162	110	finally
    //   120	134	134	finally
    //   154	157	134	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.PayeeInfo[] updatePayee(com.ffusion.ffs.bpw.interfaces.PayeeInfo paramPayeeInfo, int paramInt)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 154	[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: new 156	java/lang/Integer
    //   49: dup
    //   50: iload_2
    //   51: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   54: aastore
    //   55: aload_0
    //   56: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   59: aload_0
    //   60: bipush 27
    //   62: aload_3
    //   63: aload 4
    //   65: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   68: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   71: astore 6
    //   73: aload 6
    //   75: aload_1
    //   76: iload_2
    //   77: invokevirtual 185	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:updatePayee	(Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;I)[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   80: astore 5
    //   82: goto +94 -> 176
    //   85: astore 6
    //   87: aload_3
    //   88: aload 6
    //   90: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   93: goto +83 -> 176
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
    //   138: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   141: aload_0
    //   142: bipush 27
    //   144: aload_3
    //   145: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   148: goto +23 -> 171
    //   151: astore 10
    //   153: jsr +6 -> 159
    //   156: aload 10
    //   158: athrow
    //   159: astore 9
    //   161: aload_0
    //   162: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	182	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	182	1	paramPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo
    //   0	182	2	paramInt	int
    //   8	158	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	51	4	arrayOfObject	java.lang.Object[]
    //   19	161	5	arrayOfPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo[]
    //   71	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   85	4	6	localRemoteException	RemoteException
    //   96	4	6	localEJBException	javax.ejb.EJBException
    //   107	15	6	localThrowable	java.lang.Throwable
    //   135	1	7	localObject1	java.lang.Object
    //   127	6	8	localObject2	java.lang.Object
    //   159	1	9	localObject3	java.lang.Object
    //   151	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   21	85	85	java/rmi/RemoteException
    //   21	85	96	javax/ejb/EJBException
    //   21	85	107	java/lang/Throwable
    //   21	127	127	finally
    //   176	179	127	finally
    //   137	151	151	finally
    //   171	174	151	finally
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.PayeeInfo[] updatePayeeFromBackend(com.ffusion.ffs.bpw.interfaces.PayeeInfo paramPayeeInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 154	[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 28
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 189	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:updatePayeeFromBackend	(Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;)[Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;
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
    //   110: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 28
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	154	1	paramPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PmtInfo getPmtById(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 29
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 193	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPmtById	(Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/PmtInfo;
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	localPmtInfo	com.ffusion.ffs.bpw.interfaces.PmtInfo
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.PmtInfo[] getPmtById(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 196	[Lcom/ffusion/ffs/bpw/interfaces/PmtInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 30
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 198	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPmtById	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/PmtInfo;
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
    //   110: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: bipush 30
    //   116: aload_2
    //   117: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   120: goto +23 -> 143
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	154	1	paramArrayOfString	java.lang.String[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfPmtInfo	com.ffusion.ffs.bpw.interfaces.PmtInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.interfaces.RecPmtInfo[] getRecPmtById(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 202	[Lcom/ffusion/ffs/bpw/interfaces/RecPmtInfo;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 31
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 204	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getRecPmtById	([Ljava/lang/String;)[Lcom/ffusion/ffs/bpw/interfaces/RecPmtInfo;
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
    //   110: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	154	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	154	1	paramArrayOfString	java.lang.String[]
    //   8	130	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	134	4	arrayOfRecPmtInfo	com.ffusion.ffs.bpw.interfaces.RecPmtInfo[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.ffs.bpw.util.AccountTypesMap getAccountTypesMap()
    throws RemoteException, com.ffusion.ffs.interfaces.FFSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_1
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_2
    //   14: aconst_null
    //   15: astore_3
    //   16: aload_0
    //   17: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   20: aload_0
    //   21: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   24: ifeq +8 -> 32
    //   27: iconst_0
    //   28: anewarray 34	java/lang/Object
    //   31: astore_2
    //   32: aload_0
    //   33: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   36: aload_0
    //   37: bipush 32
    //   39: aload_1
    //   40: aload_2
    //   41: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   44: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   47: astore 4
    //   49: aload 4
    //   51: invokevirtual 208	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getAccountTypesMap	()Lcom/ffusion/ffs/bpw/util/AccountTypesMap;
    //   54: astore_3
    //   55: goto +94 -> 149
    //   58: astore 4
    //   60: aload_1
    //   61: aload 4
    //   63: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   66: goto +83 -> 149
    //   69: astore 4
    //   71: aload_1
    //   72: aload 4
    //   74: invokevirtual 150	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   77: aload 4
    //   79: athrow
    //   80: astore 4
    //   82: aload_1
    //   83: aload 4
    //   85: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   88: new 11	java/rmi/RemoteException
    //   91: dup
    //   92: ldc 53
    //   94: aload 4
    //   96: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   99: athrow
    //   100: astore 6
    //   102: jsr +6 -> 108
    //   105: aload 6
    //   107: athrow
    //   108: astore 5
    //   110: aload_0
    //   111: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   114: aload_0
    //   115: bipush 32
    //   117: aload_1
    //   118: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   121: goto +23 -> 144
    //   124: astore 8
    //   126: jsr +6 -> 132
    //   129: aload 8
    //   131: athrow
    //   132: astore 7
    //   134: aload_0
    //   135: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   138: aload_1
    //   139: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   142: ret 7
    //   144: jsr -12 -> 132
    //   147: ret 5
    //   149: jsr -41 -> 108
    //   152: aload_3
    //   153: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	154	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   8	131	1	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	28	2	arrayOfObject	java.lang.Object[]
    //   15	138	3	localAccountTypesMap	com.ffusion.ffs.bpw.util.AccountTypesMap
    //   47	3	4	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   58	4	4	localRemoteException	RemoteException
    //   69	9	4	localFFSException	com.ffusion.ffs.interfaces.FFSException
    //   80	15	4	localThrowable	java.lang.Throwable
    //   108	1	5	localObject1	java.lang.Object
    //   100	6	6	localObject2	java.lang.Object
    //   132	1	7	localObject3	java.lang.Object
    //   124	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   16	58	58	java/rmi/RemoteException
    //   16	58	69	com/ffusion/ffs/interfaces/FFSException
    //   16	58	80	java/lang/Throwable
    //   16	100	100	finally
    //   149	152	100	finally
    //   110	124	124	finally
    //   144	147	124	finally
  }
  
  /* Error */
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1 getPendingIntras(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, com.ffusion.ffs.ofx.interfaces.TypeUserData paramTypeUserData, int paramInt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore 4
    //   10: aconst_null
    //   11: checkcast 28	[Ljava/lang/Object;
    //   14: astore 5
    //   16: aconst_null
    //   17: astore 6
    //   19: aload_0
    //   20: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   23: aload_0
    //   24: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   27: ifeq +31 -> 58
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
    //   49: new 156	java/lang/Integer
    //   52: dup
    //   53: iload_3
    //   54: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   57: aastore
    //   58: aload_0
    //   59: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   62: aload_0
    //   63: bipush 33
    //   65: aload 4
    //   67: aload 5
    //   69: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   72: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   75: astore 7
    //   77: aload 7
    //   79: aload_1
    //   80: aload_2
    //   81: iload_3
    //   82: invokevirtual 212	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPendingIntras	(Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraSyncRqV1;Lcom/ffusion/ffs/ofx/interfaces/TypeUserData;I)Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraSyncRsV1;
    //   85: astore 6
    //   87: goto +87 -> 174
    //   90: astore 7
    //   92: aload 4
    //   94: aload 7
    //   96: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   99: goto +75 -> 174
    //   102: astore 7
    //   104: aload 4
    //   106: aload 7
    //   108: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   111: new 11	java/rmi/RemoteException
    //   114: dup
    //   115: ldc 53
    //   117: aload 7
    //   119: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   122: athrow
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   137: aload_0
    //   138: bipush 33
    //   140: aload 4
    //   142: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   145: goto +24 -> 169
    //   148: astore 11
    //   150: jsr +6 -> 156
    //   153: aload 11
    //   155: athrow
    //   156: astore 10
    //   158: aload_0
    //   159: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   162: aload 4
    //   164: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   167: ret 10
    //   169: jsr -13 -> 156
    //   172: ret 8
    //   174: jsr -43 -> 131
    //   177: aload 6
    //   179: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	180	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	180	1	paramTypeIntraSyncRqV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1
    //   0	180	2	paramTypeUserData	com.ffusion.ffs.ofx.interfaces.TypeUserData
    //   0	180	3	paramInt	int
    //   8	155	4	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   14	54	5	arrayOfObject	java.lang.Object[]
    //   17	161	6	localTypeIntraSyncRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1
    //   75	3	7	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   90	5	7	localRemoteException	RemoteException
    //   102	16	7	localThrowable	java.lang.Throwable
    //   131	1	8	localObject1	java.lang.Object
    //   123	6	9	localObject2	java.lang.Object
    //   156	1	10	localObject3	java.lang.Object
    //   148	6	11	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   19	90	90	java/rmi/RemoteException
    //   19	90	102	java/lang/Throwable
    //   19	123	123	finally
    //   174	177	123	finally
    //   133	148	148	finally
    //   169	172	148	finally
  }
  
  /* Error */
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1 processIntraSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, com.ffusion.ffs.ofx.interfaces.TypeUserData paramTypeUserData)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 34
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 216	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processIntraSyncRqV1	(Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraSyncRqV1;Lcom/ffusion/ffs/ofx/interfaces/TypeUserData;)Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraSyncRsV1;
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
    //   117: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: bipush 34
    //   123: aload_3
    //   124: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   127: goto +23 -> 150
    //   130: astore 10
    //   132: jsr +6 -> 138
    //   135: aload 10
    //   137: athrow
    //   138: astore 9
    //   140: aload_0
    //   141: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	161	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	161	1	paramTypeIntraSyncRqV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1
    //   0	161	2	paramTypeUserData	com.ffusion.ffs.ofx.interfaces.TypeUserData
    //   8	137	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	143	5	localTypeIntraSyncRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1
    //   61	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1[] getPendingIntrasAndHistoryByCustomerID(java.lang.String paramString, int paramInt1, int paramInt2)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore 4
    //   10: aconst_null
    //   11: checkcast 28	[Ljava/lang/Object;
    //   14: astore 5
    //   16: aconst_null
    //   17: checkcast 220	[Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraSyncRsV1;
    //   20: astore 6
    //   22: aload_0
    //   23: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   26: aload_0
    //   27: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   30: ifeq +38 -> 68
    //   33: iconst_3
    //   34: anewarray 34	java/lang/Object
    //   37: astore 5
    //   39: aload 5
    //   41: iconst_0
    //   42: aload_1
    //   43: aastore
    //   44: aload 5
    //   46: iconst_1
    //   47: new 156	java/lang/Integer
    //   50: dup
    //   51: iload_2
    //   52: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   55: aastore
    //   56: aload 5
    //   58: iconst_2
    //   59: new 156	java/lang/Integer
    //   62: dup
    //   63: iload_3
    //   64: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   67: aastore
    //   68: aload_0
    //   69: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   72: aload_0
    //   73: bipush 35
    //   75: aload 4
    //   77: aload 5
    //   79: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   82: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   85: astore 7
    //   87: aload 7
    //   89: aload_1
    //   90: iload_2
    //   91: iload_3
    //   92: invokevirtual 222	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPendingIntrasAndHistoryByCustomerID	(Ljava/lang/String;II)[Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraSyncRsV1;
    //   95: astore 6
    //   97: goto +87 -> 184
    //   100: astore 7
    //   102: aload 4
    //   104: aload 7
    //   106: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   109: goto +75 -> 184
    //   112: astore 7
    //   114: aload 4
    //   116: aload 7
    //   118: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   121: new 11	java/rmi/RemoteException
    //   124: dup
    //   125: ldc 53
    //   127: aload 7
    //   129: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   132: athrow
    //   133: astore 9
    //   135: jsr +6 -> 141
    //   138: aload 9
    //   140: athrow
    //   141: astore 8
    //   143: aload_0
    //   144: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   147: aload_0
    //   148: bipush 35
    //   150: aload 4
    //   152: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   155: goto +24 -> 179
    //   158: astore 11
    //   160: jsr +6 -> 166
    //   163: aload 11
    //   165: athrow
    //   166: astore 10
    //   168: aload_0
    //   169: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   172: aload 4
    //   174: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   177: ret 10
    //   179: jsr -13 -> 166
    //   182: ret 8
    //   184: jsr -43 -> 141
    //   187: aload 6
    //   189: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	190	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	190	1	paramString	java.lang.String
    //   0	190	2	paramInt1	int
    //   0	190	3	paramInt2	int
    //   8	165	4	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   14	64	5	arrayOfObject	java.lang.Object[]
    //   20	168	6	arrayOfTypeIntraSyncRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1[]
    //   85	3	7	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   100	5	7	localRemoteException	RemoteException
    //   112	16	7	localThrowable	java.lang.Throwable
    //   141	1	8	localObject1	java.lang.Object
    //   133	6	9	localObject2	java.lang.Object
    //   166	1	10	localObject3	java.lang.Object
    //   158	6	11	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   22	100	100	java/rmi/RemoteException
    //   22	100	112	java/lang/Throwable
    //   22	133	133	finally
    //   184	187	133	finally
    //   143	158	158	finally
    //   179	182	158	finally
  }
  
  /* Error */
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1[] getPendingIntrasByCustomerID(java.lang.String paramString, int paramInt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 220	[Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraSyncRsV1;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: new 156	java/lang/Integer
    //   49: dup
    //   50: iload_2
    //   51: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   54: aastore
    //   55: aload_0
    //   56: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   59: aload_0
    //   60: bipush 36
    //   62: aload_3
    //   63: aload 4
    //   65: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   68: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   71: astore 6
    //   73: aload 6
    //   75: aload_1
    //   76: iload_2
    //   77: invokevirtual 226	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPendingIntrasByCustomerID	(Ljava/lang/String;I)[Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraSyncRsV1;
    //   80: astore 5
    //   82: goto +83 -> 165
    //   85: astore 6
    //   87: aload_3
    //   88: aload 6
    //   90: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   93: goto +72 -> 165
    //   96: astore 6
    //   98: aload_3
    //   99: aload 6
    //   101: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   104: new 11	java/rmi/RemoteException
    //   107: dup
    //   108: ldc 53
    //   110: aload 6
    //   112: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   115: athrow
    //   116: astore 8
    //   118: jsr +6 -> 124
    //   121: aload 8
    //   123: athrow
    //   124: astore 7
    //   126: aload_0
    //   127: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   130: aload_0
    //   131: bipush 36
    //   133: aload_3
    //   134: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   137: goto +23 -> 160
    //   140: astore 10
    //   142: jsr +6 -> 148
    //   145: aload 10
    //   147: athrow
    //   148: astore 9
    //   150: aload_0
    //   151: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   154: aload_3
    //   155: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   158: ret 9
    //   160: jsr -12 -> 148
    //   163: ret 7
    //   165: jsr -41 -> 124
    //   168: aload 5
    //   170: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	171	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	171	1	paramString	java.lang.String
    //   0	171	2	paramInt	int
    //   8	147	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	51	4	arrayOfObject	java.lang.Object[]
    //   19	150	5	arrayOfTypeIntraSyncRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1[]
    //   71	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   85	4	6	localRemoteException	RemoteException
    //   96	15	6	localThrowable	java.lang.Throwable
    //   124	1	7	localObject1	java.lang.Object
    //   116	6	8	localObject2	java.lang.Object
    //   148	1	9	localObject3	java.lang.Object
    //   140	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   21	85	85	java/rmi/RemoteException
    //   21	85	96	java/lang/Throwable
    //   21	116	116	finally
    //   165	168	116	finally
    //   126	140	140	finally
    //   160	163	140	finally
  }
  
  /* Error */
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1 processIntraTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, com.ffusion.ffs.ofx.interfaces.TypeUserData paramTypeUserData)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 37
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 230	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processIntraTrnRqV1	(Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraTrnRqV1;Lcom/ffusion/ffs/ofx/interfaces/TypeUserData;)Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraTrnRsV1;
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
    //   117: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: bipush 37
    //   123: aload_3
    //   124: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   127: goto +23 -> 150
    //   130: astore 10
    //   132: jsr +6 -> 138
    //   135: aload 10
    //   137: athrow
    //   138: astore 9
    //   140: aload_0
    //   141: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	161	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	161	1	paramTypeIntraTrnRqV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1
    //   0	161	2	paramTypeUserData	com.ffusion.ffs.ofx.interfaces.TypeUserData
    //   8	137	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	143	5	localTypeIntraTrnRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1
    //   61	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1 processPayeeSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, com.ffusion.ffs.ofx.interfaces.TypeUserData paramTypeUserData)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 38
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 234	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processPayeeSyncRqV1	(Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePayeeSyncRqV1;Lcom/ffusion/ffs/ofx/interfaces/TypeUserData;)Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePayeeSyncRsV1;
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
    //   117: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: bipush 38
    //   123: aload_3
    //   124: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   127: goto +23 -> 150
    //   130: astore 10
    //   132: jsr +6 -> 138
    //   135: aload 10
    //   137: athrow
    //   138: astore 9
    //   140: aload_0
    //   141: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	161	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	161	1	paramTypePayeeSyncRqV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1
    //   0	161	2	paramTypeUserData	com.ffusion.ffs.ofx.interfaces.TypeUserData
    //   8	137	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	143	5	localTypePayeeSyncRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1
    //   61	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1 processPayeeTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, com.ffusion.ffs.ofx.interfaces.TypeUserData paramTypeUserData)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 39
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 238	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processPayeeTrnRqV1	(Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePayeeTrnRqV1;Lcom/ffusion/ffs/ofx/interfaces/TypeUserData;)Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePayeeTrnRsV1;
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
    //   117: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: bipush 39
    //   123: aload_3
    //   124: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   127: goto +23 -> 150
    //   130: astore 10
    //   132: jsr +6 -> 138
    //   135: aload 10
    //   137: athrow
    //   138: astore 9
    //   140: aload_0
    //   141: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	161	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	161	1	paramTypePayeeTrnRqV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1
    //   0	161	2	paramTypeUserData	com.ffusion.ffs.ofx.interfaces.TypeUserData
    //   8	137	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	143	5	localTypePayeeTrnRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1
    //   61	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1 processPmtInqTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, com.ffusion.ffs.ofx.interfaces.TypeUserData paramTypeUserData)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 40
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 242	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processPmtInqTrnRqV1	(Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtInqTrnRqV1;Lcom/ffusion/ffs/ofx/interfaces/TypeUserData;)Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtInqTrnRsV1;
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
    //   117: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: bipush 40
    //   123: aload_3
    //   124: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   127: goto +23 -> 150
    //   130: astore 10
    //   132: jsr +6 -> 138
    //   135: aload 10
    //   137: athrow
    //   138: astore 9
    //   140: aload_0
    //   141: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	161	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	161	1	paramTypePmtInqTrnRqV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1
    //   0	161	2	paramTypeUserData	com.ffusion.ffs.ofx.interfaces.TypeUserData
    //   8	137	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	143	5	localTypePmtInqTrnRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1
    //   61	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1 getPendingPmts(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1 paramTypePmtSyncRqV1, com.ffusion.ffs.ofx.interfaces.TypeUserData paramTypeUserData, int paramInt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore 4
    //   10: aconst_null
    //   11: checkcast 28	[Ljava/lang/Object;
    //   14: astore 5
    //   16: aconst_null
    //   17: astore 6
    //   19: aload_0
    //   20: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   23: aload_0
    //   24: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   27: ifeq +31 -> 58
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
    //   49: new 156	java/lang/Integer
    //   52: dup
    //   53: iload_3
    //   54: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   57: aastore
    //   58: aload_0
    //   59: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   62: aload_0
    //   63: bipush 41
    //   65: aload 4
    //   67: aload 5
    //   69: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   72: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   75: astore 7
    //   77: aload 7
    //   79: aload_1
    //   80: aload_2
    //   81: iload_3
    //   82: invokevirtual 246	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPendingPmts	(Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtSyncRqV1;Lcom/ffusion/ffs/ofx/interfaces/TypeUserData;I)Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtSyncRsV1;
    //   85: astore 6
    //   87: goto +87 -> 174
    //   90: astore 7
    //   92: aload 4
    //   94: aload 7
    //   96: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   99: goto +75 -> 174
    //   102: astore 7
    //   104: aload 4
    //   106: aload 7
    //   108: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   111: new 11	java/rmi/RemoteException
    //   114: dup
    //   115: ldc 53
    //   117: aload 7
    //   119: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   122: athrow
    //   123: astore 9
    //   125: jsr +6 -> 131
    //   128: aload 9
    //   130: athrow
    //   131: astore 8
    //   133: aload_0
    //   134: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   137: aload_0
    //   138: bipush 41
    //   140: aload 4
    //   142: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   145: goto +24 -> 169
    //   148: astore 11
    //   150: jsr +6 -> 156
    //   153: aload 11
    //   155: athrow
    //   156: astore 10
    //   158: aload_0
    //   159: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   162: aload 4
    //   164: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   167: ret 10
    //   169: jsr -13 -> 156
    //   172: ret 8
    //   174: jsr -43 -> 131
    //   177: aload 6
    //   179: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	180	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	180	1	paramTypePmtSyncRqV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1
    //   0	180	2	paramTypeUserData	com.ffusion.ffs.ofx.interfaces.TypeUserData
    //   0	180	3	paramInt	int
    //   8	155	4	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   14	54	5	arrayOfObject	java.lang.Object[]
    //   17	161	6	localTypePmtSyncRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1
    //   75	3	7	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   90	5	7	localRemoteException	RemoteException
    //   102	16	7	localThrowable	java.lang.Throwable
    //   131	1	8	localObject1	java.lang.Object
    //   123	6	9	localObject2	java.lang.Object
    //   156	1	10	localObject3	java.lang.Object
    //   148	6	11	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   19	90	90	java/rmi/RemoteException
    //   19	90	102	java/lang/Throwable
    //   19	123	123	finally
    //   174	177	123	finally
    //   133	148	148	finally
    //   169	172	148	finally
  }
  
  /* Error */
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1 processPmtSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1 paramTypePmtSyncRqV1, com.ffusion.ffs.ofx.interfaces.TypeUserData paramTypeUserData)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 42
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 250	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processPmtSyncRqV1	(Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtSyncRqV1;Lcom/ffusion/ffs/ofx/interfaces/TypeUserData;)Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtSyncRsV1;
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
    //   117: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: bipush 42
    //   123: aload_3
    //   124: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   127: goto +23 -> 150
    //   130: astore 10
    //   132: jsr +6 -> 138
    //   135: aload 10
    //   137: athrow
    //   138: astore 9
    //   140: aload_0
    //   141: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	161	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	161	1	paramTypePmtSyncRqV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1
    //   0	161	2	paramTypeUserData	com.ffusion.ffs.ofx.interfaces.TypeUserData
    //   8	137	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	143	5	localTypePmtSyncRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1
    //   61	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1[] getPendingPmtsAndHistoryByCustomerID(java.lang.String paramString, int paramInt1, int paramInt2)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore 4
    //   10: aconst_null
    //   11: checkcast 28	[Ljava/lang/Object;
    //   14: astore 5
    //   16: aconst_null
    //   17: checkcast 254	[Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtSyncRsV1;
    //   20: astore 6
    //   22: aload_0
    //   23: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   26: aload_0
    //   27: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   30: ifeq +38 -> 68
    //   33: iconst_3
    //   34: anewarray 34	java/lang/Object
    //   37: astore 5
    //   39: aload 5
    //   41: iconst_0
    //   42: aload_1
    //   43: aastore
    //   44: aload 5
    //   46: iconst_1
    //   47: new 156	java/lang/Integer
    //   50: dup
    //   51: iload_2
    //   52: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   55: aastore
    //   56: aload 5
    //   58: iconst_2
    //   59: new 156	java/lang/Integer
    //   62: dup
    //   63: iload_3
    //   64: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   67: aastore
    //   68: aload_0
    //   69: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   72: aload_0
    //   73: bipush 43
    //   75: aload 4
    //   77: aload 5
    //   79: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   82: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   85: astore 7
    //   87: aload 7
    //   89: aload_1
    //   90: iload_2
    //   91: iload_3
    //   92: invokevirtual 256	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPendingPmtsAndHistoryByCustomerID	(Ljava/lang/String;II)[Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtSyncRsV1;
    //   95: astore 6
    //   97: goto +87 -> 184
    //   100: astore 7
    //   102: aload 4
    //   104: aload 7
    //   106: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   109: goto +75 -> 184
    //   112: astore 7
    //   114: aload 4
    //   116: aload 7
    //   118: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   121: new 11	java/rmi/RemoteException
    //   124: dup
    //   125: ldc 53
    //   127: aload 7
    //   129: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   132: athrow
    //   133: astore 9
    //   135: jsr +6 -> 141
    //   138: aload 9
    //   140: athrow
    //   141: astore 8
    //   143: aload_0
    //   144: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   147: aload_0
    //   148: bipush 43
    //   150: aload 4
    //   152: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   155: goto +24 -> 179
    //   158: astore 11
    //   160: jsr +6 -> 166
    //   163: aload 11
    //   165: athrow
    //   166: astore 10
    //   168: aload_0
    //   169: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   172: aload 4
    //   174: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   177: ret 10
    //   179: jsr -13 -> 166
    //   182: ret 8
    //   184: jsr -43 -> 141
    //   187: aload 6
    //   189: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	190	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	190	1	paramString	java.lang.String
    //   0	190	2	paramInt1	int
    //   0	190	3	paramInt2	int
    //   8	165	4	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   14	64	5	arrayOfObject	java.lang.Object[]
    //   20	168	6	arrayOfTypePmtSyncRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1[]
    //   85	3	7	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   100	5	7	localRemoteException	RemoteException
    //   112	16	7	localThrowable	java.lang.Throwable
    //   141	1	8	localObject1	java.lang.Object
    //   133	6	9	localObject2	java.lang.Object
    //   166	1	10	localObject3	java.lang.Object
    //   158	6	11	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   22	100	100	java/rmi/RemoteException
    //   22	100	112	java/lang/Throwable
    //   22	133	133	finally
    //   184	187	133	finally
    //   143	158	158	finally
    //   179	182	158	finally
  }
  
  /* Error */
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1[] getPendingPmtsByCustomerID(java.lang.String paramString, int paramInt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 254	[Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtSyncRsV1;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: new 156	java/lang/Integer
    //   49: dup
    //   50: iload_2
    //   51: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   54: aastore
    //   55: aload_0
    //   56: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   59: aload_0
    //   60: bipush 44
    //   62: aload_3
    //   63: aload 4
    //   65: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   68: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   71: astore 6
    //   73: aload 6
    //   75: aload_1
    //   76: iload_2
    //   77: invokevirtual 260	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPendingPmtsByCustomerID	(Ljava/lang/String;I)[Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtSyncRsV1;
    //   80: astore 5
    //   82: goto +83 -> 165
    //   85: astore 6
    //   87: aload_3
    //   88: aload 6
    //   90: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   93: goto +72 -> 165
    //   96: astore 6
    //   98: aload_3
    //   99: aload 6
    //   101: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   104: new 11	java/rmi/RemoteException
    //   107: dup
    //   108: ldc 53
    //   110: aload 6
    //   112: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   115: athrow
    //   116: astore 8
    //   118: jsr +6 -> 124
    //   121: aload 8
    //   123: athrow
    //   124: astore 7
    //   126: aload_0
    //   127: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   130: aload_0
    //   131: bipush 44
    //   133: aload_3
    //   134: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   137: goto +23 -> 160
    //   140: astore 10
    //   142: jsr +6 -> 148
    //   145: aload 10
    //   147: athrow
    //   148: astore 9
    //   150: aload_0
    //   151: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   154: aload_3
    //   155: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   158: ret 9
    //   160: jsr -12 -> 148
    //   163: ret 7
    //   165: jsr -41 -> 124
    //   168: aload 5
    //   170: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	171	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	171	1	paramString	java.lang.String
    //   0	171	2	paramInt	int
    //   8	147	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	51	4	arrayOfObject	java.lang.Object[]
    //   19	150	5	arrayOfTypePmtSyncRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1[]
    //   71	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   85	4	6	localRemoteException	RemoteException
    //   96	15	6	localThrowable	java.lang.Throwable
    //   124	1	7	localObject1	java.lang.Object
    //   116	6	8	localObject2	java.lang.Object
    //   148	1	9	localObject3	java.lang.Object
    //   140	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   21	85	85	java/rmi/RemoteException
    //   21	85	96	java/lang/Throwable
    //   21	116	116	finally
    //   165	168	116	finally
    //   126	140	140	finally
    //   160	163	140	finally
  }
  
  /* Error */
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1 processPmtTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1 paramTypePmtTrnRqV1, com.ffusion.ffs.ofx.interfaces.TypeUserData paramTypeUserData)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 45
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 264	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processPmtTrnRqV1	(Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtTrnRqV1;Lcom/ffusion/ffs/ofx/interfaces/TypeUserData;)Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtTrnRsV1;
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
    //   117: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: bipush 45
    //   123: aload_3
    //   124: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   127: goto +23 -> 150
    //   130: astore 10
    //   132: jsr +6 -> 138
    //   135: aload 10
    //   137: athrow
    //   138: astore 9
    //   140: aload_0
    //   141: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	161	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	161	1	paramTypePmtTrnRqV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1
    //   0	161	2	paramTypeUserData	com.ffusion.ffs.ofx.interfaces.TypeUserData
    //   8	137	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	143	5	localTypePmtTrnRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1
    //   61	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, com.ffusion.ffs.ofx.interfaces.TypeUserData paramTypeUserData)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 46
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 268	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processRecIntraSyncRqV1	(Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecIntraSyncRqV1;Lcom/ffusion/ffs/ofx/interfaces/TypeUserData;)Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecIntraSyncRsV1;
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
    //   117: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: bipush 46
    //   123: aload_3
    //   124: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   127: goto +23 -> 150
    //   130: astore 10
    //   132: jsr +6 -> 138
    //   135: aload 10
    //   137: athrow
    //   138: astore 9
    //   140: aload_0
    //   141: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	161	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	161	1	paramTypeRecIntraSyncRqV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1
    //   0	161	2	paramTypeUserData	com.ffusion.ffs.ofx.interfaces.TypeUserData
    //   8	137	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	143	5	localTypeRecIntraSyncRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1
    //   61	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, com.ffusion.ffs.ofx.interfaces.TypeUserData paramTypeUserData)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 47
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 272	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processRecIntraTrnRqV1	(Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecIntraTrnRqV1;Lcom/ffusion/ffs/ofx/interfaces/TypeUserData;)Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecIntraTrnRsV1;
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
    //   117: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: bipush 47
    //   123: aload_3
    //   124: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   127: goto +23 -> 150
    //   130: astore 10
    //   132: jsr +6 -> 138
    //   135: aload 10
    //   137: athrow
    //   138: astore 9
    //   140: aload_0
    //   141: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	161	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	161	1	paramTypeRecIntraTrnRqV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1
    //   0	161	2	paramTypeUserData	com.ffusion.ffs.ofx.interfaces.TypeUserData
    //   8	137	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	143	5	localTypeRecIntraTrnRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1
    //   61	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, com.ffusion.ffs.ofx.interfaces.TypeUserData paramTypeUserData)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 48
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 276	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processRecPmtSyncRqV1	(Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecPmtSyncRqV1;Lcom/ffusion/ffs/ofx/interfaces/TypeUserData;)Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecPmtSyncRsV1;
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
    //   117: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: bipush 48
    //   123: aload_3
    //   124: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   127: goto +23 -> 150
    //   130: astore 10
    //   132: jsr +6 -> 138
    //   135: aload 10
    //   137: athrow
    //   138: astore 9
    //   140: aload_0
    //   141: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	161	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	161	1	paramTypeRecPmtSyncRqV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1
    //   0	161	2	paramTypeUserData	com.ffusion.ffs.ofx.interfaces.TypeUserData
    //   8	137	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	143	5	localTypeRecPmtSyncRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1
    //   61	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, com.ffusion.ffs.ofx.interfaces.TypeUserData paramTypeUserData)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   49: aload_0
    //   50: bipush 49
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   58: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   61: astore 6
    //   63: aload 6
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 280	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processRecPmtTrnRqV1	(Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecPmtTrnRqV1;Lcom/ffusion/ffs/ofx/interfaces/TypeUserData;)Lcom/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecPmtTrnRsV1;
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
    //   117: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   120: aload_0
    //   121: bipush 49
    //   123: aload_3
    //   124: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   127: goto +23 -> 150
    //   130: astore 10
    //   132: jsr +6 -> 138
    //   135: aload 10
    //   137: athrow
    //   138: astore 9
    //   140: aload_0
    //   141: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	161	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	161	1	paramTypeRecPmtTrnRqV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1
    //   0	161	2	paramTypeUserData	com.ffusion.ffs.ofx.interfaces.TypeUserData
    //   8	137	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	41	4	arrayOfObject	java.lang.Object[]
    //   16	143	5	localTypeRecPmtTrnRsV1	com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1
    //   61	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public int activateCustomers(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 50
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 284	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:activateCustomers	([Ljava/lang/String;)I
    //   60: istore 4
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: iload 4
    //   150: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramArrayOfString	java.lang.String[]
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	i	int
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public int addConsumerCrossRef(com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 51
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 288	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:addConsumerCrossRef	([Lcom/ffusion/ffs/bpw/interfaces/ConsumerCrossRefInfo;)I
    //   60: istore 4
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: iload 4
    //   150: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramArrayOfConsumerCrossRefInfo	com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfo[]
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	i	int
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public int addCustomerBankInfo(com.ffusion.ffs.bpw.interfaces.CustomerBankInfo[] paramArrayOfCustomerBankInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 52
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 292	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:addCustomerBankInfo	([Lcom/ffusion/ffs/bpw/interfaces/CustomerBankInfo;)I
    //   60: istore 4
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: iload 4
    //   150: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramArrayOfCustomerBankInfo	com.ffusion.ffs.bpw.interfaces.CustomerBankInfo[]
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	i	int
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public int addCustomerProductAccessInfo(com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 53
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 296	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:addCustomerProductAccessInfo	([Lcom/ffusion/ffs/bpw/interfaces/CustomerProductAccessInfo;)I
    //   60: istore 4
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 53
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: iload 4
    //   150: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramArrayOfCustomerProductAccessInfo	com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfo[]
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	i	int
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public int addCustomers(com.ffusion.ffs.bpw.interfaces.CustomerInfo[] paramArrayOfCustomerInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 54
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 300	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:addCustomers	([Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;)I
    //   60: istore 4
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 54
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: iload 4
    //   150: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramArrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	i	int
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public int deactivateCustomers(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 55
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 303	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:deactivateCustomers	([Ljava/lang/String;)I
    //   60: istore 4
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 55
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: iload 4
    //   150: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramArrayOfString	java.lang.String[]
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	i	int
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public int deleteConsumerCrossRef(com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 56
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 306	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:deleteConsumerCrossRef	([Lcom/ffusion/ffs/bpw/interfaces/ConsumerCrossRefInfo;)I
    //   60: istore 4
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 56
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: iload 4
    //   150: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramArrayOfConsumerCrossRefInfo	com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfo[]
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	i	int
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public int deleteCustomerBankInfo(com.ffusion.ffs.bpw.interfaces.CustomerBankInfo[] paramArrayOfCustomerBankInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 57
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 309	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:deleteCustomerBankInfo	([Lcom/ffusion/ffs/bpw/interfaces/CustomerBankInfo;)I
    //   60: istore 4
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 57
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: iload 4
    //   150: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramArrayOfCustomerBankInfo	com.ffusion.ffs.bpw.interfaces.CustomerBankInfo[]
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	i	int
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public int deleteCustomerProductAccessInfo(com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 58
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 312	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:deleteCustomerProductAccessInfo	([Lcom/ffusion/ffs/bpw/interfaces/CustomerProductAccessInfo;)I
    //   60: istore 4
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 58
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: iload 4
    //   150: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramArrayOfCustomerProductAccessInfo	com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfo[]
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	i	int
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public int deleteCustomers(java.lang.String[] paramArrayOfString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 59
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 315	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:deleteCustomers	([Ljava/lang/String;)I
    //   60: istore 4
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 59
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: iload 4
    //   150: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramArrayOfString	java.lang.String[]
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	i	int
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public int deleteCustomers(java.lang.String[] paramArrayOfString, int paramInt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: iconst_0
    //   16: istore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   43: new 156	java/lang/Integer
    //   46: dup
    //   47: iload_2
    //   48: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   51: aastore
    //   52: aload_0
    //   53: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   56: aload_0
    //   57: bipush 60
    //   59: aload_3
    //   60: aload 4
    //   62: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   65: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   68: astore 6
    //   70: aload 6
    //   72: aload_1
    //   73: iload_2
    //   74: invokevirtual 318	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:deleteCustomers	([Ljava/lang/String;I)I
    //   77: istore 5
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
    //   124: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   127: aload_0
    //   128: bipush 60
    //   130: aload_3
    //   131: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   134: goto +23 -> 157
    //   137: astore 10
    //   139: jsr +6 -> 145
    //   142: aload 10
    //   144: athrow
    //   145: astore 9
    //   147: aload_0
    //   148: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   151: aload_3
    //   152: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   155: ret 9
    //   157: jsr -12 -> 145
    //   160: ret 7
    //   162: jsr -41 -> 121
    //   165: iload 5
    //   167: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	168	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	168	1	paramArrayOfString	java.lang.String[]
    //   0	168	2	paramInt	int
    //   8	144	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	48	4	arrayOfObject	java.lang.Object[]
    //   16	150	5	i	int
    //   68	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public int getSmartDate(int paramInt)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   21: aload_0
    //   22: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   25: ifeq +19 -> 44
    //   28: iconst_1
    //   29: anewarray 34	java/lang/Object
    //   32: astore_3
    //   33: aload_3
    //   34: iconst_0
    //   35: new 156	java/lang/Integer
    //   38: dup
    //   39: iload_1
    //   40: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   43: aastore
    //   44: aload_0
    //   45: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   48: aload_0
    //   49: bipush 61
    //   51: aload_2
    //   52: aload_3
    //   53: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   56: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   59: astore 5
    //   61: aload 5
    //   63: iload_1
    //   64: invokevirtual 322	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getSmartDate	(I)I
    //   67: istore 4
    //   69: goto +94 -> 163
    //   72: astore 5
    //   74: aload_2
    //   75: aload 5
    //   77: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   80: goto +83 -> 163
    //   83: astore 5
    //   85: aload_2
    //   86: aload 5
    //   88: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   91: goto +72 -> 163
    //   94: astore 5
    //   96: aload_2
    //   97: aload 5
    //   99: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   102: new 11	java/rmi/RemoteException
    //   105: dup
    //   106: ldc 53
    //   108: aload 5
    //   110: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   113: athrow
    //   114: astore 7
    //   116: jsr +6 -> 122
    //   119: aload 7
    //   121: athrow
    //   122: astore 6
    //   124: aload_0
    //   125: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   128: aload_0
    //   129: bipush 61
    //   131: aload_2
    //   132: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   135: goto +23 -> 158
    //   138: astore 9
    //   140: jsr +6 -> 146
    //   143: aload 9
    //   145: athrow
    //   146: astore 8
    //   148: aload_0
    //   149: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   152: aload_2
    //   153: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   156: ret 8
    //   158: jsr -12 -> 146
    //   161: ret 6
    //   163: jsr -41 -> 122
    //   166: iload 4
    //   168: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	169	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	169	1	paramInt	int
    //   8	145	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	40	3	arrayOfObject	java.lang.Object[]
    //   15	152	4	i	int
    //   59	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   72	4	5	localRemoteException	RemoteException
    //   83	4	5	localEJBException	javax.ejb.EJBException
    //   94	15	5	localThrowable	java.lang.Throwable
    //   122	1	6	localObject1	java.lang.Object
    //   114	6	7	localObject2	java.lang.Object
    //   146	1	8	localObject3	java.lang.Object
    //   138	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   17	72	72	java/rmi/RemoteException
    //   17	72	83	javax/ejb/EJBException
    //   17	72	94	java/lang/Throwable
    //   17	114	114	finally
    //   163	166	114	finally
    //   124	138	138	finally
    //   158	161	138	finally
  }
  
  /* Error */
  public int modifyCustomers(com.ffusion.ffs.bpw.interfaces.CustomerInfo[] paramArrayOfCustomerInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 62
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 325	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:modifyCustomers	([Lcom/ffusion/ffs/bpw/interfaces/CustomerInfo;)I
    //   60: istore 4
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 62
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   134: aload_2
    //   135: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   138: ret 8
    //   140: jsr -12 -> 128
    //   143: ret 6
    //   145: jsr -41 -> 104
    //   148: iload 4
    //   150: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramArrayOfCustomerInfo	com.ffusion.ffs.bpw.interfaces.CustomerInfo[]
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	i	int
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public java.lang.String addPayee(com.ffusion.ffs.bpw.interfaces.PayeeInfo paramPayeeInfo, int paramInt)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   43: new 156	java/lang/Integer
    //   46: dup
    //   47: iload_2
    //   48: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   51: aastore
    //   52: aload_0
    //   53: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   56: aload_0
    //   57: bipush 63
    //   59: aload_3
    //   60: aload 4
    //   62: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   65: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   68: astore 6
    //   70: aload 6
    //   72: aload_1
    //   73: iload_2
    //   74: invokevirtual 329	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:addPayee	(Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;I)Ljava/lang/String;
    //   77: astore 5
    //   79: goto +94 -> 173
    //   82: astore 6
    //   84: aload_3
    //   85: aload 6
    //   87: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   90: goto +83 -> 173
    //   93: astore 6
    //   95: aload_3
    //   96: aload 6
    //   98: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   101: goto +72 -> 173
    //   104: astore 6
    //   106: aload_3
    //   107: aload 6
    //   109: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   112: new 11	java/rmi/RemoteException
    //   115: dup
    //   116: ldc 53
    //   118: aload 6
    //   120: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   123: athrow
    //   124: astore 8
    //   126: jsr +6 -> 132
    //   129: aload 8
    //   131: athrow
    //   132: astore 7
    //   134: aload_0
    //   135: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   138: aload_0
    //   139: bipush 63
    //   141: aload_3
    //   142: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   145: goto +23 -> 168
    //   148: astore 10
    //   150: jsr +6 -> 156
    //   153: aload 10
    //   155: athrow
    //   156: astore 9
    //   158: aload_0
    //   159: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   162: aload_3
    //   163: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   166: ret 9
    //   168: jsr -12 -> 156
    //   171: ret 7
    //   173: jsr -41 -> 132
    //   176: aload 5
    //   178: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	179	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	179	1	paramPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo
    //   0	179	2	paramInt	int
    //   8	155	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	48	4	arrayOfObject	java.lang.Object[]
    //   16	161	5	str	java.lang.String
    //   68	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   82	4	6	localRemoteException	RemoteException
    //   93	4	6	localEJBException	javax.ejb.EJBException
    //   104	15	6	localThrowable	java.lang.Throwable
    //   132	1	7	localObject1	java.lang.Object
    //   124	6	8	localObject2	java.lang.Object
    //   156	1	9	localObject3	java.lang.Object
    //   148	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   18	82	82	java/rmi/RemoteException
    //   18	82	93	javax/ejb/EJBException
    //   18	82	104	java/lang/Throwable
    //   18	124	124	finally
    //   173	176	124	finally
    //   134	148	148	finally
    //   168	171	148	finally
  }
  
  /* Error */
  public java.lang.String addPayeeFromBackend(com.ffusion.ffs.bpw.interfaces.PayeeInfo paramPayeeInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 64
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 333	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:addPayeeFromBackend	(Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;)Ljava/lang/String;
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 64
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	str	java.lang.String
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public java.lang.String getPmtStatus(java.lang.String paramString)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   38: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   41: aload_0
    //   42: bipush 65
    //   44: aload_2
    //   45: aload_3
    //   46: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   49: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   52: astore 5
    //   54: aload 5
    //   56: aload_1
    //   57: invokevirtual 337	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPmtStatus	(Ljava/lang/String;)Ljava/lang/String;
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
    //   107: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   110: aload_0
    //   111: bipush 65
    //   113: aload_2
    //   114: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   117: goto +23 -> 140
    //   120: astore 9
    //   122: jsr +6 -> 128
    //   125: aload 9
    //   127: athrow
    //   128: astore 8
    //   130: aload_0
    //   131: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	151	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	151	1	paramString	java.lang.String
    //   8	127	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	33	3	arrayOfObject	java.lang.Object[]
    //   15	134	4	str	java.lang.String
    //   52	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  public java.lang.String[] getPayeeIDs(java.lang.String paramString)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 341	[Ljava/lang/String;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 66
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 343	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPayeeIDs	(Ljava/lang/String;)[Ljava/lang/String;
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
    //   121: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   124: aload_0
    //   125: bipush 66
    //   127: aload_2
    //   128: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   131: goto +23 -> 154
    //   134: astore 9
    //   136: jsr +6 -> 142
    //   139: aload 9
    //   141: athrow
    //   142: astore 8
    //   144: aload_0
    //   145: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	165	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	165	1	paramString	java.lang.String
    //   8	141	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	145	4	arrayOfString	java.lang.String[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   68	4	5	localRemoteException	RemoteException
    //   79	4	5	localEJBException	javax.ejb.EJBException
    //   90	15	5	localThrowable	java.lang.Throwable
    //   118	1	6	localObject1	java.lang.Object
    //   110	6	7	localObject2	java.lang.Object
    //   142	1	8	localObject3	java.lang.Object
    //   134	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	68	68	java/rmi/RemoteException
    //   20	68	79	javax/ejb/EJBException
    //   20	68	90	java/lang/Throwable
    //   20	110	110	finally
    //   159	162	110	finally
    //   120	134	134	finally
    //   154	157	134	finally
  }
  
  /* Error */
  public java.lang.String[] getPayeeNames(java.lang.String paramString)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aconst_null
    //   15: checkcast 341	[Ljava/lang/String;
    //   18: astore 4
    //   20: aload_0
    //   21: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   41: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   44: aload_0
    //   45: bipush 67
    //   47: aload_2
    //   48: aload_3
    //   49: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   52: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   55: astore 5
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 346	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPayeeNames	(Ljava/lang/String;)[Ljava/lang/String;
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
    //   121: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   124: aload_0
    //   125: bipush 67
    //   127: aload_2
    //   128: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   131: goto +23 -> 154
    //   134: astore 9
    //   136: jsr +6 -> 142
    //   139: aload 9
    //   141: athrow
    //   142: astore 8
    //   144: aload_0
    //   145: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	165	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	165	1	paramString	java.lang.String
    //   8	141	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	36	3	arrayOfObject	java.lang.Object[]
    //   18	145	4	arrayOfString	java.lang.String[]
    //   55	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   68	4	5	localRemoteException	RemoteException
    //   79	4	5	localEJBException	javax.ejb.EJBException
    //   90	15	5	localThrowable	java.lang.Throwable
    //   118	1	6	localObject1	java.lang.Object
    //   110	6	7	localObject2	java.lang.Object
    //   142	1	8	localObject3	java.lang.Object
    //   134	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   20	68	68	java/rmi/RemoteException
    //   20	68	79	javax/ejb/EJBException
    //   20	68	90	java/lang/Throwable
    //   20	110	110	finally
    //   159	162	110	finally
    //   120	134	134	finally
    //   154	157	134	finally
  }
  
  /* Error */
  public java.lang.String[] getPayeeNames(java.lang.String paramString, int paramInt)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aconst_null
    //   16: checkcast 341	[Ljava/lang/String;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   46: new 156	java/lang/Integer
    //   49: dup
    //   50: iload_2
    //   51: invokespecial 159	java/lang/Integer:<init>	(I)V
    //   54: aastore
    //   55: aload_0
    //   56: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   59: aload_0
    //   60: bipush 68
    //   62: aload_3
    //   63: aload 4
    //   65: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   68: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   71: astore 6
    //   73: aload 6
    //   75: aload_1
    //   76: iload_2
    //   77: invokevirtual 349	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:getPayeeNames	(Ljava/lang/String;I)[Ljava/lang/String;
    //   80: astore 5
    //   82: goto +94 -> 176
    //   85: astore 6
    //   87: aload_3
    //   88: aload 6
    //   90: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   93: goto +83 -> 176
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
    //   138: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   141: aload_0
    //   142: bipush 68
    //   144: aload_3
    //   145: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   148: goto +23 -> 171
    //   151: astore 10
    //   153: jsr +6 -> 159
    //   156: aload 10
    //   158: athrow
    //   159: astore 9
    //   161: aload_0
    //   162: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   0	182	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	182	1	paramString	java.lang.String
    //   0	182	2	paramInt	int
    //   8	158	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	51	4	arrayOfObject	java.lang.Object[]
    //   19	161	5	arrayOfString	java.lang.String[]
    //   71	3	6	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   85	4	6	localRemoteException	RemoteException
    //   96	4	6	localEJBException	javax.ejb.EJBException
    //   107	15	6	localThrowable	java.lang.Throwable
    //   135	1	7	localObject1	java.lang.Object
    //   127	6	8	localObject2	java.lang.Object
    //   159	1	9	localObject3	java.lang.Object
    //   151	6	10	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   21	85	85	java/rmi/RemoteException
    //   21	85	96	javax/ejb/EJBException
    //   21	85	107	java/lang/Throwable
    //   21	127	127	finally
    //   176	179	127	finally
    //   137	151	151	finally
    //   171	174	151	finally
  }
  
  /* Error */
  public void addPayeeRouteInfo(com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo paramPayeeRouteInfo)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   35: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: bipush 69
    //   41: aload_2
    //   42: aload_3
    //   43: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   46: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   49: astore 4
    //   51: aload 4
    //   53: aload_1
    //   54: invokevirtual 353	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:addPayeeRouteInfo	(Lcom/ffusion/ffs/bpw/interfaces/PayeeRouteInfo;)V
    //   57: goto +83 -> 140
    //   60: astore 4
    //   62: aload_2
    //   63: aload 4
    //   65: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   68: goto +72 -> 140
    //   71: astore 4
    //   73: aload_2
    //   74: aload 4
    //   76: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   79: new 11	java/rmi/RemoteException
    //   82: dup
    //   83: ldc 53
    //   85: aload 4
    //   87: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   90: athrow
    //   91: astore 6
    //   93: jsr +6 -> 99
    //   96: aload 6
    //   98: athrow
    //   99: astore 5
    //   101: aload_0
    //   102: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   105: aload_0
    //   106: bipush 69
    //   108: aload_2
    //   109: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   112: goto +23 -> 135
    //   115: astore 8
    //   117: jsr +6 -> 123
    //   120: aload 8
    //   122: athrow
    //   123: astore 7
    //   125: aload_0
    //   126: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   129: aload_2
    //   130: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   133: ret 7
    //   135: jsr -12 -> 123
    //   138: ret 5
    //   140: jsr -41 -> 99
    //   143: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	144	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	144	1	paramPayeeRouteInfo	com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo
    //   8	122	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	30	3	arrayOfObject	java.lang.Object[]
    //   49	3	4	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   60	4	4	localRemoteException	RemoteException
    //   71	15	4	localThrowable	java.lang.Throwable
    //   99	1	5	localObject1	java.lang.Object
    //   91	6	6	localObject2	java.lang.Object
    //   123	1	7	localObject3	java.lang.Object
    //   115	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	60	60	java/rmi/RemoteException
    //   14	60	71	java/lang/Throwable
    //   14	91	91	finally
    //   140	143	91	finally
    //   101	115	115	finally
    //   135	138	115	finally
  }
  
  /* Error */
  public void deletePayee(java.lang.String paramString)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   35: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: bipush 70
    //   41: aload_2
    //   42: aload_3
    //   43: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   46: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   49: astore 4
    //   51: aload 4
    //   53: aload_1
    //   54: invokevirtual 357	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:deletePayee	(Ljava/lang/String;)V
    //   57: goto +94 -> 151
    //   60: astore 4
    //   62: aload_2
    //   63: aload 4
    //   65: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   68: goto +83 -> 151
    //   71: astore 4
    //   73: aload_2
    //   74: aload 4
    //   76: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   79: goto +72 -> 151
    //   82: astore 4
    //   84: aload_2
    //   85: aload 4
    //   87: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   90: new 11	java/rmi/RemoteException
    //   93: dup
    //   94: ldc 53
    //   96: aload 4
    //   98: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   101: athrow
    //   102: astore 6
    //   104: jsr +6 -> 110
    //   107: aload 6
    //   109: athrow
    //   110: astore 5
    //   112: aload_0
    //   113: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   116: aload_0
    //   117: bipush 70
    //   119: aload_2
    //   120: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   123: goto +23 -> 146
    //   126: astore 8
    //   128: jsr +6 -> 134
    //   131: aload 8
    //   133: athrow
    //   134: astore 7
    //   136: aload_0
    //   137: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   140: aload_2
    //   141: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   144: ret 7
    //   146: jsr -12 -> 134
    //   149: ret 5
    //   151: jsr -41 -> 110
    //   154: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	155	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	155	1	paramString	java.lang.String
    //   8	133	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	30	3	arrayOfObject	java.lang.Object[]
    //   49	3	4	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   60	4	4	localRemoteException	RemoteException
    //   71	4	4	localEJBException	javax.ejb.EJBException
    //   82	15	4	localThrowable	java.lang.Throwable
    //   110	1	5	localObject1	java.lang.Object
    //   102	6	6	localObject2	java.lang.Object
    //   134	1	7	localObject3	java.lang.Object
    //   126	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	60	60	java/rmi/RemoteException
    //   14	60	71	javax/ejb/EJBException
    //   14	60	82	java/lang/Throwable
    //   14	102	102	finally
    //   151	154	102	finally
    //   112	126	126	finally
    //   146	149	126	finally
  }
  
  /* Error */
  public void deletePayees(java.lang.String[] paramArrayOfString)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   35: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: bipush 71
    //   41: aload_2
    //   42: aload_3
    //   43: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   46: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   49: astore 4
    //   51: aload 4
    //   53: aload_1
    //   54: invokevirtual 361	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:deletePayees	([Ljava/lang/String;)V
    //   57: goto +94 -> 151
    //   60: astore 4
    //   62: aload_2
    //   63: aload 4
    //   65: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   68: goto +83 -> 151
    //   71: astore 4
    //   73: aload_2
    //   74: aload 4
    //   76: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   79: goto +72 -> 151
    //   82: astore 4
    //   84: aload_2
    //   85: aload 4
    //   87: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   90: new 11	java/rmi/RemoteException
    //   93: dup
    //   94: ldc 53
    //   96: aload 4
    //   98: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   101: athrow
    //   102: astore 6
    //   104: jsr +6 -> 110
    //   107: aload 6
    //   109: athrow
    //   110: astore 5
    //   112: aload_0
    //   113: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   116: aload_0
    //   117: bipush 71
    //   119: aload_2
    //   120: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   123: goto +23 -> 146
    //   126: astore 8
    //   128: jsr +6 -> 134
    //   131: aload 8
    //   133: athrow
    //   134: astore 7
    //   136: aload_0
    //   137: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   140: aload_2
    //   141: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   144: ret 7
    //   146: jsr -12 -> 134
    //   149: ret 5
    //   151: jsr -41 -> 110
    //   154: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	155	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	155	1	paramArrayOfString	java.lang.String[]
    //   8	133	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	30	3	arrayOfObject	java.lang.Object[]
    //   49	3	4	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   60	4	4	localRemoteException	RemoteException
    //   71	4	4	localEJBException	javax.ejb.EJBException
    //   82	15	4	localThrowable	java.lang.Throwable
    //   110	1	5	localObject1	java.lang.Object
    //   102	6	6	localObject2	java.lang.Object
    //   134	1	7	localObject3	java.lang.Object
    //   126	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	60	60	java/rmi/RemoteException
    //   14	60	71	javax/ejb/EJBException
    //   14	60	82	java/lang/Throwable
    //   14	102	102	finally
    //   151	154	102	finally
    //   112	126	126	finally
    //   146	149	126	finally
  }
  
  /* Error */
  public void disconnect()
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_1
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_2
    //   14: aload_0
    //   15: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   18: aload_0
    //   19: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   22: ifeq +8 -> 30
    //   25: iconst_0
    //   26: anewarray 34	java/lang/Object
    //   29: astore_2
    //   30: aload_0
    //   31: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   34: aload_0
    //   35: bipush 72
    //   37: aload_1
    //   38: aload_2
    //   39: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   42: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   45: astore_3
    //   46: aload_3
    //   47: invokevirtual 364	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:disconnect	()V
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
    //   90: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   93: aload_0
    //   94: bipush 72
    //   96: aload_1
    //   97: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   100: goto +23 -> 123
    //   103: astore 7
    //   105: jsr +6 -> 111
    //   108: aload 7
    //   110: athrow
    //   111: astore 6
    //   113: aload_0
    //   114: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   117: aload_1
    //   118: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   121: ret 6
    //   123: jsr -12 -> 111
    //   126: ret 4
    //   128: jsr -41 -> 87
    //   131: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	132	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   8	110	1	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	26	2	arrayOfObject	java.lang.Object[]
    //   45	2	3	localOFX151BPWServicesBean	OFX151BPWServicesBean
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
  
  /* Error */
  public void processCustPayeeRslt(com.ffusion.ffs.bpw.interfaces.CustPayeeRslt[] paramArrayOfCustPayeeRslt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   35: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: bipush 73
    //   41: aload_2
    //   42: aload_3
    //   43: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   46: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   49: astore 4
    //   51: aload 4
    //   53: aload_1
    //   54: invokevirtual 368	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processCustPayeeRslt	([Lcom/ffusion/ffs/bpw/interfaces/CustPayeeRslt;)V
    //   57: goto +83 -> 140
    //   60: astore 4
    //   62: aload_2
    //   63: aload 4
    //   65: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   68: goto +72 -> 140
    //   71: astore 4
    //   73: aload_2
    //   74: aload 4
    //   76: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   79: new 11	java/rmi/RemoteException
    //   82: dup
    //   83: ldc 53
    //   85: aload 4
    //   87: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   90: athrow
    //   91: astore 6
    //   93: jsr +6 -> 99
    //   96: aload 6
    //   98: athrow
    //   99: astore 5
    //   101: aload_0
    //   102: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   105: aload_0
    //   106: bipush 73
    //   108: aload_2
    //   109: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   112: goto +23 -> 135
    //   115: astore 8
    //   117: jsr +6 -> 123
    //   120: aload 8
    //   122: athrow
    //   123: astore 7
    //   125: aload_0
    //   126: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   129: aload_2
    //   130: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   133: ret 7
    //   135: jsr -12 -> 123
    //   138: ret 5
    //   140: jsr -41 -> 99
    //   143: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	144	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	144	1	paramArrayOfCustPayeeRslt	com.ffusion.ffs.bpw.interfaces.CustPayeeRslt[]
    //   8	122	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	30	3	arrayOfObject	java.lang.Object[]
    //   49	3	4	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   60	4	4	localRemoteException	RemoteException
    //   71	15	4	localThrowable	java.lang.Throwable
    //   99	1	5	localObject1	java.lang.Object
    //   91	6	6	localObject2	java.lang.Object
    //   123	1	7	localObject3	java.lang.Object
    //   115	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	60	60	java/rmi/RemoteException
    //   14	60	71	java/lang/Throwable
    //   14	91	91	finally
    //   140	143	91	finally
    //   101	115	115	finally
    //   135	138	115	finally
  }
  
  /* Error */
  public void processFundAllocRslt(com.ffusion.ffs.bpw.interfaces.FundsAllocRslt[] paramArrayOfFundsAllocRslt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   35: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: bipush 74
    //   41: aload_2
    //   42: aload_3
    //   43: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   46: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   49: astore 4
    //   51: aload 4
    //   53: aload_1
    //   54: invokevirtual 372	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processFundAllocRslt	([Lcom/ffusion/ffs/bpw/interfaces/FundsAllocRslt;)V
    //   57: goto +83 -> 140
    //   60: astore 4
    //   62: aload_2
    //   63: aload 4
    //   65: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   68: goto +72 -> 140
    //   71: astore 4
    //   73: aload_2
    //   74: aload 4
    //   76: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   79: new 11	java/rmi/RemoteException
    //   82: dup
    //   83: ldc 53
    //   85: aload 4
    //   87: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   90: athrow
    //   91: astore 6
    //   93: jsr +6 -> 99
    //   96: aload 6
    //   98: athrow
    //   99: astore 5
    //   101: aload_0
    //   102: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   105: aload_0
    //   106: bipush 74
    //   108: aload_2
    //   109: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   112: goto +23 -> 135
    //   115: astore 8
    //   117: jsr +6 -> 123
    //   120: aload 8
    //   122: athrow
    //   123: astore 7
    //   125: aload_0
    //   126: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   129: aload_2
    //   130: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   133: ret 7
    //   135: jsr -12 -> 123
    //   138: ret 5
    //   140: jsr -41 -> 99
    //   143: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	144	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	144	1	paramArrayOfFundsAllocRslt	com.ffusion.ffs.bpw.interfaces.FundsAllocRslt[]
    //   8	122	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	30	3	arrayOfObject	java.lang.Object[]
    //   49	3	4	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   60	4	4	localRemoteException	RemoteException
    //   71	15	4	localThrowable	java.lang.Throwable
    //   99	1	5	localObject1	java.lang.Object
    //   91	6	6	localObject2	java.lang.Object
    //   123	1	7	localObject3	java.lang.Object
    //   115	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	60	60	java/rmi/RemoteException
    //   14	60	71	java/lang/Throwable
    //   14	91	91	finally
    //   140	143	91	finally
    //   101	115	115	finally
    //   135	138	115	finally
  }
  
  /* Error */
  public void processFundRevertRslt(com.ffusion.ffs.bpw.interfaces.FundsAllocRslt[] paramArrayOfFundsAllocRslt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   35: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: bipush 75
    //   41: aload_2
    //   42: aload_3
    //   43: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   46: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   49: astore 4
    //   51: aload 4
    //   53: aload_1
    //   54: invokevirtual 375	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processFundRevertRslt	([Lcom/ffusion/ffs/bpw/interfaces/FundsAllocRslt;)V
    //   57: goto +83 -> 140
    //   60: astore 4
    //   62: aload_2
    //   63: aload 4
    //   65: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   68: goto +72 -> 140
    //   71: astore 4
    //   73: aload_2
    //   74: aload 4
    //   76: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   79: new 11	java/rmi/RemoteException
    //   82: dup
    //   83: ldc 53
    //   85: aload 4
    //   87: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   90: athrow
    //   91: astore 6
    //   93: jsr +6 -> 99
    //   96: aload 6
    //   98: athrow
    //   99: astore 5
    //   101: aload_0
    //   102: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   105: aload_0
    //   106: bipush 75
    //   108: aload_2
    //   109: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   112: goto +23 -> 135
    //   115: astore 8
    //   117: jsr +6 -> 123
    //   120: aload 8
    //   122: athrow
    //   123: astore 7
    //   125: aload_0
    //   126: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   129: aload_2
    //   130: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   133: ret 7
    //   135: jsr -12 -> 123
    //   138: ret 5
    //   140: jsr -41 -> 99
    //   143: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	144	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	144	1	paramArrayOfFundsAllocRslt	com.ffusion.ffs.bpw.interfaces.FundsAllocRslt[]
    //   8	122	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	30	3	arrayOfObject	java.lang.Object[]
    //   49	3	4	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   60	4	4	localRemoteException	RemoteException
    //   71	15	4	localThrowable	java.lang.Throwable
    //   99	1	5	localObject1	java.lang.Object
    //   91	6	6	localObject2	java.lang.Object
    //   123	1	7	localObject3	java.lang.Object
    //   115	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	60	60	java/rmi/RemoteException
    //   14	60	71	java/lang/Throwable
    //   14	91	91	finally
    //   140	143	91	finally
    //   101	115	115	finally
    //   135	138	115	finally
  }
  
  /* Error */
  public void processIntraTrnRslt(com.ffusion.ffs.bpw.interfaces.IntraTrnRslt[] paramArrayOfIntraTrnRslt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   35: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: bipush 76
    //   41: aload_2
    //   42: aload_3
    //   43: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   46: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   49: astore 4
    //   51: aload 4
    //   53: aload_1
    //   54: invokevirtual 379	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processIntraTrnRslt	([Lcom/ffusion/ffs/bpw/interfaces/IntraTrnRslt;)V
    //   57: goto +83 -> 140
    //   60: astore 4
    //   62: aload_2
    //   63: aload 4
    //   65: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   68: goto +72 -> 140
    //   71: astore 4
    //   73: aload_2
    //   74: aload 4
    //   76: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   79: new 11	java/rmi/RemoteException
    //   82: dup
    //   83: ldc 53
    //   85: aload 4
    //   87: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   90: athrow
    //   91: astore 6
    //   93: jsr +6 -> 99
    //   96: aload 6
    //   98: athrow
    //   99: astore 5
    //   101: aload_0
    //   102: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   105: aload_0
    //   106: bipush 76
    //   108: aload_2
    //   109: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   112: goto +23 -> 135
    //   115: astore 8
    //   117: jsr +6 -> 123
    //   120: aload 8
    //   122: athrow
    //   123: astore 7
    //   125: aload_0
    //   126: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   129: aload_2
    //   130: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   133: ret 7
    //   135: jsr -12 -> 123
    //   138: ret 5
    //   140: jsr -41 -> 99
    //   143: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	144	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	144	1	paramArrayOfIntraTrnRslt	com.ffusion.ffs.bpw.interfaces.IntraTrnRslt[]
    //   8	122	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	30	3	arrayOfObject	java.lang.Object[]
    //   49	3	4	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   60	4	4	localRemoteException	RemoteException
    //   71	15	4	localThrowable	java.lang.Throwable
    //   99	1	5	localObject1	java.lang.Object
    //   91	6	6	localObject2	java.lang.Object
    //   123	1	7	localObject3	java.lang.Object
    //   115	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	60	60	java/rmi/RemoteException
    //   14	60	71	java/lang/Throwable
    //   14	91	91	finally
    //   140	143	91	finally
    //   101	115	115	finally
    //   135	138	115	finally
  }
  
  /* Error */
  public void processPayeeRslt(com.ffusion.ffs.bpw.interfaces.PayeeRslt[] paramArrayOfPayeeRslt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   35: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: bipush 77
    //   41: aload_2
    //   42: aload_3
    //   43: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   46: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   49: astore 4
    //   51: aload 4
    //   53: aload_1
    //   54: invokevirtual 383	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processPayeeRslt	([Lcom/ffusion/ffs/bpw/interfaces/PayeeRslt;)V
    //   57: goto +83 -> 140
    //   60: astore 4
    //   62: aload_2
    //   63: aload 4
    //   65: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   68: goto +72 -> 140
    //   71: astore 4
    //   73: aload_2
    //   74: aload 4
    //   76: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   79: new 11	java/rmi/RemoteException
    //   82: dup
    //   83: ldc 53
    //   85: aload 4
    //   87: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   90: athrow
    //   91: astore 6
    //   93: jsr +6 -> 99
    //   96: aload 6
    //   98: athrow
    //   99: astore 5
    //   101: aload_0
    //   102: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   105: aload_0
    //   106: bipush 77
    //   108: aload_2
    //   109: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   112: goto +23 -> 135
    //   115: astore 8
    //   117: jsr +6 -> 123
    //   120: aload 8
    //   122: athrow
    //   123: astore 7
    //   125: aload_0
    //   126: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   129: aload_2
    //   130: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   133: ret 7
    //   135: jsr -12 -> 123
    //   138: ret 5
    //   140: jsr -41 -> 99
    //   143: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	144	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	144	1	paramArrayOfPayeeRslt	com.ffusion.ffs.bpw.interfaces.PayeeRslt[]
    //   8	122	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	30	3	arrayOfObject	java.lang.Object[]
    //   49	3	4	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   60	4	4	localRemoteException	RemoteException
    //   71	15	4	localThrowable	java.lang.Throwable
    //   99	1	5	localObject1	java.lang.Object
    //   91	6	6	localObject2	java.lang.Object
    //   123	1	7	localObject3	java.lang.Object
    //   115	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	60	60	java/rmi/RemoteException
    //   14	60	71	java/lang/Throwable
    //   14	91	91	finally
    //   140	143	91	finally
    //   101	115	115	finally
    //   135	138	115	finally
  }
  
  /* Error */
  public void processPmtTrnRslt(com.ffusion.ffs.bpw.interfaces.PmtTrnRslt[] paramArrayOfPmtTrnRslt)
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
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
    //   35: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: bipush 78
    //   41: aload_2
    //   42: aload_3
    //   43: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   46: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   49: astore 4
    //   51: aload 4
    //   53: aload_1
    //   54: invokevirtual 387	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:processPmtTrnRslt	([Lcom/ffusion/ffs/bpw/interfaces/PmtTrnRslt;)V
    //   57: goto +83 -> 140
    //   60: astore 4
    //   62: aload_2
    //   63: aload 4
    //   65: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   68: goto +72 -> 140
    //   71: astore 4
    //   73: aload_2
    //   74: aload 4
    //   76: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   79: new 11	java/rmi/RemoteException
    //   82: dup
    //   83: ldc 53
    //   85: aload 4
    //   87: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   90: athrow
    //   91: astore 6
    //   93: jsr +6 -> 99
    //   96: aload 6
    //   98: athrow
    //   99: astore 5
    //   101: aload_0
    //   102: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   105: aload_0
    //   106: bipush 78
    //   108: aload_2
    //   109: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   112: goto +23 -> 135
    //   115: astore 8
    //   117: jsr +6 -> 123
    //   120: aload 8
    //   122: athrow
    //   123: astore 7
    //   125: aload_0
    //   126: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   129: aload_2
    //   130: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   133: ret 7
    //   135: jsr -12 -> 123
    //   138: ret 5
    //   140: jsr -41 -> 99
    //   143: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	144	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	144	1	paramArrayOfPmtTrnRslt	com.ffusion.ffs.bpw.interfaces.PmtTrnRslt[]
    //   8	122	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	30	3	arrayOfObject	java.lang.Object[]
    //   49	3	4	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   60	4	4	localRemoteException	RemoteException
    //   71	15	4	localThrowable	java.lang.Throwable
    //   99	1	5	localObject1	java.lang.Object
    //   91	6	6	localObject2	java.lang.Object
    //   123	1	7	localObject3	java.lang.Object
    //   115	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	60	60	java/rmi/RemoteException
    //   14	60	71	java/lang/Throwable
    //   14	91	91	finally
    //   140	143	91	finally
    //   101	115	115	finally
    //   135	138	115	finally
  }
  
  /* Error */
  public void setPayeeStatus(java.lang.String paramString1, java.lang.String paramString2)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aload_0
    //   16: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   19: aload_0
    //   20: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   23: ifeq +19 -> 42
    //   26: iconst_2
    //   27: anewarray 34	java/lang/Object
    //   30: astore 4
    //   32: aload 4
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload 4
    //   39: iconst_1
    //   40: aload_2
    //   41: aastore
    //   42: aload_0
    //   43: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   46: aload_0
    //   47: bipush 79
    //   49: aload_3
    //   50: aload 4
    //   52: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   55: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   58: astore 5
    //   60: aload 5
    //   62: aload_1
    //   63: aload_2
    //   64: invokevirtual 391	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:setPayeeStatus	(Ljava/lang/String;Ljava/lang/String;)V
    //   67: goto +94 -> 161
    //   70: astore 5
    //   72: aload_3
    //   73: aload 5
    //   75: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   78: goto +83 -> 161
    //   81: astore 5
    //   83: aload_3
    //   84: aload 5
    //   86: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   89: goto +72 -> 161
    //   92: astore 5
    //   94: aload_3
    //   95: aload 5
    //   97: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   100: new 11	java/rmi/RemoteException
    //   103: dup
    //   104: ldc 53
    //   106: aload 5
    //   108: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   111: athrow
    //   112: astore 7
    //   114: jsr +6 -> 120
    //   117: aload 7
    //   119: athrow
    //   120: astore 6
    //   122: aload_0
    //   123: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   126: aload_0
    //   127: bipush 79
    //   129: aload_3
    //   130: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   133: goto +23 -> 156
    //   136: astore 9
    //   138: jsr +6 -> 144
    //   141: aload 9
    //   143: athrow
    //   144: astore 8
    //   146: aload_0
    //   147: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   150: aload_3
    //   151: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   154: ret 8
    //   156: jsr -12 -> 144
    //   159: ret 6
    //   161: jsr -41 -> 120
    //   164: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	165	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	165	1	paramString1	java.lang.String
    //   0	165	2	paramString2	java.lang.String
    //   8	143	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	38	4	arrayOfObject	java.lang.Object[]
    //   58	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   70	4	5	localRemoteException	RemoteException
    //   81	4	5	localEJBException	javax.ejb.EJBException
    //   92	15	5	localThrowable	java.lang.Throwable
    //   120	1	6	localObject1	java.lang.Object
    //   112	6	7	localObject2	java.lang.Object
    //   144	1	8	localObject3	java.lang.Object
    //   136	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   15	70	70	java/rmi/RemoteException
    //   15	70	81	javax/ejb/EJBException
    //   15	70	92	java/lang/Throwable
    //   15	112	112	finally
    //   161	164	112	finally
    //   122	136	136	finally
    //   156	159	136	finally
  }
  
  /* Error */
  public void updatePayee(com.ffusion.ffs.bpw.interfaces.PayeeInfo paramPayeeInfo, com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo paramPayeeRouteInfo)
    throws RemoteException, javax.ejb.EJBException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 26	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_3
    //   9: aconst_null
    //   10: checkcast 28	[Ljava/lang/Object;
    //   13: astore 4
    //   15: aload_0
    //   16: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   19: aload_0
    //   20: invokevirtual 32	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   23: ifeq +19 -> 42
    //   26: iconst_2
    //   27: anewarray 34	java/lang/Object
    //   30: astore 4
    //   32: aload 4
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload 4
    //   39: iconst_1
    //   40: aload_2
    //   41: aastore
    //   42: aload_0
    //   43: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   46: aload_0
    //   47: bipush 80
    //   49: aload_3
    //   50: aload 4
    //   52: invokevirtual 38	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   55: checkcast 40	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean
    //   58: astore 5
    //   60: aload 5
    //   62: aload_1
    //   63: aload_2
    //   64: invokevirtual 394	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesBean:updatePayee	(Lcom/ffusion/ffs/bpw/interfaces/PayeeInfo;Lcom/ffusion/ffs/bpw/interfaces/PayeeRouteInfo;)V
    //   67: goto +94 -> 161
    //   70: astore 5
    //   72: aload_3
    //   73: aload 5
    //   75: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   78: goto +83 -> 161
    //   81: astore 5
    //   83: aload_3
    //   84: aload 5
    //   86: invokevirtual 48	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   89: goto +72 -> 161
    //   92: astore 5
    //   94: aload_3
    //   95: aload 5
    //   97: invokevirtual 51	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   100: new 11	java/rmi/RemoteException
    //   103: dup
    //   104: ldc 53
    //   106: aload 5
    //   108: invokespecial 56	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   111: athrow
    //   112: astore 7
    //   114: jsr +6 -> 120
    //   117: aload 7
    //   119: athrow
    //   120: astore 6
    //   122: aload_0
    //   123: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   126: aload_0
    //   127: bipush 80
    //   129: aload_3
    //   130: invokevirtual 60	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   133: goto +23 -> 156
    //   136: astore 9
    //   138: jsr +6 -> 144
    //   141: aload 9
    //   143: athrow
    //   144: astore 8
    //   146: aload_0
    //   147: getfield 20	com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/EJSRemoteStatelessIOFX151BPWServices_79a24b5a:container	Lcom/ibm/ejs/container/EJSContainer;
    //   150: aload_3
    //   151: invokevirtual 64	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   154: ret 8
    //   156: jsr -12 -> 144
    //   159: ret 6
    //   161: jsr -41 -> 120
    //   164: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	165	0	this	EJSRemoteStatelessIOFX151BPWServices_79a24b5a
    //   0	165	1	paramPayeeInfo	com.ffusion.ffs.bpw.interfaces.PayeeInfo
    //   0	165	2	paramPayeeRouteInfo	com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo
    //   8	143	3	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	38	4	arrayOfObject	java.lang.Object[]
    //   58	3	5	localOFX151BPWServicesBean	OFX151BPWServicesBean
    //   70	4	5	localRemoteException	RemoteException
    //   81	4	5	localEJBException	javax.ejb.EJBException
    //   92	15	5	localThrowable	java.lang.Throwable
    //   120	1	6	localObject1	java.lang.Object
    //   112	6	7	localObject2	java.lang.Object
    //   144	1	8	localObject3	java.lang.Object
    //   136	6	9	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   15	70	70	java/rmi/RemoteException
    //   15	70	81	javax/ejb/EJBException
    //   15	70	92	java/lang/Throwable
    //   15	112	112	finally
    //   161	164	112	finally
    //   122	136	136	finally
    //   156	159	136	finally
  }
}


/* Location:           D:\drops\jd\jars\Deployed_OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.EJSRemoteStatelessIOFX151BPWServices_79a24b5a
 * JD-Core Version:    0.7.0.1
 */