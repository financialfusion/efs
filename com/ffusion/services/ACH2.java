package com.ffusion.services;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.csil.CSILException;
import java.util.Date;
import java.util.HashMap;

public abstract interface ACH2
  extends ACH
{
  public abstract boolean isBusinessRegisteredWithBPW(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void releaseACHBatches(SecureUser paramSecureUser, ACHBatches paramACHBatches, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatches getReleaseACHBatches(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException;
  
  public abstract int getReleaseACHBatchesCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException;
  
  public abstract Date getSmartDate(SecureUser paramSecureUser, DateTime paramDateTime)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ACH2
 * JD-Core Version:    0.7.0.1
 */