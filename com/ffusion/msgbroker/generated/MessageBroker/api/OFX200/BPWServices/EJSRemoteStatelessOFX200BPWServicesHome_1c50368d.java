package com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices;

import com.ibm.ejs.container.EJSWrapper;
import java.rmi.RemoteException;

public class EJSRemoteStatelessOFX200BPWServicesHome_1c50368d
  extends EJSWrapper
  implements OFX200BPWServicesHome
{
  public EJSRemoteStatelessOFX200BPWServicesHome_1c50368d()
    throws RemoteException
  {}
  
  /* Error */
  public IOFX200BPWServices create()
    throws javax.ejb.CreateException, RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 28	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_1
    //   9: aconst_null
    //   10: checkcast 30	[Ljava/lang/Object;
    //   13: astore_2
    //   14: aconst_null
    //   15: astore_3
    //   16: aload_0
    //   17: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   20: aload_0
    //   21: invokevirtual 34	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   24: ifeq +8 -> 32
    //   27: iconst_0
    //   28: anewarray 36	java/lang/Object
    //   31: astore_2
    //   32: aload_0
    //   33: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   36: aload_0
    //   37: iconst_0
    //   38: aload_1
    //   39: aload_2
    //   40: invokevirtual 40	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   43: checkcast 42	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSStatelessOFX200BPWServicesHomeBean_1c50368d
    //   46: astore 4
    //   48: aload 4
    //   50: invokevirtual 44	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSStatelessOFX200BPWServicesHomeBean_1c50368d:create	()Lcom/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/IOFX200BPWServices;
    //   53: astore_3
    //   54: goto +93 -> 147
    //   57: astore 4
    //   59: aload_1
    //   60: aload 4
    //   62: invokevirtual 50	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   65: aload 4
    //   67: athrow
    //   68: astore 4
    //   70: aload_1
    //   71: aload 4
    //   73: invokevirtual 53	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   76: goto +71 -> 147
    //   79: astore 4
    //   81: aload_1
    //   82: aload 4
    //   84: invokevirtual 56	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   87: new 11	java/rmi/RemoteException
    //   90: dup
    //   91: ldc 58
    //   93: aload 4
    //   95: invokespecial 61	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   98: athrow
    //   99: astore 6
    //   101: jsr +6 -> 107
    //   104: aload 6
    //   106: athrow
    //   107: astore 5
    //   109: aload_0
    //   110: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   113: aload_0
    //   114: iconst_0
    //   115: aload_1
    //   116: invokevirtual 65	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   119: goto +23 -> 142
    //   122: astore 8
    //   124: jsr +6 -> 130
    //   127: aload 8
    //   129: athrow
    //   130: astore 7
    //   132: aload_0
    //   133: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   136: aload_1
    //   137: invokevirtual 69	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   140: ret 7
    //   142: jsr -12 -> 130
    //   145: ret 5
    //   147: jsr -40 -> 107
    //   150: aload_3
    //   151: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	152	0	this	EJSRemoteStatelessOFX200BPWServicesHome_1c50368d
    //   8	129	1	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	27	2	arrayOfObject	java.lang.Object[]
    //   15	136	3	localIOFX200BPWServices	IOFX200BPWServices
    //   46	3	4	localEJSStatelessOFX200BPWServicesHomeBean_1c50368d	EJSStatelessOFX200BPWServicesHomeBean_1c50368d
    //   57	9	4	localCreateException	javax.ejb.CreateException
    //   68	4	4	localRemoteException	RemoteException
    //   79	15	4	localThrowable	java.lang.Throwable
    //   107	1	5	localObject1	java.lang.Object
    //   99	6	6	localObject2	java.lang.Object
    //   130	1	7	localObject3	java.lang.Object
    //   122	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   16	57	57	javax/ejb/CreateException
    //   16	57	68	java/rmi/RemoteException
    //   16	57	79	java/lang/Throwable
    //   16	99	99	finally
    //   147	150	99	finally
    //   109	122	122	finally
    //   142	145	122	finally
  }
  
  /* Error */
  public javax.ejb.EJBMetaData getEJBMetaData()
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 28	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_1
    //   9: aconst_null
    //   10: checkcast 30	[Ljava/lang/Object;
    //   13: astore_2
    //   14: aconst_null
    //   15: astore_3
    //   16: aload_0
    //   17: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   20: aload_0
    //   21: invokevirtual 34	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   24: ifeq +8 -> 32
    //   27: iconst_0
    //   28: anewarray 36	java/lang/Object
    //   31: astore_2
    //   32: aload_0
    //   33: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   36: aload_0
    //   37: iconst_1
    //   38: aload_1
    //   39: aload_2
    //   40: invokevirtual 40	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   43: checkcast 42	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSStatelessOFX200BPWServicesHomeBean_1c50368d
    //   46: astore 4
    //   48: aload 4
    //   50: invokevirtual 75	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSStatelessOFX200BPWServicesHomeBean_1c50368d:getEJBMetaData	()Ljavax/ejb/EJBMetaData;
    //   53: astore_3
    //   54: goto +82 -> 136
    //   57: astore 4
    //   59: aload_1
    //   60: aload 4
    //   62: invokevirtual 53	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   65: goto +71 -> 136
    //   68: astore 4
    //   70: aload_1
    //   71: aload 4
    //   73: invokevirtual 56	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   76: new 11	java/rmi/RemoteException
    //   79: dup
    //   80: ldc 58
    //   82: aload 4
    //   84: invokespecial 61	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   87: athrow
    //   88: astore 6
    //   90: jsr +6 -> 96
    //   93: aload 6
    //   95: athrow
    //   96: astore 5
    //   98: aload_0
    //   99: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   102: aload_0
    //   103: iconst_1
    //   104: aload_1
    //   105: invokevirtual 65	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   108: goto +23 -> 131
    //   111: astore 8
    //   113: jsr +6 -> 119
    //   116: aload 8
    //   118: athrow
    //   119: astore 7
    //   121: aload_0
    //   122: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   125: aload_1
    //   126: invokevirtual 69	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   129: ret 7
    //   131: jsr -12 -> 119
    //   134: ret 5
    //   136: jsr -40 -> 96
    //   139: aload_3
    //   140: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	141	0	this	EJSRemoteStatelessOFX200BPWServicesHome_1c50368d
    //   8	118	1	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	27	2	arrayOfObject	java.lang.Object[]
    //   15	125	3	localEJBMetaData	javax.ejb.EJBMetaData
    //   46	3	4	localEJSStatelessOFX200BPWServicesHomeBean_1c50368d	EJSStatelessOFX200BPWServicesHomeBean_1c50368d
    //   57	4	4	localRemoteException	RemoteException
    //   68	15	4	localThrowable	java.lang.Throwable
    //   96	1	5	localObject1	java.lang.Object
    //   88	6	6	localObject2	java.lang.Object
    //   119	1	7	localObject3	java.lang.Object
    //   111	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   16	57	57	java/rmi/RemoteException
    //   16	57	68	java/lang/Throwable
    //   16	88	88	finally
    //   136	139	88	finally
    //   98	111	111	finally
    //   131	134	111	finally
  }
  
  /* Error */
  public javax.ejb.HomeHandle getHomeHandle()
    throws RemoteException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 28	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_1
    //   9: aconst_null
    //   10: checkcast 30	[Ljava/lang/Object;
    //   13: astore_2
    //   14: aconst_null
    //   15: astore_3
    //   16: aload_0
    //   17: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   20: aload_0
    //   21: invokevirtual 34	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   24: ifeq +8 -> 32
    //   27: iconst_0
    //   28: anewarray 36	java/lang/Object
    //   31: astore_2
    //   32: aload_0
    //   33: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   36: aload_0
    //   37: iconst_2
    //   38: aload_1
    //   39: aload_2
    //   40: invokevirtual 40	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   43: checkcast 42	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSStatelessOFX200BPWServicesHomeBean_1c50368d
    //   46: astore 4
    //   48: aload 4
    //   50: invokevirtual 79	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSStatelessOFX200BPWServicesHomeBean_1c50368d:getHomeHandle	()Ljavax/ejb/HomeHandle;
    //   53: astore_3
    //   54: goto +82 -> 136
    //   57: astore 4
    //   59: aload_1
    //   60: aload 4
    //   62: invokevirtual 53	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   65: goto +71 -> 136
    //   68: astore 4
    //   70: aload_1
    //   71: aload 4
    //   73: invokevirtual 56	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   76: new 11	java/rmi/RemoteException
    //   79: dup
    //   80: ldc 58
    //   82: aload 4
    //   84: invokespecial 61	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   87: athrow
    //   88: astore 6
    //   90: jsr +6 -> 96
    //   93: aload 6
    //   95: athrow
    //   96: astore 5
    //   98: aload_0
    //   99: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   102: aload_0
    //   103: iconst_2
    //   104: aload_1
    //   105: invokevirtual 65	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   108: goto +23 -> 131
    //   111: astore 8
    //   113: jsr +6 -> 119
    //   116: aload 8
    //   118: athrow
    //   119: astore 7
    //   121: aload_0
    //   122: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   125: aload_1
    //   126: invokevirtual 69	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   129: ret 7
    //   131: jsr -12 -> 119
    //   134: ret 5
    //   136: jsr -40 -> 96
    //   139: aload_3
    //   140: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	141	0	this	EJSRemoteStatelessOFX200BPWServicesHome_1c50368d
    //   8	118	1	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	27	2	arrayOfObject	java.lang.Object[]
    //   15	125	3	localHomeHandle	javax.ejb.HomeHandle
    //   46	3	4	localEJSStatelessOFX200BPWServicesHomeBean_1c50368d	EJSStatelessOFX200BPWServicesHomeBean_1c50368d
    //   57	4	4	localRemoteException	RemoteException
    //   68	15	4	localThrowable	java.lang.Throwable
    //   96	1	5	localObject1	java.lang.Object
    //   88	6	6	localObject2	java.lang.Object
    //   119	1	7	localObject3	java.lang.Object
    //   111	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   16	57	57	java/rmi/RemoteException
    //   16	57	68	java/lang/Throwable
    //   16	88	88	finally
    //   136	139	88	finally
    //   98	111	111	finally
    //   131	134	111	finally
  }
  
  /* Error */
  public void remove(java.lang.Object paramObject)
    throws RemoteException, javax.ejb.RemoveException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 28	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 30	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   18: aload_0
    //   19: invokevirtual 34	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   22: ifeq +12 -> 34
    //   25: iconst_1
    //   26: anewarray 36	java/lang/Object
    //   29: astore_3
    //   30: aload_3
    //   31: iconst_0
    //   32: aload_1
    //   33: aastore
    //   34: aload_0
    //   35: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: iconst_3
    //   40: aload_2
    //   41: aload_3
    //   42: invokevirtual 40	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   45: checkcast 42	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSStatelessOFX200BPWServicesHomeBean_1c50368d
    //   48: astore 4
    //   50: aload 4
    //   52: aload_1
    //   53: invokevirtual 85	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSStatelessOFX200BPWServicesHomeBean_1c50368d:remove	(Ljava/lang/Object;)V
    //   56: goto +93 -> 149
    //   59: astore 4
    //   61: aload_2
    //   62: aload 4
    //   64: invokevirtual 53	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   67: goto +82 -> 149
    //   70: astore 4
    //   72: aload_2
    //   73: aload 4
    //   75: invokevirtual 50	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   78: aload 4
    //   80: athrow
    //   81: astore 4
    //   83: aload_2
    //   84: aload 4
    //   86: invokevirtual 56	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   89: new 11	java/rmi/RemoteException
    //   92: dup
    //   93: ldc 58
    //   95: aload 4
    //   97: invokespecial 61	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   100: athrow
    //   101: astore 6
    //   103: jsr +6 -> 109
    //   106: aload 6
    //   108: athrow
    //   109: astore 5
    //   111: aload_0
    //   112: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   115: aload_0
    //   116: iconst_3
    //   117: aload_2
    //   118: invokevirtual 65	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   121: goto +23 -> 144
    //   124: astore 8
    //   126: jsr +6 -> 132
    //   129: aload 8
    //   131: athrow
    //   132: astore 7
    //   134: aload_0
    //   135: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   138: aload_2
    //   139: invokevirtual 69	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   142: ret 7
    //   144: jsr -12 -> 132
    //   147: ret 5
    //   149: jsr -40 -> 109
    //   152: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessOFX200BPWServicesHome_1c50368d
    //   0	153	1	paramObject	java.lang.Object
    //   8	131	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	29	3	arrayOfObject	java.lang.Object[]
    //   48	3	4	localEJSStatelessOFX200BPWServicesHomeBean_1c50368d	EJSStatelessOFX200BPWServicesHomeBean_1c50368d
    //   59	4	4	localRemoteException	RemoteException
    //   70	9	4	localRemoveException	javax.ejb.RemoveException
    //   81	15	4	localThrowable	java.lang.Throwable
    //   109	1	5	localObject1	java.lang.Object
    //   101	6	6	localObject2	java.lang.Object
    //   132	1	7	localObject3	java.lang.Object
    //   124	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	59	59	java/rmi/RemoteException
    //   14	59	70	javax/ejb/RemoveException
    //   14	59	81	java/lang/Throwable
    //   14	101	101	finally
    //   149	152	101	finally
    //   111	124	124	finally
    //   144	147	124	finally
  }
  
  /* Error */
  public void remove(javax.ejb.Handle paramHandle)
    throws RemoteException, javax.ejb.RemoveException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   4: aload_0
    //   5: invokevirtual 28	com/ibm/ejs/container/EJSContainer:getEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSWrapperBase;)Lcom/ibm/ejs/container/EJSDeployedSupport;
    //   8: astore_2
    //   9: aconst_null
    //   10: checkcast 30	[Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   18: aload_0
    //   19: invokevirtual 34	com/ibm/ejs/container/EJSContainer:doesJaccNeedsEJBArguments	(Lcom/ibm/ejs/container/EJSWrapperBase;)Z
    //   22: ifeq +12 -> 34
    //   25: iconst_1
    //   26: anewarray 36	java/lang/Object
    //   29: astore_3
    //   30: aload_3
    //   31: iconst_0
    //   32: aload_1
    //   33: aastore
    //   34: aload_0
    //   35: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   38: aload_0
    //   39: iconst_4
    //   40: aload_2
    //   41: aload_3
    //   42: invokevirtual 40	com/ibm/ejs/container/EJSContainer:preInvoke	(Lcom/ibm/ejs/container/EJSWrapperBase;ILcom/ibm/ejs/container/EJSDeployedSupport;[Ljava/lang/Object;)Ljavax/ejb/EnterpriseBean;
    //   45: checkcast 42	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSStatelessOFX200BPWServicesHomeBean_1c50368d
    //   48: astore 4
    //   50: aload 4
    //   52: aload_1
    //   53: invokevirtual 88	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSStatelessOFX200BPWServicesHomeBean_1c50368d:remove	(Ljavax/ejb/Handle;)V
    //   56: goto +93 -> 149
    //   59: astore 4
    //   61: aload_2
    //   62: aload 4
    //   64: invokevirtual 53	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Exception;)V
    //   67: goto +82 -> 149
    //   70: astore 4
    //   72: aload_2
    //   73: aload 4
    //   75: invokevirtual 50	com/ibm/ejs/container/EJSDeployedSupport:setCheckedException	(Ljava/lang/Exception;)V
    //   78: aload 4
    //   80: athrow
    //   81: astore 4
    //   83: aload_2
    //   84: aload 4
    //   86: invokevirtual 56	com/ibm/ejs/container/EJSDeployedSupport:setUncheckedException	(Ljava/lang/Throwable;)V
    //   89: new 11	java/rmi/RemoteException
    //   92: dup
    //   93: ldc 58
    //   95: aload 4
    //   97: invokespecial 61	java/rmi/RemoteException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   100: athrow
    //   101: astore 6
    //   103: jsr +6 -> 109
    //   106: aload 6
    //   108: athrow
    //   109: astore 5
    //   111: aload_0
    //   112: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   115: aload_0
    //   116: iconst_4
    //   117: aload_2
    //   118: invokevirtual 65	com/ibm/ejs/container/EJSContainer:postInvoke	(Lcom/ibm/ejs/container/EJSWrapper;ILcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   121: goto +23 -> 144
    //   124: astore 8
    //   126: jsr +6 -> 132
    //   129: aload 8
    //   131: athrow
    //   132: astore 7
    //   134: aload_0
    //   135: getfield 22	com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/EJSRemoteStatelessOFX200BPWServicesHome_1c50368d:container	Lcom/ibm/ejs/container/EJSContainer;
    //   138: aload_2
    //   139: invokevirtual 69	com/ibm/ejs/container/EJSContainer:putEJSDeployedSupport	(Lcom/ibm/ejs/container/EJSDeployedSupport;)V
    //   142: ret 7
    //   144: jsr -12 -> 132
    //   147: ret 5
    //   149: jsr -40 -> 109
    //   152: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	EJSRemoteStatelessOFX200BPWServicesHome_1c50368d
    //   0	153	1	paramHandle	javax.ejb.Handle
    //   8	131	2	localEJSDeployedSupport	com.ibm.ejs.container.EJSDeployedSupport
    //   13	29	3	arrayOfObject	java.lang.Object[]
    //   48	3	4	localEJSStatelessOFX200BPWServicesHomeBean_1c50368d	EJSStatelessOFX200BPWServicesHomeBean_1c50368d
    //   59	4	4	localRemoteException	RemoteException
    //   70	9	4	localRemoveException	javax.ejb.RemoveException
    //   81	15	4	localThrowable	java.lang.Throwable
    //   109	1	5	localObject1	java.lang.Object
    //   101	6	6	localObject2	java.lang.Object
    //   132	1	7	localObject3	java.lang.Object
    //   124	6	8	localObject4	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   14	59	59	java/rmi/RemoteException
    //   14	59	70	javax/ejb/RemoveException
    //   14	59	81	java/lang/Throwable
    //   14	101	101	finally
    //   149	152	101	finally
    //   111	124	124	finally
    //   144	147	124	finally
  }
}


/* Location:           D:\drops\jd\jars\Deployed_OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.EJSRemoteStatelessOFX200BPWServicesHome_1c50368d
 * JD-Core Version:    0.7.0.1
 */