package com.ffusion.beans.user;

import com.ffusion.beans.CollectionElement;
import com.ffusion.util.Sortable;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class OneToOneUser
  extends User
  implements IMSTargeting, UserDefines, Sortable, Comparable, Serializable, CollectionElement
{
  public static final String INTENDED_USE = "INTENDED_USE";
  public static final String MARITAL_STATUS = "MARITAL_STATUS";
  public static final String FAMILY = "FAMILY";
  public static final String RESIDENCE = "RESIDENCE";
  public static final String INCOME = "INCOME";
  public static final String BIRTH_DATE = "BIRTH_DATE";
  
  protected OneToOneUser() {}
  
  public OneToOneUser(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public int getIntendedUseValue()
  {
    String str = getHashEntry("INTENDED_USE");
    int i = 0;
    if ((str != null) && (str.trim().length() > 0)) {
      i = Integer.parseInt(str);
    }
    switch (i)
    {
    case 1: 
      return 1;
    case 2: 
      return 2;
    case 3: 
      return 4;
    }
    return 0;
  }
  
  public int getIsMarriedValue()
  {
    String str = getHashEntry("MARITAL_STATUS");
    if ((str != null) && (str.length() > 0)) {
      return Integer.valueOf(str).intValue();
    }
    return 0;
  }
  
  public int getHasKidsValue()
  {
    String str = getHashEntry("FAMILY");
    if ((str != null) && (str.length() > 0)) {
      return Integer.valueOf(str).intValue();
    }
    return 0;
  }
  
  public int getOwnHomeValue()
  {
    String str = getHashEntry("RESIDENCE");
    if ((str != null) && (str.length() > 0)) {
      return Integer.valueOf(str).intValue();
    }
    return 0;
  }
  
  public int getIncomeRangeValue()
  {
    String str = getHashEntry("INCOME");
    int i = 0;
    if ((str != null) && (str.length() > 0)) {
      i = Integer.valueOf(str).intValue();
    }
    switch (i)
    {
    case 1: 
      return 1;
    case 2: 
      return 2;
    case 3: 
      return 4;
    case 4: 
      return 8;
    case 5: 
      return 16;
    case 6: 
      return 32;
    }
    return 0;
  }
  
  public int getGenderValue()
  {
    String str = getGender();
    if ((str != null) && (str.length() > 0)) {
      return Integer.valueOf(str).intValue();
    }
    return 0;
  }
  
  public int getAgeRangeValue()
  {
    int i = 0;
    HashMap localHashMap = getHash();
    String str = (String)localHashMap.get("BIRTH_DATE");
    if ((str != null) && (str.trim().length() > 0))
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
      Calendar localCalendar1 = Calendar.getInstance();
      try
      {
        localCalendar1.setTime(localSimpleDateFormat.parse(str));
      }
      catch (Exception localException)
      {
        localCalendar1 = null;
      }
      if (localCalendar1 != null)
      {
        Calendar localCalendar2 = Calendar.getInstance();
        int j = localCalendar2.get(1) - localCalendar1.get(1);
        localCalendar2.set(localCalendar2.get(1), localCalendar1.get(2), localCalendar1.get(5));
        if (Calendar.getInstance().before(localCalendar2)) {
          j--;
        }
        if (j > 64) {
          i = 32;
        } else if (j > 54) {
          i = 16;
        } else if (j > 44) {
          i = 8;
        } else if (j > 34) {
          i = 4;
        } else if (j > 24) {
          i = 2;
        } else if (j > 17) {
          i = 1;
        }
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.user.OneToOneUser
 * JD-Core Version:    0.7.0.1
 */