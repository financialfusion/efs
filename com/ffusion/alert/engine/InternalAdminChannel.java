package com.ffusion.alert.engine;

import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.interfaces.IAEStatusConstants;
import com.ffusion.alert.shared.AELog;
import com.ffusion.alert.shared.AEUtils;
import java.io.PrintWriter;
import java.util.Properties;

public class InternalAdminChannel
  implements IAEChannel, IAEStatusConstants
{
  public static final String N = "com.ffusion.alert.engine.InternalAdminChannel";
  public static final int P = 1;
  AlertEngine O;
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.O = ((AlertEngine)paramIAEAlertEngine);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    Object localObject = AEUtils.jdMethod_if(paramIAEAlertInstance.getMessageBytes());
    if (localObject != null) {
      try
      {
        IInternalAdmin localIInternalAdmin = (IInternalAdmin)localObject;
        return localIInternalAdmin.a(this.O, paramProperties) ? 0 : -1;
      }
      catch (ClassCastException localClassCastException)
      {
        AELog.a("InternalAdminChannel.processAlert", " Object deserialized not of correct type", 1);
      }
    }
    return -1;
  }
  
  public void stop() {}
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.InternalAdminChannel
 * JD-Core Version:    0.7.0.1
 */