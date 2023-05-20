package com.ffusion.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class EnhancedStringTokenizer
  implements Enumeration
{
  public static final int NO_RETURN_DELIMS = 0;
  public static final int RETURN_DELIMS = 1;
  public static final int NO_CONSECUTIVE_DELIMS = 2;
  protected int Mode = 2;
  protected String Delimiter = " \t\n\r\f";
  protected String Remainder = "";
  protected StringTokenizer st = null;
  
  public EnhancedStringTokenizer(String paramString)
  {
    a(0);
    this.st = new StringTokenizer(paramString);
  }
  
  public EnhancedStringTokenizer(String paramString1, String paramString2)
  {
    a(0);
    setDelimiter(paramString2);
    this.st = new StringTokenizer(paramString1, paramString2);
  }
  
  public EnhancedStringTokenizer(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (paramBoolean) {
      a(1);
    } else {
      a(0);
    }
    setDelimiter(paramString2);
    this.st = new StringTokenizer(paramString1, paramString2, paramBoolean);
  }
  
  public EnhancedStringTokenizer(String paramString1, String paramString2, int paramInt)
  {
    a(paramInt);
    setDelimiter(paramString2);
    switch (getMode())
    {
    case 0: 
      this.st = new StringTokenizer(paramString1, paramString2, false);
      break;
    case 1: 
      this.st = new StringTokenizer(paramString1, paramString2, true);
      break;
    case 2: 
    default: 
      a(paramString1);
    }
  }
  
  void a(String paramString)
  {
    setRemainder(paramString);
  }
  
  void a(int paramInt)
  {
    this.Mode = paramInt;
  }
  
  public int getMode()
  {
    return this.Mode;
  }
  
  protected void setDelimiter(String paramString)
  {
    this.Delimiter = paramString;
  }
  
  public String getDelimiter()
  {
    return this.Delimiter;
  }
  
  protected void setRemainder(String paramString)
  {
    this.Remainder = paramString;
  }
  
  public String getRemainder()
  {
    return this.Remainder;
  }
  
  public int countTokens()
  {
    switch (getMode())
    {
    case 2: 
      String str1 = getRemainder();
      int i = 0;
      try
      {
        for (;;)
        {
          String str2 = nextToken();
          i++;
        }
      }
      catch (NoSuchElementException localNoSuchElementException)
      {
        setRemainder(str1);
        return i;
      }
    }
    return this.st.countTokens();
  }
  
  public boolean hasMoreElements()
  {
    switch (getMode())
    {
    case 2: 
      int i = 0;
      i = getRemainder().indexOf(getDelimiter(), i);
      if (i != -1) {
        return true;
      }
      return getRemainder().length() > 0;
    }
    return this.st.hasMoreElements();
  }
  
  public Object nextElement()
    throws NoSuchElementException
  {
    switch (getMode())
    {
    case 2: 
      int i = 0;
      i = getRemainder().indexOf(getDelimiter(), i);
      String str;
      if (i != -1)
      {
        str = getRemainder().substring(0, i);
        setRemainder(getRemainder().substring(i + 1));
        return str;
      }
      if (getRemainder().length() > 0)
      {
        str = getRemainder();
        setRemainder("");
        return str;
      }
      throw new NoSuchElementException();
    }
    return this.st.nextElement();
  }
  
  public boolean hasMoreTokens()
  {
    switch (getMode())
    {
    case 2: 
      int i = 0;
      i = getRemainder().indexOf(getDelimiter(), i);
      if (i != -1) {
        return true;
      }
      return getRemainder().length() > 0;
    }
    return this.st.hasMoreElements();
  }
  
  public String nextToken()
    throws NoSuchElementException
  {
    switch (getMode())
    {
    case 2: 
      int i = 0;
      i = getRemainder().indexOf(getDelimiter(), i);
      String str;
      if (i != -1)
      {
        str = getRemainder().substring(0, i);
        setRemainder(getRemainder().substring(i + getDelimiter().length()));
        return str;
      }
      if (getRemainder().length() > 0)
      {
        str = getRemainder();
        setRemainder("");
        return str;
      }
      throw new NoSuchElementException();
    }
    return this.st.nextToken();
  }
  
  public String nextToken(String paramString)
    throws NoSuchElementException
  {
    switch (getMode())
    {
    case 2: 
      setDelimiter(paramString);
      return nextToken();
    }
    return this.st.nextToken(paramString);
  }
  
  public static ArrayList getTokens(String paramString1, String paramString2)
    throws Exception
  {
    return getTokens(paramString1, paramString2, false);
  }
  
  public static ArrayList getTokens(String paramString1, String paramString2, boolean paramBoolean)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      return localArrayList;
    }
    if ((paramString2 == null) || (paramString2.length() == 0))
    {
      localArrayList.add(paramString1);
      return localArrayList;
    }
    EnhancedStringTokenizer localEnhancedStringTokenizer = new EnhancedStringTokenizer(paramString1, paramString2, 2);
    while (localEnhancedStringTokenizer.hasMoreTokens())
    {
      String str = localEnhancedStringTokenizer.nextToken();
      if ((str != null) && (str.length() > 0))
      {
        if (paramBoolean == true) {
          str = str + paramString2;
        }
        localArrayList.add(str);
      }
    }
    return localArrayList;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.EnhancedStringTokenizer
 * JD-Core Version:    0.7.0.1
 */