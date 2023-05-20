package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.csil.CSILException;
import com.ffusion.util.beans.accountgroups.BusinessAccountGroupException;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountGroup
{
  private static final String a = "AccountGroups";
  private static com.ffusion.services.AccountGroup jdField_if = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountGroup.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "AccountGroups", str, 20107);
    jdField_if = (com.ffusion.services.AccountGroup)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      jdField_if.initialize(paramHashMap);
    }
    catch (BusinessAccountGroupException localBusinessAccountGroupException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localBusinessAccountGroupException), localBusinessAccountGroupException);
      DebugLog.throwing(str, localBusinessAccountGroupException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return jdField_if;
  }
  
  public static BusinessAccountGroups getAccountGroups(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountGroup.getAccountGroups";
    try
    {
      return jdField_if.getAccountGroups(paramSecureUser, paramInt, paramHashMap);
    }
    catch (BusinessAccountGroupException localBusinessAccountGroupException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localBusinessAccountGroupException), localBusinessAccountGroupException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getAccountGroupIds(SecureUser paramSecureUser, int paramInt, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountGroup.getAccountGroupIds";
    try
    {
      return jdField_if.getAccountGroupIds(paramSecureUser, paramInt, paramArrayList, paramHashMap);
    }
    catch (BusinessAccountGroupException localBusinessAccountGroupException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localBusinessAccountGroupException), localBusinessAccountGroupException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static BusinessAccountGroup getAccountGroup(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountGroup.getAccountGroup";
    try
    {
      return jdField_if.getAccountGroup(paramSecureUser, paramInt, paramHashMap);
    }
    catch (BusinessAccountGroupException localBusinessAccountGroupException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localBusinessAccountGroupException), localBusinessAccountGroupException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static BusinessAccountGroup addAccountGroup(SecureUser paramSecureUser, BusinessAccountGroup paramBusinessAccountGroup, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountGroup.addAccountGroup";
    try
    {
      return jdField_if.addAccountGroup(paramSecureUser, paramBusinessAccountGroup, paramHashMap);
    }
    catch (BusinessAccountGroupException localBusinessAccountGroupException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localBusinessAccountGroupException), localBusinessAccountGroupException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void modifyAccountGroup(SecureUser paramSecureUser, BusinessAccountGroup paramBusinessAccountGroup1, BusinessAccountGroup paramBusinessAccountGroup2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountGroup.modifyAccountGroup";
    try
    {
      jdField_if.modifyAccountGroup(paramSecureUser, paramBusinessAccountGroup1, paramBusinessAccountGroup2, paramHashMap);
    }
    catch (BusinessAccountGroupException localBusinessAccountGroupException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localBusinessAccountGroupException), localBusinessAccountGroupException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteAccountGroup(SecureUser paramSecureUser, BusinessAccountGroup paramBusinessAccountGroup, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountGroup.deleteAccountGroup";
    try
    {
      jdField_if.deleteAccountGroup(paramSecureUser, paramBusinessAccountGroup, paramHashMap);
    }
    catch (BusinessAccountGroupException localBusinessAccountGroupException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localBusinessAccountGroupException), localBusinessAccountGroupException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  private static int a(BusinessAccountGroupException paramBusinessAccountGroupException)
  {
    switch (paramBusinessAccountGroupException.getErrorCode())
    {
    case 1: 
      return 20107;
    case 2: 
      return 16002;
    case 3: 
      return 40005;
    case 4: 
      return 40006;
    case 5: 
      return 40000;
    case 6: 
      return 40003;
    case 7: 
      return 40001;
    case 8: 
      return 40002;
    }
    return 1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.AccountGroup
 * JD-Core Version:    0.7.0.1
 */