package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.stoppayments.StopCheck;
import com.ffusion.beans.stoppayments.StopChecks;
import com.ffusion.csil.CSILException;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface Stops4
  extends Stops3
{
  public abstract StopCheck modStopPayment(SecureUser paramSecureUser, StopCheck paramStopCheck, HashMap paramHashMap)
    throws CSILException;
  
  public abstract StopCheck addStopPayment(SecureUser paramSecureUser, StopCheck paramStopCheck, HashMap paramHashMap)
    throws CSILException;
  
  public abstract StopCheck deleteStopPayment(SecureUser paramSecureUser, StopCheck paramStopCheck, HashMap paramHashMap)
    throws CSILException;
  
  public abstract StopChecks getStopPayments(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Stops4
 * JD-Core Version:    0.7.0.1
 */