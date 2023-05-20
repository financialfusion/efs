package com.ffusion.services.FFServer.OFX151.NoEJB;

import com.microstar.xml.HandlerBase;

public class Banking
  extends com.ffusion.services.FFServer.OFX151.Banking
{
  protected int initialize(String paramString, HandlerBase paramHandlerBase)
  {
    this.useNoEJB = true;
    return super.initialize(paramString, paramHandlerBase);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.FFServer.OFX151.NoEJB.Banking
 * JD-Core Version:    0.7.0.1
 */