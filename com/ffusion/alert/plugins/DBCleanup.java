package com.ffusion.alert.plugins;

import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import java.io.PrintWriter;
import java.util.Properties;

public class DBCleanup
  implements IAEChannel, AlertChannelConstants
{
  private PrintWriter aSJ;
  private boolean aSK;
  public static final String THIS_PLUGIN_NAME = "DBCleanup";
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.aSJ = paramPrintWriter;
    this.aSK = Boolean.valueOf(paramProperties.getProperty("DEBUG")).booleanValue();
    AlertUtil.logEntry("Initializing DBCleanup Plugin.", this.aSK, paramPrintWriter);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    AlertUtil.logEntry("Processing DBCleanup Alert\tDeliveryProperties = " + paramProperties, this.aSK, this.aSJ);
    int i = 0;
    return i;
  }
  
  public void stop()
  {
    AlertUtil.logEntry("Stopping BankMessage plugin...", this.aSK, this.aSJ);
  }
  
  protected String getPluginName()
  {
    return "DBCleanup";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.DBCleanup
 * JD-Core Version:    0.7.0.1
 */