package com.ffusion.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Modules
  extends ArrayList
{
  public void sortByName()
  {
    Collections.sort(this, Module.getDescNameComparator());
  }
  
  public Module getByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Module localModule = (Module)localIterator.next();
      if (paramString.equals(localModule.getName()))
      {
        localObject = localModule;
        break;
      }
    }
    return localObject;
  }
  
  public Module getById(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Module localModule = (Module)localIterator.next();
      if (paramInt == localModule.getId())
      {
        localObject = localModule;
        break;
      }
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.Modules
 * JD-Core Version:    0.7.0.1
 */