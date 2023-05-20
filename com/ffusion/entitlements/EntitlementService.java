package com.ffusion.entitlements;

import java.io.Serializable;
import java.util.ArrayList;

public abstract interface EntitlementService
  extends Serializable
{
  public static final int METHOD_NOT_SUPPORTED = 14500;
  public static final int ERROR_UNABLE_TO_GET_ENTITLEMENTS = 14501;
  public static final int ERROR_UNABLE_TO_SAVE_ENTITLEMENTS = 14502;
  public static final int ERROR_UNABLE_TO_DELETE_ENTITLEMENTS = 14503;
  public static final int ERROR_UNABLE_TO_GET_RUNNINGTOTAL = 14504;
  public static final int ERROR_UNABLE_TO_UPDATE_RUNNINGTOTAL = 14505;
  public static final int ERROR_INVALID_INIT_FILE = 14506;
  public static final int ERROR_UNABLE_TO_COMPLETE_REQUEST = 14507;
  public static final int ERROR_INVALID_USER_IDENTIFIER = 14508;
  public static final int ERROR_N0_VALUELIMITABLE_OBJECT = 14509;
  
  public abstract int initialize(String paramString);
  
  public abstract int getEntitlements(Entitlements paramEntitlements);
  
  public abstract int saveEntitlements(Entitlements paramEntitlements);
  
  public abstract int getRunningTotals(ArrayList paramArrayList);
  
  public abstract int updateRunningTotals(ValueLimitable paramValueLimitable, ArrayList paramArrayList);
  
  public abstract int rollbackRunningTotals(ValueLimitable paramValueLimitable, ArrayList paramArrayList);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.entitlements.EntitlementService
 * JD-Core Version:    0.7.0.1
 */