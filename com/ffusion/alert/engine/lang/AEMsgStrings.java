package com.ffusion.alert.engine.lang;

import com.ffusion.alert.engine.IAEMsgConstants;
import java.util.ListResourceBundle;

public class AEMsgStrings
  extends ListResourceBundle
  implements IAEMsgConstants
{
  private static final String bQ = System.getProperty("line.separator");
  static final Object[][] bP = { { "MSG_ENGINE_ACTIVATING", "The alerts engine at {0} is starting, and will take over alerts processing for the cluster." }, { "MSG_ENGINE_ACTIVATED", "The alerts engine at {0} has taken over alerts processing for the cluster." }, { "MSG_ENGINE_DEACTIVATING", "The engine at {0} is stopping processing alerts for the cluster because the alerts engine at {1} has become active." }, { "MSG_ENGINE_DEACTIVATING_NO_ACTIVE", "The engine at {0} is stopping processing alerts for the cluster. There is currently no active engine in the cluster." }, { "MSG_ENGINE_DEACTIVATED", "The alerts engine at {0} has stopped processing alerts for the cluster." }, { "MSG_ENGINE_PRIMARY_ACTIVATION_PENDING", "The alerts engine at {0} is the primary engine and is taking over processing for the cluster from the server at {1}." }, { "MSG_ENGINE_PRIMARY_ACTIVATION_PENDING_NO_ACTIVE", "The alerts engine at {0} is the primary engine and is taking over processing for the cluster, and there is currently no active server in the cluster." }, { "MSG_ENGINE_ACTIVATION_PENDING", "The alerts engine at {0} has not logged a heartbeat since {1}, so the alerts engine at {2} will become active at the next heartbeat." }, { "MSG_ENGINE_ACTIVATION_PENDING_NO_ACTIVE", "There is no active alerts engine in the cluster, so the alerts engine at {0} will become active at the next heartbeat." }, { "MSG_ENGINE_ACTIVATION_CANCELLED", "The alerts engine at {0} has taken over processing for the cluster, so the engine at {1} will no longer become active." }, { "MSG_AUDIT_LOG_CLEANUP_BEGIN", "Cleanup of the Audit Log is beginning." }, { "MSG_AUDIT_LOG_CLEANUP_END", "Cleanup of the Audit Log has finished." }, { "MSG_CR_LOG_CLEANUP_BEGIN", "Cleanup of the Crash Recovery Log is beginning." }, { "MSG_CR_LOG_CLEANUP_END", "Cleanup of the Crash Recovery Log has finished." }, { "MSG_ALERT_CLEANUP_BEGIN", "Cleanup of completed and cancelled Alerts is beginning." }, { "MSG_ALERT_CLEANUP_END", "Cleanup of completed and cancelled Alerts has finished." }, { "MSG_CHANNEL_CLEANUP_BEGIN", "Cleanup of deleted channels is beginning." }, { "MSG_CHANNEL_CLEANUP_END", "Cleanup of deleted channels has completed." }, { "MSG_APP_CLEANUP_BEGIN", "Cleanup of deleted applications is beginning." }, { "MSG_APP_CLEANUP_END", "Cleanup of deleted applications has completed." } };
  
  public Object[][] getContents()
  {
    return bP;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.lang.AEMsgStrings
 * JD-Core Version:    0.7.0.1
 */