package com.ffusion.alert.shared;

import com.ffusion.alert.interfaces.AEException;
import java.io.Serializable;

public class AELogParams
  implements Serializable
{
  private boolean a;
  private boolean jdField_for;
  private String jdField_case;
  private int jdField_do;
  public static final String jdField_if = "AlertEngine.log";
  public static final int jdField_byte = -2147483648;
  public static final int jdField_new = 0;
  public static final int jdField_int = 1;
  public static final int jdField_try = 2147483647;
  
  public static AELogParams a(boolean paramBoolean1, boolean paramBoolean2, String paramString, int paramInt)
    throws AEException
  {
    AELogParams localAELogParams = new AELogParams();
    localAELogParams.a = paramBoolean1;
    localAELogParams.jdField_for = paramBoolean2;
    localAELogParams.jdField_case = paramString;
    localAELogParams.jdField_do = paramInt;
    return localAELogParams;
  }
  
  public static AELogParams a()
    throws AEException
  {
    AELogParams localAELogParams = new AELogParams();
    localAELogParams.a = true;
    localAELogParams.jdField_for = true;
    localAELogParams.jdField_case = "AlertEngine.log";
    localAELogParams.jdField_do = 0;
    return localAELogParams;
  }
  
  public String toString()
  {
    return "AELogParams contents { consoleEnabled=" + this.a + ", fileEnabled=" + this.jdField_for + ", logFilename=" + this.jdField_case + ", outputLevel=" + this.jdField_do + " }";
  }
  
  public final boolean jdField_do()
  {
    return this.a;
  }
  
  public final boolean jdField_if()
  {
    return this.jdField_for;
  }
  
  public final String jdField_int()
  {
    return this.jdField_case;
  }
  
  public final int jdField_for()
  {
    return this.jdField_do;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.shared.AELogParams
 * JD-Core Version:    0.7.0.1
 */