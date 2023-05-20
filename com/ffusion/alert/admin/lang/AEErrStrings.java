package com.ffusion.alert.admin.lang;

import com.ffusion.alert.admin.IAEErrConstants;
import java.util.ListResourceBundle;

public class AEErrStrings
  extends ListResourceBundle
  implements IAEErrConstants
{
  private static final String jdField_for = System.getProperty("line.separator");
  static final Object[][] jdField_do = { { "ENGINE_NOT_INITIALIZED", "The Universal Alert Engine has not yet been initialized." }, { "ENGINE_ALREADY_INITIALIZED", "The Universal Alert Engine has already been initialized." } };
  
  public Object[][] getContents()
  {
    return jdField_do;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.admin.lang.AEErrStrings
 * JD-Core Version:    0.7.0.1
 */