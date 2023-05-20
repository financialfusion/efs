package com.ffusion.util.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.enums.ValuedEnum;

public class UserAssignedAmount
  extends ValuedEnum
{
  public static final UserAssignedAmount SINGLE = new UserAssignedAmount("single", 0);
  public static final UserAssignedAmount FROM = new UserAssignedAmount("from", 1);
  public static final UserAssignedAmount TO = new UserAssignedAmount("to", 2);
  
  public UserAssignedAmount(String paramString, int paramInt)
  {
    super(paramString, paramInt);
  }
  
  public static UserAssignedAmount getEnum(String paramString)
  {
    return (UserAssignedAmount)getEnum(UserAssignedAmount.class, paramString);
  }
  
  public static UserAssignedAmount getEnum(int paramInt)
  {
    return (UserAssignedAmount)getEnum(UserAssignedAmount.class, paramInt);
  }
  
  public static Map getEnumMap()
  {
    return getEnumMap(UserAssignedAmount.class);
  }
  
  public static List getEnumList()
  {
    return getEnumList(UserAssignedAmount.class);
  }
  
  public static Iterator iterator()
  {
    return iterator(UserAssignedAmount.class);
  }
  
  public static UserAssignedAmount getDefaultUserAssignedAmount()
  {
    return getEnum("single");
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.enums.UserAssignedAmount
 * JD-Core Version:    0.7.0.1
 */