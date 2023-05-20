package com.ffusion.services.ofx.OFX200;

import com.ffusion.services.ofx.StopPayments.a;

public class StopPayments
  extends com.ffusion.services.ofx.StopPayments
{
  public int initialize(String paramString)
  {
    setOFXVersion(200);
    return initialize(paramString, new StopPayments.a(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.OFX200.StopPayments
 * JD-Core Version:    0.7.0.1
 */