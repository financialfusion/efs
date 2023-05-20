package com.ffusion.ims21.servlet.parse;

import com.ffusion.ims21.util.AcctKey;
import java.util.StringTokenizer;

public class AcctBalRule
{
  boolean a = false;
  boolean jdField_do = false;
  a[] jdField_if = null;
  
  public AcctBalRule(String paramString, boolean paramBoolean)
  {
    this.a = paramBoolean;
    this.jdField_do = a(paramString);
  }
  
  public boolean isValid()
  {
    return this.jdField_do;
  }
  
  private boolean a(String paramString)
  {
    int i = paramString.indexOf("(");
    int j = paramString.indexOf(")");
    if ((i == -1) || (j == -1)) {
      return false;
    }
    String str1 = paramString.substring(i + 1, j);
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ";");
    int k = localStringTokenizer.countTokens();
    if (k == 0) {
      return false;
    }
    this.jdField_if = new a[k];
    int m = 0;
    while (localStringTokenizer.hasMoreTokens())
    {
      a locala = new a();
      String str2 = localStringTokenizer.nextToken();
      i = str2.indexOf("<");
      if (i == -1)
      {
        locala.a = true;
        i = str2.indexOf(">");
        if (i == -1) {
          return false;
        }
        if (i == paramString.length() - 1) {
          return false;
        }
      }
      try
      {
        locala.jdField_do = Integer.parseInt(str2.substring(0, i));
        locala.jdField_if = Integer.parseInt(str2.substring(i + 1, str2.length()));
      }
      catch (Exception localException)
      {
        return false;
      }
      this.jdField_if[(m++)] = locala;
    }
    return true;
  }
  
  public boolean validateAccts(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      return false;
    }
    if ((this.jdField_if == null) || (this.jdField_if.length == 0)) {
      return true;
    }
    for (int i = 0; i < this.jdField_if.length; i++)
    {
      a locala = this.jdField_if[i];
      int j = 0;
      for (int k = 0; k < paramArrayOfInt.length; k++)
      {
        AcctKey localAcctKey = new AcctKey(paramArrayOfInt[k]);
        if (localAcctKey.getAcctId() == locala.jdField_do)
        {
          j = 1;
          if (locala.a)
          {
            if (localAcctKey.getAcctBal() > locala.jdField_if)
            {
              if (this.a) {
                break;
              }
              return true;
            }
            if (!this.a) {
              break;
            }
            return false;
          }
          if (localAcctKey.getAcctBal() < locala.jdField_if)
          {
            if (this.a) {
              break;
            }
            return true;
          }
          if (!this.a) {
            break;
          }
          return false;
        }
      }
      if ((this.a) && (j == 0)) {
        return false;
      }
    }
    return this.a;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer("\nValid=");
    localStringBuffer.append(this.jdField_do).append("\nbInclusive=").append(this.a);
    if ((this.jdField_if == null) || (this.jdField_if.length == 0))
    {
      localStringBuffer.append("\nNoAccts");
    }
    else
    {
      localStringBuffer.append("\nAccts\n");
      for (int i = 0; i < this.jdField_if.length; i++) {
        localStringBuffer.append(this.jdField_if[i]).append("\n");
      }
    }
    return localStringBuffer.toString();
  }
  
  class a
  {
    int jdField_do = 0;
    int jdField_if = 0;
    boolean a = false;
    
    a() {}
    
    public String toString()
    {
      String str = "\nId=" + this.jdField_do + "\nBal=" + this.jdField_if + "\nGreater=" + this.a;
      return str;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.servlet.parse.AcctBalRule
 * JD-Core Version:    0.7.0.1
 */