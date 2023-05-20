package com.ffusion.ffs.bpw.util.swift;

import com.ffusion.ffs.bpw.util.BPWConfigWrapper;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.io.InputStream;

public class SWIFTFactory
{
  private static SWIFTFactory jdField_if;
  private BPWConfigWrapper a = null;
  
  private SWIFTFactory()
    throws SWIFTParseException
  {
    a();
  }
  
  public static synchronized SWIFTFactory getInstance()
    throws SWIFTParseException
  {
    if (jdField_if == null) {
      jdField_if = new SWIFTFactory();
    }
    return jdField_if;
  }
  
  public ISWIFTMapper getMapper(String paramString1, String paramString2)
    throws SWIFTParseException
  {
    if ((this.a == null) || (!BPWConfigWrapper.initialized))
    {
      str1 = "Either _bpwConfigWrapper is null or is not initialized";
      DebugLog.log(str1);
      throw new SWIFTParseException(str1);
    }
    String str1 = null;
    try
    {
      str1 = this.a.getSWIFTMapperClassName(paramString1, paramString2).trim();
    }
    catch (FFSException localFFSException)
    {
      localObject2 = "Error while getting the Mapper class name from BPWConfigWrapper object";
      DebugLog.log((String)localObject2);
      throw new SWIFTParseException((String)localObject2);
    }
    Object localObject1;
    if (str1 == null)
    {
      localObject1 = "Null Mapper class name returned from BPWConfigWrapper object";
      DebugLog.log((String)localObject1);
      throw new SWIFTParseException((String)localObject1);
    }
    Object localObject2 = null;
    try
    {
      localObject1 = Class.forName(str1);
      localObject2 = (ISWIFTMapper)((Class)localObject1).newInstance();
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      str3 = "Error While creating ISWIFTMapper implementation instance";
      DebugLog.throwing(str3, localClassNotFoundException);
      throw new SWIFTParseException(localClassNotFoundException, str3);
    }
    catch (InstantiationException localInstantiationException)
    {
      str3 = "Error While creating ISWIFTMapper implementation instance";
      DebugLog.throwing(str3, localInstantiationException);
      throw new SWIFTParseException(localInstantiationException, str3);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      String str3 = "Error While creating ISWIFTMapper implementation instance";
      DebugLog.throwing(str3, localIllegalAccessException);
      throw new SWIFTParseException(localIllegalAccessException, str3);
    }
    if (localObject2 == null)
    {
      String str2 = "Error While creating ISWIFTMapper implementation instance";
      DebugLog.log(str2);
      throw new SWIFTParseException(str2);
    }
    return localObject2;
  }
  
  private void a()
    throws SWIFTParseException
  {
    String str1 = "bpwconfig.xml";
    InputStream localInputStream = FFSUtil.getResourceAsStream(this, str1);
    if (localInputStream != null)
    {
      try
      {
        localInputStream.close();
        localInputStream = null;
      }
      catch (IOException localIOException1)
      {
        DebugLog.log("Error while closing InputStream" + localIOException1.getMessage());
      }
      this.a = ((BPWConfigWrapper)FFSRegistry.lookup("BPWCONFIGWRAPPER"));
    }
    else
    {
      str1 = "swiftconfig.xml";
      localInputStream = FFSUtil.getResourceAsStream(this, str1);
      if (localInputStream == null)
      {
        String str2 = "SWIFT Configuration file not found in classpath";
        throw new SWIFTParseException(str2);
      }
      try
      {
        localInputStream.close();
        localInputStream = null;
      }
      catch (IOException localIOException2)
      {
        DebugLog.log("Error while closing InputStream" + localIOException2.getMessage());
      }
    }
    if (this.a == null) {
      try
      {
        this.a = BPWConfigWrapper.getInstance();
        this.a.initialize(str1, false);
      }
      catch (Exception localException)
      {
        String str4 = "Error while getting the BPWConfigWrapper object";
        DebugLog.throwing(str4, localException);
        throw new SWIFTParseException(localException, str4);
      }
    }
    if (this.a == null)
    {
      String str3 = "Could not initialize BPWConfigWrapper instance";
      DebugLog.log(str3);
      throw new SWIFTParseException(str3);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.swift.SWIFTFactory
 * JD-Core Version:    0.7.0.1
 */