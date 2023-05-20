package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.csil.CSILException;
import java.util.HashMap;

public abstract interface ACH3
  extends ACH2
{
  public abstract ACHPayees getACHPayees(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void processApprovalResult(SecureUser paramSecureUser, Object paramObject, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract HashMap getStateNamesWithTaxForms(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ACH3
 * JD-Core Version:    0.7.0.1
 */