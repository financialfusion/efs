package com.ffusion.beans.business;

import com.ffusion.util.FilteredList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

public class ServiceFeatures
  extends FilteredList
{
  public ServiceFeatures() {}
  
  public ServiceFeatures(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public ServiceFeature add()
  {
    ServiceFeature localServiceFeature = new ServiceFeature(this.locale);
    add(localServiceFeature);
    return localServiceFeature;
  }
  
  public ServiceFeature create()
  {
    ServiceFeature localServiceFeature = new ServiceFeature(this.locale);
    return localServiceFeature;
  }
  
  public void set(ServiceFeatures paramServiceFeatures)
  {
    Iterator localIterator = paramServiceFeatures.iterator();
    while (localIterator.hasNext())
    {
      ServiceFeature localServiceFeature = (ServiceFeature)localIterator.next();
      if (localServiceFeature != null) {
        add(localServiceFeature);
      }
    }
  }
  
  public ServiceFeature getByFeatureName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ServiceFeature localServiceFeature = (ServiceFeature)localIterator.next();
      if (localServiceFeature.getFeatureName().equalsIgnoreCase(paramString))
      {
        localObject = localServiceFeature;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ServiceFeature localServiceFeature = (ServiceFeature)localIterator.next();
      if (localServiceFeature.getFeatureName().equals(paramString) == true)
      {
        localObject = localServiceFeature;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
  
  public ArrayList getFeatureCategories()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localObject = (ServiceFeature)localIterator.next();
      String str = ((ServiceFeature)localObject).getFeatureCategory();
      if (!localArrayList.contains(str)) {
        localArrayList.add(str);
      }
    }
    Object localObject = new String[localArrayList.size()];
    localArrayList.toArray((Object[])localObject);
    Arrays.sort((Object[])localObject);
    localArrayList.clear();
    localArrayList.addAll(Arrays.asList((Object[])localObject));
    return localArrayList;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.ServiceFeatures
 * JD-Core Version:    0.7.0.1
 */