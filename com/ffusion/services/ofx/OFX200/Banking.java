package com.ffusion.services.ofx.OFX200;

import com.ffusion.services.ofx.Banking.BankingXMLHandler;

public class Banking
  extends com.ffusion.services.ofx.Banking
{
  public int initialize(String paramString)
  {
    setOFXVersion(200);
    return initialize(paramString, new Banking.BankingXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.OFX200.Banking
 * JD-Core Version:    0.7.0.1
 */