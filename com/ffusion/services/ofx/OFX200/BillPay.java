package com.ffusion.services.ofx.OFX200;

import com.ffusion.services.ofx.BillPay.BillPayXMLHandler;

public class BillPay
  extends com.ffusion.services.ofx.BillPay
{
  public int initialize(String paramString)
  {
    setOFXVersion(200);
    return initialize(paramString, new BillPay.BillPayXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.OFX200.BillPay
 * JD-Core Version:    0.7.0.1
 */