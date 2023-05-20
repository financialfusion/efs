package com.ffusion.csil.beans.entitlements;

import com.ffusion.util.Filterable;
import com.ffusion.util.Sortable;
import com.ffusion.util.beans.XMLStrings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public abstract class TypePropertyList
  implements Cloneable, Sortable, XMLStrings, Filterable
{
  private String Y;
  private HashMap V;
  private String X;
  private String W;
  
  public TypePropertyList(String paramString)
  {
    this.Y = paramString;
    this.V = new HashMap();
  }
  
  public Object clone()
  {
    TypePropertyList localTypePropertyList = null;
    try
    {
      localTypePropertyList = (TypePropertyList)super.clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException) {}
    localTypePropertyList.V = ((HashMap)this.V.clone());
    Iterator localIterator = this.V.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = this.V.get(str);
      if ((localObject instanceof ArrayList))
      {
        localObject = ((ArrayList)localObject).clone();
        localTypePropertyList.V.put(str, localObject);
      }
    }
    return localTypePropertyList;
  }
  
  public void setOperationName(String paramString)
  {
    this.Y = paramString;
  }
  
  public String getOperationName()
  {
    return this.Y;
  }
  
  public void clearProperty(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    this.V.remove(paramString);
  }
  
  public void removeProperty(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      throw new NullPointerException();
    }
    Object localObject = this.V.get(paramString1);
    if ((localObject instanceof String))
    {
      if (paramString2.equals(localObject)) {
        this.V.remove(paramString1);
      }
    }
    else if ((localObject instanceof ArrayList))
    {
      ArrayList localArrayList = (ArrayList)localObject;
      int i = localArrayList.indexOf(paramString2);
      if (i != -1)
      {
        int j = localArrayList.size();
        if (j == 1)
        {
          this.V.remove(paramString1);
        }
        else
        {
          localArrayList.set(i, localArrayList.get(j - 1));
          localArrayList.remove(j - 1);
        }
      }
    }
  }
  
  public void addProperty(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      throw new NullPointerException();
    }
    Object localObject = this.V.get(paramString1);
    if (localObject == null)
    {
      this.V.put(paramString1, paramString2);
    }
    else
    {
      ArrayList localArrayList;
      if ((localObject instanceof String))
      {
        if (!paramString2.equals(localObject))
        {
          localArrayList = new ArrayList();
          localArrayList.add(localObject);
          localArrayList.add(paramString2);
          this.V.put(paramString1, localArrayList);
        }
      }
      else if ((localObject instanceof ArrayList))
      {
        localArrayList = (ArrayList)localObject;
        if (!localArrayList.contains(paramString2)) {
          localArrayList.add(paramString2);
        }
      }
    }
  }
  
  public boolean isPropertySet(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      throw new NullPointerException();
    }
    Object localObject = this.V.get(paramString1);
    if ((localObject instanceof String)) {
      return paramString2.equals(localObject);
    }
    if ((localObject instanceof ArrayList)) {
      return ((ArrayList)localObject).contains(paramString2);
    }
    return false;
  }
  
  public boolean isPropertySet(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    return this.V.containsKey(paramString);
  }
  
  public boolean areAllPropertiesSet(HashMap paramHashMap)
  {
    Iterator localIterator1 = paramHashMap.keySet().iterator();
    while (localIterator1.hasNext()) {
      if (!this.V.containsKey(localIterator1.next())) {
        return false;
      }
    }
    localIterator1 = paramHashMap.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str = (String)localIterator1.next();
      Object localObject1 = paramHashMap.get(str);
      Object localObject2 = this.V.get(str);
      if (localObject1 != null)
      {
        if (localObject2 == null) {
          return false;
        }
        Object localObject3;
        if ((localObject2 instanceof String))
        {
          if ((localObject1 instanceof String))
          {
            if (!localObject1.equals(localObject2)) {
              return false;
            }
          }
          else if ((localObject1 instanceof ArrayList))
          {
            localObject3 = ((ArrayList)localObject1).iterator();
            while (((Iterator)localObject3).hasNext()) {
              if (!localObject2.equals(((Iterator)localObject3).next())) {
                return false;
              }
            }
          }
          else
          {
            return false;
          }
        }
        else if ((localObject2 instanceof ArrayList))
        {
          localObject3 = (ArrayList)localObject2;
          if ((localObject1 instanceof String))
          {
            if (!((ArrayList)localObject3).contains(localObject1)) {
              return false;
            }
          }
          else if ((localObject1 instanceof ArrayList))
          {
            Iterator localIterator2 = ((ArrayList)localObject1).iterator();
            while (localIterator2.hasNext()) {
              if (!((ArrayList)localObject3).contains(localIterator2.next())) {
                return false;
              }
            }
          }
          else
          {
            return false;
          }
        }
      }
    }
    return true;
  }
  
  public int numPropertyValues(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    Object localObject = this.V.get(paramString);
    if ((localObject instanceof String)) {
      return 1;
    }
    if ((localObject instanceof ArrayList)) {
      return ((ArrayList)localObject).size();
    }
    return 0;
  }
  
  public String getPropertyValue(String paramString, int paramInt)
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    Object localObject = this.V.get(paramString);
    if ((localObject instanceof String))
    {
      if (paramInt != 0) {
        throw new IndexOutOfBoundsException();
      }
      return (String)localObject;
    }
    if ((localObject instanceof ArrayList)) {
      return (String)((ArrayList)localObject).get(paramInt);
    }
    return null;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append('(').append(getOperationName()).append(':');
    PropertyIterator localPropertyIterator = new PropertyIterator();
    while (localPropertyIterator.hasNextName())
    {
      String str1 = localPropertyIterator.nextName();
      localStringBuffer.append('(').append(str1).append('=');
      while (localPropertyIterator.hasNextValue())
      {
        String str2 = localPropertyIterator.nextValue();
        localStringBuffer.append(str2);
        if (localPropertyIterator.hasNextValue()) {
          localStringBuffer.append(',');
        }
      }
      localStringBuffer.append(')');
      if (localPropertyIterator.hasNextName()) {
        localStringBuffer.append(',');
      }
    }
    localStringBuffer.append(')');
    return localStringBuffer.toString();
  }
  
  public String getValue()
  {
    String str = null;
    try
    {
      str = getPropertyValue(this.X, 0);
    }
    catch (Exception localException) {}
    if (str == null) {
      str = "";
    }
    return str;
  }
  
  public void setCurrentProperty(String paramString)
  {
    this.X = paramString;
  }
  
  public void setCurrentValue(String paramString)
  {
    this.W = paramString;
  }
  
  public String getIsCurrentValueSet()
  {
    boolean bool;
    try
    {
      bool = isPropertySet(this.X, this.W);
    }
    catch (Exception localException)
    {
      bool = false;
    }
    return bool ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
  }
  
  public String getIsCurrentPropertySet()
  {
    boolean bool;
    try
    {
      bool = isPropertySet(this.X);
    }
    catch (Exception localException)
    {
      bool = false;
    }
    return bool ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    TypePropertyList localTypePropertyList = (TypePropertyList)paramObject;
    int i = 1;
    if ((paramString.equals("OPERATION_NAME")) && (this.Y != null) && (localTypePropertyList.getOperationName() != null))
    {
      i = this.Y.compareTo(localTypePropertyList.getOperationName());
    }
    else
    {
      String str1 = null;
      String str2 = null;
      try
      {
        str1 = getPropertyValue(paramString, 0);
      }
      catch (Exception localException1) {}
      try
      {
        str2 = localTypePropertyList.getPropertyValue(paramString, 0);
      }
      catch (Exception localException2) {}
      if ((str1 == null) && (str2 == null)) {
        i = 0;
      } else if ((str1 != null) && (str2 == null)) {
        i = -1;
      } else if ((str1 == null) && (str2 != null)) {
        i = 1;
      } else {
        i = str1.compareTo(str2);
      }
    }
    return i;
  }
  
  public boolean isFilterable(String paramString)
  {
    boolean bool = true;
    int i = -1;
    i = paramString.indexOf('=');
    if (i == -1)
    {
      bool = false;
    }
    else
    {
      String str1 = paramString.substring(0, i);
      String str2 = paramString.substring(i + 1);
      if ((str1 == null) || (str2 == null)) {
        bool = false;
      } else {
        bool = isPropertySet(str1, str2);
      }
    }
    return bool;
  }
  
  public HashMap getPropertiesMap()
  {
    return this.V;
  }
  
  public void setPropertiesMap(HashMap paramHashMap)
  {
    if (paramHashMap != null) {
      this.V = paramHashMap;
    }
  }
  
  public class PropertyIterator
  {
    private Iterator jdField_if;
    private Iterator a;
    private String jdField_do;
    
    public PropertyIterator()
    {
      this(TypePropertyList.this.V);
    }
    
    public PropertyIterator(HashMap paramHashMap)
    {
      this.jdField_if = paramHashMap.entrySet().iterator();
      this.a = null;
      this.jdField_do = null;
    }
    
    public boolean hasNextName()
    {
      return this.jdField_if.hasNext();
    }
    
    public boolean hasNextValue()
    {
      if (this.a != null) {
        return this.a.hasNext();
      }
      return this.jdField_do != null;
    }
    
    public String nextName()
    {
      Map.Entry localEntry = (Map.Entry)this.jdField_if.next();
      this.a = null;
      this.jdField_do = null;
      Object localObject = localEntry.getValue();
      if ((localObject instanceof ArrayList)) {
        this.a = ((ArrayList)localObject).iterator();
      } else if ((localObject instanceof String)) {
        this.jdField_do = ((String)localObject);
      } else {
        throw new NoSuchElementException();
      }
      return (String)localEntry.getKey();
    }
    
    public String nextValue()
    {
      if (this.a != null) {
        return (String)this.a.next();
      }
      if (this.jdField_do != null)
      {
        String str = this.jdField_do;
        this.jdField_do = null;
        return str;
      }
      throw new NoSuchElementException();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.TypePropertyList
 * JD-Core Version:    0.7.0.1
 */