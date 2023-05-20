package com.ffusion.ffs.bpw.enums;

import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.util.FFSProperties;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserType
  extends BPWEnum
{
  public static final UserType CONSUMER = new UserType("consumer", 1);
  public static final UserType BUSINESS = new UserType("business", 2);
  
  public UserType(String paramString, int paramInt)
  {
    super(paramString, paramInt);
  }
  
  public static UserType getEnum(String paramString)
  {
    return (UserType)getEnum(UserType.class, paramString);
  }
  
  public static UserType getEnum(int paramInt)
  {
    return (UserType)getEnum(UserType.class, paramInt);
  }
  
  public static Map getEnumMap()
  {
    return getEnumMap(UserType.class);
  }
  
  public static List getEnumList()
  {
    return getEnumList(UserType.class);
  }
  
  public static Iterator iterator()
  {
    return iterator(UserType.class);
  }
  
  public static int getMaxValue()
  {
    return getMaxValue(UserType.class);
  }
  
  public static int getMinValue()
  {
    return getMinValue(UserType.class);
  }
  
  public static UserType getDefaultUserType()
  {
    return getEnum(a());
  }
  
  private static String a()
  {
    if ((PropertyConfig.getRegisteredProperties() == null) || (PropertyConfig.getRegisteredProperties().otherProperties == null)) {
      return "consumer";
    }
    return PropertyConfig.getRegisteredProperties().otherProperties.getProperty("UserTypeDefault", "consumer");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.enums.UserType
 * JD-Core Version:    0.7.0.1
 */