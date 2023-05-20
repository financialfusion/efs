package com.ffusion.beans;

import com.ffusion.util.Stringable;
import java.io.Serializable;

public class Email
  implements Serializable, Stringable, Cloneable
{
  private String a;
  private boolean jdField_if = true;
  
  protected Email() {}
  
  public Email(String paramString)
  {
    fromString(paramString);
  }
  
  public Object clone()
  {
    try
    {
      return super.clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException) {}
    return null;
  }
  
  public void fromString(String paramString)
  {
    this.a = paramString;
    if ((paramString != null) && (paramString.length() > 0)) {
      a(paramString);
    }
  }
  
  private void a(String paramString)
  {
    int i = paramString.indexOf("@");
    int j = paramString.lastIndexOf(".");
    try
    {
      if ((i != -1) && (j != -1))
      {
        if (a(paramString.substring(0, i), "."))
        {
          if (a(paramString.substring(i + 1, j), "."))
          {
            if (a(paramString.substring(j + 1), ""))
            {
              this.a = paramString;
              this.jdField_if = true;
            }
            else
            {
              this.jdField_if = false;
            }
          }
          else {
            this.jdField_if = false;
          }
        }
        else {
          this.jdField_if = false;
        }
      }
      else {
        this.jdField_if = false;
      }
    }
    catch (Exception localException)
    {
      this.jdField_if = false;
    }
  }
  
  public boolean isValid()
  {
    return this.jdField_if;
  }
  
  public String toString()
  {
    return this.a;
  }
  
  public String getString()
  {
    return toString();
  }
  
  public void setString(String paramString)
  {
    fromString(paramString);
  }
  
  private static boolean a(String paramString1, String paramString2)
  {
    for (int i = 0; i < paramString1.length(); i++) {
      if ((!Character.isLetterOrDigit(paramString1.charAt(i))) && (!a(paramString1.charAt(i))))
      {
        int j = 0;
        for (int k = 0; k < paramString2.length(); k++) {
          if (paramString2.charAt(k) == paramString1.charAt(i))
          {
            j = 1;
            break;
          }
        }
        if (j == 0) {
          return false;
        }
      }
    }
    return true;
  }
  
  private static boolean a(char paramChar)
  {
    boolean bool = false;
    switch (Character.getType(paramChar))
    {
    case 20: 
    case 23: 
    case 24: 
      bool = true;
      break;
    case 0: 
    case 1: 
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 6: 
    case 7: 
    case 8: 
    case 9: 
    case 10: 
    case 11: 
    case 12: 
    case 13: 
    case 14: 
    case 15: 
    case 16: 
    case 17: 
    case 18: 
    case 19: 
    case 21: 
    case 22: 
    case 25: 
    case 26: 
    case 27: 
    case 28: 
    default: 
      bool = false;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.Email
 * JD-Core Version:    0.7.0.1
 */