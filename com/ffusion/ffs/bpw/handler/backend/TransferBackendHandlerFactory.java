package com.ffusion.ffs.bpw.handler.backend;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.ITransferBackendHandler;
import com.ffusion.ffs.bpw.util.BPWConfigWrapper;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;

public class TransferBackendHandlerFactory
{
  private static String a = "Transfer-Handler";
  
  public static ITransferBackendHandler createBackendHandler()
    throws BPWException
  {
    BPWConfigWrapper localBPWConfigWrapper = (BPWConfigWrapper)FFSRegistry.lookup("BPWCONFIGWRAPPER");
    String str1 = null;
    ITransferBackendHandler localITransferBackendHandler = null;
    Class localClass = null;
    String str3;
    try
    {
      str1 = localBPWConfigWrapper.getBackendHandlerClassName(a);
    }
    catch (FFSException localFFSException)
    {
      str3 = "Error While getting ISWIFTMapper implementation classname from configWrapper";
      FFSDebug.log(str3, localFFSException);
      str1 = null;
    }
    if (str1 == null)
    {
      String str2 = "Could not read Transfer Backend Handler class name using configWrapper. Setting class name to com.ffusion.ffs.bpw.handler.backend.ACHTransferBackendHandler";
      FFSDebug.log(str2);
      str1 = "com.ffusion.ffs.bpw.handler.backend.ACHTransferBackendHandler";
    }
    try
    {
      localClass = Class.forName(str1);
      localITransferBackendHandler = (ITransferBackendHandler)localClass.newInstance();
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      str3 = "Error While creating ITransferBackendHandler implementation instance";
      FFSDebug.throwing(str3, localClassNotFoundException);
      throw new BPWException(localClassNotFoundException, str3);
    }
    catch (InstantiationException localInstantiationException)
    {
      str3 = "Error While creating ITransferBackendHandler implementation instance";
      FFSDebug.throwing(str3, localInstantiationException);
      throw new BPWException(localInstantiationException, str3);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      str3 = "Error While creating ITransferBackendHandler implementation instance";
      FFSDebug.throwing(str3, localIllegalAccessException);
      throw new BPWException(localIllegalAccessException, str3);
    }
    return localITransferBackendHandler;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.backend.TransferBackendHandlerFactory
 * JD-Core Version:    0.7.0.1
 */