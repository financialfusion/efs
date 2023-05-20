package com.ffusion.alert.engine;

import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.shared.AELog;
import java.util.Properties;

public class RepositoryCleaner
  implements IInternalAdmin, IAEErrConstants
{
  public boolean a(AlertEngine paramAlertEngine, Properties paramProperties)
  {
    try
    {
      paramAlertEngine.o();
    }
    catch (AEException localAEException)
    {
      AELog.a(localAEException, AEInstance.a, "ERR_REPOSITORY_CLEANUP", 0);
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.RepositoryCleaner
 * JD-Core Version:    0.7.0.1
 */