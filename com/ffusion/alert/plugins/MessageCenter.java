package com.ffusion.alert.plugins;

import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.Messages;
import com.ffusion.csil.ejb.ICSILEJB;
import com.ffusion.services.Messaging2;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class MessageCenter
  implements IAEChannel, AlertChannelConstants
{
  private Properties aSz;
  private IAEAlertEngine aSB;
  private PrintWriter aSt;
  private Messaging2 aSw;
  private boolean aSD;
  private static final String aSx = "MessageCenter";
  private String aSq = "";
  private String aSA = "";
  private String aSr = "";
  private String aSC = "";
  private String aSv = "";
  private String aSE;
  private String aSs = "";
  private boolean aSy = false;
  private ICSILEJB aSu;
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.aSB = paramIAEAlertEngine;
    this.aSt = paramPrintWriter;
    this.aSz = paramProperties;
    this.aSD = Boolean.valueOf(paramProperties.getProperty("DEBUG")).booleanValue();
    AlertUtil.logEntry("Initializing MessageCenter plugin...", this.aSD, paramPrintWriter);
    this.aSs = paramProperties.getProperty("USECSIL", "");
    if ((this.aSs != null) && (this.aSs.equalsIgnoreCase("True")))
    {
      this.aSy = true;
      AlertUtil.logEntry("Preparing to call CSIL EJB ...", this.aSD, paramPrintWriter);
      try
      {
        this.aSu = AlertUtil.connectToCSILEJB(paramProperties);
      }
      catch (RemoteException localRemoteException)
      {
        if (this.aSD)
        {
          paramPrintWriter.println("Unable to connect to CSIL EJB when initializing the Message Center alert delivery channel");
          localRemoteException.printStackTrace(paramPrintWriter);
        }
      }
    }
    AlertUtil.logEntry("MessageCenter initialization completed", this.aSD, paramPrintWriter);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    AlertUtil.logEntry("MessageCenter: Processing Alert...", this.aSD, this.aSt);
    int i = 0;
    int j = 0;
    Properties localProperties = null;
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = paramIAEAlertInstance.getDeliveryInfo();
    for (int k = 0; k < arrayOfIAEDeliveryInfo.length; k++)
    {
      localObject = arrayOfIAEDeliveryInfo[k];
      if (((IAEDeliveryInfo)localObject).getDeliveryChannelName().equals(getPluginName()))
      {
        localProperties = ((IAEDeliveryInfo)localObject).getDeliveryProperties();
        j = k;
        break;
      }
    }
    if ((this.aSy) && (this.aSu == null)) {
      try
      {
        this.aSu = AlertUtil.connectToCSILEJB(this.aSz);
      }
      catch (Exception localException1)
      {
        AlertUtil.logEntry("Exception occurred in processAlert with message " + localException1.getMessage(), this.aSD, this.aSt);
      }
    }
    SecureUser localSecureUser = new SecureUser();
    Object localObject = localProperties.getProperty("SECUREUSER");
    localSecureUser.setXML((String)localObject);
    String str1 = localProperties.getProperty("PREFERREDLANGUAGE");
    if ((str1 != null) && (str1.trim().length() > 0)) {
      localSecureUser.setLocale(str1);
    }
    Messages localMessages = new Messages();
    Message localMessage = localMessages.createNoAdd();
    localMessage.setTo(localSecureUser.getProfileID());
    localMessage.setToType("CUSTOMER");
    localMessage.setFrom("1");
    localMessage.setFromType("EMPLOYEE");
    String str2 = localProperties.getProperty("subject");
    String str3 = localProperties.getProperty("body");
    if (str2 == null)
    {
      str2 = localProperties.getProperty("SUBJECT");
      if (str2 != null) {
        localMessage.setSubject(str2);
      }
    }
    else
    {
      localMessage.setSubject(str2);
    }
    if (str3 == null) {
      str3 = localProperties.getProperty("BODY");
    }
    localMessage.setDate(new Date());
    String str4 = localProperties.getProperty("PREFORMAT");
    if ((str4 == null) || (str4.equalsIgnoreCase("false"))) {
      localMessage.setMemo(str3);
    } else {
      localMessage.setMemo("<PRE>" + str3 + "</PRE>");
    }
    localMessage.setType(8);
    HashMap localHashMap = new HashMap();
    if (this.aSD)
    {
      String str5 = paramIAEAlertInstance.getDeliveryInfo()[j].getDeliveryProperties().getProperty("AlertType");
      AlertUtil.logEntry("AlertType being used is : " + str5, this.aSD, this.aSt);
    }
    if (this.aSy) {
      try
      {
        try
        {
          this.aSu.sendMessage(localSecureUser, localMessage, localHashMap);
        }
        catch (RemoteException localRemoteException)
        {
          this.aSu.sendMessage(localSecureUser, localMessage, localHashMap);
        }
        i = 0;
      }
      catch (Exception localException2)
      {
        AlertUtil.logEntry("Exception occurred in processAlert with message " + localException2.getMessage(), this.aSD, this.aSt);
      }
    }
    return i;
  }
  
  public void stop()
  {
    AlertUtil.logEntry("Stopping MessageCenter plugin...", this.aSD, this.aSt);
  }
  
  private Properties jdMethod_for(IAEAlertDefinition paramIAEAlertDefinition)
  {
    Properties localProperties = null;
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = paramIAEAlertDefinition.getDeliveryInfo();
    for (int i = 0; i < arrayOfIAEDeliveryInfo.length; i++)
    {
      IAEDeliveryInfo localIAEDeliveryInfo = arrayOfIAEDeliveryInfo[i];
      if (localIAEDeliveryInfo.getDeliveryChannelName().equals(getPluginName()))
      {
        localProperties = localIAEDeliveryInfo.getDeliveryProperties();
        break;
      }
    }
    return localProperties;
  }
  
  protected String getPluginName()
  {
    return "MessageCenter";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.MessageCenter
 * JD-Core Version:    0.7.0.1
 */