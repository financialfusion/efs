package com.ffusion.beans;

import java.util.Collection;

public abstract interface IdCollection
  extends Collection
{
  public abstract Object getElementByID(String paramString);
  
  public abstract void removeByID(String paramString);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.IdCollection
 * JD-Core Version:    0.7.0.1
 */