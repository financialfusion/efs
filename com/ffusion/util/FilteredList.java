package com.ffusion.util;

import com.ffusion.util.beans.PagingContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.StringTokenizer;

public class FilteredList
  extends LocaleableList
  implements Localeable
{
  public static final String REVERSE = "REVERSE";
  String jdField_int = "";
  String jdField_for = "All";
  String jdField_try = "All";
  protected ArrayList filteredList;
  private boolean jdField_do = false;
  private boolean jdField_new = false;
  private PagingContext a = null;
  protected boolean caseInsensitiveSort = false;
  private String jdField_if = ",";
  
  public FilteredList() {}
  
  public FilteredList(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public FilteredList(Collection paramCollection)
  {
    super(paramCollection);
  }
  
  public FilteredList(int paramInt)
  {
    super(paramInt);
  }
  
  public void setAllToken(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.jdField_try = paramString;
    } else {
      this.jdField_try = "All";
    }
  }
  
  public void setFilter(String paramString)
  {
    this.jdField_new = false;
    jdField_if(paramString);
  }
  
  public void setFilterSeparator(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getFilterSeparator()
  {
    return this.jdField_if;
  }
  
  public void setFilterOnFilter(String paramString)
  {
    if (this.filteredList != null) {
      this.jdField_new = true;
    }
    jdField_if(paramString);
  }
  
  private void jdField_if(String paramString)
  {
    this.jdField_do = false;
    paramString = XMLUtil.XMLDecode(paramString);
    this.jdField_for = paramString;
    if (paramString.equals(this.jdField_try))
    {
      this.filteredList = null;
    }
    else
    {
      List localList = a(paramString);
      if (localList.size() > 0) {
        a(localList);
      } else if (!this.jdField_new) {
        this.filteredList = null;
      }
    }
  }
  
  public String getFilter()
  {
    return this.jdField_for;
  }
  
  private List a(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, this.jdField_if);
    ArrayList localArrayList = new ArrayList();
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = (String)localStringTokenizer.nextElement();
      if (str.equalsIgnoreCase("and"))
      {
        this.jdField_do = true;
      }
      else
      {
        int i = 0;
        for (int j = 0; j < "=<>!".length(); j++) {
          if (str.endsWith(String.valueOf("=<>!".charAt(j))))
          {
            i = 1;
            break;
          }
        }
        if (i == 0) {
          localArrayList.add(str);
        }
      }
    }
    return localArrayList;
  }
  
  private void a(List paramList)
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    if (this.jdField_new) {
      i = this.filteredList.size();
    } else {
      i = super.size();
    }
    for (int j = 0; j < i; j++)
    {
      Object localObject = null;
      if (this.jdField_new) {
        localObject = this.filteredList.get(j);
      } else {
        localObject = super.get(j);
      }
      if ((localObject instanceof Filterable))
      {
        Filterable localFilterable = (Filterable)localObject;
        int k = 1;
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext()) {
          if (localFilterable.isFilterable((String)localIterator.next()))
          {
            if (!this.jdField_do) {
              localArrayList.add(localFilterable);
            }
          }
          else if (this.jdField_do) {
            k = 0;
          }
        }
        if ((this.jdField_do) && (k != 0)) {
          localArrayList.add(localFilterable);
        }
      }
    }
    this.filteredList = localArrayList;
  }
  
  public Object getFirstByFilter(String paramString)
  {
    ArrayList localArrayList = this.filteredList;
    this.filteredList = null;
    String str = getFilter();
    setFilter(paramString);
    Iterator localIterator;
    if (this.filteredList == null) {
      localIterator = super.iterator();
    } else {
      localIterator = this.filteredList.iterator();
    }
    Object localObject = null;
    if (localIterator.hasNext()) {
      localObject = localIterator.next();
    }
    this.filteredList = localArrayList;
    this.jdField_for = str;
    return localObject;
  }
  
  public boolean remove(Object paramObject)
  {
    boolean bool = false;
    if (paramObject != null)
    {
      ArrayList localArrayList = this.filteredList;
      this.filteredList = null;
      for (int i = 0; i < size(); i++) {
        if (paramObject == get(i))
        {
          remove(i);
          bool = true;
          break;
        }
      }
      if ((bool) && (localArrayList != null)) {
        for (i = 0; i < localArrayList.size(); i++) {
          if (paramObject == localArrayList.get(i))
          {
            localArrayList.remove(i);
            break;
          }
        }
      }
      this.filteredList = localArrayList;
    }
    return bool;
  }
  
  public Object get(int paramInt)
  {
    if (this.filteredList == null) {
      return super.get(paramInt);
    }
    return this.filteredList.get(paramInt);
  }
  
  public ListIterator listIterator()
  {
    if (this.filteredList == null) {
      return super.listIterator();
    }
    return this.filteredList.listIterator();
  }
  
  public ListIterator listIterator(int paramInt)
  {
    if (this.filteredList == null) {
      return super.listIterator(paramInt);
    }
    return this.filteredList.listIterator(paramInt);
  }
  
  public Iterator iterator()
  {
    if (this.filteredList == null) {
      return super.iterator();
    }
    return this.filteredList.iterator();
  }
  
  public int size()
  {
    if (this.filteredList == null) {
      return super.size();
    }
    return this.filteredList.size();
  }
  
  public String getHasItems()
  {
    if (this.filteredList == null) {
      return String.valueOf(super.size() != 0);
    }
    return String.valueOf(this.filteredList.size() != 0);
  }
  
  public String getSize()
  {
    if (this.filteredList == null) {
      return String.valueOf(super.size());
    }
    return String.valueOf(this.filteredList.size());
  }
  
  public String getSortedBy()
  {
    return this.jdField_int;
  }
  
  public void setToggleSortedBy(String paramString)
  {
    if (this.jdField_int == null) {
      this.jdField_int = "";
    }
    if (paramString == null) {
      paramString = "";
    }
    Object localObject = this.jdField_int;
    int i = 1;
    String str = "";
    if (((String)localObject).indexOf("REVERSE") != -1) {
      i = 0;
    }
    String[] arrayOfString = jdField_do((String)localObject);
    int j;
    if (arrayOfString.length > 0) {
      for (j = 0; j < arrayOfString.length; j++) {
        str = str + "," + arrayOfString[j];
      }
    }
    localObject = str;
    arrayOfString = jdField_do(paramString);
    str = "";
    if (arrayOfString.length > 0) {
      for (j = 0; j < arrayOfString.length; j++) {
        str = str + "," + arrayOfString[j];
      }
    }
    if (str.equalsIgnoreCase((String)localObject)) {
      paramString = (String)localObject + (i != 0 ? ",REVERSE" : "");
    }
    setSortedBy(paramString);
    if (this.filteredList != null) {
      setFilterSortedBy(paramString);
    }
  }
  
  private static String[] jdField_do(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = Strings.trimChars(localStringTokenizer.nextToken(), ' ');
      if ((!str.equals("REVERSE")) && (str.length() > 0)) {
        localArrayList.add(str);
      }
    }
    String[] arrayOfString = new String[localArrayList.size()];
    arrayOfString = (String[])localArrayList.toArray(arrayOfString);
    return arrayOfString;
  }
  
  public void setSortedBy(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      return;
    }
    this.jdField_int = paramString;
    int i = 1;
    ArrayList localArrayList = this.filteredList;
    this.filteredList = null;
    int j = size();
    if (paramString.indexOf("REVERSE") != -1) {
      i = -1;
    }
    if (j > 0)
    {
      Object localObject = get(0);
      if ((localObject instanceof Sortable)) {
        Qsort.sortSortables(this, jdField_do(paramString), i, isCaseInsensitiveSort());
      } else if (isCaseInsensitiveSort()) {
        Qsort.sortStringsIgnoreCase(this, i);
      } else {
        Qsort.sortStrings(this, i);
      }
    }
    this.filteredList = localArrayList;
  }
  
  public void setFilterSortedBy(String paramString)
  {
    this.jdField_int = paramString;
    int i = 0;
    if (this.filteredList != null) {
      i = this.filteredList.size();
    }
    if (i > 0)
    {
      int j = 1;
      if (paramString.indexOf("REVERSE") != -1) {
        j = -1;
      }
      Object localObject = this.filteredList.get(0);
      if ((localObject instanceof Sortable)) {
        Qsort.sortSortables(this.filteredList, jdField_do(paramString), j, isCaseInsensitiveSort());
      } else if (isCaseInsensitiveSort()) {
        Qsort.sortStringsIgnoreCase(this.filteredList, j);
      } else {
        Qsort.sortStrings(this.filteredList, j);
      }
    }
  }
  
  public PagingContext getPagingContext()
  {
    return this.a;
  }
  
  public void setPagingContext(PagingContext paramPagingContext)
  {
    this.a = paramPagingContext;
  }
  
  public boolean isCaseInsensitiveSort()
  {
    return this.caseInsensitiveSort;
  }
  
  public void setCaseInsensitiveSort(boolean paramBoolean)
  {
    this.caseInsensitiveSort = paramBoolean;
  }
  
  public void setCaseInsensitiveSort(String paramString)
  {
    this.caseInsensitiveSort = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getCaseInsensitiveSort()
  {
    return String.valueOf(this.caseInsensitiveSort);
  }
  
  public void setRemove(String paramString)
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
    if (i != -1) {
      remove(i);
    }
  }
  
  public void setLocale(Locale paramLocale)
  {
    if (paramLocale == null) {
      paramLocale = DEFAULT_LOCALE;
    }
    if (!paramLocale.equals(this.locale))
    {
      super.setLocale(paramLocale);
      setSortedBy(getSortedBy());
    }
    else
    {
      super.setLocale(paramLocale);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.FilteredList
 * JD-Core Version:    0.7.0.1
 */