package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffDefinition;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface AffiliateBankAdmin2
  extends AffiliateBankAdmin
{
  public abstract CutOffDefinition getCutOffDefinition(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void setCutOffDefinition(SecureUser paramSecureUser, int paramInt1, int paramInt2, CutOffDefinition paramCutOffDefinition, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AffiliateBankAdmin2
 * JD-Core Version:    0.7.0.1
 */