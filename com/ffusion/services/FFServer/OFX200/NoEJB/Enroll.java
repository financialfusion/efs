package com.ffusion.services.FFServer.OFX200.NoEJB;

import com.microstar.xml.HandlerBase;

public class Enroll
  extends com.ffusion.services.FFServer.OFX200.Enroll
{
  protected int initialize(String paramString, HandlerBase paramHandlerBase)
  {
    this.useNoEJB = true;
    return super.initialize(paramString, paramHandlerBase);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.FFServer.OFX200.NoEJB.Enroll
 * JD-Core Version:    0.7.0.1
 */