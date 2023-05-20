package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlements;
import com.ffusion.services.autoentitle.AutoEntitleException;
import java.util.HashMap;

public abstract interface AutoEntitle
{
  public static final String SERVICE_INIT_XML = "csil.xml";
  
  public abstract void initialize()
    throws AutoEntitleException;
  
  public abstract com.ffusion.beans.autoentitle.AutoEntitle getSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws AutoEntitleException;
  
  public abstract com.ffusion.beans.autoentitle.AutoEntitle getCumulativeSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws AutoEntitleException;
  
  public abstract com.ffusion.beans.autoentitle.AutoEntitle getCumulativeParentSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws AutoEntitleException;
  
  public abstract void setSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle1, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle2, HashMap paramHashMap)
    throws AutoEntitleException;
  
  public abstract void deleteSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws AutoEntitleException;
  
  public abstract Entitlements filterEntitlements(Entitlements paramEntitlements)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AutoEntitle
 * JD-Core Version:    0.7.0.1
 */