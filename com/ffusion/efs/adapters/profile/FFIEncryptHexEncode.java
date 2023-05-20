package com.ffusion.efs.adapters.profile;

import com.ffusion.util.HfnEncrypt;

public class FFIEncryptHexEncode
  implements Encrypt
{
  public String dbEncrypt(String paramString)
    throws Exception
  {
    return HfnEncrypt.encryptHexEncode(paramString);
  }
  
  public String dbDecrypt(String paramString)
    throws Exception
  {
    return HfnEncrypt.decryptHexEncode(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.FFIEncryptHexEncode
 * JD-Core Version:    0.7.0.1
 */