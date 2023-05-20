package com.ffusion.services.alerts;

import com.ffusion.alert.AlertAdapter;
import com.ffusion.alert.clientEJB.IAEAlertClient;
import com.ffusion.alert.clientEJB.IAEAlertClientHome;
import com.ffusion.alert.factory.AEDeliveryInfoFactory;
import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAuditInfo;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.plugins.AlertUtil;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.Alerts;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.alerts.LogMessage;
import com.ffusion.beans.alerts.LogMessages;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.alerts.UserAlerts;
import com.ffusion.beans.messages.BankMessageInfo;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.beans.user.ContactPoints;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Messages;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.ejb.EJBDefines;
import com.ffusion.services.Alerts2;
import com.ffusion.services.InitFileHandler;
import com.ffusion.services.ejb.FFEJBBase;
import com.ffusion.services.ejb.FFEJBBase.ServiceXMLHandler;
import com.ffusion.util.ContextPool;
import com.ffusion.util.IntMap;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLTag;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.logging.DebugLog;
import com.microstar.xml.HandlerBase;
import java.io.InputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;
import java.util.logging.Level;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

public class UAEAlerts
  extends FFEJBBase
  implements Alerts2, Serializable, EJBDefines
{
  String ad = null;
  String ac = null;
  String ae = null;
  String aa = "BalanceCheck,ProcessUserInformation,StockPortfolio,BankMessage,PendingCheck";
  protected AEApplicationInfo appInfo = null;
  static final String af = "AlertType";
  static final String Y = "to";
  static final String ab = "Email";
  static final String Z = "MessageCenter";
  public static final String ALERTS_INIT_XML = "alerts.xml";
  public static final String XML_ADAPTER_TAG = "adapter";
  public static final String XML_ADAPTER_SETTINGS_TAG = "ADAPTER_SETTINGS";
  
  public void setUserName(String paramString)
  {
    this.ae = paramString;
  }
  
  public void setPassword(String paramString) {}
  
  public String getSettings()
  {
    return this.aa;
  }
  
  public void setSettings(String paramString)
  {
    this.aa = paramString;
  }
  
  public int initialize(String paramString)
  {
    return initialize(paramString, new FFEJBBase.ServiceXMLHandler(this));
  }
  
  protected int initialize(String paramString, HandlerBase paramHandlerBase)
  {
    String str = "com.ffusion.services.alerts.UAEAlerts.initialize";
    int i = 0;
    try
    {
      InitFileHandler localInitFileHandler = new InitFileHandler();
      i = localInitFileHandler.initialize(paramString, paramHandlerBase);
      if ((this.context_factory == null) || (this.provider_url == null)) {
        i = 19005;
      }
      this.ad = ((String)this.xmlHashMap.get("AppName"));
      this.ac = ((String)this.xmlHashMap.get("AppPassword"));
      this.appInfo = new AEApplicationInfo(this.ad, this.ac);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.out);
      DebugLog.throwing(str, localException);
      i = 19005;
    }
    return i;
  }
  
  public void initialize(HashMap paramHashMap)
    throws AlertsException
  {
    String str = "UAEAlerts.initialize";
    try
    {
      int i = initialize("alerts.xml");
      if (i != 0) {
        throw new AlertsException(str, i);
      }
      InputStream localInputStream = null;
      localInputStream = ResourceUtil.getResourceAsStream(this, "alerts.xml");
      int j = 0;
      int k = 0;
      ArrayList localArrayList = new ArrayList(3);
      while (k != -1)
      {
        arrayOfByte = new byte[1024];
        k = localInputStream.read(arrayOfByte, 0, 1024);
        if (k == -1) {
          break;
        }
        localArrayList.add(arrayOfByte);
        j += k;
      }
      localInputStream.close();
      byte[] arrayOfByte = new byte[j];
      int m = 0;
      localObject1 = localArrayList.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (byte[])((Iterator)localObject1).next();
        if (j - m > 1024) {
          k = 1024;
        } else {
          k = j - m;
        }
        System.arraycopy(localObject2, 0, arrayOfByte, m, k);
        m += k;
      }
      Object localObject2 = new XMLTag(true);
      ((XMLTag)localObject2).build(new String(arrayOfByte, 0, j));
      XMLTag localXMLTag1 = ((XMLTag)localObject2).getContainedTag("adapter");
      if (localXMLTag1 == null) {
        throw new AlertsException(str, 19005, "Missing adapter tag in the alerts.xml file.");
      }
      XMLTag localXMLTag2 = localXMLTag1.getContainedTag("ADAPTER_SETTINGS");
      if (localXMLTag2 == null) {
        throw new AlertsException(str, 19005, "Missing ADAPTER_SETTINGS tag in the alerts.xml file.");
      }
      HashMap localHashMap = XMLUtil.tagToHashMap(localXMLTag2);
      if (!localHashMap.containsKey("DB_PROPERTIES")) {
        localHashMap.put("DB_PROPERTIES", paramHashMap.get("DB_PROPERTIES"));
      }
      AlertAdapter.initialize(localHashMap);
    }
    catch (AlertsException localAlertsException)
    {
      DebugLog.throwing(str, localAlertsException);
      throw localAlertsException;
    }
    catch (Exception localException)
    {
      Object localObject1 = new AlertsException(str, 19005, localException);
      DebugLog.throwing(str, (Throwable)localObject1);
    }
  }
  
  public int getAlerts(Alerts paramAlerts)
  {
    String str = "com.ffusion.services.alerts.UAEAlerts.getAlerts";
    int i = 0;
    IAEAlertDefinition[] arrayOfIAEAlertDefinition = null;
    Locale localLocale = paramAlerts.getLocale();
    if (localLocale == null) {
      localLocale = new Locale("en", "us");
    }
    IAEAlertClient localIAEAlertClient = null;
    try
    {
      localIAEAlertClient = getHandler();
      if (localIAEAlertClient != null)
      {
        arrayOfIAEAlertDefinition = localIAEAlertClient.getUserAlerts(this.appInfo, this.ae, true);
        if (arrayOfIAEAlertDefinition != null) {
          for (int j = 0; j < arrayOfIAEAlertDefinition.length; j++)
          {
            Alert localAlert = convertAlert(arrayOfIAEAlertDefinition[j], localLocale);
            paramAlerts.add(localAlert);
          }
        }
      }
      else
      {
        i = 19006;
      }
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.throwing(str, localRemoteException);
      i = 19006;
    }
    catch (AEException localAEException)
    {
      DebugLog.throwing(str, localAEException);
      i = mapError(localAEException.getErrorCode());
    }
    finally
    {
      if (localIAEAlertClient != null) {
        removeHandler(localIAEAlertClient);
      }
    }
    return i;
  }
  
  public int addAlert(Alert paramAlert)
  {
    String str = "com.ffusion.services.alerts.UAEAlerts.addAlert";
    int i = 0;
    if ((paramAlert == null) || (paramAlert.getStartDateValue() == null) || (paramAlert.getEndDateValue() == null))
    {
      i = 19012;
      return i;
    }
    AEScheduleInfo localAEScheduleInfo = new AEScheduleInfo(paramAlert.getStartDateValue().getTime(), paramAlert.getEndDateValue().getTime(), paramAlert.getIntervalValue(), paramAlert.getIntervalType(), paramAlert.getRegularTimeZoneValue(), paramAlert.getRespectDST());
    int k = paramAlert.getDeliveryInfosValue().size();
    Properties[] arrayOfProperties = new Properties[k];
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = new IAEDeliveryInfo[k];
    for (int m = 0; m < k; m++)
    {
      DeliveryInfo localDeliveryInfo = (DeliveryInfo)paramAlert.getDeliveryInfosValue().get(m);
      arrayOfProperties[m] = new Properties();
      arrayOfProperties[m] = localDeliveryInfo.getPropertiesValue();
      arrayOfIAEDeliveryInfo[m] = AEDeliveryInfoFactory.create(localDeliveryInfo.getChannelName(), localDeliveryInfo.getOrderValue(), localDeliveryInfo.getMaxDelayValue(), localDeliveryInfo.getPropertiesValue());
      arrayOfIAEDeliveryInfo[m].setSuspended(localDeliveryInfo.getSuspendedValue());
    }
    IAEAlertClient localIAEAlertClient = null;
    try
    {
      localIAEAlertClient = getHandler();
      if (localIAEAlertClient != null)
      {
        int j = localIAEAlertClient.createAlert(this.appInfo, this.ae, paramAlert.getTypeValue(), paramAlert.getPriorityValue(), localAEScheduleInfo, arrayOfIAEDeliveryInfo, paramAlert.getMessage());
        paramAlert.setID(String.valueOf(j));
      }
      else
      {
        i = 19006;
      }
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.throwing(str, localRemoteException);
      i = 19006;
    }
    catch (AEException localAEException)
    {
      DebugLog.throwing(str, localAEException);
      i = mapError(localAEException.getErrorCode());
    }
    finally
    {
      if (localIAEAlertClient != null) {
        removeHandler(localIAEAlertClient);
      }
    }
    return i;
  }
  
  public int deleteAlert(Alert paramAlert)
  {
    String str = "com.ffusion.services.alerts.UAEAlerts.deleteAlert";
    int i = 0;
    IAEAlertClient localIAEAlertClient = null;
    try
    {
      localIAEAlertClient = getHandler();
      if (localIAEAlertClient != null) {
        localIAEAlertClient.cancelAlert(this.appInfo, Integer.parseInt(paramAlert.getID()));
      } else {
        i = 19006;
      }
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.throwing(str, localRemoteException);
      i = 19006;
    }
    catch (AEException localAEException)
    {
      DebugLog.throwing(str, localAEException);
      i = mapError(localAEException.getErrorCode());
    }
    finally
    {
      if (localIAEAlertClient != null) {
        removeHandler(localIAEAlertClient);
      }
    }
    return i;
  }
  
  public int modifyAlert(Alert paramAlert)
  {
    String str = "com.ffusion.services.alerts.UAEAlerts.modifyAlert";
    int i = 0;
    IAEAlertDefinition localIAEAlertDefinition = null;
    if ((paramAlert == null) || (paramAlert.getStartDateValue() == null) || (paramAlert.getEndDateValue() == null))
    {
      i = 19012;
      return i;
    }
    AEScheduleInfo localAEScheduleInfo = new AEScheduleInfo(paramAlert.getStartDateValue().getTime(), paramAlert.getEndDateValue().getTime(), paramAlert.getIntervalValue(), paramAlert.getIntervalType(), paramAlert.getRegularTimeZoneValue(), paramAlert.getRespectDST());
    Locale localLocale = paramAlert.getLocale();
    if (localLocale == null) {
      localLocale = new Locale("en", "us");
    }
    int j = paramAlert.getDeliveryInfosValue().size();
    Properties[] arrayOfProperties = new Properties[j];
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = new IAEDeliveryInfo[j];
    Object localObject1;
    for (int k = 0; k < j; k++)
    {
      localObject1 = (DeliveryInfo)paramAlert.getDeliveryInfosValue().get(k);
      arrayOfProperties[k] = new Properties();
      arrayOfProperties[k] = ((DeliveryInfo)localObject1).getPropertiesValue();
      arrayOfIAEDeliveryInfo[k] = AEDeliveryInfoFactory.create(((DeliveryInfo)localObject1).getChannelName(), ((DeliveryInfo)localObject1).getOrderValue(), ((DeliveryInfo)localObject1).getMaxDelayValue(), ((DeliveryInfo)localObject1).getPropertiesValue());
      arrayOfIAEDeliveryInfo[k].setSuspended(((DeliveryInfo)localObject1).getSuspendedValue());
    }
    IAEAlertClient localIAEAlertClient = null;
    try
    {
      localIAEAlertClient = getHandler();
      if (localIAEAlertClient != null)
      {
        localIAEAlertDefinition = localIAEAlertClient.updateAlert(this.appInfo, Integer.parseInt(paramAlert.getID()), this.ae, paramAlert.getTypeValue(), paramAlert.getPriorityValue(), localAEScheduleInfo, arrayOfIAEDeliveryInfo, paramAlert.getMessage());
        localObject1 = convertAlert(localIAEAlertDefinition, localLocale);
        paramAlert.set((Alert)localObject1);
      }
      else
      {
        i = 19006;
      }
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.throwing(str, localRemoteException);
      i = 19006;
    }
    catch (AEException localAEException)
    {
      DebugLog.throwing(str, localAEException);
      i = mapError(localAEException.getErrorCode());
    }
    finally
    {
      if (localIAEAlertClient != null) {
        removeHandler(localIAEAlertClient);
      }
    }
    return i;
  }
  
  public int sendAlertToUser(String paramString1, String paramString2)
  {
    String str1 = "com.ffusion.services.alerts.UAEAlerts.sendAlertToUser";
    int i = 0;
    Date localDate1 = new Date(System.currentTimeMillis() + 60000L);
    Date localDate2 = new Date(localDate1.getTime() + 300000L);
    long l = 60000L;
    TimeZone localTimeZone = TimeZone.getDefault();
    AEScheduleInfo localAEScheduleInfo = new AEScheduleInfo(localDate1, localDate2, l, 0, localTimeZone, true);
    if ((paramString1 == null) || (paramString1.trim().length() == 0)) {
      return 19001;
    }
    IAEAlertClient localIAEAlertClient = null;
    try
    {
      localIAEAlertClient = getHandler();
      if (localIAEAlertClient != null)
      {
        IAEAlertDefinition[] arrayOfIAEAlertDefinition = localIAEAlertClient.getUserAlerts(this.appInfo, paramString1, true);
        if ((arrayOfIAEAlertDefinition == null) || (arrayOfIAEAlertDefinition.length == 0))
        {
          k = 19008;
          return k;
        }
        int k = 0;
        int m = 0;
        IAEAlertDefinition localIAEAlertDefinition = null;
        for (int n = 0; n < arrayOfIAEAlertDefinition.length; n++)
        {
          for (int i1 = 0; i1 < arrayOfIAEAlertDefinition[n].getDeliveryInfo().length; i1++) {
            if (arrayOfIAEAlertDefinition[n].getDeliveryInfo()[i1].getDeliveryChannelName().equals("BankMessage"))
            {
              localIAEAlertDefinition = arrayOfIAEAlertDefinition[n];
              k = i1;
              m = 1;
              break;
            }
          }
          if (m != 0) {
            break;
          }
        }
        if (localIAEAlertDefinition == null)
        {
          n = 19008;
          return n;
        }
        String str2 = null;
        if ((paramString2 == null) || (paramString2.trim().length() == 0)) {
          str2 = localIAEAlertDefinition.getMessage();
        } else {
          str2 = paramString2;
        }
        IAEDeliveryInfo[] arrayOfIAEDeliveryInfo1 = localIAEAlertDefinition.getDeliveryInfo();
        IAEDeliveryInfo[] arrayOfIAEDeliveryInfo2 = new IAEDeliveryInfo[arrayOfIAEDeliveryInfo1.length];
        for (int i2 = 0; i2 < arrayOfIAEDeliveryInfo1.length; i2++) {
          if (i2 == k)
          {
            IAEDeliveryInfo localIAEDeliveryInfo = arrayOfIAEDeliveryInfo1[i2];
            localIAEDeliveryInfo.setDeliveryOrder(localIAEDeliveryInfo.getDeliveryOrder() + 1);
            arrayOfIAEDeliveryInfo2[i2] = localIAEDeliveryInfo;
          }
          else
          {
            arrayOfIAEDeliveryInfo2[i2] = arrayOfIAEDeliveryInfo1[i2];
          }
        }
        int j = localIAEAlertClient.createAlert(this.appInfo, paramString1, 0, 1, localAEScheduleInfo, arrayOfIAEDeliveryInfo2, str2);
      }
      else
      {
        i = 19006;
      }
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.throwing(str1, localRemoteException);
      i = 19006;
    }
    catch (AEException localAEException)
    {
      DebugLog.throwing(str1, localAEException);
      i = mapError(localAEException.getErrorCode());
    }
    finally
    {
      if (localIAEAlertClient != null) {
        removeHandler(localIAEAlertClient);
      }
    }
    return i;
  }
  
  public int getLogMessages(LogMessages paramLogMessages)
  {
    String str1 = "com.ffusion.services.alerts.UAEAlerts.getLogMessages";
    int i = 0;
    Locale localLocale = paramLogMessages.getLocale();
    if (localLocale == null) {
      localLocale = new Locale("en", "us");
    }
    IAEAlertClient localIAEAlertClient = null;
    try
    {
      localIAEAlertClient = getHandler();
      if (localIAEAlertClient != null)
      {
        Date localDate1 = new Date(System.currentTimeMillis() - 7776000000L);
        Date localDate2 = new Date(System.currentTimeMillis());
        IAEAuditInfo[] arrayOfIAEAuditInfo = localIAEAlertClient.getUserAuditInfo(this.appInfo, this.ae, localDate1, localDate2);
        int j = arrayOfIAEAuditInfo.length;
        for (int k = 0; k < j; k++)
        {
          LogMessage localLogMessage = new LogMessage(localLocale);
          localLogMessage.setAlertID(String.valueOf(arrayOfIAEAuditInfo[k].getAlertId()));
          localLogMessage.setStatus(String.valueOf(arrayOfIAEAuditInfo[k].getAuditStatus()));
          localLogMessage.setTimeInfo(arrayOfIAEAuditInfo[k].getTimeInfo());
          Date localDate3 = arrayOfIAEAuditInfo[k].getAuditDate();
          Calendar localCalendar = Calendar.getInstance(localLocale);
          localCalendar.setTime(localDate3);
          localLogMessage.setDate(new DateTime(localCalendar, localLocale));
          IAEDeliveryInfo localIAEDeliveryInfo = arrayOfIAEAuditInfo[k].getDeliveryInfo();
          String str2 = arrayOfIAEAuditInfo[k].getChannelName();
          if ((localIAEDeliveryInfo != null) && (str2 != null) && ((str2.equalsIgnoreCase("Email")) || (str2.equalsIgnoreCase("MessageCenter"))))
          {
            localLogMessage.setDeliveryChannel(str2);
            localLogMessage.setType(localIAEDeliveryInfo.getDeliveryProperties().getProperty("AlertType", "unknown"));
            if (str2.equalsIgnoreCase("Email"))
            {
              String str3 = localIAEDeliveryInfo.getDeliveryProperties().getProperty("to");
              if ((str3 != null) && (str3.length() != 0)) {
                localLogMessage.setDeliveryChannel("Email (" + str3 + ")");
              }
            }
            else
            {
              paramLogMessages.add(localLogMessage);
            }
          }
        }
      }
      else
      {
        i = 19006;
      }
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.throwing(str1, localRemoteException);
      i = 19006;
    }
    catch (AEException localAEException)
    {
      DebugLog.throwing(str1, localAEException);
      i = mapError(localAEException.getErrorCode());
    }
    finally
    {
      if (localIAEAlertClient != null) {
        removeHandler(localIAEAlertClient);
      }
    }
    return i;
  }
  
  protected Alert convertAlert(IAEAlertDefinition paramIAEAlertDefinition, Locale paramLocale)
  {
    Alert localAlert = new Alert(paramLocale);
    localAlert.setID(String.valueOf(paramIAEAlertDefinition.getId()));
    localAlert.setType(paramIAEAlertDefinition.getType());
    localAlert.setPriority(paramIAEAlertDefinition.getPriority());
    Date localDate1 = paramIAEAlertDefinition.getScheduleInfo().getStartDate();
    Calendar localCalendar1 = Calendar.getInstance(paramIAEAlertDefinition.getScheduleInfo().getRegularTimeZone(), paramLocale);
    localCalendar1.setTime(localDate1);
    Date localDate2 = paramIAEAlertDefinition.getScheduleInfo().getEndDate();
    Calendar localCalendar2 = Calendar.getInstance(paramIAEAlertDefinition.getScheduleInfo().getRegularTimeZone(), paramLocale);
    localCalendar2.setTime(localDate2);
    localAlert.setStartDate(new DateTime(localCalendar1, paramLocale));
    localAlert.setEndDate(new DateTime(localCalendar2, paramLocale));
    localAlert.setInterval(paramIAEAlertDefinition.getScheduleInfo().getInterval());
    localAlert.setTimeZone(paramIAEAlertDefinition.getScheduleInfo().getRegularTimeZone());
    localAlert.setAppInfo(paramIAEAlertDefinition.getApplicationName());
    int i = paramIAEAlertDefinition.getDeliveryInfo().length;
    DeliveryInfos localDeliveryInfos = new DeliveryInfos();
    for (int j = 0; j < i; j++)
    {
      DeliveryInfo localDeliveryInfo = new DeliveryInfo(localAlert.getLocale());
      localDeliveryInfo.setID(String.valueOf(j + 1));
      localDeliveryInfo.setChannelName(paramIAEAlertDefinition.getDeliveryInfo()[j].getDeliveryChannelName());
      localDeliveryInfo.setOrder(paramIAEAlertDefinition.getDeliveryInfo()[j].getDeliveryOrder());
      localDeliveryInfo.setMaxDelay(String.valueOf(paramIAEAlertDefinition.getDeliveryInfo()[j].getMaxDelay()));
      localDeliveryInfo.setProperties(paramIAEAlertDefinition.getDeliveryInfo()[j].getDeliveryProperties());
      if (paramIAEAlertDefinition.getDeliveryInfo()[j].isSuspended()) {
        localDeliveryInfo.setSuspended(true);
      }
      localDeliveryInfos.add(localDeliveryInfo);
    }
    localAlert.setDeliveryInfos(localDeliveryInfos);
    localAlert.setMessage(paramIAEAlertDefinition.getMessage());
    return localAlert;
  }
  
  protected IAEAlertClient getHandler()
  {
    String str1 = "com.ffusion.services.alerts.UAEAlerts.getHandler";
    IAEAlertClient localIAEAlertClient = null;
    ContextPool localContextPool = null;
    Context localContext = null;
    for (int i = 0; i < this.provider_url_list.size(); i++)
    {
      String str2 = (String)this.provider_url_list.get(i);
      try
      {
        localContextPool = getContextPoolFromList(str2);
        localContext = localContextPool.getContext();
        if (localContext != null)
        {
          Object localObject1 = localContext.lookup(this.callback_JNDI_name);
          if ((localObject1 instanceof IAEAlertClientHome)) {
            localObject2 = (IAEAlertClientHome)localObject1;
          } else {
            localObject2 = (IAEAlertClientHome)PortableRemoteObject.narrow(localObject1, IAEAlertClientHome.class);
          }
          if (localObject2 != null) {
            localIAEAlertClient = ((IAEAlertClientHome)localObject2).create();
          }
          localContextPool.returnContext(localContext);
          break;
        }
      }
      catch (Throwable localThrowable1)
      {
        Object localObject2;
        DebugLog.throwing(str1 + "in first catch", localThrowable1);
        try
        {
          localContext = localContextPool.refreshContext(localContext);
          if (localContext != null)
          {
            localObject2 = localContext.lookup(this.callback_JNDI_name);
            IAEAlertClientHome localIAEAlertClientHome;
            if ((localObject2 instanceof IAEAlertClientHome)) {
              localIAEAlertClientHome = (IAEAlertClientHome)localObject2;
            } else {
              localIAEAlertClientHome = (IAEAlertClientHome)PortableRemoteObject.narrow(localObject2, IAEAlertClientHome.class);
            }
            if (localIAEAlertClientHome != null) {
              localIAEAlertClient = localIAEAlertClientHome.create();
            }
            localContextPool.returnContext(localContext);
            break;
          }
        }
        catch (Throwable localThrowable2)
        {
          DebugLog.throwing(str1 + "in second catch", localThrowable2);
          if ((localContextPool != null) && (localContext != null)) {
            localContextPool.returnContext(localContext);
          }
        }
      }
    }
    return localIAEAlertClient;
  }
  
  protected void removeHandler(IAEAlertClient paramIAEAlertClient)
  {
    String str = "com.ffusion.services.alerts.UAEAlerts.removeHandler";
    try
    {
      paramIAEAlertClient.remove();
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
    }
  }
  
  protected int mapError(int paramInt)
  {
    int i = 19009;
    switch (paramInt)
    {
    case 1007: 
      i = 19010;
      break;
    case 1023: 
      i = 19011;
    }
    return i;
  }
  
  public void addUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws AlertsException
  {
    a(paramUserAlert);
    AlertAdapter.addUserAlert(paramSecureUser, paramUserAlert, paramHashMap);
  }
  
  public void modifyUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws AlertsException
  {
    a(paramUserAlert);
    AlertAdapter.modifyUserAlert(paramSecureUser, paramUserAlert, paramHashMap);
  }
  
  public void deleteUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws AlertsException
  {
    AlertAdapter.deleteUserAlert(paramSecureUser, paramUserAlert, paramHashMap);
  }
  
  public UserAlerts getUserAlerts(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws AlertsException
  {
    IntMap localIntMap = new IntMap();
    UserAlerts localUserAlerts = AlertAdapter.getUserAlerts(paramSecureUser, paramInt, paramHashMap);
    if (localUserAlerts != null)
    {
      SecureUser localSecureUser = jdMethod_do(paramInt);
      Iterator localIterator = localUserAlerts.iterator();
      while (localIterator.hasNext())
      {
        UserAlert localUserAlert = (UserAlert)localIterator.next();
        localUserAlert.setAdditionalProperty("SECUREUSER", localSecureUser.getXML());
        a(localSecureUser, localUserAlert, localIntMap);
      }
    }
    return localUserAlerts;
  }
  
  public UserAlert getUserAlert(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws AlertsException
  {
    IntMap localIntMap = new IntMap();
    UserAlert localUserAlert = AlertAdapter.getUserAlert(paramSecureUser, paramInt, paramHashMap);
    SecureUser localSecureUser = jdMethod_do(localUserAlert.getDirectoryIdValue());
    localUserAlert.setAdditionalProperty("SECUREUSER", localSecureUser.toXML());
    a(localSecureUser, localUserAlert, localIntMap);
    return localUserAlert;
  }
  
  public ArrayList getAlertsForAccounts(Accounts paramAccounts, HashMap paramHashMap)
    throws AlertsException
  {
    String str = "UAEAlerts.getAlertsForAccounts";
    IntMap localIntMap = new IntMap();
    ArrayList localArrayList = AlertAdapter.getAlertsForAccounts(paramAccounts, paramHashMap);
    if (localArrayList != null)
    {
      MultiEntitlement localMultiEntitlement = new MultiEntitlement();
      localMultiEntitlement.setOperations(new String[] { "Access" });
      String[] arrayOfString = { "Account" };
      for (int i = 0; i < localArrayList.size(); i++)
      {
        UserAlerts localUserAlerts = (UserAlerts)localArrayList.get(i);
        if (localUserAlerts != null)
        {
          localMultiEntitlement.setObjects(arrayOfString, new String[] { EntitlementsUtil.getEntitlementObjectId((Account)paramAccounts.get(i)) });
          Iterator localIterator = localUserAlerts.iterator();
          while (localIterator.hasNext())
          {
            UserAlert localUserAlert = (UserAlert)localIterator.next();
            try
            {
              SecureUser localSecureUser = jdMethod_do(localUserAlert.getDirectoryIdValue());
              EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
              if (EntitlementsUtil.checkAccountAndAccountGroupEntitlement(localEntitlementGroupMember, localMultiEntitlement, localSecureUser.getBusinessID()) == null)
              {
                localUserAlert.setAdditionalProperty("SECUREUSER", localSecureUser.getXML());
                a(localSecureUser, localUserAlert, localIntMap);
              }
              else
              {
                localIterator.remove();
              }
            }
            catch (AlertsException localAlertsException)
            {
              DebugLog.throwing(str, localAlertsException);
              localIterator.remove();
            }
            catch (CSILException localCSILException)
            {
              DebugLog.throwing(str, localCSILException);
              localIterator.remove();
            }
          }
          if (localUserAlerts.size() == 0) {
            localArrayList.set(i, null);
          }
        }
      }
    }
    return localArrayList;
  }
  
  public UserAlerts getPagedUserAlerts(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws AlertsException
  {
    IntMap localIntMap = new IntMap();
    UserAlerts localUserAlerts = AlertAdapter.getPagedUserAlerts(paramInt1, paramInt2, paramInt3, paramHashMap);
    if (localUserAlerts != null)
    {
      Iterator localIterator = localUserAlerts.iterator();
      while (localIterator.hasNext())
      {
        UserAlert localUserAlert = (UserAlert)localIterator.next();
        SecureUser localSecureUser = jdMethod_do(localUserAlert.getDirectoryIdValue());
        localUserAlert.setAdditionalProperty("SECUREUSER", localSecureUser.getXML());
        a(localSecureUser, localUserAlert, localIntMap);
      }
    }
    return localUserAlerts;
  }
  
  public void sendImmediateAlert(SecureUser paramSecureUser, ContactPoint paramContactPoint, String paramString1, String paramString2, HashMap paramHashMap)
    throws AlertsException
  {
    String str1 = "UAEAlerts.sendImmediateAlert";
    String str2 = AlertAdapter.getChannelName(paramContactPoint.getContactPointType());
    String str3 = (String)paramHashMap.get("from");
    String str4 = (String)paramHashMap.get("AlertType");
    if ((str3 == null) || (str3.length() == 0)) {
      try
      {
        BankMessageInfo localBankMessageInfo = Messages.getEmailSettings(paramSecureUser, paramContactPoint.getDirectoryID(), paramHashMap);
        str3 = localBankMessageInfo.getNotifyFromEmail();
      }
      catch (CSILException localCSILException)
      {
        throw new AlertsException(1, localCSILException);
      }
    }
    Properties localProperties = new Properties();
    localProperties.setProperty("to", paramContactPoint.getAddress());
    localProperties.setProperty("from", str3);
    localProperties.setProperty("subject", paramString1);
    if (str4 != null) {
      localProperties.setProperty("AlertType", str4);
    }
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = new IAEDeliveryInfo[1];
    arrayOfIAEDeliveryInfo[0] = AEDeliveryInfoFactory.create(str2, 1, -1, localProperties);
    IAEAlertClient localIAEAlertClient = null;
    try
    {
      String str5 = AlertUtil.getUserIdForAlert(paramSecureUser);
      localIAEAlertClient = getHandler();
      int i = localIAEAlertClient.createAlert(this.appInfo, str5, 0, 1, null, arrayOfIAEDeliveryInfo, paramString2);
      DebugLog.log(Level.FINE, str1 + ": successfully created a new alert with ID " + i + " for user " + str5, this);
    }
    catch (AEException localAEException)
    {
      localAlertsException = new AlertsException(str1, mapError(localAEException.getErrorCode()), localAEException);
      DebugLog.throwing(str1, localAlertsException);
      throw localAlertsException;
    }
    catch (Exception localException)
    {
      AlertsException localAlertsException = new AlertsException(str1, 1, localException);
      DebugLog.throwing(str1, localAlertsException);
      throw localAlertsException;
    }
    finally
    {
      if (localIAEAlertClient != null) {
        removeHandler(localIAEAlertClient);
      }
    }
  }
  
  public LogMessages getLogMessages(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws AlertsException
  {
    String str1 = "UAEAlerts.getLogMessatges";
    IAEAlertClient localIAEAlertClient = null;
    Locale localLocale = paramSecureUser.getLocale();
    LogMessages localLogMessages = new LogMessages(localLocale);
    try
    {
      String str2 = AlertUtil.getUserIdForAlert(paramSecureUser);
      localIAEAlertClient = getHandler();
      localObject1 = new Date(System.currentTimeMillis());
      Date localDate1 = new Date(((Date)localObject1).getTime() - paramInt * 86400000L);
      IAEAuditInfo[] arrayOfIAEAuditInfo = localIAEAlertClient.getUserAuditInfo(this.appInfo, str2, localDate1, (Date)localObject1);
      for (int i = 0; i < arrayOfIAEAuditInfo.length; i++) {
        if (arrayOfIAEAuditInfo[i].getAuditType() == 2)
        {
          LogMessage localLogMessage = new LogMessage(localLocale);
          localLogMessage.setAlertID(String.valueOf(arrayOfIAEAuditInfo[i].getAlertId()));
          localLogMessage.setStatus(String.valueOf(arrayOfIAEAuditInfo[i].getAuditStatus()));
          localLogMessage.setTimeInfo(arrayOfIAEAuditInfo[i].getTimeInfo());
          Date localDate2 = arrayOfIAEAuditInfo[i].getAuditDate();
          localLogMessage.setDate(new DateTime(localDate2, localLocale));
          IAEDeliveryInfo localIAEDeliveryInfo = arrayOfIAEAuditInfo[i].getDeliveryInfo();
          String str3 = localIAEDeliveryInfo.getDeliveryProperties().getProperty("to");
          String str4 = arrayOfIAEAuditInfo[i].getChannelName() + ((str3 == null) || (str3.length() == 0) ? "" : new StringBuffer().append(" (").append(str3).append(")").toString());
          localLogMessage.setDeliveryChannel(str4);
          localLogMessage.setType(localIAEDeliveryInfo.getDeliveryProperties().getProperty("AlertType", "unknown"));
          localLogMessages.add(localLogMessage);
        }
      }
    }
    catch (AEException localAEException)
    {
      localObject1 = new AlertsException(str1, mapError(localAEException.getErrorCode()), localAEException);
      DebugLog.throwing(str1, (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
    catch (Exception localException)
    {
      Object localObject1 = new AlertsException(str1, 1, localException);
      DebugLog.throwing(str1, (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
    finally
    {
      if (localIAEAlertClient != null) {
        removeHandler(localIAEAlertClient);
      }
    }
    return localLogMessages;
  }
  
  private void a(UserAlert paramUserAlert)
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();
    DeliveryInfos localDeliveryInfos = paramUserAlert.getDeliveryInfos();
    if (localDeliveryInfos != null)
    {
      Iterator localIterator = localDeliveryInfos.iterator();
      while (localIterator.hasNext())
      {
        DeliveryInfo localDeliveryInfo = (DeliveryInfo)localIterator.next();
        Properties localProperties = localDeliveryInfo.getPropertiesValue();
        String str = localProperties.getProperty("ContactPointID");
        int i = localDeliveryInfo.getOrderValue();
        if (i == 1)
        {
          if (localStringBuffer1.length() > 0) {
            localStringBuffer1.append(',');
          }
          localStringBuffer1.append(str);
        }
        else
        {
          if (localStringBuffer2.length() > 0) {
            localStringBuffer2.append(',');
          }
          localStringBuffer2.append(str);
        }
      }
    }
    if (localStringBuffer1.length() > 0) {
      paramUserAlert.setAdditionalProperty("PrimaryContacts", localStringBuffer1.toString());
    } else {
      paramUserAlert.removeAdditionalProperty("PrimaryContacts");
    }
    if (localStringBuffer2.length() > 0) {
      paramUserAlert.setAdditionalProperty("SecondaryContacts", localStringBuffer2.toString());
    } else {
      paramUserAlert.removeAdditionalProperty("SecondaryContacts");
    }
  }
  
  private void a(SecureUser paramSecureUser, UserAlert paramUserAlert, IntMap paramIntMap)
  {
    int i = paramUserAlert.getDirectoryIdValue();
    ContactPoints localContactPoints = (ContactPoints)paramIntMap.get(i);
    if ((localContactPoints == null) && (!paramIntMap.containsKey(i)))
    {
      try
      {
        localContactPoints = CustomerAdapter.getContactPoints(paramSecureUser, i, null);
        if ((localContactPoints != null) && (localContactPoints.size() == 0)) {
          localContactPoints = null;
        }
      }
      catch (ProfileException localProfileException) {}
      paramIntMap.put(i, localContactPoints);
    }
    DeliveryInfos localDeliveryInfos = new DeliveryInfos(paramUserAlert.getLocale());
    if (localContactPoints != null)
    {
      String str1 = paramUserAlert.getAdditionalProperty("PrimaryContacts");
      a(paramSecureUser, str1, 1, localDeliveryInfos, localContactPoints, paramUserAlert.getAdditionalProperties());
      String str2 = paramUserAlert.getAdditionalProperty("SecondaryContacts");
      a(paramSecureUser, str2, 2, localDeliveryInfos, localContactPoints, paramUserAlert.getAdditionalProperties());
    }
    if (localDeliveryInfos.size() == 0) {
      addDefaultDeliveryInfo(paramSecureUser, localDeliveryInfos);
    }
    paramUserAlert.setDeliveryInfos(localDeliveryInfos);
  }
  
  private void a(SecureUser paramSecureUser, String paramString, int paramInt, DeliveryInfos paramDeliveryInfos, ContactPoints paramContactPoints, Properties paramProperties)
  {
    if (paramString == null) {
      return;
    }
    Locale localLocale = paramDeliveryInfos.getLocale();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      int i;
      try
      {
        i = Integer.parseInt(str);
      }
      catch (NumberFormatException localNumberFormatException) {}
      continue;
      ContactPoint localContactPoint = paramContactPoints.getContactPointByID(i);
      if (localContactPoint != null)
      {
        Properties localProperties = new Properties();
        localProperties.setProperty("ContactPointID", str);
        DeliveryInfo localDeliveryInfo = new DeliveryInfo(localLocale);
        localDeliveryInfo.setProperties(localProperties);
        localDeliveryInfo.setOrder(paramInt);
        if (loadDeliveryInfoFromContactPoint(paramSecureUser, localDeliveryInfo, localContactPoint, paramProperties)) {
          paramDeliveryInfos.add(localDeliveryInfo);
        }
      }
    }
  }
  
  protected boolean loadDeliveryInfoFromContactPoint(SecureUser paramSecureUser, DeliveryInfo paramDeliveryInfo, ContactPoint paramContactPoint, Properties paramProperties)
  {
    try
    {
      paramDeliveryInfo.setChannelName(AlertAdapter.getChannelName(paramContactPoint.getContactPointType()));
    }
    catch (AlertsException localAlertsException)
    {
      return false;
    }
    Properties localProperties = paramDeliveryInfo.getPropertiesValue();
    localProperties.setProperty("secure", Boolean.toString(paramContactPoint.getSecure()));
    switch (paramContactPoint.getContactPointType())
    {
    case 1: 
      localProperties.setProperty("SECUREUSER", paramSecureUser.getXML());
      break;
    case 2: 
      String str = paramProperties.getProperty("from");
      if ((str == null) || (str.length() == 0)) {
        try
        {
          BankMessageInfo localBankMessageInfo = Messages.getEmailSettings(paramSecureUser, paramContactPoint.getDirectoryID(), new HashMap());
          str = localBankMessageInfo.getNotifyFromEmail();
        }
        catch (CSILException localCSILException)
        {
          DebugLog.throwing("Unable to retrieve e-mail address.", localCSILException);
          return false;
        }
      }
      localProperties.setProperty("to", paramContactPoint.getAddress());
      localProperties.setProperty("from", str);
    }
    paramDeliveryInfo.setProperties(localProperties);
    return true;
  }
  
  protected void addDefaultDeliveryInfo(SecureUser paramSecureUser, DeliveryInfos paramDeliveryInfos)
  {
    Properties localProperties = new Properties();
    localProperties.setProperty("SECUREUSER", paramSecureUser.getXML());
    localProperties.setProperty("secure", "True");
    DeliveryInfo localDeliveryInfo = new DeliveryInfo(paramDeliveryInfos.getLocale());
    localDeliveryInfo.setOrder(1);
    localDeliveryInfo.setMaxDelay(-1);
    localDeliveryInfo.setProperties(localProperties);
    try
    {
      localDeliveryInfo.setChannelName(AlertAdapter.getChannelName(1));
    }
    catch (AlertsException localAlertsException)
    {
      return;
    }
    paramDeliveryInfos.add(localDeliveryInfo);
  }
  
  private SecureUser jdMethod_do(int paramInt)
    throws AlertsException
  {
    SecureUser localSecureUser = null;
    try
    {
      User localUser = CustomerAdapter.getUserById(paramInt);
      if (localUser.getAccountStatus().equals("8")) {
        throw new AlertsException(8);
      }
      EntitlementsUtil.fillUserEntitlementInfo(localUser);
      localSecureUser = localUser.getSecureUser();
      localSecureUser.setLocale(localUser.getPreferredLanguage());
    }
    catch (AlertsException localAlertsException)
    {
      throw localAlertsException;
    }
    catch (Exception localException)
    {
      throw new AlertsException(1, localException);
    }
    return localSecureUser;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.alerts.UAEAlerts
 * JD-Core Version:    0.7.0.1
 */