package com.ffusion.beans.portal;

import java.util.ArrayList;
import java.util.Iterator;

public class GeographicUnits
  extends ArrayList
{
  private String a = "UN";
  
  public String getSelected()
  {
    return this.a;
  }
  
  public void setSelected(String paramString)
  {
    this.a = paramString;
  }
  
  public GeographicUnit getByAbbr(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      GeographicUnit localGeographicUnit = (GeographicUnit)localIterator.next();
      if (localGeographicUnit.getAbbr().equals(paramString)) {
        return localGeographicUnit;
      }
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.GeographicUnits
 * JD-Core Version:    0.7.0.1
 */