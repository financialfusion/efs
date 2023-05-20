package com.ffusion.services.ofx.OFX200;

import com.ffusion.services.ofx.Enroll.EnrollXMLHandler;

public class Enroll
  extends com.ffusion.services.ofx.Enroll
{
  public int initialize(String paramString)
  {
    setOFXVersion(200);
    return initialize(paramString, new Enroll.EnrollXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.OFX200.Enroll
 * JD-Core Version:    0.7.0.1
 */