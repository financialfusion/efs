package com.ffusion.alert.plugins;

import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.ICSILEJB;
import com.ffusion.util.HfnEncrypt;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

public abstract class BaseAlert
{
  private PrintWriter _writer;
  private boolean aRP = false;
  private IAEAlertEngine _engine = null;
  private AEApplicationInfo aRS = null;
  private Properties aRU;
  private SecureUser aRT = null;
  private boolean aRQ = false;
  private ICSILEJB aRV = null;
  private ArrayList aRR = null;
  
  protected abstract String getPluginName();
  
  protected void createEJBs(int paramInt)
    throws NamingException, RemoteException, CreateException, EJBException, CSILException
  {
    this.aRR = new ArrayList(paramInt);
    for (int i = 0; i < paramInt; i++) {
      this.aRR.add(new CSILEJBWrapper(AlertUtil.connectToCSILEJB(getInitProps()), getInitProps()));
    }
  }
  
  protected void cleaupCSILEJBs()
  {
    if (this.aRR != null) {
      for (int i = 0; i < this.aRR.size(); i++) {
        try
        {
          ((CSILEJBWrapper)this.aRR.get(i)).remove();
        }
        catch (Exception localException) {}
      }
    }
  }
  
  protected String defaultIfNull(String paramString)
  {
    if (paramString == null) {
      return "";
    }
    return paramString;
  }
  
  protected void reconnectToCsil(boolean paramBoolean)
    throws NamingException, RemoteException, CreateException, EJBException, CSILException
  {
    if ((paramBoolean) || (getCsilInstance() == null))
    {
      setCsilInstance(null);
      setCsilInstance(AlertUtil.connectToCSILEJB(getInitProps()));
    }
  }
  
  protected void setUpEnvironment(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter, OnAlertConnectionHandler paramOnAlertConnectionHandler)
    throws Exception
  {
    setEngine(paramIAEAlertEngine);
    setWriter(paramPrintWriter);
    setInitProps(paramProperties);
    setDebugOn(Boolean.valueOf(getInitProps().getProperty("DEBUG")).booleanValue());
    logEntry("Initializing " + getPluginName() + " plugin...");
    initAppInfo();
    if (paramOnAlertConnectionHandler != null)
    {
      logEntry("Calling CSIL Connection Handler");
      paramOnAlertConnectionHandler.initializePriorToCSILConnect();
    }
    try
    {
      String str = getInitProps().getProperty("USECSIL", "");
      if ((str != null) && (str.trim().equalsIgnoreCase("True")))
      {
        setUseCsil(true);
        setCsilInstance(AlertUtil.connectToCSILEJB(getInitProps()));
      }
      if (paramOnAlertConnectionHandler != null)
      {
        logEntry("Calling Create Thread Connection Handler");
        paramOnAlertConnectionHandler.initializePriorToStartingAlertThread();
      }
      logEntry("Creating Alert Thread for " + getPluginName());
      Properties localProperties = new Properties();
      CreateAlertThread localCreateAlertThread = new CreateAlertThread(getInitProps(), getPluginName(), getWriter(), getEngine(), localProperties);
      localCreateAlertThread.start();
      logEntry("Completed Alert Thread for " + getPluginName());
    }
    catch (Exception localException)
    {
      logEntry(localException);
      return;
    }
  }
  
  protected void setUpEnvironment(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    setUpEnvironment(paramProperties, paramIAEAlertEngine, paramPrintWriter, null);
  }
  
  protected IAEDeliveryInfo[] getAlertsDeliveryInfo(IAEAlertDefinition paramIAEAlertDefinition, int paramInt)
  {
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = null;
    if ((paramIAEAlertDefinition != null) && (paramIAEAlertDefinition.getDeliveryInfo() != null))
    {
      arrayOfIAEDeliveryInfo = new IAEDeliveryInfo[paramIAEAlertDefinition.getDeliveryInfo().length - 1];
      int i = 0;
      int j = 0;
      while (i < paramIAEAlertDefinition.getDeliveryInfo().length)
      {
        if (paramInt != i)
        {
          arrayOfIAEDeliveryInfo[j] = paramIAEAlertDefinition.getDeliveryInfo()[i];
          j++;
        }
        i++;
      }
    }
    return arrayOfIAEDeliveryInfo;
  }
  
  protected PrintWriter getWriter()
  {
    return this._writer;
  }
  
  protected void setWriter(PrintWriter paramPrintWriter)
  {
    this._writer = paramPrintWriter;
  }
  
  protected boolean isDebugOn()
  {
    return this.aRP;
  }
  
  protected void setDebugOn(boolean paramBoolean)
  {
    this.aRP = paramBoolean;
  }
  
  public void logEntry(Exception paramException)
  {
    logEntry(paramException, null);
  }
  
  public void logEntry(Exception paramException, String paramString)
  {
    if ((isDebugOn()) && (paramException != null))
    {
      String str1 = "";
      if ((paramString != null) && (paramString.trim().length() > 0)) {
        str1 = paramString;
      }
      String str2 = AlertUtil.sdf.format(new Date());
      StringBuffer localStringBuffer = new StringBuffer("[");
      getWriter().println(localStringBuffer.append(str2).append("] "));
      getWriter().println(str1);
      paramException.printStackTrace(getWriter());
    }
  }
  
