package com.ffusion.beans.user;

public abstract interface IMSTargeting
{
  public static final int TARGET_INCOME_UNKNOWN = 0;
  public static final int TARGET_INCOME_0TO25K = 1;
  public static final int TARGET_INCOME_25TO50K = 2;
  public static final int TARGET_INCOME_50TO75K = 4;
  public static final int TARGET_INCOME_75TO100K = 8;
  public static final int TARGET_INCOME_100TO150K = 16;
  public static final int TARGET_INCOME_OVER150K = 32;
  public static final int TARGET_FAMILY_UNKNOWN = 0;
  public static final int TARGET_FAMILY_CHILDREN = 1;
  public static final int TARGET_FAMILY_NO_CHILDREN = 2;
  public static final int TARGET_RESIDENCE_UNKNOWN = 0;
  public static final int TARGET_RESIDENCE_OWN = 1;
  public static final int TARGET_RESIDENCE_RENT = 2;
  public static final int TARGET_USE_UNKNOWN = 0;
  public static final int TARGET_USE_PERSONAL = 1;
  public static final int TARGET_USE_BUSINESS = 2;
  public static final int TARGET_USE_BOTH = 4;
  public static final int TARGET_GENDER_UNKNOWN = 0;
  public static final int TARGET_MALE = 1;
  public static final int TARGET_FEMALE = 2;
  public static final int TARGET_MARITAL_UNKNOWN = 0;
  public static final int TARGET_MARITAL_MARRIED = 1;
  public static final int TARGET_MARITAL_SINGLE = 2;
  public static final int TARGET_AGE_UNKNOWN = 0;
  public static final int TARGET_AGE_18TO24 = 1;
  public static final int TARGET_AGE_25TO34 = 2;
  public static final int TARGET_AGE_35TO44 = 4;
  public static final int TARGET_AGE_45TO54 = 8;
  public static final int TARGET_AGE_55TO64 = 16;
  public static final int TARGET_AGE_OVER65 = 32;
  
  public abstract int getIntendedUseValue();
  
  public abstract int getIsMarriedValue();
  
  public abstract int getHasKidsValue();
  
  public abstract int getOwnHomeValue();
  
  public abstract int getIncomeRangeValue();
  
  public abstract int getGenderValue();
  
  public abstract int getAgeRangeValue();
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.user.IMSTargeting
 * JD-Core Version:    0.7.0.1
 */