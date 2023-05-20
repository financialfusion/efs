package com.ffusion.ims21.servlet.parse;

import com.ffusion.ims21.util.AcctKey;
import java.util.StringTokenizer;

public class AcctSumRule
{
  private boolean jdField_if = false;
  private int jdField_for = 0;
  private boolean a = false;
  private int[] jdField_do = null;
  
  public AcctSumRule(String paramString) {}
  
  private boolean a(String paramString)
  {
    int i = paramString.indexOf("<");
    if (i == -1)
    {
      this.a = true;
      i = paramString.indexOf(">");
      if (i == -1) {
        return false;
      }
      if (i == paramString.length() - 1) {
        return false;
      }
    }
    int j = paramString.indexOf("]");
    if (j == -1) {
      return false;
    }
    String str1 = paramString.substring(i + 1, j);
    try
    {
      this.jdField_for = Integer.parseInt(str1);
    }
    catch (Exception localException1)
    {
      return false;
    }
    i = j + 1;
    j = paramString.indexOf(")");
    if (j == -1) {
      return false;
    }
    str1 = paramString.substring(i, j);
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ";");
    int k = localStringTokenizer.countTokens();
    if (k == 0) {
      return false;
    }
    this.jdField_do = new int[k];
    int m = 0;
    while (localStringTokenizer.hasMoreTokens())
    {
      String str2 = localStringTokenizer.nextToken();
      try
      {
        this.jdField_do[(m++)] = Integer.parseInt(str2);
      }
      catch (Exception localException2)
      {
        return false;
      }
    }
    return true;
  }
  
  public boolean validateAccts(int[] paramArrayOfInt)
  {
    boolean bool = false;
    if (paramArrayOfInt != null)
    {
      int i = 0;
      for (int j = 0; j < paramArrayOfInt.length; j++)
      {
        AcctKey localAcctKey = new AcctKey(paramArrayOfInt[j]);
        if (a(localAcctKey.getAcctId())) {
          i += localAcctKey.getAcctBal();
        }
      }
      if (((this.a) && (i > this.jdField_for)) || ((!this.a) && (i < this.jdField_for))) {
        bool = true;
      }
    }
    return bool;
  }
  
  public boolean isValid()
  {
    return this.jdField_if;
  }
  
  private boolean a(int paramInt)
  {
    boolean bool = false;
    if (this.jdField_do != null) {
      for (int i = 0; i < this.jdField_do.length; i++) {
        if (paramInt == this.jdField_do[i])
        {
          bool = true;
          break;
        }
      }
    }
    return bool;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer("Valid=");
    localStringBuffer.append(this.jdField_if);
    localStringBuffer.append("\n Rulebal=").append(this.jdField_for).append("\n bGreater=").append(this.a);
    if ((this.jdField_do == null) || (this.jdField_do.length == 0))
    {
      localStringBuffer.append("\nNoAccts");
    }
    else
    {
      localStringBuffer.append("\nAccts\n");
      for (int i = 0; i < this.jdField_do.length; i++) {
        localStringBuffer.append(this.jdField_do[i]).append("\n");
      }
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.servlet.parse.AcctSumRule
 * JD-Core Version:    0.7.0.1
 */