  public void logEntry(String paramString)
  {
    AlertUtil.logEntry(paramString, isDebugOn(), getWriter());
  }
  
  protected AEApplicationInfo getAppInfo()
  {
    return this.aRS;
  }
  
  protected void initAppInfo()
  {
    AEApplicationInfo localAEApplicationInfo = null;
    try
    {
      String str = HfnEncrypt.decryptHexEncode(getInitProps().getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
      if (str == null) {
        str = getInitProps().getProperty("APP_INFO_PASSWORD", "ApplicationAlerts");
      }
      localAEApplicationInfo = new AEApplicationInfo(getInitProps().getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), str);
    }
    catch (Exception localException)
    {
      localAEApplicationInfo = new AEApplicationInfo(getInitProps().getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), getInitProps().getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
    }
    setAppInfo(localAEApplicationInfo);
  }
  
  protected void setAppInfo(AEApplicationInfo paramAEApplicationInfo)
  {
    this.aRS = paramAEApplicationInfo;
  }
  
  protected Properties getInitProps()
  {
    return this.aRU;
  }
  
  protected void setInitProps(Properties paramProperties)
  {
    this.aRU = paramProperties;
  }
  
  protected void suspendAlert(String paramString, IAEAlertDefinition paramIAEAlertDefinition, Alert paramAlert, int paramInt)
  {
    AEScheduleInfo localAEScheduleInfo = new AEScheduleInfo(paramAlert.getStartDateValue().getTime(), paramAlert.getEndDateValue().getTime(), paramAlert.getIntervalValue(), paramAlert.getIntervalType(), paramAlert.getRegularTimeZoneValue(), paramAlert.getRespectDST());
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = paramIAEAlertDefinition.getDeliveryInfo();
    if (arrayOfIAEDeliveryInfo != null)
    {
      for (int i = 0; i < arrayOfIAEDeliveryInfo.length; i++) {
        if (paramInt != i) {
          arrayOfIAEDeliveryInfo[i].setSuspended(true);
        }
      }
      try
      {
        getEngine().updateAlert(getAppInfo(), paramIAEAlertDefinition.getId(), paramString, paramAlert.getTypeValue(), paramAlert.getPriorityValue(), localAEScheduleInfo, arrayOfIAEDeliveryInfo, paramAlert.getMessage());
      }
      catch (AEException localAEException)
      {
        logEntry(localAEException);
      }
    }
  }
  
  protected int getPluginLocation(IAEAlertDefinition paramIAEAlertDefinition, String paramString)
  {
    return getPluginLocation(paramIAEAlertDefinition, paramString, "Alert");
  }
  
  protected int getPluginLocation(IAEAlertDefinition paramIAEAlertDefinition, String paramString1, String paramString2)
  {
    int i = -1;
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = paramIAEAlertDefinition.getDeliveryInfo();
    for (int j = 0; j < arrayOfIAEDeliveryInfo.length; j++)
    {
      IAEDeliveryInfo localIAEDeliveryInfo = arrayOfIAEDeliveryInfo[j];
      if (((paramIAEAlertDefinition.getUserId() == null) || (!paramIAEAlertDefinition.getUserId().equalsIgnoreCase(getPluginName() + paramString2))) && (localIAEDeliveryInfo.getDeliveryChannelName().equals(paramString1)))
      {
        SecureUser localSecureUser = new SecureUser();
        localSecureUser.setXML(localIAEDeliveryInfo.getDeliveryProperties().getProperty("SECUREUSER"));
        String str = localIAEDeliveryInfo.getDeliveryProperties().getProperty("PREFERREDLANGUAGE");
        if ((str != null) && (str.trim().length() > 0)) {
          localSecureUser.setLocale(str);
        }
        setSecureUser(localSecureUser);
        i = j;
        break;
      }
    }
    return i;
  }
  
  protected IAEAlertEngine getEngine()
  {
    return this._engine;
  }
  
  protected void setEngine(IAEAlertEngine paramIAEAlertEngine)
  {
    this._engine = paramIAEAlertEngine;
  }
  
  protected SecureUser getSecureUser()
  {
    return this.aRT;
  }
  
  protected void setSecureUser(SecureUser paramSecureUser)
  {
    this.aRT = paramSecureUser;
  }
  
  protected boolean isUseCsil()
  {
    return this.aRQ;
  }
  
  protected void setUseCsil(boolean paramBoolean)
  {
    this.aRQ = paramBoolean;
  }
  
  protected ICSILEJB getCsilInstance()
  {
    return this.aRV;
  }
  
  protected ArrayList getCsilInstances()
  {
    return this.aRR;
  }
  
  protected void setCsilInstance(ICSILEJB paramICSILEJB)
  {
    this.aRV = paramICSILEJB;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.BaseAlert
 * JD-Core Version:    0.7.0.1
 */