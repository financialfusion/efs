package com.ffusion.efs.adapters.profile;

import com.ffusion.util.des.DESKeySet;
import com.ffusion.util.des.TripleDES;

public class ProfileTripleDES
  implements Encrypt
{
  private DESKeySet jdField_if = null;
  private String a = "0000000000000000FF12A419C63BE771F12AF419C63BE475512AC414C23B547E";
  
  public ProfileTripleDES()
  {
    String str1 = this.a.substring(0, 16);
    String str2 = this.a.substring(16, 32);
    String str3 = this.a.substring(32, 48);
    String str4 = this.a.substring(48, 64);
    this.jdField_if = new DESKeySet(str1, str2, str3, str4);
  }
  
  public String dbEncrypt(String paramString)
    throws Exception
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return "";
    }
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    String str1 = null;
    try
    {
      str1 = TripleDES.encrypt(localStringBuffer.toString(), this.jdField_if);
    }
    catch (Exception localException)
    {
      String str2 = "3DES failed on parameter '" + paramString + "': " + localException.getLocalizedMessage();
      throw new Exception(str2);
    }
    return str1;
  }
  
  public String dbDecrypt(String paramString)
    throws Exception
  {
    byte[] arrayOfByte = TripleDES.decrypt(paramString, this.jdField_if);
    return new String(arrayOfByte);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.ProfileTripleDES
 * JD-Core Version:    0.7.0.1
 */