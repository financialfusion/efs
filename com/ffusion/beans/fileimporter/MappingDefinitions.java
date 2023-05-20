package com.ffusion.beans.fileimporter;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import java.util.Iterator;
import java.util.Locale;

public class MappingDefinitions
  extends FilteredList
  implements Localeable
{
  public MappingDefinition findByID(int paramInt)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MappingDefinition localMappingDefinition = (MappingDefinition)localIterator.next();
      if (localMappingDefinition.getMappingID() == paramInt) {
        return localMappingDefinition;
      }
    }
    return null;
  }
  
  public MappingDefinition findByName(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MappingDefinition localMappingDefinition = (MappingDefinition)localIterator.next();
      if (localMappingDefinition.getName().equalsIgnoreCase(paramString)) {
        return localMappingDefinition;
      }
    }
    return null;
  }
  
  public MappingDefinition add()
  {
    MappingDefinition localMappingDefinition = new MappingDefinition(this.locale);
    super.add(localMappingDefinition);
    return localMappingDefinition;
  }
  
  public MappingDefinition create()
  {
    return new MappingDefinition(this.locale);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if ((paramObject == null) || (!(paramObject instanceof MappingDefinitions))) {
      return false;
    }
    MappingDefinitions localMappingDefinitions = (MappingDefinitions)paramObject;
    if (super.size() != localMappingDefinitions.size()) {
      return false;
    }
    if (this.locale != localMappingDefinitions.locale)
    {
      if ((this.locale == null) || (localMappingDefinitions.locale == null)) {
        return false;
      }
      if (!this.locale.equals(localMappingDefinitions.locale)) {
        return false;
      }
    }
    return super.containsAll(localMappingDefinitions);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.MappingDefinitions
 * JD-Core Version:    0.7.0.1
 */