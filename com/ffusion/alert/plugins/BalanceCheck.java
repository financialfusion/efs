package com.ffusion.alert.plugins;

import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import java.io.PrintWriter;
import java.util.Properties;

public class BalanceCheck
  implements IAEChannel, AlertChannelConstants
{
  private PrintWriter aRX;
  private boolean aRW;
  public static final String THIS_PLUGIN_NAME = "BalanceCheck";
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.aRX = paramPrintWriter;
    this.aRW = Boolean.valueOf(paramProperties.getProperty("DEBUG")).booleanValue();
    AlertUtil.logEntry("BalanceCheck plugin has been initialized...", this.aRW, paramPrintWriter);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    AlertUtil.logEntry("Processing BalanceCheck Alert...", this.aRW, this.aRX);
    return 0;
  }
  
  public void stop()
  {
    AlertUtil.logEntry("Stopping BalanceCheck plugin...", this.aRW, this.aRX);
  }
  
  protected String getPluginName()
  {
    return "BalanceCheck";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.BalanceCheck
 * JD-Core Version:    0.7.0.1
 */