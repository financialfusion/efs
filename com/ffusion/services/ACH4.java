package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import java.util.HashMap;

public abstract interface ACH4
  extends ACH3
{
  public abstract void addCustomer(SecureUser paramSecureUser, String paramString, Business paramBusiness, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void modifyCustomer(SecureUser paramSecureUser, String paramString, Business paramBusiness, HashMap paramHashMap)
    throws CSILException;
  
  public abstract boolean getBusinessRegistrationBPWInfo(SecureUser paramSecureUser, String paramString, Business paramBusiness, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void deactivateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void activateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ACH4
 * JD-Core Version:    0.7.0.1
 */