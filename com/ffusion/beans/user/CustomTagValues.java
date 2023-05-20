package com.ffusion.beans.user;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import java.text.Collator;

public class CustomTagValues
  extends FilteredList
  implements IMSDefines, Sortable
{
  protected String tagName;
  
  public CustomTagValues() {}
  
  public CustomTagValues(String paramString)
  {
    this.tagName = paramString;
  }
  
  public void setTagName(String paramString)
  {
    this.tagName = paramString;
  }
  
  public String getTagName()
  {
    return this.tagName;
  }
  
  public void setValue(String paramString)
  {
    add(paramString);
  }
  
  public void setSortedBy(String paramString)
  {
    if ((paramString.equals("VALUE")) && (size() > 1))
    {
      String str = (String)get(0);
      try
      {
        int i = Integer.parseInt(str);
        jdMethod_int();
      }
      catch (NumberFormatException localNumberFormatException)
      {
        jdMethod_for();
      }
    }
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    CustomTagValues localCustomTagValues = (CustomTagValues)paramObject;
    int i = 0;
    if ((paramString.equals("TAG_NAME")) && (getTagName() != null) && (localCustomTagValues.getTagName() != null)) {
      i = localCollator.compare(getTagName(), localCustomTagValues.getTagName());
    }
    return i;
  }
  
  private void jdMethod_for()
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      Object localObject = (String)get(j);
      for (int k = j + 1; k < i; k++)
      {
        String str = (String)get(k);
        if (str.compareTo((String)localObject) < 0)
        {
          set(j, str);
          set(k, localObject);
          localObject = str;
        }
      }
    }
  }
  
  private void jdMethod_int()
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      int k = Integer.parseInt((String)get(j));
      for (int m = j + 1; m < i; m++)
      {
        int n = Integer.parseInt((String)get(m));
        if (n < k)
        {
          set(j, Integer.toString(n));
          set(m, Integer.toString(k));
          k = n;
        }
      }
    }
  }
  
  public void set(String paramString1, String paramString2)
  {
    int i = 1;
    if (paramString1.equals("TAG_NAME")) {
      this.tagName = paramString2;
    } else if (paramString1.equals("VALUE")) {
      add(paramString2);
    }
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if (str.length() > 0) {
        CustomTagValues.this.set(getElement(), str);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.user.CustomTagValues
 * JD-Core Version:    0.7.0.1
 */