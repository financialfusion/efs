package com.ffusion.util;

public class Formatable
{
  protected String format;
  protected boolean bIsAllNumbers = true;
  
  public void setFormat(String paramString)
  {
    this.format = paramString;
    if (paramString.indexOf('A') != -1) {
      this.bIsAllNumbers = false;
    }
  }
  
  protected String removeFormatting(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < arrayOfChar.length; i++) {
      if (Character.isLetterOrDigit(arrayOfChar[i])) {
        localStringBuffer.append(arrayOfChar[i]);
      }
    }
    return localStringBuffer.toString();
  }
  
  protected int getFormatLength()
  {
    char[] arrayOfChar = this.format.toCharArray();
    int i = 0;
    for (int j = 0; j < arrayOfChar.length; j++) {
      if ((arrayOfChar[j] == 'N') || (arrayOfChar[j] == 'A')) {
        i++;
      }
    }
    return i;
  }
  
  protected boolean allNumbers(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    for (int i = 0; i < arrayOfChar.length; i++) {
      if (!Character.isDigit(arrayOfChar[i])) {
        return false;
      }
    }
    return true;
  }
  
  protected boolean formatUsingCustomPattern(StringBuffer paramStringBuffer, String paramString)
  {
    boolean bool = true;
    int i = getFormatLength();
    if (i != paramString.length())
    {
      bool = false;
    }
    else
    {
      char[] arrayOfChar1 = paramString.toCharArray();
      char[] arrayOfChar2 = this.format.toCharArray();
      int j = 0;
      int k = 0;
      while (k < arrayOfChar2.length)
      {
        if (arrayOfChar2[k] == 'N')
        {
          if (Character.isDigit(arrayOfChar1[j]))
          {
            paramStringBuffer.append(arrayOfChar1[j]);
          }
          else
          {
            bool = false;
            break;
          }
        }
        else if (arrayOfChar2[k] == 'A')
        {
          if (Character.isLetter(arrayOfChar1[j]))
          {
            paramStringBuffer.append(arrayOfChar1[j]);
          }
          else
          {
            bool = false;
            break;
          }
        }
        else
        {
          paramStringBuffer.append(arrayOfChar2[k]);
          j--;
        }
        k++;
        j++;
      }
    }
    return bool;
  }
  
  public void setAllNumbers(String paramString)
  {
    this.bIsAllNumbers = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAllNumbers()
  {
    return String.valueOf(this.bIsAllNumbers);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.Formatable
 * JD-Core Version:    0.7.0.1
 */