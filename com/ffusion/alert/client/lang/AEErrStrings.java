package com.ffusion.alert.client.lang;

import com.ffusion.alert.client.IAEErrConstants;
import java.util.ListResourceBundle;

public class AEErrStrings
  extends ListResourceBundle
  implements IAEErrConstants
{
  private static final String jdField_do = System.getProperty("line.separator");
  static final Object[][] jdField_if = { { "ENGINE_NOT_RUNNING", "The Universal Alert Engine is not running." }, { "ENGINE_ALREADY_RUNNING", "The Universal Alert Engine is already running." } };
  
  public Object[][] getContents()
  {
    return jdField_if;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.client.lang.AEErrStrings
 * JD-Core Version:    0.7.0.1
 */