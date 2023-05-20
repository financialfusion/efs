package com.ffusion.ims21.util;

import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.user.OneToOneUser;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DemoKey
{
  static final int jdField_char = 0;
  static final int c = 3;
  static final int jdField_new = 5;
  static final int jdField_void = 7;
  static final int jdField_byte = 9;
  static final int d = 12;
  static final int jdField_goto = 18;
  static final int jdField_do = 24;
  static final int e = 30;
  static final int jdField_case = 34;
  static final int f = 7;
  static final int jdField_if = 3;
  static final int jdField_try = 3;
  static final int jdField_null = 3;
  static final int b = 7;
  static final int jdField_else = 63;
  static final int a = 63;
  static final int jdField_int = 63;
  static final int jdField_for = 15;
  static final int jdField_long = 31;
  protected int gender = 0;
  protected int marital = 0;
  protected int residence = 0;
  protected int family = 0;
  protected int use = 0;
  protected int age = 0;
  protected int income = 0;
  protected String state = null;
  protected int bMonth = 0;
  protected int bDay = 0;
  public static final String GENDERTITLE = "marital_type";
  public static final String ISMARRIED = "marital_status";
  public static final String OWNYOURHOME = "residence_type";
  public static final String HASKIDS = "family_type";
  public static final String INTENDEDUSE = "software_use_type";
  public static final String DATEOFBIRTH = "bday";
  public static final String INCOMERANGE = "income_type";
  public static final String STATE = "state";
  
  public DemoKey(long paramLong)
  {
    setValue(paramLong);
  }
  
  public DemoKey(int paramInt1, int paramInt2)
  {
    setValues(paramInt1, paramInt2);
  }
  
  public DemoKey() {}
  
  public DemoKey(Object paramObject)
  {
    if ((paramObject instanceof OneToOneUser)) {
      doDemoKey((OneToOneUser)paramObject);
    }
  }
  
  public void doDemoKey(OneToOneUser paramOneToOneUser)
  {
    setGender(paramOneToOneUser.getGenderValue());
    setMarital(paramOneToOneUser.getIsMarriedValue());
    setResidence(paramOneToOneUser.getOwnHomeValue());
    setFamily(paramOneToOneUser.getHasKidsValue());
    setUse(paramOneToOneUser.getIntendedUseValue());
    setIncome(paramOneToOneUser.getIncomeRangeValue());
    setState(paramOneToOneUser.getState());
    setAge(paramOneToOneUser.getAgeRangeValue());
    String str = (String)paramOneToOneUser.get("BIRTH_DATE");
    if (str != null)
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
      Calendar localCalendar = Calendar.getInstance();
      try
      {
        localCalendar.setTime(localSimpleDateFormat.parse(str));
      }
      catch (Exception localException)
      {
        localCalendar = null;
      }
      if (localCalendar != null)
      {
        setBirthDay(localCalendar.get(5));
        setBirthMonth(localCalendar.get(2) + 1);
      }
    }
  }
  
  public void setValues(int paramInt1, int paramInt2)
  {
    setValue(makeLong(paramInt1, paramInt2));
  }
  
  public static int getHigh(long paramLong)
  {
    return (int)(paramLong >>> 32) & 0xFFFFFFFF;
  }
  
  public static int getLow(long paramLong)
  {
    return (int)paramLong & 0xFFFFFFFF;
  }
  
  public static long makeLong(int paramInt1, int paramInt2)
  {
    long l = paramInt2 << 32;
    l >>>= 32;
    l |= paramInt1 << 32;
    return l;
  }
  
  public int getHighKey()
  {
    return getHigh(getValue());
  }
  
  public int getLowKey()
  {
    return getLow(getValue());
  }
  
  public String getHexValue()
  {
    return Long.toHexString(getValue());
  }
  
  public long getValue()
  {
    long l = 0L;
    l |= (0x7 & this.gender) << 0;
    l |= (0x3 & this.marital) << 3;
    l |= (0x3 & this.residence) << 5;
    l |= (0x3 & this.family) << 7;
    l |= (0x7 & this.use) << 9;
    l |= (0x3F & this.age) << 12;
    l |= (0x3F & this.income) << 18;
    l |= (0xF & this.bMonth) << 30;
    l |= (0x1F & this.bDay) << 34;
    return l;
  }
  
  public void setValue(long paramLong)
  {
    this.gender = ((int)(0x7 & paramLong >>> 0));
    this.marital = ((int)(0x3 & paramLong >>> 3));
    this.residence = ((int)(0x3 & paramLong >>> 5));
    this.family = ((int)(0x3 & paramLong >>> 7));
    this.use = ((int)(0x7 & paramLong >>> 9));
    this.age = ((int)(0x3F & paramLong >>> 12));
    this.income = ((int)(0x3F & paramLong >>> 18));
    this.bMonth = ((int)(0xF & paramLong >>> 30));
    this.bDay = ((int)(0x1F & paramLong >>> 34));
  }
  
  public int getGender()
  {
    return this.gender;
  }
  
  public void setGender(int paramInt)
  {
    this.gender = paramInt;
  }
  
  public int getMarital()
  {
    return this.marital;
  }
  
  public void setMarital(int paramInt)
  {
    this.marital = paramInt;
  }
  
  public int getResidence()
  {
    return this.residence;
  }
  
  public void setResidence(int paramInt)
  {
    this.residence = paramInt;
  }
  
  public int getFamily()
  {
    return this.family;
  }
  
  public void setFamily(int paramInt)
  {
    this.family = paramInt;
  }
  
  public int getUse()
  {
    return this.use;
  }
  
  public void setUse(int paramInt)
  {
    this.use = paramInt;
  }
  
  public int getAge()
  {
    return this.age;
  }
  
  public void setAge(int paramInt)
  {
    this.age = paramInt;
  }
  
  public int getIncome()
  {
    return this.income;
  }
  
  public void setIncome(int paramInt)
  {
    this.income = paramInt;
  }
  
  public String getState()
  {
    return this.state;
  }
  
  public void setState(String paramString)
  {
    this.state = paramString;
  }
  
  public int getBirthMonth()
  {
    return this.bMonth;
  }
  
  public void setBirthMonth(int paramInt)
  {
    this.bMonth = paramInt;
  }
  
  public int getBirthDay()
  {
    return this.bDay;
  }
  
  public void setBirthDay(int paramInt)
  {
    this.bDay = paramInt;
  }
  
  public boolean qualifies(DemoKey paramDemoKey)
  {
    if ((getGender() > 0) && ((paramDemoKey.getGender() == 0) || ((getGender() & paramDemoKey.getGender()) != paramDemoKey.getGender()))) {
      return false;
    }
    if ((getMarital() > 0) && ((paramDemoKey.getMarital() == 0) || ((getMarital() & paramDemoKey.getMarital()) != paramDemoKey.getMarital()))) {
      return false;
    }
    if ((getResidence() > 0) && ((paramDemoKey.getResidence() == 0) || ((getResidence() & paramDemoKey.getResidence()) != paramDemoKey.getResidence()))) {
      return false;
    }
    if ((getFamily() > 0) && ((paramDemoKey.getFamily() == 0) || ((getFamily() & paramDemoKey.getFamily()) != paramDemoKey.getFamily()))) {
      return false;
    }
    if ((getUse() > 0) && ((paramDemoKey.getUse() == 0) || ((getUse() & paramDemoKey.getUse()) != paramDemoKey.getUse()))) {
      return false;
    }
    if ((getAge() > 0) && ((paramDemoKey.getAge() == 0) || ((getAge() & paramDemoKey.getAge()) != paramDemoKey.getAge()))) {
      return false;
    }
    return (getIncome() <= 0) || ((paramDemoKey.getIncome() != 0) && ((getIncome() & paramDemoKey.getIncome()) == paramDemoKey.getIncome()));
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    long l = getValue();
    setValue(l);
    if (l != getValue()) {
      localStringBuffer.append("\nProblem GetValue not equal ").append(l).append("=").append(getValue()).append("\n");
    }
    localStringBuffer.append("\n\t\tgender = ").append(this.gender).append("\n\t\tmarital = ").append(this.marital);
    localStringBuffer.append("\n\t\tresidence = ").append(this.residence).append("\n\t\tfamily = ").append(this.family);
    localStringBuffer.append("\n\t\tUseId = ").append(this.use).append("\n\t\tageid = ").append(this.age);
    localStringBuffer.append("\n\t\tincomeid = ").append(this.income).append("\n\t\tbirthMonth = ").append(this.bMonth);
    localStringBuffer.append("\n\t\tbirthDay = ").append(this.bDay).append("\n\t\tKey = 0x");
    localStringBuffer.append(Long.toHexString(getValue())).append("\n");
    return localStringBuffer.toString();
  }
  
  public static String getDemoURI(OneToOneUser paramOneToOneUser)
  {
    DemoKey localDemoKey = new DemoKey(paramOneToOneUser);
    return Long.toHexString(localDemoKey.getValue());
  }
  
  public static String getTargetingURI(OneToOneUser paramOneToOneUser, Accounts paramAccounts)
  {
    return "target=" + getDemoURI(paramOneToOneUser) + "&acct=" + AcctKey.getAccountsURI(paramAccounts);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.util.DemoKey
 * JD-Core Version:    0.7.0.1
 */