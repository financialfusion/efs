package com.ffusion.efs.adapters.entitlements;

import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.Entitlements;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;

public class EntitlementUtil
{
  public static Entitlements getEntitlements(MultiEntitlement paramMultiEntitlement)
    throws EntitlementException
  {
    Entitlements localEntitlements = new Entitlements();
    String[] arrayOfString1 = paramMultiEntitlement.getOperations();
    String[] arrayOfString2 = paramMultiEntitlement.getObjectTypes();
    String[] arrayOfString3 = paramMultiEntitlement.getObjectIds();
    if ((arrayOfString1 == null) || (arrayOfString1.length == 0)) {
      arrayOfString1 = new String[1];
    }
    if ((arrayOfString2 == null) || (arrayOfString2.length == 0)) {
      arrayOfString2 = new String[1];
    }
    if ((arrayOfString3 == null) || (arrayOfString3.length == 0)) {
      arrayOfString3 = new String[1];
    }
    if (arrayOfString2.length != arrayOfString3.length) {
      throw new EntitlementException(29);
    }
    for (int i = 0; i < arrayOfString1.length; i++) {
      for (int j = 0; j < arrayOfString2.length; j++) {
        localEntitlements.add(new Entitlement(arrayOfString1[i], arrayOfString2[j], arrayOfString3[j]));
      }
    }
    return localEntitlements;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.efs.adapters.entitlements.EntitlementUtil
 * JD-Core Version:    0.7.0.1
 */