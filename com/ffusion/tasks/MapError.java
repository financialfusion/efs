package com.ffusion.tasks;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.FIException;
import com.ffusion.services.cashcon.CashConException;
import javax.servlet.http.HttpSession;

public class MapError
{
  public static int mapError(CSILException paramCSILException)
  {
    int i = 0;
    if (paramCSILException.getCode() == -1009) {
      i = paramCSILException.getServiceError();
    } else {
      i = paramCSILException.getCode();
    }
    return i;
  }
  
  public static int mapError(CSILException paramCSILException, HttpSession paramHttpSession)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = paramCSILException;
    while (((localObject1 == null) || (localObject2 == null)) && (localObject3 != null))
    {
      String str1 = null;
      String str2 = null;
      if ((localObject3 instanceof CSILException))
      {
        str1 = ((CSILException)localObject3).why;
        str2 = ((CSILException)localObject3).where;
        localObject3 = ((CSILException)localObject3).childException;
      }
      else if ((localObject3 instanceof FIException))
      {
        str1 = ((FIException)localObject3).why;
        str2 = ((FIException)localObject3).where;
        localObject3 = ((FIException)localObject3).childException;
      }
      else
      {
        if (!(localObject3 instanceof CashConException)) {
          break;
        }
        str1 = ((CashConException)localObject3).why;
        str2 = ((CashConException)localObject3).where;
        localObject3 = ((CashConException)localObject3).childException;
      }
      if ((localObject1 == null) && (str1 != null) && (str1.length() > 0)) {
        localObject1 = str1;
      }
      if ((localObject2 == null) && (str2 != null) && (str2.length() > 0)) {
        localObject2 = str2;
      }
    }
    if (localObject1 != null) {
      paramHttpSession.setAttribute("SE_WHY", localObject1);
    }
    if (localObject2 != null) {
      paramHttpSession.setAttribute("SE_WHERE", localObject2);
    }
    return mapError(paramCSILException);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.MapError
 * JD-Core Version:    0.7.0.1
 */