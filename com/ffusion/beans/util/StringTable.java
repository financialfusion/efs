package com.ffusion.beans.util;

import com.ffusion.util.Qsort;
import com.ffusion.util.XMLHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class StringTable
  extends ArrayList
{
  private static String a = "KEY";
  private static String jdField_int = "VALUE";
  private static String jdField_byte = "REVERSE";
  private String jdField_if;
  private HashMap jdField_for = new HashMap();
  private String jdField_try;
  private String jdField_do;
  private int jdField_new = 1;
  
  public void setKey(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public void setValue(String paramString)
  {
    if ((this.jdField_if != null) && (paramString != null)) {
      this.jdField_for.put(this.jdField_if, paramString);
    }
    a();
  }
  
  public String getKey()
  {
    return this.jdField_if;
  }
  
  public String getValue()
  {
    if (this.jdField_if == null) {
      return null;
    }
    return (String)this.jdField_for.get(this.jdField_if);
  }
  
  public void setRemove(String paramString)
  {
    this.jdField_for.remove(paramString);
    a();
  }
  
  public void setUpdate(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    int i = localStringTokenizer.countTokens();
    String str1 = localStringTokenizer.nextToken();
    String str2 = localStringTokenizer.nextToken();
    String str3 = "";
    if (i == 3) {
      str3 = localStringTokenizer.nextToken();
    }
    if (str2.equalsIgnoreCase("Set")) {
      this.jdField_for.put(str1, str3);
    } else if (str2.equalsIgnoreCase("Remove")) {
      this.jdField_for.remove(str1);
    }
    a();
  }
  
  public String getSize()
  {
    return String.valueOf(size());
  }
  
  public String getKeys()
  {
    Iterator localIterator = this.jdField_for.keySet().iterator();
    String str = "";
    while (localIterator.hasNext())
    {
      str = str + (String)localIterator.next();
      if (localIterator.hasNext()) {
        str = str + ",";
      }
    }
    return str;
  }
  
  public String getValues()
  {
    Iterator localIterator = this.jdField_for.values().iterator();
    String str = "";
    while (localIterator.hasNext())
    {
      str = str + (String)localIterator.next();
      if (localIterator.hasNext()) {
        str = str + ",";
      }
    }
    return str;
  }
  
  public void setFilter(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.jdField_do = null;
    } else {
      this.jdField_do = paramString;
    }
    a();
  }
  
  public void setSortedBy(String paramString)
  {
    int i = paramString.indexOf(',');
    this.jdField_new = 1;
    if (i != -1)
    {
      if (paramString.indexOf(jdField_byte) != -1) {
        this.jdField_new = -1;
      }
      this.jdField_try = a(paramString.substring(0, i), ' ');
      if (this.jdField_try.equals(jdField_byte)) {
        this.jdField_try = a(paramString.substring(i + 1), ' ');
      }
    }
    else
    {
      this.jdField_try = a(paramString, ' ');
    }
    a();
  }
  
  private void a()
  {
    synchronized (this)
    {
      Iterator localIterator = this.jdField_for.keySet().iterator();
      clear();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        String str2 = (String)this.jdField_for.get(str1);
        if (this.jdField_do != null)
        {
          if (str2.equals(this.jdField_do)) {
            add(str1);
          }
        }
        else if ((this.jdField_try == null) || (this.jdField_try.equals(jdField_int))) {
          add(str2);
        } else {
          add(str1);
        }
      }
      Qsort.sortStrings(this, this.jdField_new);
    }
  }
  
  private static String a(String paramString, char paramChar)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      int i = 0;
      int j = paramString.length();
      while ((i < j) && (paramString.charAt(i) == paramChar)) {
        i++;
      }
      if (i > 0)
      {
        if (i == j) {
          paramString = "";
        } else {
          paramString = paramString.substring(i);
        }
        j = paramString.length();
      }
      for (i = j - 1; paramString.charAt(i) == paramChar; i--) {}
      if (i != j - 1) {
        paramString = paramString.substring(0, i + 1);
      }
    }
    return paramString;
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
      str1 = str1.trim();
      String str2 = getElement();
      if (str2.equalsIgnoreCase("KEY")) {
        StringTable.this.setKey(str1);
      } else if (str2.equalsIgnoreCase("VALUE")) {
        StringTable.this.setValue(str1);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.StringTable
 * JD-Core Version:    0.7.0.1
 */