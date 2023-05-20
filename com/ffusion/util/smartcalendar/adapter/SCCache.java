package com.ffusion.util.smartcalendar.adapter;

import com.ffusion.util.LocaleUtil;
import com.ffusion.util.ReadWriteLock;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.smartcalendar.SCBusinessDays;
import com.ffusion.util.beans.smartcalendar.SCCalendar;
import com.ffusion.util.beans.smartcalendar.SCCalendars;
import com.ffusion.util.beans.smartcalendar.SCHoliday;
import com.ffusion.util.smartcalendar.SCException;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

class SCCache
{
  private static int jdField_for = -1;
  private int jdField_new = jdField_for;
  private HashMap jdField_byte = new HashMap();
  private HashMap a = new HashMap();
  private HashMap jdField_case = new HashMap();
  private HashMap jdField_do = new HashMap();
  private HashMap jdField_int = new HashMap();
  private HashMap jdField_if = new HashMap();
  private ReadWriteLock jdField_try = new ReadWriteLock();
  
  public void loadData(HashMap paramHashMap1, HashMap paramHashMap2, HashMap paramHashMap3, HashMap paramHashMap4, HashMap paramHashMap5, HashMap paramHashMap6, int paramInt)
  {
    this.jdField_try.getWriteLock();
    try
    {
      this.jdField_byte = new HashMap(paramHashMap1);
      this.a = new HashMap(paramHashMap2);
      this.jdField_case = new HashMap(paramHashMap3);
      this.jdField_do = new HashMap(paramHashMap4);
      this.jdField_int = new HashMap(paramHashMap5);
      this.jdField_if = new HashMap(paramHashMap6);
      this.jdField_new = paramInt;
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public int getVersion()
  {
    this.jdField_try.getReadLock();
    try
    {
      int i = this.jdField_new;
      return i;
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public boolean isCalendarInUse(SCCalendar paramSCCalendar)
    throws SCException
  {
    this.jdField_try.getReadLock();
    try
    {
      paramSCCalendar = (SCCalendar)this.jdField_byte.get(new Integer(paramSCCalendar.getCalendarId()));
      if (paramSCCalendar == null) {
        throw new SCException(20, "The specified calendar id is invalid");
      }
      if (this.jdField_do.containsValue(paramSCCalendar))
      {
        bool = true;
        return bool;
      }
      if (this.jdField_case.containsValue(paramSCCalendar))
      {
        bool = true;
        return bool;
      }
      boolean bool = false;
      return bool;
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public SCCalendar getCalendar(int paramInt)
  {
    this.jdField_try.getReadLock();
    try
    {
      SCCalendar localSCCalendar = (SCCalendar)this.jdField_byte.get(new Integer(paramInt));
      return localSCCalendar;
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public SCCalendar getCalendar(SCCalendarAssociationKey paramSCCalendarAssociationKey)
  {
    this.jdField_try.getReadLock();
    try
    {
      SCCalendar localSCCalendar = (SCCalendar)this.jdField_do.get(paramSCCalendarAssociationKey);
      return localSCCalendar;
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public SCCalendar resolveCalendar(SCCalendarAssociationKey paramSCCalendarAssociationKey)
  {
    this.jdField_try.getReadLock();
    try
    {
      SCCalendar localSCCalendar1 = (SCCalendar)this.jdField_do.get(paramSCCalendarAssociationKey);
      if (localSCCalendar1 == null) {
        localSCCalendar1 = (SCCalendar)this.jdField_case.get(paramSCCalendarAssociationKey.getServiceBureauId());
      }
      SCCalendar localSCCalendar2 = localSCCalendar1;
      return localSCCalendar2;
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public SCCalendar getCalendarForName(String paramString1, String paramString2)
  {
    this.jdField_try.getReadLock();
    try
    {
      HashMap localHashMap = (HashMap)this.a.get(paramString2);
      if (localHashMap == null)
      {
        localSCCalendar = null;
        return localSCCalendar;
      }
      SCCalendar localSCCalendar = (SCCalendar)localHashMap.get(paramString1);
      return localSCCalendar;
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public void updateCalendar(SCCalendar paramSCCalendar)
    throws SCException
  {
    this.jdField_try.getWriteLock();
    try
    {
      String str1 = paramSCCalendar.getCalendarName();
      SCBusinessDays localSCBusinessDays = paramSCCalendar.getBusinessDays();
      HashMap localHashMap1 = paramSCCalendar.getHolidays();
      boolean bool1 = paramSCCalendar.getIsDefault();
      boolean bool2 = paramSCCalendar.getIgnoreForTransfersValue();
      paramSCCalendar = (SCCalendar)this.jdField_byte.get(new Integer(paramSCCalendar.getCalendarId()));
      if (paramSCCalendar == null) {
        throw new SCException(20, "The specified calendar id is invalid");
      }
      Locale localLocale = paramSCCalendar.getLocale();
      String str2 = (String)this.jdField_int.remove(paramSCCalendar);
      HashMap localHashMap2 = (HashMap)this.a.get(str2);
      if (localHashMap2 == null) {
        throw new SCException(3, "A cache error occurred while updating the calendar name to id mapping");
      }
      localHashMap2.remove(paramSCCalendar.getCalendarName());
      localHashMap2.put(str1, paramSCCalendar);
      paramSCCalendar.setCalendarName(str1);
      paramSCCalendar.setIgnoreForTransfersValue(bool2);
      HashMap localHashMap3 = new HashMap(localHashMap1.size());
      Iterator localIterator = localHashMap1.keySet().iterator();
      Object localObject2;
      while (localIterator.hasNext())
      {
        localObject1 = (DateTime)localIterator.next();
        localObject2 = (SCHoliday)localHashMap1.get(localObject1);
        localObject1 = ((SCHoliday)localObject2).getDate();
        Calendar localCalendar = SCAdapter.a(((DateTime)localObject1).getTime());
        localObject1 = new DateTime(localCalendar, localLocale);
        SCHoliday localSCHoliday = new SCHoliday(localLocale);
        localSCHoliday.setDate((DateTime)localObject1);
        localSCHoliday.setName(((SCHoliday)localObject2).getName());
        localSCHoliday.setAction(((SCHoliday)localObject2).getAction());
        localHashMap3.put(localObject1, localSCHoliday);
      }
      paramSCCalendar.setHolidays(localHashMap3);
      Object localObject1 = new SCBusinessDays(localSCBusinessDays.getLocale());
      ((SCBusinessDays)localObject1).setBusinessDays(localSCBusinessDays.getBusinessDays());
      ((SCBusinessDays)localObject1).setActions(localSCBusinessDays.getActions());
      paramSCCalendar.setBusinessDays((SCBusinessDays)localObject1);
      if (bool1)
      {
        localObject2 = (SCCalendar)this.jdField_case.put(str2, paramSCCalendar);
        if (localObject2 != null) {
          ((SCCalendar)localObject2).setIsDefault(false);
        }
      }
      else
      {
        this.jdField_case.values().remove(paramSCCalendar);
      }
      paramSCCalendar.setIsDefault(bool1);
      this.jdField_int.put(paramSCCalendar, str2);
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public void updateCalendarHolidays(SCCalendar paramSCCalendar)
    throws SCException
  {
    this.jdField_try.getWriteLock();
    try
    {
      HashMap localHashMap1 = paramSCCalendar.getHolidays();
      paramSCCalendar = (SCCalendar)this.jdField_byte.get(new Integer(paramSCCalendar.getCalendarId()));
      if (paramSCCalendar == null) {
        throw new SCException(3, "The specified calendar could not be retrieved from the cache");
      }
      Locale localLocale = paramSCCalendar.getLocale();
      String str = (String)this.jdField_int.remove(paramSCCalendar);
      HashMap localHashMap2 = new HashMap(localHashMap1.size());
      Iterator localIterator = localHashMap1.keySet().iterator();
      while (localIterator.hasNext())
      {
        DateTime localDateTime = (DateTime)localIterator.next();
        SCHoliday localSCHoliday1 = (SCHoliday)localHashMap1.get(localDateTime);
        localDateTime = localSCHoliday1.getDate();
        Calendar localCalendar = SCAdapter.a(localDateTime.getTime());
        localDateTime = new DateTime(localCalendar, localLocale);
        SCHoliday localSCHoliday2 = new SCHoliday(localLocale);
        localSCHoliday2.setDate(localDateTime);
        localSCHoliday2.setName(localSCHoliday1.getName());
        localSCHoliday2.setAction(localSCHoliday1.getAction());
        localHashMap2.put(localDateTime, localSCHoliday2);
      }
      paramSCCalendar.setHolidays(localHashMap2);
      this.jdField_int.put(paramSCCalendar, str);
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public void updateCalendarName(SCCalendar paramSCCalendar)
    throws SCException
  {
    this.jdField_try.getWriteLock();
    try
    {
      String str1 = paramSCCalendar.getCalendarName();
      paramSCCalendar = (SCCalendar)this.jdField_byte.get(new Integer(paramSCCalendar.getCalendarId()));
      if (paramSCCalendar == null) {
        throw new SCException(20, "The specified calendar id is invalid");
      }
      String str2 = (String)this.jdField_int.remove(paramSCCalendar);
      HashMap localHashMap = (HashMap)this.a.get(str2);
      if (localHashMap == null) {
        throw new SCException(3, "A cache error occurred while updating the calendar name to id mapping");
      }
      localHashMap.remove(paramSCCalendar.getCalendarName());
      localHashMap.put(str1, paramSCCalendar);
      paramSCCalendar.setCalendarName(str1);
      this.jdField_int.put(paramSCCalendar, str2);
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public void updateCalendarBusinessDays(SCCalendar paramSCCalendar)
    throws SCException
  {
    this.jdField_try.getWriteLock();
    try
    {
      SCBusinessDays localSCBusinessDays1 = paramSCCalendar.getBusinessDays();
      paramSCCalendar = (SCCalendar)this.jdField_byte.get(new Integer(paramSCCalendar.getCalendarId()));
      if (paramSCCalendar == null) {
        throw new SCException(20, "The specified calendar id is invalid");
      }
      String str = (String)this.jdField_int.remove(paramSCCalendar);
      SCBusinessDays localSCBusinessDays2 = new SCBusinessDays(localSCBusinessDays1.getLocale());
      localSCBusinessDays2.setBusinessDays(localSCBusinessDays1.getBusinessDays());
      localSCBusinessDays2.setActions(localSCBusinessDays1.getActions());
      paramSCCalendar.setBusinessDays(localSCBusinessDays2);
      this.jdField_int.put(paramSCCalendar, str);
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public void addCalendar(String paramString, SCCalendar paramSCCalendar)
  {
    this.jdField_try.getWriteLock();
    try
    {
      Integer localInteger = new Integer(paramSCCalendar.getCalendarId());
      String str = paramSCCalendar.getCalendarName();
      this.jdField_byte.put(localInteger, paramSCCalendar);
      HashMap localHashMap = (HashMap)this.a.get(paramString);
      if (localHashMap == null)
      {
        localHashMap = new HashMap();
        this.a.put(paramString, localHashMap);
      }
      localHashMap.put(str, paramSCCalendar);
      HashSet localHashSet = (HashSet)this.jdField_if.get(paramString);
      if (localHashSet == null)
      {
        localHashSet = new HashSet();
        this.jdField_if.put(paramString, localHashSet);
      }
      localHashSet.add(paramSCCalendar);
      this.jdField_int.put(paramSCCalendar, paramString);
      if (paramSCCalendar.getIsDefault())
      {
        SCCalendar localSCCalendar = (SCCalendar)this.jdField_case.put(paramString, paramSCCalendar);
        if (localSCCalendar != null) {
          localSCCalendar.setIsDefault(false);
        }
      }
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public void removeCalendar(SCCalendar paramSCCalendar)
    throws SCException
  {
    this.jdField_try.getWriteLock();
    try
    {
      Integer localInteger = new Integer(paramSCCalendar.getCalendarId());
      paramSCCalendar = (SCCalendar)this.jdField_byte.remove(localInteger);
      if (paramSCCalendar == null) {
        throw new SCException(20, "The specified calendar id is invalid");
      }
      String str = (String)this.jdField_int.remove(paramSCCalendar);
      HashMap localHashMap = (HashMap)this.a.get(str);
      if (localHashMap != null)
      {
        localObject1 = paramSCCalendar.getCalendarName();
        localHashMap.remove(localObject1);
      }
      Object localObject1 = (HashSet)this.jdField_if.get(str);
      if (localObject1 != null) {
        ((HashSet)localObject1).remove(paramSCCalendar);
      }
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public SCCalendars getCalendars(String paramString)
  {
    this.jdField_try.getReadLock();
    try
    {
      HashSet localHashSet = (HashSet)this.jdField_if.get(paramString);
      if (localHashSet == null)
      {
        localSCCalendars = null;
        return localSCCalendars;
      }
      SCCalendars localSCCalendars = new SCCalendars(LocaleUtil.getDefaultLocale());
      Iterator localIterator = localHashSet.iterator();
      while (localIterator.hasNext())
      {
        localObject1 = (SCCalendar)localIterator.next();
        localSCCalendars.add(localObject1);
      }
      Object localObject1 = localSCCalendars;
      return localObject1;
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public SCCalendar getDefaultCalendar(String paramString)
  {
    this.jdField_try.getReadLock();
    try
    {
      SCCalendar localSCCalendar = (SCCalendar)this.jdField_case.get(paramString);
      return localSCCalendar;
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public void setDefaultCalendar(String paramString, SCCalendar paramSCCalendar)
    throws SCException
  {
    this.jdField_try.getWriteLock();
    try
    {
      if (paramSCCalendar == null)
      {
        paramSCCalendar = (SCCalendar)this.jdField_case.remove(paramString);
        paramSCCalendar.setIsDefault(false);
      }
      else
      {
        paramSCCalendar = (SCCalendar)this.jdField_byte.get(new Integer(paramSCCalendar.getCalendarId()));
        if (paramSCCalendar == null) {
          throw new SCException(20, "The specified calendar id is invalid");
        }
        SCCalendar localSCCalendar = (SCCalendar)this.jdField_case.put(paramString, paramSCCalendar);
        if (localSCCalendar != null) {
          localSCCalendar.setIsDefault(false);
        }
        paramSCCalendar.setIsDefault(true);
      }
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
  
  public void setCalendarForBank(String paramString, BankIdentifier paramBankIdentifier, SCCalendar paramSCCalendar)
    throws SCException
  {
    this.jdField_try.getWriteLock();
    try
    {
      BankIdentifier localBankIdentifier = new BankIdentifier(paramBankIdentifier.getLocale());
      localBankIdentifier.setBankDirectoryType(paramBankIdentifier.getBankDirectoryType());
      localBankIdentifier.setBankDirectoryId(paramBankIdentifier.getBankDirectoryId());
      SCCalendarAssociationKey localSCCalendarAssociationKey = new SCCalendarAssociationKey(localBankIdentifier, paramString);
      if (paramSCCalendar == null)
      {
        this.jdField_do.remove(localSCCalendarAssociationKey);
      }
      else
      {
        paramSCCalendar = (SCCalendar)this.jdField_byte.get(new Integer(paramSCCalendar.getCalendarId()));
        if (paramSCCalendar == null) {
          throw new SCException(20, "The specified calendar id is invalid");
        }
        this.jdField_do.put(localSCCalendarAssociationKey, paramSCCalendar);
      }
    }
    finally
    {
      this.jdField_try.releaseLock();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.smartcalendar.adapter.SCCache
 * JD-Core Version:    0.7.0.1
 */