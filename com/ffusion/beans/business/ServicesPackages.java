package com.ffusion.beans.business;

import com.ffusion.util.FilteredList;
import java.util.Iterator;
import java.util.Locale;

public class ServicesPackages
  extends FilteredList
{
  public ServicesPackages() {}
  
  public ServicesPackages(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public ServicesPackage add()
  {
    ServicesPackage localServicesPackage = new ServicesPackage(this.locale);
    add(localServicesPackage);
    return localServicesPackage;
  }
  
  public ServicesPackage create()
  {
    ServicesPackage localServicesPackage = new ServicesPackage(this.locale);
    return localServicesPackage;
  }
  
  public void set(ServicesPackages paramServicesPackages)
  {
    Iterator localIterator = paramServicesPackages.iterator();
    while (localIterator.hasNext())
    {
      ServicesPackage localServicesPackage = (ServicesPackage)localIterator.next();
      if (localServicesPackage != null) {
        add(localServicesPackage);
      }
    }
  }
  
  public ServicesPackage getById(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ServicesPackage localServicesPackage = (ServicesPackage)localIterator.next();
      if (localServicesPackage.getIdValue() == paramInt)
      {
        localObject = localServicesPackage;
        break;
      }
    }
    return localObject;
  }
  
  public ServicesPackage getByServicesPackageName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ServicesPackage localServicesPackage = (ServicesPackage)localIterator.next();
      if (localServicesPackage.getServicesPackageName().equalsIgnoreCase(paramString))
      {
        localObject = localServicesPackage;
        break;
      }
    }
    return localObject;
  }
  
  public void removeById(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ServicesPackage localServicesPackage = (ServicesPackage)localIterator.next();
      if (localServicesPackage.getIdValue() == paramInt)
      {
        localObject = localServicesPackage;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
  
  public void removeByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ServicesPackage localServicesPackage = (ServicesPackage)localIterator.next();
      if (localServicesPackage.getServicesPackageName().equals(paramString) == true)
      {
        localObject = localServicesPackage;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.ServicesPackages
 * JD-Core Version:    0.7.0.1
 */