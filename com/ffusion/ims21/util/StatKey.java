package com.ffusion.ims21.util;

public class StatKey
{
  static final int jdField_new = 0;
  static final int jdField_int = 2;
  static final int jdField_else = 4;
  static final int jdField_byte = 6;
  static final int jdField_goto = 8;
  static final int jdField_char = 11;
  static final int jdField_for = 14;
  static final int jdField_try = 17;
  static final int jdField_if = 21;
  static final int jdField_case = 25;
  static final int jdField_do = 29;
  static final int a = 33;
  protected int gender = 0;
  protected int marital = 0;
  protected int residence = 0;
  protected int family = 0;
  protected int use = 0;
  protected int age = 0;
  protected int income = 0;
  protected int custBal1 = 0;
  protected int custBal2 = 0;
  protected int custBal3 = 0;
  protected int custBal4 = 0;
  protected int state = 0;
  
  public StatKey(long paramLong)
  {
    setValue(paramLong);
  }
  
  public StatKey(int paramInt1, int paramInt2)
  {
    setValues(paramInt1, paramInt2);
  }
  
  public StatKey() {}
  
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
  
  public long getValue()
  {
    long l = 0L;
    l |= (0x3 & this.gender) << 0;
    l |= (0x3 & this.marital) << 2;
    l |= (0x3 & this.residence) << 4;
    l |= (0x3 & this.family) << 6;
    l |= (0x7 & this.use) << 8;
    l |= (0x7 & this.age) << 11;
    l |= (0x7 & this.income) << 14;
    l |= (0xF & this.custBal1) << 17;
    l |= (0xF & this.custBal2) << 21;
    l |= (0xF & this.custBal3) << 25;
    l |= (0xF & this.custBal4) << 29;
    l |= (0x3F & this.state) << 33;
    return l;
  }
  
  public void setValue(long paramLong)
  {
    this.gender = ((int)(0x3 & paramLong >>> 0));
    this.marital = ((int)(0x3 & paramLong >>> 2));
    this.residence = ((int)(0x3 & paramLong >>> 4));
    this.family = ((int)(0x3 & paramLong >>> 6));
    this.use = ((int)(0x7 & paramLong >>> 8));
    this.age = ((int)(0x7 & paramLong >>> 11));
    this.income = ((int)(0x7 & paramLong >>> 14));
    this.custBal1 = ((int)(0xF & paramLong >>> 17));
    this.custBal2 = ((int)(0xF & paramLong >>> 21));
    this.custBal3 = ((int)(0xF & paramLong >>> 25));
    this.custBal4 = ((int)(0xF & paramLong >>> 29));
    this.state = ((int)(0x3F & paramLong >>> 33));
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
  
  public int getCustBal1()
  {
    return this.custBal1;
  }
  
  public void setCustBal1(int paramInt)
  {
    this.custBal1 = paramInt;
  }
  
  public int getCustBal2()
  {
    return this.custBal2;
  }
  
  public void setCustBal2(int paramInt)
  {
    this.custBal2 = paramInt;
  }
  
  public int getCustBal3()
  {
    return this.custBal3;
  }
  
  public void setCustBal3(int paramInt)
  {
    this.custBal3 = paramInt;
  }
  
  public int getCustBal4()
  {
    return this.custBal4;
  }
  
  public void setCustBal4(int paramInt)
  {
    this.custBal4 = paramInt;
  }
  
  public int getState()
  {
    return this.state;
  }
  
  public void setState(int paramInt)
  {
    this.state = paramInt;
  }
  
  public int getGenderId()
  {
    return this.gender == 2 ? 4 : this.gender == 0 ? 0 : 1;
  }
  
  public void setGenderId(int paramInt)
  {
    this.gender = (paramInt == 4 ? 2 : paramInt);
  }
  
  public int getMaritalId()
  {
    return this.marital;
  }
  
  public void setMaritalId(int paramInt)
  {
    this.marital = paramInt;
  }
  
  public int getResidenceId()
  {
    return this.residence;
  }
  
  public void setResidenceId(int paramInt)
  {
    this.residence = paramInt;
  }
  
  public int getFamilyId()
  {
    return this.family;
  }
  
  public void setFamilyId(int paramInt)
  {
    this.family = paramInt;
  }
  
  public int getUseId()
  {
    return this.use == 3 ? 4 : this.use;
  }
  
  public void setUseId(int paramInt)
  {
    this.use = (paramInt == 4 ? 3 : paramInt);
  }
  
  public int getAgeId()
  {
    return a(this.age);
  }
  
  public void setAgeId(int paramInt)
  {
    this.age = jdField_if(paramInt);
  }
  
  public int getIncomeId()
  {
    return a(this.income);
  }
  
  public void setIncomeId(int paramInt)
  {
    this.income = jdField_if(paramInt);
  }
  
  public int getCustBal1Id()
  {
    return this.custBal1 - 1;
  }
  
  public void setCustBal1Id(int paramInt)
  {
    this.custBal1 = (paramInt + 1);
  }
  
  public int getCustBal2Id()
  {
    return this.custBal2 - 1;
  }
  
  public void setCustBal2Id(int paramInt)
  {
    this.custBal2 = (paramInt + 1);
  }
  
  public int getCustBal3Id()
  {
    return this.custBal3 - 1;
  }
  
  public void setCustBal3Id(int paramInt)
  {
    this.custBal3 = (paramInt + 1);
  }
  
  public int getCustBal4Id()
  {
    return this.custBal4 - 1;
  }
  
  public void setCustBal4Id(int paramInt)
  {
    this.custBal4 = (paramInt + 1);
  }
  
  public int getStateId()
  {
    return this.state;
  }
  
  public void setStateId(int paramInt)
  {
    this.state = paramInt;
  }
  
  public String toString()
  {
    return " gender = " + this.gender + "\n marital = " + this.marital + "\n residence = " + this.residence + "\n family = " + this.family + "\n UseId = " + this.use + "\n ageid = " + this.age + "\n incomeid = " + this.income + "\n custBal1 =" + this.custBal1 + "\n custBal2=" + this.custBal2 + "\n custBal3=" + this.custBal3 + "\n custBal4=" + this.custBal4 + "\n stateId=" + this.state + "\n Key = 0x" + Long.toHexString(getValue()) + "\n High = 0x" + Integer.toHexString(getHighKey()) + "\n Low = 0x" + Integer.toHexString(getLowKey());
  }
  
  static int jdField_if(int paramInt)
  {
    int i = 0;
    switch (paramInt)
    {
    case 0: 
      i = 0;
      break;
    case 1: 
      i = 1;
      break;
    case 2: 
      i = 2;
      break;
    case 4: 
      i = 3;
      break;
    case 8: 
      i = 4;
      break;
    case 16: 
      i = 5;
      break;
    case 32: 
      i = 6;
      break;
    case 64: 
      i = 7;
    }
    return i;
  }
  
  static int a(int paramInt)
  {
    switch (paramInt)
    {
    case 0: 
      return 0;
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
    case 7: 
      return 64;
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.util.StatKey
 * JD-Core Version:    0.7.0.1
 */