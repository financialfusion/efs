package com.ffusion.alert.encryption;

import com.ffusion.alert.encryption.des.TripleDESEncrypt;
import com.zerog.ia.api.pub.CustomCodeAction;
import com.zerog.ia.api.pub.InstallException;
import com.zerog.ia.api.pub.InstallerProxy;
import com.zerog.ia.api.pub.UninstallerProxy;

public class EncryptCodeAction
  extends CustomCodeAction
{
  public void install(InstallerProxy paramInstallerProxy)
    throws InstallException
  {
    String str1 = paramInstallerProxy.substitute((String)paramInstallerProxy.getVariable("UAE_STRING_TO_ENCRYPT"));
    try
    {
      String str2 = TripleDESEncrypt.jdMethod_if(str1);
      paramInstallerProxy.setVariable("UAE_ENCRYPT_RESULT", str2);
    }
    catch (Exception localException)
    {
      paramInstallerProxy.setVariable("UAE_ENCRYPT_RESULT", null);
    }
  }
  
  public void uninstall(UninstallerProxy paramUninstallerProxy)
    throws InstallException
  {}
  
  public String getInstallStatusMessage()
  {
    return null;
  }
  
  public String getUninstallStatusMessage()
  {
    return null;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.encryption.EncryptCodeAction
 * JD-Core Version:    0.7.0.1
 */