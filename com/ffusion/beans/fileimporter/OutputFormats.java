package com.ffusion.beans.fileimporter;

import java.util.ArrayList;
import java.util.Iterator;

public class OutputFormats
  extends ArrayList
{
  public OutputFormat findByName(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      OutputFormat localOutputFormat = (OutputFormat)localIterator.next();
      if (localOutputFormat.getName().equalsIgnoreCase(paramString)) {
        return localOutputFormat;
      }
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.OutputFormats
 * JD-Core Version:    0.7.0.1
 */