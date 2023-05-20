package com.ffusion.services.smartcalendar;

import com.ffusion.beans.SecureUser;
import com.ffusion.services.SmartCalendar2;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.smartcalendar.SCCalendar;
import com.ffusion.util.beans.smartcalendar.SCCalendars;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.smartcalendar.SCException;
import com.ffusion.util.smartcalendar.adapter.ISCAdapter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

public class SmartCalendarService
  implements SmartCalendar2
{
  public static final String SC_ADAPTER_KEY = "SCADAPTER";
  public static final String JAVA_CLASS = "JAVA_CLASS";
  public static final String SC_ADAPTER_SETTINGS = "SCADAPTER_SETTINGS";
  private ISCAdapter a = null;
  
  public void initialize()
    throws SCException
  {
    try
    {
      DebugLog.log(Level.INFO, "Processing Smart Calendar service config xml");
      HashMap localHashMap = XMLUtil.readXmlToHashMap(this, "smartcalendar.xml");
      localObject1 = (HashMap)localHashMap.get("SCADAPTER");
      if (localObject1 == null)
      {
        localObject2 = "The <SCADAPTER> tag was not found in the XML configuration file.";
        DebugLog.log(Level.SEVERE, (String)localObject2);
        throw new SCException(1, (String)localObject2);
      }
      a((HashMap)localObject1);
      DebugLog.log(Level.INFO, "Smart Calendar service initialized");
    }
    catch (Exception localException)
    {
      Object localObject1 = new StringWriter();
      Object localObject2 = new PrintWriter((Writer)localObject1);
      localException.printStackTrace((PrintWriter)localObject2);
      String str = "An error occurred while initializing the Smart Calendar Service: " + ((StringWriter)localObject1).toString();
      DebugLog.log(Level.SEVERE, str);
      throw new SCException(1, str, localException);
    }
  }
  
  public boolean isBankingDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.isBankingDay";
    a(str);
    return this.a.isBankingDay(paramSecureUser.getBankID(), paramBankIdentifier, paramDate, paramHashMap);
  }
  
  public boolean isHoliday(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.isHoliday";
    a(str);
    return this.a.isHoliday(paramSecureUser.getBankID(), paramBankIdentifier, paramDate, paramHashMap);
  }
  
  public Date getPreviousDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.getPreviousDay";
    a(str);
    return this.a.getPreviousDay(paramSecureUser.getBankID(), paramBankIdentifier, paramDate, paramHashMap);
  }
  
  public Date getOffsetBankingDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, int paramInt, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.getOffsetBankingDay";
    a(str);
    return this.a.getOffsetBankingDay(paramSecureUser.getBankID(), paramBankIdentifier, paramDate, paramInt, paramHashMap);
  }
  
  public Date getTransactionDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.getTransactionDay";
    a(str);
    return this.a.getTransactionDay(paramSecureUser.getBankID(), paramBankIdentifier, paramDate, paramHashMap);
  }
  
  public void deleteCalendarHolidaysForDate(SecureUser paramSecureUser, SCCalendar paramSCCalendar, Date paramDate, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.deleteCalendarHolidaysForDate";
    a(str);
    this.a.deleteCalendarHolidaysForDate(paramSCCalendar, paramDate, paramHashMap);
  }
  
  public SCCalendar addCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.addCalendar";
    a(str);
    return this.a.addCalendar(paramSecureUser.getBankID(), paramSCCalendar, paramHashMap);
  }
  
  public void modifyCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.modifyCalendar";
    a(str);
    this.a.modifyCalendar(paramSCCalendar, paramHashMap);
  }
  
  public void modifyCalendarName(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.modifyCalendarName";
    a(str);
    this.a.modifyCalendarName(paramSCCalendar, paramHashMap);
  }
  
  public void modifyCalendarBusinessDays(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.modifyCalendarBusinessDays";
    a(str);
    this.a.modifyCalendarBusinessDays(paramSCCalendar, paramHashMap);
  }
  
  public void modifyCalendarHolidays(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.modifyCalendarHolidays";
    a(str);
    this.a.modifyCalendarHolidays(paramSCCalendar, paramHashMap);
  }
  
  public void deleteCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.deleteCalendar";
    a(str);
    this.a.deleteCalendar(paramSCCalendar, paramHashMap);
  }
  
  public boolean isCalendarInUse(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.isCalendarInUse";
    a(str);
    return this.a.isCalendarInUse(paramSCCalendar, paramHashMap);
  }
  
  public SCCalendar getCalendar(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.getCalendar";
    a(str);
    return this.a.getCalendar(paramInt, paramHashMap);
  }
  
  public SCCalendar getCalendarForBank(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.getCalendarForBank";
    a(str);
    return this.a.getCalendarForBank(paramSecureUser.getBankID(), paramBankIdentifier, paramHashMap);
  }
  
  public SCCalendar resolveCalendarForBank(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.resolveCalendarForBank";
    a(str);
    return this.a.resolveCalendarForBank(paramSecureUser.getBankID(), paramBankIdentifier, paramHashMap);
  }
  
  public SCCalendar getCalendarForName(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.getCalendarForName";
    a(str);
    return this.a.getCalendarForName(paramString, paramSecureUser.getBankID(), paramHashMap);
  }
  
  public SCCalendars getCalendars(SecureUser paramSecureUser, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.getCalendars";
    a(str);
    return this.a.getCalendars(paramSecureUser.getBankID(), paramHashMap);
  }
  
  public void setCalendarForBank(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.setCalendarForBank";
    a(str);
    this.a.setCalendarForBank(paramSecureUser.getBankID(), paramBankIdentifier, paramSCCalendar, paramHashMap);
  }
  
  public void setDefaultCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.setDefaultCalendar";
    a(str);
    this.a.setDefaultCalendar(paramSecureUser.getBankID(), paramSCCalendar, paramHashMap);
  }
  
  public SCCalendar getDefaultCalendar(SecureUser paramSecureUser, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.getDefaultCalendar";
    a(str);
    return this.a.getDefaultCalendar(paramSecureUser.getBankID(), paramHashMap);
  }
  
  private void a(HashMap paramHashMap)
    throws SCException
  {
    String str1 = "SmartCalendarService.initializeSCAdapter";
    String str2 = (String)paramHashMap.get("JAVA_CLASS");
    if (str2 == null)
    {
      localObject1 = "The <JAVA_CLASS> tag was not found in the XML configuration file.";
      DebugLog.log(Level.SEVERE, (String)localObject1);
      throw new SCException(1, (String)localObject1);
    }
    Object localObject1 = (HashMap)paramHashMap.get("SCADAPTER_SETTINGS");
    Object localObject2;
    if (localObject1 == null)
    {
      localObject2 = "The <SCADAPTER_SETTINGS> tag was not found in the XML configuration file.";
      DebugLog.log(Level.SEVERE, (String)localObject2);
      throw new SCException(1, (String)localObject2);
    }
    try
    {
      localObject2 = Class.forName(str2);
      localObject3 = ((Class)localObject2).newInstance();
      if (!(localObject3 instanceof ISCAdapter))
      {
        String str3 = "The adapter class specified ( " + str2 + " ) " + "does not conform to the ISCAdapter interface as required.";
        DebugLog.log(Level.SEVERE, str3);
        throw new SCException(1, str3);
      }
      this.a = ((ISCAdapter)localObject3);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localObject3 = "The class \"" + str2 + "\" specified in the " + "configuration XML could not be found in the classpath.";
      DebugLog.throwing((String)localObject3, localClassNotFoundException);
      throw new SCException(1, (String)localObject3, localClassNotFoundException);
    }
    catch (InstantiationException localInstantiationException)
    {
      localObject3 = "The class \"" + str2 + "\" specified in the " + "configuration XML could not be instantiated.  Please " + "ensure that this class is not abstract or an interface.";
      DebugLog.throwing((String)localObject3, localInstantiationException);
      throw new SCException(1, (String)localObject3, localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      Object localObject3 = "The service does not have access to the class \"" + str2 + "\" as specified in the " + "configuration XML.  Ensure that this class is " + "not declared private or package protected.";
      DebugLog.throwing((String)localObject3, localIllegalAccessException);
      throw new SCException(1, (String)localObject3, localIllegalAccessException);
    }
    this.a.initialize((HashMap)localObject1);
  }
  
  private void a(String paramString)
    throws SCException
  {
    if (this.a == null)
    {
      String str = "The Smart Calendar Service must be initialized before any calls are made to the service method " + paramString + ".";
      DebugLog.log(Level.SEVERE, str);
      throw new SCException(2, str);
    }
  }
  
  public boolean getIgnoreForTransfers(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws SCException
  {
    String str = "SmartCalendarService.getIgnoreForTransfers";
    a(str);
    return this.a.getIgnoreForTransfers(paramSecureUser.getBankID(), paramBankIdentifier, paramHashMap);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.smartcalendar.SmartCalendarService
 * JD-Core Version:    0.7.0.1
 */