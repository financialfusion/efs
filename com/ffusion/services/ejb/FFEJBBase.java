package com.ffusion.services.ejb;

import com.ffusion.util.ContextPool;
import com.ffusion.util.ContextPoolList;
import com.microstar.xml.HandlerBase;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

public class FFEJBBase
  implements Serializable
{
  private static final String T = "CallBackBeanURL";
  private static final String P = "ContextFactory";
  private static final String X = "CallBackJNDIName";
  private static final String S = "ContextUserName";
  private static final String R = "ContextPassword";
  private static final String Q = "BankID";
  private static final String V = "UseEJB";
  private static final String W = "MaxContexts";
  protected Vector provider_url_list = new Vector();
  protected String provider_url;
  protected String context_factory;
  protected String callback_JNDI_name = "";
  protected String context_username = "system";
  protected String context_password = "weblogic";
  protected String bankID;
  protected HashMap xmlHashMap;
  protected boolean useEJB = true;
  protected int maxContexts = 30;
  private static ContextPoolList U = null;
  
  public FFEJBBase()
  {
    if (U == null) {
      U = new ContextPoolList();
    }
    this.xmlHashMap = new HashMap();
  }
  
  public ContextPool getContextPoolFromList(String paramString)
    throws Exception
  {
    ContextPool localContextPool = U.getContextPool(paramString);
    if (localContextPool == null) {
      try
      {
        localContextPool = new ContextPool(paramString, this.context_username, this.context_password, this.context_factory);
        localContextPool.setInitialContexts(10);
        localContextPool.setIncrementalContexts(6);
        localContextPool.setMaxContexts(this.maxContexts);
        localContextPool.createPool();
        U.add(localContextPool);
      }
      catch (Throwable localThrowable)
      {
        System.out.println("Error: " + localThrowable.toString());
      }
    }
    return localContextPool;
  }
  
  public ContextPool getContextPoolFromList()
    throws Exception
  {
    ContextPool localContextPool = U.getContextPool(this.provider_url);
    if (localContextPool == null) {
      try
      {
        localContextPool = new ContextPool(this.provider_url, this.context_username, this.context_password, this.context_factory);
        localContextPool.setInitialContexts(10);
        localContextPool.setIncrementalContexts(6);
        localContextPool.setMaxContexts(this.maxContexts);
        localContextPool.createPool();
        U.add(localContextPool);
      }
      catch (Throwable localThrowable)
      {
        System.out.println("Error: " + localThrowable.toString());
      }
    }
    return localContextPool;
  }
  
  public class ServiceXMLHandler
    extends HandlerBase
  {
    public ServiceXMLHandler() {}
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("CallBackBeanURL"))
      {
        StringTokenizer localStringTokenizer = new StringTokenizer(paramString2);
        while (localStringTokenizer.hasMoreTokens())
        {
          String str = localStringTokenizer.nextToken(",");
          FFEJBBase.this.provider_url_list.add(str);
        }
        FFEJBBase.this.provider_url = paramString2;
      }
      else if (paramString1.equals("ContextFactory"))
      {
        FFEJBBase.this.context_factory = paramString2;
      }
      else if (paramString1.equals("CallBackJNDIName"))
      {
        FFEJBBase.this.callback_JNDI_name = paramString2;
      }
      else if (paramString1.equals("ContextUserName"))
      {
        FFEJBBase.this.context_username = paramString2;
      }
      else if (paramString1.equals("ContextPassword"))
      {
        FFEJBBase.this.context_password = paramString2;
      }
      else if (paramString1.equals("BankID"))
      {
        FFEJBBase.this.bankID = paramString2;
      }
      else if (paramString1.equals("UseEJB"))
      {
        FFEJBBase.this.useEJB = Boolean.valueOf(paramString2).booleanValue();
      }
      else if (paramString1.equals("MaxContexts"))
      {
        FFEJBBase.this.maxContexts = Integer.parseInt(paramString2);
      }
      FFEJBBase.this.xmlHashMap.put(paramString1, paramString2);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ejb.FFEJBBase
 * JD-Core Version:    0.7.0.1
 */