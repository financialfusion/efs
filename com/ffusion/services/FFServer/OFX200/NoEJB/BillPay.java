package com.ffusion.services.FFServer.OFX200.NoEJB;

import com.microstar.xml.HandlerBase;

public class BillPay
  extends com.ffusion.services.FFServer.OFX200.BillPay
{
  protected int initialize(String paramString, HandlerBase paramHandlerBase)
  {
    this.useNoEJB = true;
    return super.initialize(paramString, paramHandlerBase);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.FFServer.OFX200.NoEJB.BillPay
 * JD-Core Version:    0.7.0.1
 */