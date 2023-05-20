package com.ffusion.csil.beans.entitlements;

import java.util.ArrayList;

public class EntitlementGroupPermissions
  extends ArrayList
{
  public EntitlementGroupPermission getPermission(int paramInt)
  {
    return (EntitlementGroupPermission)get(paramInt);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.EntitlementGroupPermissions
 * JD-Core Version:    0.7.0.1
 */