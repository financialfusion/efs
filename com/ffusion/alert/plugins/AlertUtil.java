package com.ffusion.alert.plugins;

import com.ffusion.alert.factory.AEDeliveryInfoFactory;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.CSILEJBHome;
import com.ffusion.csil.ejb.ICSILEJB;
import com.ffusion.util.HfnEncrypt;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public class AlertUtil
{
  private static final String jdField_if = "com.ibm.websphere.naming.jndicache.cacheobject";
  private static final String a = "cleared";
  public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  public static SimpleDateFormat sdfMMDDYYYY = new SimpleDateFormat("MM/dd/yyyy");
  public static SimpleDateFormat sdfHHMMSS = new SimpleDateFormat("HH:mm:ss");
  
  public static void logEntry(String paramString, boolean paramBoolean, PrintWriter paramPrintWriter)
  {
    logEntry(null, paramString, paramBoolean, paramPrintWriter);
  }
  
  public static void logEntry(IAEAlertInstance paramIAEAlertInstance, String paramString, boolean paramBoolean, PrintWriter paramPrintWriter)
  {
    if ((paramBoolean) && (paramPrintWriter != null))
    {
      String str = sdf.format(new Date());
      StringBuffer localStringBuffer = new StringBuffer("[");
      localStringBuffer.append(str);
      if (paramIAEAlertInstance != null)
      {
        localStringBuffer.append(" id: ");
        localStringBuffer.append(paramIAEAlertInstance.getId());
        localStringBuffer.append(", seq: ");
        localStringBuffer.append(paramIAEAlertInstance.getSequence());
      }
      localStringBuffer.append("] ");
      localStringBuffer.append(paramString);
      paramPrintWriter.println(localStringBuffer.toString());
    }
  }
  
  public static void setProperty(Properties paramProperties, String paramString1, String paramString2)
  {
    if (paramProperties.containsKey(paramString1)) {
      paramProperties.remove(paramString1);
    }
    String str1 = paramString1.toLowerCase();
    if (paramProperties.containsKey(str1)) {
      paramProperties.remove(str1);
    }
    String str2 = paramString1.toUpperCase();
    if (paramProperties.containsKey(str2)) {
      paramProperties.remove(str2);
    }
    paramProperties.setProperty(paramString1, paramString2);
  }
  
  public static void setProperty(IAEAlertDefinition paramIAEAlertDefinition, String paramString1, String paramString2)
  {
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = paramIAEAlertDefinition.getDeliveryInfo();
    for (int i = 0; i < arrayOfIAEDeliveryInfo.length; i++)
    {
      Properties localProperties = arrayOfIAEDeliveryInfo[i].getDeliveryProperties();
      setProperty(localProperties, paramString1, paramString2);
      arrayOfIAEDeliveryInfo[i].setDeliveryProperties(localProperties);
    }
  }
  
  public static ICSILEJB connectToCSILEJB(Properties paramProperties)
    throws EJBException, NamingException, RemoteException, CreateException, CSILException
  {
    String str1 = paramProperties.getProperty("csil.provider.url", "iiop://localhost:900");
    String str2 = paramProperties.getProperty("csil.initial.context.factory", "com.ibm.websphere.naming.WsnInitialContextFactory");
    String str3 = paramProperties.getProperty("csil.jndi.name", "CSILEJBHome");
    String str4 = paramProperties.getProperty("csil.user");
    String str5 = null;
    if ((str4 == null) || (str4.length() <= 0)) {
      str4 = null;
    } else {
      try
      {
        str5 = HfnEncrypt.decryptHexEncode(paramProperties.getProperty("csil.password"));
        if (str5 == null) {
          str5 = paramProperties.getProperty("csil.password");
        }
      }
      catch (Exception localException)
      {
        str5 = paramProperties.getProperty("csil.password");
      }
    }
    Properties localProperties = new Properties();
    localProperties.put("java.naming.factory.initial", str2);
    localProperties.put("java.naming.provider.url", str1);
    if (str4 != null)
    {
      localProperties.put("java.naming.security.principal", str4);
      localProperties.put("java.naming.security.credentials", str5);
    }
    InitialContext localInitialContext = new InitialContext(localProperties);
    Object localObject = localInitialContext.lookup(str3);
    CSILEJBHome localCSILEJBHome = (CSILEJBHome)PortableRemoteObject.narrow(localObject, CSILEJBHome.class);
    ICSILEJB localICSILEJB = null;
    try
    {
      localICSILEJB = localCSILEJBHome.create();
    }
    catch (RemoteException localRemoteException)
    {
      if (isServerWebSphere(str2))
      {
        localProperties.setProperty("com.ibm.websphere.naming.jndicache.cacheobject", "cleared");
        localInitialContext = new InitialContext(localProperties);
        localObject = localInitialContext.lookup(str3);
        localCSILEJBHome = (CSILEJBHome)PortableRemoteObject.narrow(localObject, CSILEJBHome.class);
        localICSILEJB = localCSILEJBHome.create();
      }
      else
      {
        throw localRemoteException;
      }
    }
    return localICSILEJB;
  }
  
  public static void printProperties(String paramString, Properties paramProperties, PrintStream paramPrintStream)
  {
    paramPrintStream.println(new Date() + "\t" + paramString);
    if (paramProperties == null)
    {
      paramPrintStream.println(" null property ");
      return;
    }
    Enumeration localEnumeration = paramProperties.propertyNames();
    Vector localVector = new Vector();
    while (localEnumeration.hasMoreElements()) {
      localVector.add((String)localEnumeration.nextElement());
    }
    Iterator localIterator = localVector.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      paramPrintStream.println(str + "=" + paramProperties.getProperty(str));
    }
  }
  
  public static final String getDateString()
  {
    return sdf.format(new Date());
  }
  
  public static final String getDateString(DateFormat paramDateFormat)
  {
    return paramDateFormat.format(new Date());
  }
  
  public static final String getDateString(Date paramDate, DateFormat paramDateFormat)
  {
    return paramDateFormat.format(paramDate);
  }
  
  public static final String getUserIdForAlert(SecureUser paramSecureUser)
  {
    return String.valueOf(paramSecureUser.getProfileID());
  }
  
  public static final boolean isServerWebSphere(String paramString)
  {
    return (paramString != null) && (paramString.indexOf("com.ibm.websphere") >= 0);
  }
  
  public static IAEDeliveryInfo[] convertDeliveryInfos(DeliveryInfos paramDeliveryInfos)
  {
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = new IAEDeliveryInfo[paramDeliveryInfos.size()];
    Iterator localIterator = paramDeliveryInfos.iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      DeliveryInfo localDeliveryInfo = (DeliveryInfo)localIterator.next();
      arrayOfIAEDeliveryInfo[i] = AEDeliveryInfoFactory.create(localDeliveryInfo.getChannelName(), localDeliveryInfo.getOrderValue(), localDeliveryInfo.getMaxDelayValue(), localDeliveryInfo.getPropertiesValue());
    }
    return arrayOfIAEDeliveryInfo;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.AlertUtil
 * JD-Core Version:    0.7.0.1
 */