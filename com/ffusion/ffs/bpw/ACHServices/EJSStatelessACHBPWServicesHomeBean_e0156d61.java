package com.ffusion.ffs.bpw.ACHServices;

import com.ibm.ejs.container.EJSHome;
import java.rmi.RemoteException;

public class EJSStatelessACHBPWServicesHomeBean_e0156d61
  extends EJSHome
{
  public EJSStatelessACHBPWServicesHomeBean_e0156d61()
    throws RemoteException
  {}
  
  /* Error */
  public ACHBPWServices create()
    throws javax.ejb.CreateException, RemoteException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aconst_null
    //   3: astore_2
    //   4: iconst_0
    //   5: istore_3
    //   6: aload_0
    //   7: new 18	com/ibm/ejs/container/BeanId
    //   10: dup
    //   11: aload_0
    //   12: aconst_null
    //   13: invokespecial 21	com/ibm/ejs/container/BeanId:<init>	(Lcom/ibm/ejs/container/HomeInternal;Ljava/io/Serializable;)V
    //   16: invokespecial 25	com/ibm/ejs/container/EJSHome:createWrapper	(Lcom/ibm/ejs/container/BeanId;)Lcom/ibm/ejs/container/EJSWrapper;
    //   19: checkcast 27	com/ffusion/ffs/bpw/ACHServices/ACHBPWServices
    //   22: astore_2
    //   23: goto +52 -> 75
    //   26: astore 4
    //   28: iconst_1
    //   29: istore_3
    //   30: aload 4
    //   32: athrow
    //   33: astore 4
    //   35: iconst_1
    //   36: istore_3
    //   37: aload 4
    //   39: athrow
    //   40: astore 4
    //   42: iconst_1
    //   43: istore_3
    //   44: new 29	com/ibm/ejs/container/CreateFailureException
    //   47: dup
    //   48: aload 4
    //   50: invokespecial 32	com/ibm/ejs/container/CreateFailureException:<init>	(Ljava/lang/Throwable;)V
    //   53: athrow
    //   54: astore 6
    //   56: jsr +6 -> 62
    //   59: aload 6
    //   61: athrow
    //   62: astore 5
    //   64: iload_3
    //   65: ifeq +8 -> 73
    //   68: aload_0
    //   69: aload_1
    //   70: invokespecial 36	com/ibm/ejs/container/EJSHome:createFailure	(Lcom/ibm/ejs/container/BeanO;)V
    //   73: ret 5
    //   75: jsr -13 -> 62
    //   78: aload_2
    //   79: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	80	0	this	EJSStatelessACHBPWServicesHomeBean_e0156d61
    //   1	69	1	localBeanO	com.ibm.ejs.container.BeanO
    //   3	76	2	localACHBPWServices	ACHBPWServices
    //   5	60	3	i	int
    //   26	5	4	localCreateException	javax.ejb.CreateException
    //   33	5	4	localRemoteException	RemoteException
    //   40	9	4	localThrowable	java.lang.Throwable
    //   62	1	5	localObject1	java.lang.Object
    //   54	6	6	localObject2	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   6	26	26	javax/ejb/CreateException
    //   6	26	33	java/rmi/RemoteException
    //   6	26	40	java/lang/Throwable
    //   6	54	54	finally
    //   75	78	54	finally
  }
}


/* Location:           D:\drops\jd\jars\Deployed_ACHBPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.ACHServices.EJSStatelessACHBPWServicesHomeBean_e0156d61
 * JD-Core Version:    0.7.0.1
 */