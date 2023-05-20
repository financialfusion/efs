package com.ffusion.alert.shared;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public final class AEResourceBundle
{
  private ResourceBundle jdField_if = null;
  private String a;
  
  public AEResourceBundle(String paramString)
  {
    this.a = paramString;
  }
  
  public final String a(String paramString)
  {
    if (this.jdField_if == null) {
      this.jdField_if = ResourceBundle.getBundle(this.a);
    }
    return this.jdField_if.getString(paramString);
  }
  
  public final String a(String paramString, Object[] paramArrayOfObject)
  {
    return MessageFormat.format(a(paramString), paramArrayOfObject);
  }
  
  public final String a(String paramString, Object paramObject)
  {
    return a(paramString, new Object[] { paramObject });
  }
  
  public final String a(String paramString, Object paramObject1, Object paramObject2)
  {
    return a(paramString, new Object[] { paramObject1, paramObject2 });
  }
  
  public final String a(String paramString, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    return a(paramString, new Object[] { paramObject1, paramObject2, paramObject3 });
  }
  
  public final String a(String paramString, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4)
  {
    return a(paramString, new Object[] { paramObject1, paramObject2, paramObject3, paramObject4 });
  }
  
  public final String a(String paramString, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5)
  {
    return a(paramString, new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5 });
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.shared.AEResourceBundle
 * JD-Core Version:    0.7.0.1
 */