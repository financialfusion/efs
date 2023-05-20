package com.ffusion.services.ofx.OFX200;

import com.ffusion.services.ofx.Base.ServiceXMLHandler;

public class Messaging
  extends com.ffusion.services.ofx.Messaging
{
  public int initialize(String paramString)
  {
    setOFXVersion(200);
    return initialize(paramString, new Base.ServiceXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.OFX200.Messaging
 * JD-Core Version:    0.7.0.1
 */