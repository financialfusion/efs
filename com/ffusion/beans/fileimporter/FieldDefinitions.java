package com.ffusion.beans.fileimporter;

import java.util.ArrayList;
import java.util.Iterator;

public class FieldDefinitions
  extends ArrayList
  implements Cloneable
{
  public Object clone()
  {
    FieldDefinitions localFieldDefinitions = new FieldDefinitions();
    for (int i = 0; i < size(); i++) {
      localFieldDefinitions.add(((FieldDefinition)get(i)).clone());
    }
    return localFieldDefinitions;
  }
  
  public FieldDefinition findByName(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FieldDefinition localFieldDefinition = (FieldDefinition)localIterator.next();
      if ((localFieldDefinition.getName() != null) && (localFieldDefinition.getName().equalsIgnoreCase(paramString))) {
        return localFieldDefinition;
      }
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.FieldDefinitions
 * JD-Core Version:    0.7.0.1
 */