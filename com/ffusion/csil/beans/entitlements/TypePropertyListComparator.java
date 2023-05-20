package com.ffusion.csil.beans.entitlements;

import com.ffusion.util.beans.LocaleableBean;
import java.text.Collator;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class TypePropertyListComparator
  extends LocaleableBean
  implements Comparator
{
  private List a7;
  
  public TypePropertyListComparator(Locale paramLocale, List paramList)
  {
    super(paramLocale);
    this.a7 = paramList;
  }
  
  public int compare(Object paramObject1, Object paramObject2)
  {
    int i = 0;
    String str = null;
    Iterator localIterator = this.a7.iterator();
    while ((i == 0) && (localIterator.hasNext()))
    {
      str = (String)localIterator.next();
      i = a(paramObject1, paramObject2, str);
    }
    return i;
  }
  
  private int a(Object paramObject1, Object paramObject2, String paramString)
  {
    TypePropertyList localTypePropertyList1 = (TypePropertyList)paramObject1;
    TypePropertyList localTypePropertyList2 = (TypePropertyList)paramObject2;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramString);
    localStringBuffer.append('_');
    localStringBuffer.append(this.locale.getLanguage());
    String str1 = localStringBuffer.toString();
    localStringBuffer.append('_');
    localStringBuffer.append(this.locale.getCountry());
    int i = 1;
    if ((paramString.equals("OPERATION_NAME")) && (localTypePropertyList1.getOperationName() != null) && (localTypePropertyList2.getOperationName() != null))
    {
      i = jdMethod_do(localTypePropertyList1.getOperationName(), localTypePropertyList2.getOperationName());
    }
    else
    {
      String str2 = null;
      String str3 = null;
      try
      {
        if (str2 == null) {
          str2 = localTypePropertyList1.getPropertyValue(localStringBuffer.toString(), 0);
        }
        if (str2 == null) {
          str2 = localTypePropertyList1.getPropertyValue(str1, 0);
        }
        if (str2 == null) {
          str2 = localTypePropertyList1.getPropertyValue(paramString, 0);
        }
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException1)
      {
        str2 = null;
      }
      try
      {
        if (str3 == null) {
          str3 = localTypePropertyList2.getPropertyValue(localStringBuffer.toString(), 0);
        }
        if (str3 == null) {
          str3 = localTypePropertyList2.getPropertyValue(str1, 0);
        }
        if (str3 == null) {
          str3 = localTypePropertyList2.getPropertyValue(paramString, 0);
        }
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException2)
      {
        str3 = null;
      }
      i = jdMethod_do(str2, str3);
    }
    return i;
  }
  
  private int jdMethod_do(String paramString1, String paramString2)
  {
    if ((paramString1 == null) && (paramString2 == null)) {
      return 0;
    }
    if (paramString1 == null) {
      return -1;
    }
    if (paramString2 == null) {
      return 1;
    }
    String str1 = paramString1.replace('-', ' ');
    String str2 = paramString2.replace('-', ' ');
    StringTokenizer localStringTokenizer1 = new StringTokenizer(str1, " ");
    StringTokenizer localStringTokenizer2 = new StringTokenizer(str2, " ");
    while ((localStringTokenizer1.hasMoreTokens()) && (localStringTokenizer2.hasMoreTokens()))
    {
      String str3 = localStringTokenizer1.nextToken();
      String str4 = localStringTokenizer2.nextToken();
      int i = doGetCollator().compare(str3, str4);
      if (i != 0) {
        return i;
      }
    }
    if ((!localStringTokenizer1.hasMoreTokens()) && (localStringTokenizer2.hasMoreTokens())) {
      return -1;
    }
    if ((localStringTokenizer1.hasMoreTokens()) && (!localStringTokenizer2.hasMoreTokens())) {
      return 1;
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.TypePropertyListComparator
 * JD-Core Version:    0.7.0.1
 */