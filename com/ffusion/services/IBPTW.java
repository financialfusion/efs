package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.services.bptw.BPTWException;
import java.util.HashMap;

public abstract interface IBPTW
{
  public static final int ERROR_UNKNOWN = 1;
  public static final int ERROR_INIT_FILE_NOT_FOUND = 7;
  public static final int ERROR_INVALID_INIT_FILE = 8;
  public static final int ERROR_NONE = 0;
  public static final int ERROR_BPTW_NOT_AVAILABLE = 30000;
  public static final int ERROR_BPTW_INIT_FILE_NOT_VALID = 30001;
  
  public abstract int initialize(String paramString);
  
  public abstract void addCustomers(SecureUser paramSecureUser, CustomerInfo[] paramArrayOfCustomerInfo, HashMap paramHashMap)
    throws BPTWException;
  
  public abstract void modifyCustomers(SecureUser paramSecureUser, CustomerInfo[] paramArrayOfCustomerInfo, HashMap paramHashMap)
    throws BPTWException;
  
  public abstract void deleteCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws BPTWException;
  
  public abstract void deactivateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws BPTWException;
  
  public abstract void activateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws BPTWException;
  
  public abstract CustomerInfo[] getCustomersInfo(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws BPTWException;
  
  public abstract PayeeInfo[] getLinkedPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws BPTWException;
  
  public abstract PayeeInfo[] getMostUsedPayees(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws BPTWException;
  
  public abstract PayeeInfo[] getPreferredPayees(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws BPTWException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.IBPTW
 * JD-Core Version:    0.7.0.1
 */